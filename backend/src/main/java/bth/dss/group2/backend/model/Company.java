package bth.dss.group2.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

public class Company extends AbstractUser {
    @JsonProperty
    private String name;
    @JsonProperty
    private List<String> cities;
    @JsonProperty
    @DBRef
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
    public Company loginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    @Override
    public Company hashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    @Override
    public Company description(String description) {
        this.description = description;
        return this;
    }

    @Override
    public Company emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    @Override
    public Company phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public Company likedProject(List<Project> likedProjects) {
        this.likedProjects = likedProjects;
        return this;
    }

    @Override
    public Company followedProjects(List<Project> followedProjects) {
        this.followedProjects = followedProjects;
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
