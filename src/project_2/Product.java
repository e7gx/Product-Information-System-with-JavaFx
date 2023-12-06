package Project_2;

import java.time.LocalDate;
import javafx.beans.property.*;

public class Product {

    private final Integer id;
    private final String type;
    private final String model;
    private final Float price;
    private final Integer count;
    private final LocalDate deliveryDate;

    public Product(Integer id, String type, String model, Float price, Integer count, LocalDate deliveryDate) {
        this.id = id;
        this.type = type;
        this.model = model;
        this.price = price;
        this.count = count;
        this.deliveryDate = deliveryDate;
    }

    // Getters for the private fields

    /**
     * Returns the ID of the product.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns the type of the product.
     * @return 
     */
    public String getType() {
        return type;
    }

    /**
     * Returns the model of the product.
     * @return 
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the price of the product.
     * @return 
     */
    public Float getPrice() {
        return price;
    }

    /**
     * Returns the count of the product.
     * @return 
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Returns the delivery date of the product.
     * @return 
     */
    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    // Property methods for JavaFX

    /**
     * Returns the ID of the product as an IntegerProperty.
     * @return 
     */
    public IntegerProperty idProperty() {
        return new SimpleIntegerProperty(id);
    }

    /**
     * Returns the type of the product as a StringProperty.
     * @return 
     */
    public StringProperty typeProperty() {
        return new SimpleStringProperty(type);
    }

    /**
     * Returns the model of the product as a StringProperty.
     * @return 
     */
    public StringProperty modelProperty() {
        return new SimpleStringProperty(model);
    }

    /**
     * Returns the price of the product as a FloatProperty.
     * @return 
     */
    public FloatProperty priceProperty() {
        return new SimpleFloatProperty(price);
    }

    /**
     * Returns the count of the product as an IntegerProperty.
     * @return 
     */
    public IntegerProperty countProperty() {
        return new SimpleIntegerProperty(count);
    }

    /**
     * Returns the delivery date of the product as an ObjectProperty of LocalDate.
     * @return 
     */
    public ObjectProperty<LocalDate> deliveryDateProperty() {
        return new SimpleObjectProperty<>(deliveryDate);
    }
}

