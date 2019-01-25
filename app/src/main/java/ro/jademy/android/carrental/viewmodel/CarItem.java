package ro.jademy.android.carrental.viewmodel;

import java.util.Objects;

public class CarItem {

    private String make;
    private String model;
    private int imageResourceId;

    public CarItem(String make, String model, int imageResourceId) {
        this.make = make;
        this.model = model;
        this.imageResourceId = imageResourceId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarItem carItem = (CarItem) o;
        return imageResourceId == carItem.imageResourceId &&
                Objects.equals(make, carItem.make) &&
                Objects.equals(model, carItem.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(make, model, imageResourceId);
    }
}
