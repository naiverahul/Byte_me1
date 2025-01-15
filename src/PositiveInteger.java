public class PositiveInteger {
    private int value;

    public PositiveInteger(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be positive");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if (value <= 0) {
            throw new IllegalArgumentException("Value must be positive");
        }
        this.value = value;
    }

    public PositiveInteger add(PositiveInteger other) {
        return new PositiveInteger(this.value + other.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiveInteger that = (PositiveInteger) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(value);
    }
}
