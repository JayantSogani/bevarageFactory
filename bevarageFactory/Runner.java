import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;



public class Runner {

    BeverageFactory bevFactory = new BeverageFactory();

    @BeforeTest
    public void init(){
        bevFactory.init(setup());
    }

    @Test
    public void testMultiInput() {
        Result result = bevFactory.order("Coffee, -milk, Chai, -sugar, -water");
        Assert.assertEquals(result.getCost().floatValue(),7.0f);
    }

    @Test
    public void testOnlyMenuItems() {
        Result result = bevFactory.order("Coffee, Chai");
        Assert.assertEquals(result.getCost().floatValue(),9.0f);
    }

    @Test
    public void testSingleMenuItems() {
        Result result = bevFactory.order("Coffee");
        Assert.assertEquals(result.getCost().floatValue(),5.0f);
    }

    @Test
    public void testAllIngredientsForAMenuItems() {
        Result result = bevFactory.order("Coffee, -sugar, -water, -milk");
        Assert.assertEquals(result.getErrorMessage().size(),1,String.join(",",result.getErrorMessage()));
    }

    @Test
    public void testSameIngredientForAMenuItems() {
        Result result = bevFactory.order("Coffee, -sugar, -water, -water");
        Assert.assertEquals(result.getErrorMessage().size(),1,String.join(",",result.getErrorMessage()));
    }

    @Test
    public void testNoMenuItemSupplied() {
        Result result = bevFactory.order("-sugar, -water, -water");
        Assert.assertEquals(result.getErrorMessage().size(),3,String.join(",",result.getErrorMessage()));
    }

    @Test
    public void testMenuItemWithOtherIngredientSupplied() {
        Result result = bevFactory.order("Chai, -water, -data");
        Assert.assertEquals(result.getErrorMessage().size(),2,String.join(",",result.getErrorMessage()));
    }

    @Test
    public void testMenuItemChai() {
        Result result = bevFactory.order("Chai, -sugar");
        Assert.assertEquals(result.getCost().floatValue(),3.5f);
    }

    @Test
    public void testNoInputSupplied() {
        Result result = bevFactory.order("");
        Assert.assertEquals(result.getErrorMessage().size(),1,String.join(",",result.getErrorMessage()));
    }

    private Map<String,Item> setup() {
        HashMap<String, Item> bevItems = new HashMap<>();

        //coffee
        Map<String,Float> subIngridentsForCoffee = new HashMap<>();
        subIngridentsForCoffee.put("-milk",1f);
        subIngridentsForCoffee.put("-sugar",0.5f);
        subIngridentsForCoffee.put("-water",0.5f);
        bevItems.put("Coffee",new Item("Coffee",subIngridentsForCoffee,5f));

        //chai
        Map<String,Float> subIngridentsForChai = new HashMap<>();
        subIngridentsForChai.put("-milk",1f);
        subIngridentsForChai.put("-sugar",0.5f);
        subIngridentsForChai.put("-water",0.5f);
        bevItems.put("Chai",new Item("Chai",subIngridentsForChai,4f));

        //banana smoothie
        Map<String,Float> subIngridentsForBanana = new HashMap<>();
        subIngridentsForBanana.put("-milk",1f);
        subIngridentsForBanana.put("-sugar",0.5f);
        subIngridentsForBanana.put("-water",0.5f);
        bevItems.put("banana",new Item("banana",subIngridentsForBanana,6f));


        //banana Strawberries
        Map<String,Float> subIngridentsForStrawberries = new HashMap<>();
        subIngridentsForStrawberries.put("-milk",1f);
        subIngridentsForStrawberries.put("-sugar",0.5f);
        subIngridentsForStrawberries.put("-water",0.5f);
        bevItems.put("Strawberries",new Item("Strawberries",subIngridentsForCoffee,7f));

        //Mojito Lemon
        Map<String,Float> subIngridentsForLemon = new HashMap<>();
        subIngridentsForLemon.put("-soda",1f);
        subIngridentsForLemon.put("-sugar",0.5f);
        subIngridentsForLemon.put("-water",0.5f);
        subIngridentsForLemon.put("-mint",0.5f);
        bevItems.put("Lemon",new Item("Lemon",subIngridentsForCoffee,7.5f));

        return bevItems;
    }
}
