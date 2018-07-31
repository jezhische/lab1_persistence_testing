package com.jezh.springmvcjpa.model;

import jdk.nashorn.internal.objects.annotations.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name="APP_USER")
public class User implements Serializable{

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

//	Single Sign-On Id
	@NotEmpty
	@Column(name="SSO_ID", unique=true, nullable=false)
	private String ssoId;

	@NotEmpty
	@Column(name="PASSWORD", nullable=false)
	private String password;
		
	@NotEmpty
	@Column(name="FIRST_NAME", nullable=false)
	private String firstName;

	@NotEmpty
	@Column(name="LAST_NAME", nullable=false)
	private String lastName;

	@NotEmpty
	@Column(name="EMAIL", nullable=false)
	private String email;

	@NotEmpty
// Since the relationship is unidirectional, cascade is not defined: userprofile is not dependent of user, and can live independently
	@ManyToMany(fetch = FetchType.LAZY) // (default behavior) so I need to initialize this Set when I retrieve userProfiles
// from DB, if I want to use these Set when the transaction is commited - i.g. call the content of this Set
	@JoinTable(name = "APP_USER_USER_PROFILE",
//  User is the owner of assotiation (i.e. the owning side of the association is here). Because of there is no Set<User>
// in the UserProfile class, so the relationship is unidirectional (see UserProfile):
             joinColumns = { @JoinColumn(name = "USER_ID") },
             inverseJoinColumns = { @JoinColumn(name = "USER_PROFILE_ID") })
	private Set<UserProfile> userProfiles = new HashSet<UserProfile>();


    public User() {
    }
// constructor for testing
    public User(String ssoId, String password, String firstName, String lastName, String email) {
        this.ssoId = ssoId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSsoId() {
		return ssoId;
	}

	public void setSsoId(String ssoId) {
		this.ssoId = ssoId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserProfile> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ssoId == null) ? 0 : ssoId.hashCode());
		return result;
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj) return true;
//		if (obj == null) return false;
//		if (!(obj instanceof User)) return false;
//		User other = (User) obj;
//		if (id == null) {
//		    if (other.id != null) return false;
//		} else if (!id.equals(other.id)) return false;
//
//		if (ssoId == null) {
//            return other.ssoId == null;
//		} else if (!ssoId.equals(other.ssoId)) return false;
//		return true;
//	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
// id and ssoId are @NotEmpty, but I can't simplify the last lines, as there is empty constructor to create new User.
        if (id != null? !id.equals(that.id) : that.id != null) return false;
        return ssoId == null ? that.ssoId == null : ssoId.equals(that.ssoId);
    }

    @Override
	public String toString() {
		return "User [id=" + id + ", ssoId=" + ssoId + ", password=" + password
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", userProfiles=" + userProfiles + "]";
	}


	
}
