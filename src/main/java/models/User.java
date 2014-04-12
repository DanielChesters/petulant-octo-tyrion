package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.avaje.ebean.Ebean;

@Entity
public class User {

    @Id
    public Long id;

    private final String email;
    private final String password;
    private final String fullname;
    private boolean isAdmin;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public boolean isAdmin() {
        return isAdmin;
    }


    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }


    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }


    public String getFullname() {
        return fullname;
    }


    public static User connect(String email, String password) {
        return Ebean.find(User.class)
                .where()
                .eq("email", email)
                .eq("password", password)
                .findUnique();
    }

}
