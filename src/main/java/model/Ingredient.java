package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Ingredient {
    private String name;
    private List<Ingredient> alternatives;

    //String type;
    //Integer score;
    public Ingredient(String name, List<Ingredient> alternatives) {
        this.name = name;
        this.alternatives = alternatives;
    }

    public Ingredient() {
    }

    public Ingredient(String name) {
        this.name = name;
        this.alternatives = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getAlternatives() {
        return alternatives;
    }

    public void setAlternatives(List<Ingredient> alternatives) {
        this.alternatives = alternatives;
    }



    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", alternatives=" + alternatives +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
