package repository;

import model.Ingredient;
import model.User;

import java.util.List;

public interface UserRepository {

    User login(String username, String password);
    void addIngredient(String username, Ingredient ingredient);
    void deleteIngredient(String username, Ingredient ingredient);
    List<Ingredient> availableIngredients(User user);
    void updateRecipePreferenceScore(String username, String recipe, Integer score);
}
