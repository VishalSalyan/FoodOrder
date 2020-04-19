package com.example.foodorder.firebaseRepo;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.foodorder.data.FoodData;
import com.example.foodorder.data.UserData;
import com.example.foodorder.data.WishListData;
import com.example.foodorder.utils.Constants;
import com.example.foodorder.utils.SessionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FireBaseRepo {
    public static final FireBaseRepo I = new FireBaseRepo();

    private FireBaseRepo() {
    }

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference userRef = database.getReference(Constants.USER);
    private DatabaseReference bestDealsRef = database.getReference(Constants.BEST_DEAL);
    private DatabaseReference hotDealsRef = database.getReference(Constants.HOT_DEAL);
    private DatabaseReference nearbyRef = database.getReference(Constants.NEARBY);

    //File Storage
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference mStorageReference = storage.getReference();

    public void signUp(final UserData userData, final ServerResponse<Boolean> serverResponse) {
        userRef.push().setValue(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                serverResponse.onSuccess(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                serverResponse.onFailure(new Throwable(e.getMessage()));
            }
        });
    }

    public void login(final String email, final String password, final ServerResponse<UserData> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData user = snapshot.getValue(UserData.class);

//                    for (int i = 0; i < user.userData.size(); i++) {
                    if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                        serverResponse.onSuccess(user);
                        break;
                    }
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchBestDeals(final ServerResponse<ArrayList<FoodData>> serverResponse) {
        bestDealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<FoodData> foodList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodData foodData = snapshot.getValue(FoodData.class);
                    foodList.add(foodData);
                }
                serverResponse.onSuccess(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchHotDeals(final ServerResponse<ArrayList<FoodData>> serverResponse) {
        hotDealsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<FoodData> foodList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodData foodData = snapshot.getValue(FoodData.class);
                    foodList.add(foodData);
                }
                serverResponse.onSuccess(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void fetchNearby(final ServerResponse<ArrayList<FoodData>> serverResponse) {
        nearbyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<FoodData> foodList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodData foodData = snapshot.getValue(FoodData.class);
                    foodList.add(foodData);
                }
                serverResponse.onSuccess(foodList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }


    public void setProfile(final UserData userData, final ServerResponse<String> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData user = snapshot.getValue(UserData.class);
                    assert user != null;
                    if (user.getEmail().equals(userData.getEmail())) {
                        userRef.orderByChild("email").equalTo(user.getEmail()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String key = childSnapshot.getKey();
                                    assert key != null;
                                    userRef.child(key).setValue(userData);
                                }
                                serverResponse.onSuccess("Update Successfully");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getFoodDetails(final String id, String mode, final ServerResponse<FoodData> serverResponse) {
        switch (mode) {
            case Constants.BEST_DEAL:
                bestDealsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FoodData foodData = snapshot.getValue(FoodData.class);
                            assert foodData != null;
                            if (foodData.getId().equals(id)) {
                                serverResponse.onSuccess(foodData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
            case Constants.HOT_DEAL:
                hotDealsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FoodData foodData = snapshot.getValue(FoodData.class);
                            assert foodData != null;
                            if (foodData.getId().equals(id)) {
                                serverResponse.onSuccess(foodData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
            case Constants.NEARBY:
                nearbyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FoodData foodData = snapshot.getValue(FoodData.class);
                            assert foodData != null;
                            if (foodData.getId().equals(id)) {
                                serverResponse.onSuccess(foodData);
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
                break;
        }
    }

    public void wishListFood(final String email, final ServerResponse<ArrayList<WishListData>> serverResponse) {
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserData userData = snapshot.getValue(UserData.class);
                    assert userData != null;
                    if (userData.getEmail().equals(email)) {
                        serverResponse.onSuccess(userData.getFavouriteFoods());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

    public void uploadFile(String fileNameWithExtension, Uri data, final ServerResponse<String> serverResponse) {
        final StorageReference sRef = mStorageReference.child(fileNameWithExtension);
        sRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @SuppressWarnings("VisibleForTests")
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        serverResponse.onSuccess(uri.toString());
                    }
                });
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        serverResponse.onFailure(new Throwable(exception.getMessage()));
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        textViewStatus.setText((int) progress + "% Uploading...");
                    }
                });
    }

    public void fetchAllFoodData(final ServerResponse<String> serverResponse) {
        SessionData.getInstance().totalFoodList.clear();
        bestDealsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    FoodData foodData = snapshot.getValue(FoodData.class);
                    SessionData.getInstance().totalFoodList.add(foodData);
                }
                serverResponse.onSuccess("Explore Car Added");
                hotDealsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FoodData foodData = snapshot.getValue(FoodData.class);
                            SessionData.getInstance().totalFoodList.add(foodData);
                        }
                        serverResponse.onSuccess("New Car Added");

                        nearbyRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    FoodData foodData = snapshot.getValue(FoodData.class);
                                    SessionData.getInstance().totalFoodList.add(foodData);
                                }
                                serverResponse.onSuccess("Collection Car Added");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                serverResponse.onFailure(new Throwable(databaseError.getMessage()));

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        serverResponse.onFailure(new Throwable(databaseError.getMessage()));
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                serverResponse.onFailure(new Throwable(databaseError.getMessage()));
            }
        });
    }

}
