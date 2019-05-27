package repository;

import model.Ingredient;
import model.User;

import java.util.List;

public interface UserRepository {
    public User login(String username, String password);


    public void addIngredient(String username, Ingredient ingredient);

    public void deleteIngredient(String username, Ingredient ingredient);

    public List<Ingredient> availableIngredients(User user);
}
