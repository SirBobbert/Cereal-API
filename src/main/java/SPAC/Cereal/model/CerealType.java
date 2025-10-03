package SPAC.Cereal.model;

public enum CerealType {
    C("Cold"),
    H("Hot");

    private final String type;

    // Returns the string representation of the cereal type
    CerealType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
