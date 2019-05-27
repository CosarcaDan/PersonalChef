package model;

import java.util.List;

public class User {
    String username;
    String password;

    List<Ingredient> availableIngredients;
    List<RecipePreferences> recipePreferences;


    public User(String username, String password, List<Ingredient> availableIngredients, List<RecipePreferences> recipePreferences) {
        this.username = username;
        this.password = password;
        this.availableIngredients = availableIngredients;
        this.recipePreferences = recipePreferences;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Ingredient> getAvailableIngredients() {
        return availableIngredients;
    }

    public void setAvailableIngredients(List<Ingredient> availableIngredients) {
        this.availableIngredients = availableIngredients;
    }

    public List<RecipePreferences> getRecipePreferences() {
        return recipePreferences;
    }

    public void setRecipePreferences(List<RecipePreferences> recipePreferences) {
        this.recipePreferences = recipePreferences;
    }
}
