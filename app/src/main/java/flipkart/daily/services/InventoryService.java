package flipkart.daily.services;

import flipkart.daily.dao.InventoryDao;
import flipkart.daily.entities.Items;
import flipkart.daily.enums.SortCriteria;
import flipkart.daily.enums.SortOrder;
import flipkart.daily.exceptions.InventoryException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


interface ItemFilter{
    boolean check(Items items);
}

class BrandFilter implements ItemFilter {
    private final Set<String> brands;

    public BrandFilter(List<String> brands){
        this.brands = new HashSet<>(brands);
    }

    @Override
    public boolean check(Items items){
        return brands.contains(items.getBrand());
    }
}

class CategoryFilter implements ItemFilter {
    private final Set<String> categories;

    public CategoryFilter(List<String> categories){
        this.categories = new HashSet<>(categories);
    }

    @Override
    public boolean check(Items items){
        return categories.contains(items.getCategory());
    }
}

class PriceRangeFilter implements ItemFilter{
    private final int minPrice;
    private final int maxPrice;

    public PriceRangeFilter(Integer minPrice, Integer maxPrice){
        this.minPrice = minPrice != null ? minPrice : 0;
        this.maxPrice = maxPrice != null ? maxPrice : Integer.MAX_VALUE;
        if(this.minPrice > this.maxPrice){
            throw new InventoryException("Invalid price range : minPrice > maxPrice");
        }
    }

    @Override
    public boolean check(Items items){
        return items.getPrice() >= minPrice && items.getPrice() <= maxPrice;
    }
}


@Service
public class InventoryService {
    private final InventoryDao inventoryDao;

    public InventoryService(InventoryDao inventoryDao){
        this.inventoryDao = inventoryDao;
    }

    public void addItem(Items items){
        inventoryDao.addItem(items);
    }
    public void addInventory(String category, String brand, int price, int quantity){
         inventoryDao.addInventory(category, brand, price, quantity);
    }
    public List<Items> getAllItems(){
        return inventoryDao.getAllItems();
    }
    public Items getItem(String category, String brand){
        return inventoryDao.getItem(brand, category);
    }

    public List<Items> searchItems(Map<String, List<String>> filters, Integer minPrice, Integer maxPrice,
                                   SortCriteria sortBy, SortOrder sortOrder){
        List<ItemFilter> itemFilters = new ArrayList<>();

        if(filters.containsKey("category")){
            itemFilters.add(new CategoryFilter(filters.get("category")));
        }
        if(filters.containsKey("brand")){
            itemFilters.add(new BrandFilter(filters.get("brand")));
        }
        if(minPrice != null || maxPrice != null){
            itemFilters.add(new PriceRangeFilter(minPrice, maxPrice));
        }
        return inventoryDao.getAllItems().stream()
                .filter(items -> itemFilters.stream().allMatch(filter -> filter.check(items)))
                .sorted(getComparator(sortBy, sortOrder))
                .collect(Collectors.toList());

    }

    public Comparator<Items> getComparator(SortCriteria sortBy, SortOrder sortOrder){
        Comparator<Items> comparator;
        switch (sortBy){
            case PRICE :
                comparator = Comparator.comparingInt(Items::getPrice);
                break;
            case QUANTITY :
                comparator = Comparator.comparingInt(Items::getQuantity);
                break;
            default :
                comparator = Comparator.comparingInt(Items::getPrice);
                break;
        }
        return sortOrder == sortOrder.DESC ? comparator.reversed() : comparator;
    }

    public void printInventory(){
        System.out.println("\nInventory");
        Map<String, Map<String, Integer>> result = new TreeMap<>();
        for(Items item: inventoryDao.getAllItems()){
            result.computeIfAbsent(item.getBrand(), k -> new TreeMap<>()).
                    put(item.getCategory(), item.getQuantity());
        }

        for(Map.Entry<String, Map<String, Integer>> brandEntry: result.entrySet()){
            for(Map.Entry<String, Integer> categoryEntry: brandEntry.getValue().entrySet() ){
                System.out.printf("%s %s %d\n",
                        brandEntry.getKey(), categoryEntry.getKey(), categoryEntry.getValue());
            }
        }
    }
}
