package ca.bvc.employeeconnect.model;

public class User {
    private String name, email, photoUrl, id;

    public User(String name, String email, String logoUrl, String id) {
        this.name = name;
        this.email = email;
        this.photoUrl = logoUrl;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
