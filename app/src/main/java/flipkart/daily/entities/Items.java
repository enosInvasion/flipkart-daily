package flipkart.daily.entities;

import flipkart.daily.exceptions.InventoryException;
import jakarta.persistence.Entity;
import lombok.Data;


import java.util.Objects;

enum SortCriteria {
    PRICE, QUANTITY
}

enum SortOrder {
    ASC, DESC
}

@Data
@Entity
public class Items {

    private  String brand;
    private  String category;
    private  int price;
    private int quantity;

    public Items(String brand, String category, int price, int quantity){
        this.brand = Objects.requireNonNull(brand, "Brand cannot be null");
        this.category = Objects.requireNonNull(category, "Category cannot be null");
        if(price < 0){
            throw new InventoryException("Price cannot be negative");
        }
        this.price = price;
        this.quantity = 0;
    }

    public void addQuantity(int quantity){
        if(quantity < 0){
            throw new InventoryException("Quantity cannot be negative");
        }
        this.quantity += quantity;
    }

    @Override
    public String toString(){
        return String.format("%s %s %d", brand, category, quantity);
    }
}
