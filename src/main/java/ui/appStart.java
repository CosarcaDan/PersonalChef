package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.RecipePreferences;
import model.User;
import repository.RecipeDatabaseRepository;
import repository.RecipeRepository;
import repository.UserDatabaseRepository;
import repository.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class appStart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
//        Parent root = FXMLLoader.load(appStart.class.getResource("login.fxml"));
//        primaryStage.setTitle("Personal Chef Login");
//        primaryStage.setScene(new Scene(root, 350, 450));
//        primaryStage.show();

        Properties properties = new Properties();
        try {
            properties.load(new FileReader("bd.config"));
            System.out.println("Database properties set. ");
            properties.list(System.out);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        RecipeRepository recipeRepository = new RecipeDatabaseRepository(properties);
        UserRepository userRepository = new UserDatabaseRepository(properties);

        User user = userRepository.login("Ana08", "def8");

        if( user != null)
        {
            //System.out.println(user.getAvailableIngredients());

            for(RecipePreferences r : user.getRecipePreferences()){
                System.out.println(r.getRecipe());
            }
        }
        else System.out.println("Nope");



    }
}
