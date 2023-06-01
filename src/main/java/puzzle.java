import java.util.LinkedList;
import java.util.List;

public class puzzle {
    private float target;
    private List<Float> numbers = new LinkedList<Float>();

    public float getTarget() {
        return target;
    }

    public void setTarget(float target) {
        this.target = target;
    }

    public List<Float> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<Float> numbers) {
        this.numbers = numbers;
    }

    public void addNumber(float number) {
        this.numbers.add(number);
    }
}
