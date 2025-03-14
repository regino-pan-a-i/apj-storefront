package edu.byui.apj.storefront.tutorial102;


import io.swagger.v3.oas.models.media.Schema;
import org.springframework.data.annotation.Id;

public class Person {

    @Id
    private String id;
    private String firstName;
    private String lastName;

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
}
