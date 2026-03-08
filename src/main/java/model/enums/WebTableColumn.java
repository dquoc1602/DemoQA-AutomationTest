package model.enums;

public enum WebTableColumn {
    FIRST_NAME(0),
    LAST_NAME(1),
    AGE(2),
    EMAIL(3),
    SALARY(4),
    DEPARTMENT(5);

    public final int index;

    WebTableColumn(int index) {
        this.index = index;
    }
}
