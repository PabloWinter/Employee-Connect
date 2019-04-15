package ca.bvc.employeeconnect.viewmodel;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import ca.bvc.employeeconnect.Home;
import ca.bvc.employeeconnect.LoginActivity;
import ca.bvc.employeeconnect.model.User;
import ca.bvc.employeeconnect.remote.FirebaseQueryLiveData;

public class UserViewModel extends ViewModel {
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String USER_PREF_TAG = "User-Pref";
    private static final String USER_PREF_FILE = "USER-PREF-FILE";

    private FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(db.collection("users"));

    public static User getUser(Activity activity) {
        try {
            SharedPreferences sharedPref = activity.getSharedPreferences(USER_PREF_FILE, Context.MODE_PRIVATE);
            Gson gson = new Gson();
            User user =  gson.fromJson(sharedPref.getString(USER_PREF_TAG, ""), User.class);
            return user;
        } catch (Exception e) {
            return null;
        }
    }

    public static void logOutUser(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(USER_PREF_FILE, Context.MODE_PRIVATE);
        sharedPref.edit().clear().commit();
    }

    public void authenticateUser(final String userId, final String pin, final Activity activity) {
        Query authQuery = db.collection("users")
                .whereEqualTo("Id", userId)
                .whereEqualTo("Pin", pin);

        authQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful() && task.getResult().size() == 1) {
                    DocumentSnapshot data = task.getResult().getDocuments().get(0);

                    String defualtPhotoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYERCqZ1DunHMgZ1S4ict2pPvSh5BfmBY9yjbeRZTal28yr2WPmg";
                    User user = new User(data.getString("Name"), data.getString("Email"), defualtPhotoUrl, data.getId(), data.getString("StoreId"), data.getBoolean("Admin"));
                    SharedPreferences sharedPref = activity.getSharedPreferences(USER_PREF_FILE, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    Gson gson = new Gson();
                    String serializedUser = gson.toJson(user);
                    editor.putString(USER_PREF_TAG, serializedUser);
                    editor.commit();

                    Intent intent = new Intent(activity, Home.class);
                    activity.startActivity(intent);
                    activity.finish();
//                    activity.setResult(RESULT_OK, intent);
//                    activity.finish();
                    Toast.makeText(activity, "Sign In Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(activity, task.getResult().size() + "Sign In Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean verifyAdminUser(String userInput, String employeeCode, String adminCode) throws Exception {
        if (userInput.equals(employeeCode)) {
            return false;
        } else if (userInput.equals(adminCode)) {
            return true;
        } else {
            throw new Exception("Invalid User");
        }
    }

    public void registerAccount(final Activity activity, final String employeeNumber, final String name, final String email, final String userId, final String pin, String confirmPin) {
        final Query registerAccountQuery = db.collection("users");
        Query employeeVerifyQuery = db.collection("stores");

        try {
            if (employeeNumber.equals("") || name.equals("") || email.equals("") || userId.equals("") || pin.equals("")) {
                throw new Exception("Please fill all the field.");
            }
            if (pin.length() > 4 && !pin.equals(confirmPin)) {
                throw new Exception("Pin doesn't match or invalid pin!");
            }
            employeeVerifyQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && task.getResult().size() >= 1) {
                        for (DocumentSnapshot data : task.getResult()){
                            try {
                                final boolean admin = verifyAdminUser(employeeNumber, data.getString("EmployeeRegisterKey"), data.getString("AdminRegisterKey"));
                                final String storeId = data.getId();

                                registerAccountQuery.whereEqualTo("Id", userId)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful() && task.getResult().size() >= 1) {
                                                    Toast.makeText(activity, "Please use different userId. User Already Registered", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Map<String, Object>  user = new HashMap<String, Object>();
                                                    user.put("Admin", admin);
                                                    user.put("Email", email);
                                                    user.put("Id", userId);
                                                    user.put("Pin", pin);
                                                    user.put("Name", name);
                                                    user.put("StoreId", storeId);
                                                    ((CollectionReference) registerAccountQuery).add(user)
                                                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                                                    Toast.makeText(activity, "User Registered.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(activity, "Please Try again.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                    Intent intent = new Intent(activity, LoginActivity.class);
                                                    activity.startActivity(intent);
                                                    activity.finish();
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(activity, "Please Try again.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                break;
                            } catch (Exception e) {
                                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
