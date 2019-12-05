package domain;

public enum BetType {
    WIN("Win"),
    NOT_WIN("Not win"),
    WIN_OR_PRIZE("Win or prize");

    private String name;

    private BetType(String name) {
        this.name = name;
    }

    public Long getId() {
        return (long) ordinal();
    }

    public String getName() {
        return name;
    }
}
