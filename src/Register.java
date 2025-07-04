public class Register {
    private short value;
    private final String name;

    public Register(String name, short initValue) {
        this.name = name;
        this.value = initValue;
    }
    public short getValue() {
        return this.value;
    }
    public void setValue(short value) {
        this.value = (short)value;
    }
    public String getName() {
        return this.name;
    }
    public void printValue() {
        System.out.println(this.name + ": " + this.value);
    }
}
