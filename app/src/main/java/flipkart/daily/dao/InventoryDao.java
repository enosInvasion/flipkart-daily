package flipkart.daily.dao;

import java.util.*;

import flipkart.daily.exceptions.InventoryException;
import flipkart.daily.entities.Items;
import org.springframework.stereotype.Component;

interface InventoryDaoInf{
    void addItem(Items item);
    void addInventory(String category, String brand, int price, int quantity);
    List<Items> getAllItems();
    Items getItem(String brand, String category);
}

@Component
public class InventoryDao implements InventoryDaoInf{

    private final Map<String , Items> inventory = new HashMap<>();

    private String getKey(String brand, String category){
        return brand + "#" + category;
    }

    @Override
    public void addItem(Items item){
        String key = getKey(item.getBrand(), item.getCategory());
        if(inventory.containsKey(key)){
            throw  new InventoryException("Item already exists : " + item.getBrand() + ", " + item.getCategory());
        }
        inventory.put(key, item);
    }

    @Override
    public void addInventory(String category, String brand, int price, int quantity){
        Items item = getItem(brand, category);
        if(item == null){
            throw new InventoryException("Item not found : " + brand + ", " + category);
        }
    }

    @Override
    public List<Items> getAllItems(){
        List<Items> allItems = new ArrayList<>(inventory.values());
        if(allItems.isEmpty()){
            throw new InventoryException("No items found in inventory");
        }
        return allItems;
    }

    @Override
    public Items getItem(String brand, String category){
        String key = getKey(brand, category);
        if(inventory.containsKey(key)){
            return inventory.get(key);
        }
        else {
            throw new InventoryException("Item not found : " + brand + ", " + category);
        }
    }

}
