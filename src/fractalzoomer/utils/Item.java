package fractalzoomer.utils;

public class Item {
    public String label;
    public int value;
    public Item(String label, int value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public String toString() {
        return label;
    }
}
