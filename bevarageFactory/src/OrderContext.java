import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * For each order list this context
 * get stamped
 */
public class OrderContext {

    private Map<String,Item> bevarges;
    private Result result;
    private String menuItem;
    private Set<String> subIngridentsForMenuItem;

    public OrderContext(Map<String,Item> bevarges){
        this.bevarges = bevarges;
        this.result = new Result();
    }

    /**
     * apply the order from the context
     * 1. re-compute correct menu item.
     * 2. check is order valid.
     * 3. update cost is everything looks okay.
     * @param order
     *
     */
    public void apply(String order){
        updateMenuItem(order);
        isOrderValid(order);
        updateCost(order);
    }

    /**
     * updates the menu item as per the order,
     * as there can be many order in one go
     * @param order
     */
    private void updateMenuItem(String order) {
        if(bevarges.containsKey(order)) {
            this.menuItem = order;
            this.subIngridentsForMenuItem = new HashSet<>(bevarges.get(order).getSubIngredients());
        }
    }

    /**
     * Updates the cost, add the cost for menu item and subtract for sub-ingredients
     * @param order
     */
    private void updateCost(String order) {
        // must have already subtracted
        if(this.result.hasErrorMessage())
            return;

        Item item = bevarges.get(order);
        if(null != item){
            result.setCost(result.getCost() + item.getCostForMenuItem());
            return;
        }

        result.setCost(result.getCost() - bevarges.get(this.menuItem).getCostForSubIngredient(order));
    }

    /**
     * Conditions needs to be checked
     * 1. In a menu item only applicable ingredients can only be discounted
     * 2. assumption, A menu item will be followed by its ingredient items or
     *                menu items
     * 3. an menu item can come after ingredients ?? - should not be; need to confirm
     * @param order
     */
    private void isOrderValid(String order) {
        if(this.menuItem == order) return;

        //first item should have been a menu item before ordering any sub-ingredients
        if(null == this.menuItem){
            result.getErrorMessage().add("First Item should always be a menu Item");
            return;
        }

        //sub-ingredients should be valid.
        if(null != bevarges.get(this.menuItem) && !bevarges.get(this.menuItem).isValidSubIngredient(order)){
            result.getErrorMessage().add(String.format("[%s] is not valid ingredient for menu item [%s]"
            ,order,this.menuItem));
        }

        boolean againRepeatedSameSubIngredient = this.subIngridentsForMenuItem.remove(order);
        if(!againRepeatedSameSubIngredient){
            result.getErrorMessage().add(String.format("Ingredient :[%s] is already substracted from menu item :[%s]"
                    ,order,this.menuItem));
        }

        //all the sub-ingredients is been subtracted for a menu item is not allowed
        if(null != subIngridentsForMenuItem && this.subIngridentsForMenuItem.isEmpty()){
            result.getErrorMessage().add(String.format("Menu item :[%s] without all ingredient is not allowed"
                    ,this.menuItem));
        }
    }

    public Result getResult(){
        return this.result;
    }
}