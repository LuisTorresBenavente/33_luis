package vallegrande.edu.pe.model;

import java.util.Objects;

/**
 * Modelo que representa un contacto en la agenda.
 */
public class Contact {
    private String id;
    private String name;
    private String email;
    private String phone;
    private int age;
    private String location;

    public Contact(String id, String name, String email, String phone, int age, String location) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.location = location;
    }

    // Getters
    public String id() { return id; }
    public String name() { return name; }
    public String email() { return email; }
    public String phone() { return phone; }
    public int age() { return age; }
    public String location() { return location; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAge(int age) { this.age = age; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public String toString() {
        return String.format("Contact{id='%s', name='%s', email='%s', phone='%s', age=%d, location='%s'}",
                id, name, email, phone, age, location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(id, contact.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
