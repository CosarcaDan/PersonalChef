package model;

import java.util.Date;

public class RecipePreferences {
    private Recipe recipe;
    private Integer preferenceScore;
    private Date lastTimeEaten;
    private Integer numberOfTimeEatenInLast2Weeks;
    private Integer totalTimesEaten;

    public Integer getTotalTimesEaten() {
        return totalTimesEaten;
    }

    public void setTotalTimesEaten(Integer totalTimesEaten) {
        this.totalTimesEaten = totalTimesEaten;
    }

    public RecipePreferences(Recipe recipe, Integer preferenceScore, Date lastTimeEaten, Integer numberOfTimeEatenInLast2Weeks, Integer totalTimesEaten) {
        this.recipe = recipe;
        this.preferenceScore = preferenceScore;
        this.lastTimeEaten = lastTimeEaten;
        this.numberOfTimeEatenInLast2Weeks = numberOfTimeEatenInLast2Weeks;
        this.totalTimesEaten = totalTimesEaten;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Integer getPreferenceScore() {
        return preferenceScore;
    }

    public void setPreferenceScore(Integer preferenceScore) {
        this.preferenceScore = preferenceScore;
    }


    public Date getLastTimeEaten() {
        return lastTimeEaten;
    }

    public void setLastTimeEaten(Date lastTimeEaten) {
        this.lastTimeEaten = lastTimeEaten;
    }

    public Integer getNumberOfTimeEatenInLast2Weeks() {
        return numberOfTimeEatenInLast2Weeks;
    }

    public void setNumberOfTimeEatenInLast2Weeks(Integer numberOfTimeEatenInLast2Weeks) {
        this.numberOfTimeEatenInLast2Weeks = numberOfTimeEatenInLast2Weeks;
    }


}
