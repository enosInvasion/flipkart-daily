package flipkart.daily.controllers;

import flipkart.daily.dao.InventoryDao;
import flipkart.daily.entities.Items;
import flipkart.daily.enums.SortCriteria;
import flipkart.daily.enums.SortOrder;
import flipkart.daily.services.InventoryService;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class InventoryController {
    InventoryDao inventoryDao = new InventoryDao();
    InventoryService inventoryService = new InventoryService(inventoryDao);

    public void addItem(String brand, String category, int price) {
        try {
            if(!brand.isEmpty() && !category.isEmpty() && price >= 0) inventoryService.addItem(brand, category, price);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addInventory(String brand, String category, int quantity) {
        try {
            if(!brand.isEmpty() && !category.isEmpty() && quantity >= 0) inventoryService.addInventory(brand, category, quantity);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void printInventory() {
        try {
            inventoryService.printInventory();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchByBrand(List<String> brands, String sortCriteria, String sortOrder) {
        SortCriteria sc = null;
        SortOrder so = null;
        if (sortCriteria == null) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("price")) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("quantity")) {
            sc = SortCriteria.QUANTITY;
        }
        if (sortOrder == null) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("asc")) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            so = SortOrder.DESC;
        }
        try {
            Map<String, List<String>> itemsByBrand = new HashMap<>();
            itemsByBrand.put("brand", brands);
            List<Items> results = inventoryService.searchItems(itemsByBrand, null, null,
                    sc, so);
            for (Items item : results) {
                System.out.println(item.getBrand() + ", " + item.getCategory() + ", " + item.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchByCategory(List<String> categories, String sortCriteria, String sortOrder) {
        SortCriteria sc = null;
        SortOrder so = null;
        if (sortCriteria == null) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("price")) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("quantity")) {
            sc = SortCriteria.QUANTITY;
        }
        if (sortOrder == null) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("asc")) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            so = SortOrder.DESC;
        }
        try {
            Map<String, List<String>> itemsByCategory = new HashMap<>();
            itemsByCategory.put("category", categories);
            List<Items> results = inventoryService.searchItems(itemsByCategory, null, null,
                    sc, so);
            for (Items item : results) {
                System.out.println(item.getBrand() + ", " + item.getCategory() + ", " + item.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void searchByPriceRange(Integer minPrice, Integer maxPrice,
                                   String sortCriteria, String sortOrder) {
        SortCriteria sc = null;
        SortOrder so = null;
        if (sortCriteria == null) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("price")) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("quantity")) {
            sc = SortCriteria.QUANTITY;
        }
        if (sortOrder == null) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("asc")) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            so = SortOrder.DESC;
        }
        try {
            List<Items> results = inventoryService.searchItems(null, minPrice, maxPrice,
                    sc, so);
            for (Items item : results) {
                System.out.println(item.getBrand() + ", " + item.getCategory() + ", " + item.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchByCategoryAndPriceRange(List<String> categories, Integer minPrice,
                                              Integer maxPrice, String sortCriteria, String sortOrder) {
        SortCriteria sc = null;
        SortOrder so = null;
        if (sortCriteria == null) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("price")) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("quantity")) {
            sc = SortCriteria.QUANTITY;
        }
        if (sortOrder == null) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("asc")) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            so = SortOrder.DESC;
        }
        try {
            Map<String, List<String>> itemsByCategory = new HashMap<>();
            itemsByCategory.put("category", categories);
            List<Items> results = inventoryService.searchItems(itemsByCategory, minPrice, maxPrice,
                    sc, so);
            for (Items item : results) {
                System.out.println(item.getBrand() + ", " + item.getCategory() + ", " + item.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void searchItemsWithAllTheFilters(List<String> categories, List<String> brands, Integer minPrice,
                                             Integer maxPrice, String sortCriteria, String sortOrder) {
        SortCriteria sc = null;
        SortOrder so = null;
        if (sortCriteria == null) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("price")) {
            sc = SortCriteria.PRICE;
        } else if (sortCriteria.equalsIgnoreCase("quantity")) {
            sc = SortCriteria.QUANTITY;
        }
        if (sortOrder == null) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("asc")) {
            so = SortOrder.ASC;
        } else if (sortOrder.equalsIgnoreCase("desc")) {
            so = SortOrder.DESC;
        }
        try {
            Map<String, List<String>> itemsByCategoryAndBrand = new HashMap<>();
            if(categories != null) itemsByCategoryAndBrand.put("category", categories);
            if(brands != null) itemsByCategoryAndBrand.put("brand", brands);
            List<Items> results = inventoryService.searchItems(itemsByCategoryAndBrand, minPrice, maxPrice,
                    sc, so);

            for (Items item : results) {
                System.out.println(item.getBrand() + ", " + item.getCategory() + ", " + item.getPrice());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
