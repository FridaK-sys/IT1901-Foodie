package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 * Lists of ingredients in a recipe.
 *///
@Entity
public class Recipe {

  private @Id @GeneratedValue Long id;
  private String name;
  private String description;
  private @Column @ElementCollection(targetClass = Ingredient.class) List<Ingredient> ingredients = new ArrayList<>();
  private int portions;
  private boolean fav = false;
  private String label = "";
  static final List<String> allowedLabels = Arrays.asList("Frokost", "Lunsj", "Middag");

  public Recipe(String name, String description, int portions, List<Ingredient> ingredients) {
    setName(name);
    setPortions(portions);
    this.description = description;
    this.ingredients = new ArrayList<>(ingredients);
  }

  public Recipe(String name, int portions) {
    setName(name);
    setPortions(portions);
    this.description = "nothing here...";
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    if (name.isBlank()) {
      throw new IllegalArgumentException("Invalid name");
    }
    if (!name.matches("^[ÆØÅæøåa-zA-Z0-9\\s]{1,20}$")) {
      throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPortions() {
    return this.portions;
  }

  public void setPortions(int portions) {
    if (portions <= 0) {
      throw new IllegalArgumentException("Portions must be more than 0");
    }
    ingredients.stream().forEach(i -> i.setAmount((double) i.getAmount() / this.portions * portions));
    this.portions = portions;
  }

  public List<Ingredient> getIngredients() {
    return new ArrayList<>(ingredients);
  }

  public void addIngredient(Ingredient ingredient) {
    if (!ingredients.contains(ingredient)) {
      ingredients.add(ingredient);
    } else {
      throw new IllegalArgumentException("Recipe contains this ingredient");
    }
  }

  public void removeIngredient(Ingredient ingredient) {
    if (!ingredients.contains(ingredient)) {
      throw new IllegalArgumentException(this.name + "does not contain this ingredient");
    }
    ingredients.remove(ingredient);
  }

  public void removeIngredient(String name) {
    for (Ingredient i : ingredients) {
      if (i.getName().equals(name)) {
        ingredients.remove(i);
      }
    }
  }

  public void setFav() {
    this.fav = true;
  }

  public void removeFav() {
    this.fav = false;
  }

  public boolean getFav() {
    return this.fav;
  }

  public void setLabel(String label) {
    if (allowedLabels.contains(label)) {
      this.label = label;
    } else {
      throw new IllegalArgumentException("Label has to be either Frokost, Lunsj or Middag");
    }
  }

  public void removeLabel() {
    this.label = "";
  }

  public String getLabel() {
    return this.label;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    ingredients.stream().forEach(i -> sb.append(i.getName()));
    return getName() + ": " + sb.toString();
  }
}
