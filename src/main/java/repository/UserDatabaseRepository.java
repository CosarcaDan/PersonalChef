package repository;

import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class UserDatabaseRepository implements UserRepository {

    private JDBCUtils jdbcUtils;
    private RecipeRepository recipeRepository;


    public UserDatabaseRepository(Properties properties){

        jdbcUtils = new JDBCUtils(properties);
        recipeRepository = new RecipeDatabaseRepository(properties);
    }

    @Override
    public User login(String username, String password) {

        Connection connection =jdbcUtils.getConnection();

        try{

            PreparedStatement selectStatement = connection.prepareStatement("SELECT Password FROM Users WHERE Username=?");
            selectStatement.setString(1, username);

            ResultSet selectResult = selectStatement.executeQuery();

            if(selectResult.next() && selectResult.getString("Password").equals(password)){

                List<Ingredient> availableIngredients = getAvailableIngredients(username);
                List<RecipePreferences> recipePreferences = getRecipePreferences(username);
                return new User(username, password, availableIngredients, recipePreferences);
            }

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return null;
    }


    private List<Ingredient> getAvailableIngredients(String username){

        List<Ingredient> ingredientList = new ArrayList<>();

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement selectStatement = connection.prepareStatement("SELECT Name FROM IngredientsUser WHERE IngredientsUser.Username=?");
            selectStatement.setString(1, username);

            ResultSet selectResult = selectStatement.executeQuery();

            while(selectResult.next()){
                ingredientList.add(new Ingredient(selectResult.getString("Name")));
            }

            connection.close();

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return ingredientList;
    }

    private List<RecipePreferences> getRecipePreferences(String username){

        List<RecipePreferences> recipePreferencesList = new ArrayList<>();

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM RecipePreferences WHERE Username=?");
            selectStatement.setString(1, username);

            ResultSet selectResult = selectStatement.executeQuery();

            while(selectResult.next()){

                Recipe recipe = getRecipe(selectResult.getString("RecipeName"));
                Integer prefScore = selectResult.getInt("PreferenceScore");
                //Date lastTimeEaten = selectResult.getDate("LastTimeEaten");
                Date lastTimeEaten = new SimpleDateFormat("yyyy-MM-dd").parse(selectResult.getString("LastTimeEaten"));
                Integer totalTimesEaten = selectResult.getInt("TotalTimesEaten");
                Integer numberOfTimeEatenInLast2Weeks = selectResult.getInt("NrOfTimeEatenInLast2Weeks");

                recipePreferencesList.add(new RecipePreferences(recipe, prefScore, lastTimeEaten, totalTimesEaten, numberOfTimeEatenInLast2Weeks));
            }
            connection.close();

        }catch (SQLException e){

            System.out.println(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return recipePreferencesList;
    }

    private Recipe getRecipe(String recipeName){

        for(Recipe r : this.recipeRepository.getRecipeList()){
            if(r.getName().equals(recipeName))
                return r;
        }

        return null;
    }

    @Override
    public void addIngredient(String username, Ingredient ingredient) {

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO IngredientsUser(Name, User) VALUES(?,?)");
            insertStatement.setString(1, ingredient.getName());
            insertStatement.setString(2, username);

            int insertResult = insertStatement.executeUpdate();

            connection.close();

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteIngredient(String username, Ingredient ingredient) {

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM IngredientsUser WHERE IngredientsUser.Name=? AND IngredientsUser.Username=?");
            deleteStatement.setString(1, ingredient.getName());
            deleteStatement.setString(2, username);

            int deleteResult = deleteStatement.executeUpdate();

            connection.close();

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Ingredient> availableIngredients(User user) {
        return this.getAvailableIngredients(user.getUsername());
    }

    @Override
    public void updateRecipePreferenceScore(String username, String recipe, Integer score){

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement selectScoreStatement = connection.prepareStatement("SELECT PreferenceScore FROM RecipePreferences WHERE Username=? AND RecipeName=?");
            selectScoreStatement.setString(1, username);
            selectScoreStatement.setString(2, recipe);

            ResultSet selectScoreResult = selectScoreStatement.executeQuery();

            if(selectScoreResult.next()){

                Integer preferenceScore = selectScoreResult.getInt("PreferenceScore");
                Integer totalTimesEaten = selectScoreResult.getInt("TotalTimesEaten");
                Integer nrOfTimeEatenInLast2Weeks = selectScoreResult.getInt("NrOfTimeEatenInLast2Weeks");
                if(score == 1) {
                    totalTimesEaten++;
                    nrOfTimeEatenInLast2Weeks++;
                    if (totalTimesEaten < 5 && totalTimesEaten > 0)
                        preferenceScore = 1;
                    else if (totalTimesEaten < 15)
                        preferenceScore = 3;
                    else if(totalTimesEaten > 15)
                        preferenceScore = 5;

                }else{
                    totalTimesEaten--;
                    nrOfTimeEatenInLast2Weeks--;
                    if (totalTimesEaten > -5 && totalTimesEaten < 0)
                        preferenceScore = -1;
                    else if (totalTimesEaten > -15)
                        preferenceScore = -3;
                    else if(totalTimesEaten < -15)
                        preferenceScore = -5;
                }
                PreparedStatement updateScoreStatement = connection.prepareStatement("UPDATE RecipePreferences SET PreferenceScore=?,TotalTimesEaten=?,NrOfTimeEatenInLast2Weeks=? WHERE Username=? AND RecipeName=?");
                updateScoreStatement.setInt(1, preferenceScore);
                updateScoreStatement.setInt(2, totalTimesEaten);
                updateScoreStatement.setInt(3, nrOfTimeEatenInLast2Weeks);
                updateScoreStatement.setString(4, username);
                updateScoreStatement.setString(5, recipe);

                int updateResult = updateScoreStatement.executeUpdate();
            }

            connection.close();

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }
    }
}
