package models;

import java.util.Objects;

public class WebTableRecord {
    public final String firstName;
    public final String lastName;
    public final String age;
    public final String email;
    public final String salary;
    public final String department;

    public WebTableRecord(String firstName, String lastName,
            String age, String email,
            String salary, String department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.salary = salary;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WebTableRecord))
            return false;
        WebTableRecord r = (WebTableRecord) o;
        return Objects.equals(email, r.email)
                && Objects.equals(firstName, r.firstName)
                && Objects.equals(lastName, r.lastName)
                && Objects.equals(age, r.age)
                && Objects.equals(salary, r.salary)
                && Objects.equals(department, r.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return String.format(
                "[%s %s | age=%s | email=%s | salary=%s | dept=%s]",
                firstName, lastName, age, email, salary, department);
    }
}
