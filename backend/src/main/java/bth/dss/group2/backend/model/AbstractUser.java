package bth.dss.group2.backend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;
import java.util.Objects;

@Document(collection = "User")
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

    public abstract AbstractUser loginName(String loginName);

    public abstract AbstractUser hashedPassword(String hashedPassword);

    public abstract AbstractUser description(String description);

    public abstract AbstractUser emailAddress(String emailAddress);

    public abstract AbstractUser phoneNumber(String phoneNumber);

    public abstract AbstractUser likedProject(List<Project> likedProjects);

    public abstract AbstractUser followedProjects(List<Project> followedProjects);

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
