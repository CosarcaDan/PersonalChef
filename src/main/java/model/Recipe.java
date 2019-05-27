package model;

import java.util.List;
import java.util.Objects;

public class Recipe {
    private String name;
    //int level;
    //String url;
    private String cousine;
    private Type type;
    private Process process;
    private List<NeededTools> neededTools;
    private List<Ingredient> highImportanceIngredients;
    private List<Ingredient> mediumImportanceIngredients;
    private List<Ingredient> lowImportanceIngredients;

    public Recipe(String name, String cousine, Type type, Process process, List<NeededTools> neededTools, List<Ingredient> highImportanceIngredients, List<Ingredient> mediumImportanceIngredients, List<Ingredient> lowImportanceIngredients) {
        this.name = name;
        this.cousine = cousine;
        this.type = type;
        this.process = process;
        this.neededTools = neededTools;
        this.highImportanceIngredients = highImportanceIngredients;
        this.mediumImportanceIngredients = mediumImportanceIngredients;
        this.lowImportanceIngredients = lowImportanceIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCousine() {
        return cousine;
    }

    public void setCousine(String cousine) {
        this.cousine = cousine;
    }

    public List<Ingredient> getHighImportanceIngredients() {
        return highImportanceIngredients;
    }

    public void setHighImportanceIngredients(List<Ingredient> highImportanceIngredients) {
        this.highImportanceIngredients = highImportanceIngredients;
    }

    public List<Ingredient> getMediumImportanceIngredients() {
        return mediumImportanceIngredients;
    }

    public void setMediumImportanceIngredients(List<Ingredient> mediumImportanceIngredients) {
        this.mediumImportanceIngredients = mediumImportanceIngredients;
    }

    public List<Ingredient> getLowImportanceIngredients() {
        return lowImportanceIngredients;
    }

    public void setLowImportanceIngredients(List<Ingredient> lowImportanceIngredients) {
        this.lowImportanceIngredients = lowImportanceIngredients;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public List<NeededTools> getNeededTools() {
        return neededTools;
    }

    public void setNeededTools(List<NeededTools> neededTools) {
        this.neededTools = neededTools;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "name='" + name + '\'' +
                ", cousine='" + cousine + '\'' +
                ", type=" + type +
                ", process=" + process +
                ", neededTools=" + neededTools +
                ", highImportanceIngredients=" + highImportanceIngredients +
                ", mediumImportanceIngredients=" + mediumImportanceIngredients +
                ", lowImportanceIngredients=" + lowImportanceIngredients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(name, recipe.name) &&
                Objects.equals(cousine, recipe.cousine) &&
                type == recipe.type &&
                process == recipe.process &&
                Objects.equals(neededTools, recipe.neededTools) &&
                Objects.equals(highImportanceIngredients, recipe.highImportanceIngredients) &&
                Objects.equals(mediumImportanceIngredients, recipe.mediumImportanceIngredients) &&
                Objects.equals(lowImportanceIngredients, recipe.lowImportanceIngredients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cousine, type, process, neededTools, highImportanceIngredients, mediumImportanceIngredients, lowImportanceIngredients);
    }
}




