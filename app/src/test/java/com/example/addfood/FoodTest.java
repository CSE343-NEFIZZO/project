package com.example.addfood;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodTest {

    Food testFood = new Food("testname","4-6",5,40,
            2,30,"ingredientsForTest","tomato pepper","test.jpg");
    @Test
    void getFoodName() {
        assertEquals("testname",testFood.getFoodName());
    }

    @Test
    void setFoodName() {
        testFood.setFoodName("newName");
        assertEquals("newName",testFood.getFoodName());
    }

    @Test
    void getServingNumber() {
        assertEquals("4-6",testFood.getServingNumber());
    }

    @Test
    void setServingNumber() {
        testFood.setServingNumber("5-7");
        assertEquals("5-7",testFood.getServingNumber());
    }

    @Test
    void getPreparationHour() {
        assertEquals(5,testFood.getPreparationHour());
    }

    @Test
    void setPreparationHour() {
        testFood.setPreparationHour(2);
        assertEquals(2,testFood.getPreparationHour());
    }

    @Test
    void getPreparationMin() {
        assertEquals(40,testFood.getPreparationMin());
    }

    @Test
    void setPreparationMin() {
        testFood.setPreparationMin(35);
        assertEquals(35,testFood.getPreparationMin());
    }

    @Test
    void getCookingHour() {
        assertEquals(2,testFood.getCookingHour());
    }

    @Test
    void setCookingHour() {
        testFood.setCookingHour(1);
        assertEquals(1,testFood.getCookingHour());
    }

    @Test
    void getCookingMin() {
        assertEquals(30,testFood.getCookingMin());
    }

    @Test
    void setCookingMin() {
        testFood.setCookingMin(15);
        assertEquals(15,testFood.getCookingMin());
    }

    @Test
    void getIngredients() {
        assertEquals("ingredientsForTest",testFood.getIngredients());
    }

    @Test
    void setIngredients() {
        testFood.setIngredients("newIngredientsForTest");
        assertEquals("newIngredientsForTest",testFood.getIngredients());
    }

    @Test
    void getInstructions() {
        assertEquals("tomato pepper",testFood.getInstructions());
    }

    @Test
    void setInstructions() {
        testFood.setInstructions("cheese olive tomato");
        assertEquals("cheese olive tomato",testFood.getInstructions());
    }

    @Test
    void getItemImage() {
        assertEquals("test.jpg",testFood.getItemImage());
    }

    @Test
    void setItemImage() {
        testFood.setItemImage("newPicture.jpg");
        assertEquals("newPicture.jpg",testFood.getItemImage());
    }
}