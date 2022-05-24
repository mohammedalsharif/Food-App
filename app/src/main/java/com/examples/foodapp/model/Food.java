package com.examples.foodapp.model;

import java.io.Serializable;

public class Food implements Serializable {

    private String imageUrl;
    private String foodName;
    private String foodPrice;

    public Food() {
    }

    public Food(String imageUrl, String foodName, String foodPrice) {
        this.imageUrl = imageUrl;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
