package ro.jademy.android.carrental.model;

import java.util.Objects;

public class Car {

    private int id;
    private String model;
    private String make;
    private String image;

    public Car(String model, String make, String image) {
        this.model = model;
        this.make = make;
        this.image = image;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(model, car.model) &&
                Objects.equals(make, car.make) &&
                Objects.equals(image, car.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, make, image);
    }
}
