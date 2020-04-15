import java.util.ArrayList;
import java.util.List;

public class Result {

    private Float cost = 0f;
    private List<String> errorMessage;

    public Result() {
        errorMessage = new ArrayList<>();
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean hasErrorMessage() {
        return !this.errorMessage.isEmpty();
    }
}