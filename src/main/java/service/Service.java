package service;

import model.*;
import model.Process;
import repository.RecipeRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

public class Service {
    private User currentUser;

    public Service(UserRepository userRepository, RecipeRepository recipeRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
    }

    private UserRepository userRepository;
    private RecipeRepository recipeRepository;
    int incopatibleMatch = -20;
    int okMatch = 5;
    int goodMatch = 7;
    int gretMatch = 10;
    int perfectMatch = 12;

    int preferedProcess = 2;
    int preferedType = 10;
    int preferedToolsNotUsed = -20;
    int preferedCousine = 2;
    int preferedIngredient = 7;

    int repededEatenPentalty = -2;

    int recipeThreshold = 5;

    int highIngredientWeight = 80;
    int mediumIngredientWeight = 15; //sum of the 3 must be 100
    int lowIngredientWeight = 5;


    int highIngredientMin = 60;
    int mediumHighIngredientMin = 75; //high+medium must be higher

    double alternativeWeight = (double)3 / 4;

    int minMatchThreshold = 80;
    int minOkMatch = 90;
    int minGoodMatch = 95;
    int minGreatMath = 100;
    int minPerfectMatch = 100;

    public User login(String username, String password) {
        User user = userRepository.login(username, password);
        if (user != null) {
            this.currentUser = user;
        }
        return user;
    }

    public void addIngredient(String ingredientName, List<Ingredient> alternative) {
        Ingredient ingredient = new Ingredient(ingredientName, alternative);
        userRepository.addIngredient(this.currentUser.getUsername(), ingredient);
    }

    public void deleteIngredient(String ingredientName) {
        Ingredient ingredient = new Ingredient(ingredientName);
        userRepository.deleteIngredient(this.currentUser.getUsername(), ingredient);
    }

    private Double ingredientsMatch(Recipe recipe) {
        int countHighIngredients = 0;
        int countMediumIngredients = 0;
        int countLowIngredients = 0;
        List<Ingredient> availableIngredients = userRepository.availableIngredients(currentUser);
        double weightHighIngrediets = (double)highIngredientWeight / recipe.getHighImportanceIngredients().size();
        double weightMediumIngrediets = (double)mediumIngredientWeight / recipe.getMediumImportanceIngredients().size();
        double weightLowIngrediets = (double)lowIngredientWeight / recipe.getLowImportanceIngredients().size();
        double weightSumHighIngredients = 0;
        double weightSumMediumIngredients = 0;
        double weightSumLowIngredients = 0;
        //iterates trough HIGH importance Ingredients of the recipe and compares with available ingredients of the user
        for (Ingredient highIngredient : recipe.getHighImportanceIngredients()) {
            if (availableIngredients.contains(highIngredient)) {
                countHighIngredients += 1;
                weightSumHighIngredients += weightHighIngrediets;
            } else if (!highIngredient.getAlternatives().isEmpty()) {
                for (Ingredient alternative : highIngredient.getAlternatives()) {
                    if (availableIngredients.contains(alternative)) {
                        countHighIngredients += 1;
                        weightSumHighIngredients += weightHighIngrediets * (alternativeWeight); //alternative dont add as much to the value as the original
                        break;
                    }
                }
            }
        }
        if (weightSumHighIngredients >= highIngredientMin) {
            for (Ingredient mediumIngredient : recipe.getMediumImportanceIngredients()) {
                if (availableIngredients.contains(mediumIngredient)) {
                    countMediumIngredients += 1;
                    weightSumMediumIngredients += weightMediumIngrediets;
                } else if (!mediumIngredient.getAlternatives().isEmpty()) {
                    for (Ingredient alternative : mediumIngredient.getAlternatives()) {
                        if (availableIngredients.contains(alternative)) {
                            weightSumMediumIngredients += weightMediumIngrediets * (alternativeWeight);
                            countMediumIngredients += 1;
                            break;
                        }
                    }
                }
            }
            if (weightSumHighIngredients + weightSumMediumIngredients >= mediumHighIngredientMin) {
                for (Ingredient lowIngredient : recipe.getLowImportanceIngredients()) {
                    if (availableIngredients.contains(lowIngredient)) {
                        countLowIngredients += 1;
                        weightSumLowIngredients += weightLowIngrediets;
                    } else if (!lowIngredient.getAlternatives().isEmpty()) {
                        for (Ingredient alternative : lowIngredient.getAlternatives()) {
                            if (availableIngredients.contains(alternative)) {
                                countLowIngredients += 1;
                                weightSumLowIngredients += weightLowIngrediets * (alternativeWeight);
                            }
                        }
                    }
                }
                return weightSumHighIngredients + weightSumMediumIngredients + weightSumLowIngredients;
            }

        }

        return 0.0;
    }

    //represents action potential of the first dendrite(aka first level)
    private Integer ingredientMatchEval(Double im) {
        if (im < minMatchThreshold) {
            return incopatibleMatch;
        }
        if (im < minOkMatch) {
            return okMatch;
        }
        if (im < minGoodMatch) {
            return goodMatch;
        }
        if (im < minGreatMath) {
            return gretMatch;
        }
        if (im == minPerfectMatch) {
            return perfectMatch;
        }
        return 0;
    }

    private Integer evalPreferences(Recipe recipe, Process pProcess, Type pType, NeededTools pToolsNOTtoUser, String pCousine, Ingredient pIngredient) {
        Integer score = 0;
        if (pProcess != null) {
            if (recipe.getProcess().equals(pProcess))
                score += preferedProcess;
        }
        if (pType != null) {
            if (recipe.getType().equals(pType))
                score += preferedType;
        }
        if (pToolsNOTtoUser != null) {
            if (recipe.getNeededTools().contains(pToolsNOTtoUser)) {
                score += preferedToolsNotUsed;
            }
        }
        if (pCousine != null) {
            if (recipe.getCousine().equals(pCousine)) {
                score += preferedCousine;
            }
        }
        if (pIngredient != null) {
            if (recipe.getHighImportanceIngredients().contains(pIngredient))
                score += preferedIngredient;
            else if (recipe.getMediumImportanceIngredients().contains(pIngredient))
                score += preferedIngredient;
            else if (recipe.getLowImportanceIngredients().contains(pIngredient))
                score += preferedIngredient;
        }
        return score;
    }

    private Integer evalRecipeHistory(Recipe recipe){
        Integer score=0;
        for(RecipePreferences userRecipePreferences: currentUser.getRecipePreferences()){
            if(userRecipePreferences.getRecipe().equals(recipe)){
                if(userRecipePreferences.getNumberOfTimeEatenInLast2Weeks() > 4){
                    score += (userRecipePreferences.getNumberOfTimeEatenInLast2Weeks() - 4) * (repededEatenPentalty);
                }
                score += userRecipePreferences.getPreferenceScore();
            }
        }
        return  score;
    }


    public List<Recipe> recipesMatch(Process pProcess, Type pType, NeededTools pToolsNOTtoUser, String pCousine, Ingredient pIngredient) {
        List<Recipe> suggestedRecipe = new ArrayList<>();
        List<RecipeWithScore> suggestedRecipewithScore = new ArrayList<>();

        Double ingredientsMatch;
        Integer ingredientsScore;
        Integer preferencesScore;
        Integer recipeHistoryScore;
        List<Recipe> recipes = recipeRepository.findAllRecipe();
        for (Recipe recipe : recipes) {
            ingredientsMatch = ingredientsMatch(recipe);
            ingredientsScore = ingredientMatchEval(ingredientsMatch);
            preferencesScore = evalPreferences(recipe, pProcess, pType, pToolsNOTtoUser, pCousine, pIngredient);
            recipeHistoryScore = evalRecipeHistory(recipe);
            if (ingredientsScore + preferencesScore + recipeHistoryScore >= recipeThreshold) {
                //some Trashhold for action fire
                suggestedRecipewithScore.add(new RecipeWithScore(recipe,(ingredientsScore + preferencesScore + recipeHistoryScore)));
            }
        }
        suggestedRecipewithScore.sort((r1,r2)->r2.getScore().compareTo(r1.getScore()));
        for(RecipeWithScore rWS:suggestedRecipewithScore){
            System.out.println(rWS.getRecipe().getName() + " - " + rWS.getScore());
            suggestedRecipe.add(rWS.getRecipe());
        }
        return suggestedRecipe;
    }


    public void enforceRecipe(Recipe recipe){
        userRepository.updateRecipePreferenceScore(currentUser.getUsername(), recipe.getName(), 1);
    }


    class RecipeWithScore{
        Recipe recipe;
        Integer score;

        public RecipeWithScore(Recipe recipe, Integer score) {
            this.recipe = recipe;
            this.score = score;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public Recipe getRecipe() {
            return recipe;
        }

        public void setRecipe(Recipe recipe) {
            this.recipe = recipe;
        }
    }

}

