package domain;

public enum Role {
    CLIENT("Client"),
    ADMINISTRATOR("Administrator"),
    BOOKMAKER("Bookmaker");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return (long) ordinal();
    }

    public String getName() {
        return name;
    }
}
