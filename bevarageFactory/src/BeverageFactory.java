import java.util.Arrays;
import java.util.Map;

public class BeverageFactory {

    Map<String,Item> bevarges;

    public void init(Map<String,Item> bevarges){
        this.bevarges = bevarges;
    }

    public Result order(String orders){
        OrderContext ctx = new OrderContext(bevarges);
        Arrays.asList(orders.split(",")).stream().forEachOrdered(order -> ctx.apply(order.trim()));
        return ctx.getResult();
    }

}
