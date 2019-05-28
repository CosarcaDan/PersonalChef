package repository;

import model.*;
import model.Process;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RecipeDatabaseRepository implements RecipeRepository {

    private JDBCUtils jdbcUtils;
    private List<Recipe> recipeList;


    public RecipeDatabaseRepository(Properties properties){
        jdbcUtils = new JDBCUtils(properties);

        recipeList = findAllRecipe();
    }

    @Override
    public void addRecipe() {

    }

    @Override
    public void deleteRecipe() {

    }

    @Override
    public List<Recipe> findAllRecipe() {

        List<Recipe> recipeList = new ArrayList<>();

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM Recipes");

            ResultSet selectResult = selectStatement.executeQuery();

            while(selectResult.next()){

                String name = selectResult.getString("Name");
                String cusine = selectResult.getString("Cuisine");
                Type type = Type.valueOf(selectResult.getString("Type"));
                Process process = Process.valueOf(selectResult.getString("Process"));
                List<NeededTools> neededTools = getNeededTools(name);
                List<Ingredient> highImportanceIngredients = getIngredientsByImportance(name, "HIGH");
                List<Ingredient> mediumImportanceIngredients = getIngredientsByImportance(name, "MEDIUM");
                List<Ingredient> lowImportanceIngredients = getIngredientsByImportance(name, "LOW");

                Recipe recipe = new Recipe(name, cusine, type, process, neededTools, highImportanceIngredients, mediumImportanceIngredients, lowImportanceIngredients);
                recipeList.add(recipe);

            }

            connection.close();

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return recipeList;
    }

    private List<NeededTools> getNeededTools(String recipeName){

        List<NeededTools> neededToolsList = new ArrayList<>();

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement selectStatement = connection.prepareStatement("SELECT ToolID FROM RecipeTools WHERE RecipeName=?");
            selectStatement.setString(1, recipeName);

            ResultSet selectResult = selectStatement.executeQuery();

            while(selectResult.next()){

                PreparedStatement selectToolStatement = connection.prepareStatement("SELECT Name FROM Tools WHERE ToolID=?");
                String toolId = selectResult.getString("ToolID");
                selectToolStatement.setString(1, toolId);

                ResultSet selectToolResult = selectToolStatement.executeQuery();

                if(selectToolResult.next()){
                    NeededTools tool = NeededTools.valueOf(selectToolResult.getString("Name"));

                    neededToolsList.add(tool);
                }

            }

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return neededToolsList;
    }

    private List<Ingredient> getIngredientsByImportance(String recipeName, String importance){

        List<Ingredient> ingredientList = new ArrayList<>();

        Connection connection = jdbcUtils.getConnection();

        try{

            PreparedStatement selectStatement = connection.prepareStatement("SELECT IngredientName FROM IngredientsRecipe WHERE RecipeName=? AND Importance=?");
            selectStatement.setString(1,recipeName);
            selectStatement.setString(2,importance);

            ResultSet selectResult = selectStatement.executeQuery();

            while(selectResult.next()){

                Ingredient ingredient = new Ingredient(selectResult.getString("IngredientName"));

                ingredientList.add(ingredient);
            }

        }catch (SQLException e){

            System.out.println(e.getMessage());
        }

        return ingredientList;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

}
