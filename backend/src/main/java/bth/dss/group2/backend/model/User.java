package bth.dss.group2.backend.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public abstract class User {
	@Id
	protected String id;
	@JsonProperty
	@Indexed(unique = true)
	protected String loginName;
	@JsonIgnore
	String hashedPassword;
	@JsonProperty
	String description;
	@JsonProperty
	@Indexed(unique = true)
	String email;
	@JsonProperty
	String phoneNumber;
	@JsonProperty
	@DBRef
	List<Project> followedProjects;
	@JsonProperty
	@DBRef
	List<Project> likedProjects;

	public abstract User loginName(String loginName);

	public abstract User hashedPassword(String hashedPassword);

	public abstract User description(String description);

	public abstract User email(String email);

	public abstract User phoneNumber(String phoneNumber);

	public abstract User likedProject(List<Project> likedProjects);

	public abstract User followedProjects(List<Project> followedProjects);

	public String getId() {
		return id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
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

	public String getEmail() {
		return email;
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
