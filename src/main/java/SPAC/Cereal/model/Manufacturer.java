package SPAC.Cereal.model;

public enum Manufacturer {
    A("American Home Food Products"),
    G("General Mills"),
    K("Kellogg's"),
    N("Nabisco"),
    P("Post"),
    Q("Quaker Oats"),
    R("Ralston Purina");

    private final String fullName;

    // Returns the full name of the manufacturer
    Manufacturer(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
