package com.example.RecipeList;

import static org.junit.jupiter.api.Assertions.*;

class RecipeTest {

    Recipe testRec = new Recipe(0, 45, "test recipe", "test ingredients", "test instructions",
            "test_image.jpg", 0, 30, "3-4");

    @org.junit.jupiter.api.Test
    void getCookingHour() {
        assertEquals(0,  testRec.getCookingHour());
    }

    @org.junit.jupiter.api.Test
    void setCookingHour() {
        testRec.setCookingHour(1);
        assertEquals(1, testRec.getCookingHour());
    }

    @org.junit.jupiter.api.Test
    void getCookingMin() {
        assertEquals(45, testRec.getCookingMin());
    }

    @org.junit.jupiter.api.Test
    void setCookingMin() {
        testRec.setCookingMin(10);
        assertEquals(10, testRec.getCookingMin());
    }

    @org.junit.jupiter.api.Test
    void getIngredients() {
        assertEquals("test ingredients", testRec.getIngredients());
    }

    @org.junit.jupiter.api.Test
    void setIngredients() {
        testRec.setIngredients("new ingredients");
        assertEquals("new ingredients", testRec.getIngredients());
    }

    @org.junit.jupiter.api.Test
    void getInstructions() {
        assertEquals("test instructions", testRec.getInstructions());
    }

    @org.junit.jupiter.api.Test
    void setInstructions() {
        testRec.setInstructions("new instructions");
        assertEquals("new instructions", testRec.getInstructions());
    }

    @org.junit.jupiter.api.Test
    void getPreparationHour() {
        assertEquals(0, testRec.getPreparationHour());
    }

    @org.junit.jupiter.api.Test
    void setPreparationHour() {
        testRec.setPreparationHour(1);
        assertEquals(1, testRec.getPreparationHour());
    }

    @org.junit.jupiter.api.Test
    void getPreparationMin() {
        assertEquals(30, testRec.getPreparationMin());
    }

    @org.junit.jupiter.api.Test
    void setPreparationMin() {
        testRec.setPreparationMin(15);
        assertEquals(15, testRec.getPreparationMin());
    }

    @org.junit.jupiter.api.Test
    void getServingNumber() {
        assertEquals("3-4", testRec.getServingNumber());
    }

    @org.junit.jupiter.api.Test
    void setServingNumber() {
        testRec.setServingNumber("4-5");
        assertEquals("4-5", testRec.getServingNumber());
    }

    @org.junit.jupiter.api.Test
    void getRecipeName() {
        assertEquals("test recipe", testRec.getFoodName());
    }

    @org.junit.jupiter.api.Test
    void setRecipeName() {
        testRec.setFoodName("new recipe name");
        assertEquals("new recipe name", testRec.getFoodName());
    }

    @org.junit.jupiter.api.Test
    void getItemImage() {
        assertEquals("test_image.jpg", testRec.getItemImage());
    }

    @org.junit.jupiter.api.Test
    void setItemImage() {
        testRec.setItemImage("new_image.jpg");
        assertEquals("new_image.jpg", testRec.getItemImage());
    }
}