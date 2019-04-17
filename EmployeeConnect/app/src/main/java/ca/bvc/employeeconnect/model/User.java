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
    private boolean admin;

    /**
     * Constructor
     * @param name
     * @param email
     * @param logoUrl
     * @param id
     * @param storeId
     * @param admin
     */
    public User(String name, String email, String logoUrl, String id, String storeId, boolean admin) {
        this.name = name;
        this.email = email;
        this.photoUrl = logoUrl;
        this.id = id;
        this.storeId = storeId;
        this.admin = admin;
    }

    /**
     * Constructor
     * @param name
     * @param uId
     * @param sId
     */
    public User(String name, String uId, String sId){
        this.name = name;
        this.id = uId;
        this.storeId = sId;
    }

    /**
     * Get Name of User
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Set for User Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get for User Email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set for User Email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * get for photo URl
     * @return
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * get for store ID of user
     * @return
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * set for photo of user
     * @param photoUrl
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    /**
     * get for User Id
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * set for User id
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * get for user isAdmin Status
     * @return
     */
    public boolean isAdmin() {
        return admin;
    }

}
