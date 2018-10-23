package demo;

import javax.persistence.Entity;

public class User {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String id;
    private String firstName;
    private String lastName;

    public User(){}

    public User(String id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString(){
        return String.format("User id: %s, first name: %s, last name: %s\n", this.id, this.firstName, this.lastName);
    }
}