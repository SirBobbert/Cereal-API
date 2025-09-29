package SPAC.Cereal.service;

public enum Manufacturer {
    A("American Home Food Products"),
    G("General Mills"),
    K("Kelloggs"),
    N("Nabisco"),
    P("Post"),
    Q("Quaker Oats"),
    R("Ralston Purina");

    private final String fullName;

    Manufacturer(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
