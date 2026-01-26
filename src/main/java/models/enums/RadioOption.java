package models.enums;

public enum RadioOption {
    YES("Yes", "yesRadio"),
    IMPRESSIVE("Impressive", "impressiveRadio"),
    NO("No", "noRadio");

    public final String label;
    public final String id;

    RadioOption(String label, String id) {
        this.label = label;
        this.id = id;
    }
}
