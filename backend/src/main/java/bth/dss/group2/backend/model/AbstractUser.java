package bth.dss.group2.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

public abstract class AbstractUser {
    @Id
    protected String id;
    @JsonProperty
    @Indexed(unique = true)
    protected String loginName;
    @JsonProperty
    protected String hashedPassword;
    @JsonProperty
    protected String description;
    @JsonProperty
    @Indexed(unique = true)
    protected String emailAddress;
    @JsonProperty
    protected String phoneNumber;
    @JsonProperty
    @DBRef
    protected List<Project> followedProjects;
    @JsonProperty
    @DBRef
    protected List<Project> likedProjects;

    public AbstractUser loginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public AbstractUser hashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
        return this;
    }

    public AbstractUser description(String description) {
        this.description = description;
        return this;
    }

    public AbstractUser emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public AbstractUser phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public AbstractUser likedProject(List<Project> likedProjects) {
        this.likedProjects = likedProjects;
        return this;
    }

    public AbstractUser followedProjects(List<Project> followedProjects) {
        this.followedProjects = followedProjects;
        return this;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractUser user = (AbstractUser) o;
        return loginName.equals(user.loginName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getLoginName() {
        return loginName;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getDescription() {
        return description;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Project> getFollowedProjects() {
        return followedProjects;
    }

    public List<Project> getLikedProjects() {
        return likedProjects;
    }

    //Only for testing purposes
    public void setID(String id) {
        this.id = id;
    }


    /* If a print method have to be common for user and company !
    public String toString(){
        return "";
    };*/
}
