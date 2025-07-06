public class Register32bit {
    private int value;
    private final String name;

    public Register32bit(String name, int initValue) {
        this.name = name;
        this.value = initValue;
    }
    public int get() {
        return this.value;
    }
    public void set(int value) {
        this.value = value;
    }
    public String getName() {
        return this.name;
    }
    public void printValue() {
        System.out.println(this.name + ": " + this.value);
    }
}

