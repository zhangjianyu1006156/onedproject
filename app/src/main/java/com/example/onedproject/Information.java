package com.example.onedproject;

public class Information {
    private String food;
    private String weight;

    public Information(){
    }

    public Information(String food,String weight){
        this.food = food;
        this.weight = weight;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }


}
