public class Register {
    private short value;
    private final String name;

    public Register(String name) {
        this.name = name;
        this.value = 0;
    }
    public int getValue() {
        return this.value;
    }
    public void setValue(int value) {
        this.value = (short)value;
    }
    public String getName() {
        return this.name;
    }
}
