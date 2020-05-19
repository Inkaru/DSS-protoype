package bth.dss.group2.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Company extends AbstractUser {
    @JsonProperty
    private String name;
    @JsonProperty
    private List<String> cities;
    @JsonProperty
    private List<User> employees;

    public Company(){
        super();
    }

    public Company name(String name){
        this.name = name;
        return this;
    }

    public Company cities(List<String> cities){
        this.cities = cities;
        return this;
    }

    public Company employees(List<User> employees){
        this.employees = employees;
        return this;
    }

    @Override
    public String toString() {
        //TODO: add more
        return String.format(
                "Company[id='%s', loginName='%s', emailAddress='%s', hashedPassword='%s', name='%s', employee='%s']",
                id, loginName, emailAddress, hashedPassword, name, employees);
    }

}
