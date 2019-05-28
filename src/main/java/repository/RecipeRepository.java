package repository;

import model.Recipe;

import java.util.List;

public interface RecipeRepository {

    void addRecipe();
    void deleteRecipe();
    List<Recipe> findAllRecipe();
    List<Recipe> getRecipeList();
}
