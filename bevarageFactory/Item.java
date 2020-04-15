import java.util.Map;
import java.util.Set;

public class Item {

    private String menuItem;
    private Map<String,Float> subIngredientItems;
    private Float costForMenuItem;

    public Item(String menuItem, Map<String, Float> subIngredientItems, Float costForMenuItem) {
        this.menuItem = menuItem;
        this.subIngredientItems = subIngredientItems;
        this.costForMenuItem = costForMenuItem;
    }

    public boolean isValidSubIngredient(String ingredient){
        return subIngredientItems.keySet().contains(ingredient);
    }

    public String getMenuItem() {
        return menuItem;
    }

    public Float getCostForMenuItem() {
        return costForMenuItem;
    }

    public Float getCostForSubIngredient(String order) {
       return subIngredientItems.get(order);
    }

    public Set<String> getSubIngredients() {
        return this.subIngredientItems.keySet();
    }
}