package crabster.rudakov.carspresentation.recyclerViewHelpers;

import java.io.Serializable;

public class Car implements Serializable {

    private int imageId;
    private String model;
    private int price;
    private int power;

    public Car(int imageId, String model, int price, int power) {
        this.imageId = imageId;
        this.model = model;
        this.price = price;
        this.power = power;
    }

    public int getImageId() {
        return imageId;
    }

    public String getModel() {
        return model;
    }

    public int getPrice() {
        return price;
    }

    public int getPower() {
        return power;
    }

}
