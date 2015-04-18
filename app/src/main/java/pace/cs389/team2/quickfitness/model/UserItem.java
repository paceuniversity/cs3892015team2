package pace.cs389.team2.quickfitness.model;

import java.io.Serializable;

public class UserItem implements Serializable {

    private int id;
    private String username;
    private String email;
    private String password;
    private int genre;
    private String picture;

    public UserItem(String username, String email, String password, int genre, String picture) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.picture = picture;
    }

    public UserItem(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserItem(int id, String username, String email, String password, int genre, String picture) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.genre = genre;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getGenre() {
        return genre;
    }

    public String getPicture() {
        return picture;
    }

}
