package com.example.nefizzo;

public class Food {
    private String foodName;
    private String servingNumber;
    private int preparationHour,preparationMin;
    private int cookingHour,cookingMin;
    private String ingredients;
    private String instructions;
    private String itemImage;

    public Food(String foodName, String servingNumber, int preparationHour, int preparationMin, int cookingHour, int cookingMin, String ingredients, String instructions, String itemImage) {
        this.foodName = foodName;
        this.servingNumber = servingNumber;
        this.preparationHour = preparationHour;
        this.preparationMin = preparationMin;
        this.cookingHour = cookingHour;
        this.cookingMin = cookingMin;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.itemImage = itemImage;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getServingNumber() {
        return servingNumber;
    }

    public void setServingNumber(String servingNumber) {
        this.servingNumber = servingNumber;
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

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
