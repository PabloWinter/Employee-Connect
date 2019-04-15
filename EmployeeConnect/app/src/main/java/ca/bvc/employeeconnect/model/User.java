package ca.bvc.employeeconnect.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

import java.util.List;

public class User {
    private String name;
    private String email;
    private String photoUrl;
    private String id;
    private String storeId;


    public String getStoreId() {
        return storeId;
    }
    private boolean admin;

    public User(String name, String email, String logoUrl, String id, String storeId, boolean admin) {
        this.name = name;
        this.email = email;
        this.photoUrl = logoUrl;
        this.id = id;
        this.storeId = storeId;
        this.admin = admin;
    }

    public User(String name, String uId, String sId){
        this.name = name;
        this.id = uId;
        this.storeId = sId;
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

    public boolean isAdmin() {
        return admin;
    }

}
