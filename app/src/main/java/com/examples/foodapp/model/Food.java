package com.examples.foodapp.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Food implements Serializable {
    @Exclude private String id;
    private String isFavorite;
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

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
