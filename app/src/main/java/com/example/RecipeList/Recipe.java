package com.example.RecipeList;

public class Recipe {
    private int cookingHour;
    private int cookingMin;
    private String recipeName;
    private String ingredients;
    private String instructions;
    private String itemImage;
    private int preparationHour;
    private int preparationMin;
    private String servingNumber;

    public Recipe(int cookingHour, int cookingMin, String recipeName,
                  String ingredients, String instructions, String itemImage,
                  int preparationHour, int preparationMin, String servingNumber) {
        this.cookingHour = cookingHour;
        this.cookingMin = cookingMin;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.itemImage = itemImage;
        this.preparationHour = preparationHour;
        this.preparationMin = preparationMin;
        this.servingNumber = servingNumber;
    }

    public Recipe() {

    }

    public int getCookingHour() {
        return cookingHour;
    }

    public void setCookingHour(int cookingHour) {
        this.cookingHour = cookingHour;
    }

    public int getCookingMin() {
        return cookingMin;
    }

    public void setCookingMin(int cookingMin) {
        this.cookingMin = cookingMin;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getPreparationHour() {
        return preparationHour;
    }

    public void setPreparationHour(int preparationHour) {
        this.preparationHour = preparationHour;
    }

    public int getPreparationMin() {
        return preparationMin;
    }

    public void setPreparationMin(int preparationMin) {
        this.preparationMin = preparationMin;
    }

    public String getServingNumber() {
        return servingNumber;
    }

    public void setServingNumber(String servingNumber) {
        this.servingNumber = servingNumber;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "cookingHour=" + cookingHour +
                ", cookingMin=" + cookingMin +
                ", foodName='" + recipeName + '\'' +
                ", ingredients='" + ingredients + '\'' +
                ", instructions='" + instructions + '\'' +
                ", itemImage='" + itemImage + '\'' +
                ", preparationHour=" + preparationHour +
                ", preparationMin=" + preparationMin +
                ", servingNumber='" + servingNumber + '\'' +
                '}';
    }
}
