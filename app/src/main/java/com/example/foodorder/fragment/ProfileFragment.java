package com.example.foodorder.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.brouding.simpledialog.SimpleDialog;
import com.example.foodorder.R;
import com.example.foodorder.activity.LoginActivity;
import com.example.foodorder.data.UserData;
import com.example.foodorder.firebaseRepo.FireBaseRepo;
import com.example.foodorder.firebaseRepo.ServerResponse;
import com.example.foodorder.utils.SessionData;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static com.example.foodorder.utils.GoTo.go;
import static com.example.foodorder.utils.Toasts.show;


public class ProfileFragment extends Fragment {

    private static final int REQUEST_CODE = 100;

    private EditText etPhoneNumber, etUserName, etEmail, etFullName;
    private Button btnUpdate, btnLogOut;
    private ImageView imageView;
    private RadioButton rbFemale, rbMale;
    private String gender = null;
    private Uri imageUri = null;
    private boolean isEditable = false;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etEmail = view.findViewById(R.id.et_email);
        etUserName = view.findViewById(R.id.et_username);
        etFullName = view.findViewById(R.id.et_fullname);
        etPhoneNumber = view.findViewById(R.id.et_phone_number);
        imageView = view.findViewById(R.id.image_view);
        rbFemale = view.findViewById(R.id.radioFemale);
        rbMale = view.findViewById(R.id.radioMale);
        btnUpdate = view.findViewById(R.id.btn_update);
        btnLogOut = view.findViewById(R.id.btn_log_out);

        setData();
        setEditTextEditable(false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditable) {
                    if (!isValidate()) {
                        return;
                    }
                    final String email = etEmail.getText().toString().trim();
                    final String userName = etUserName.getText().toString().trim();
                    final String fullName = etFullName.getText().toString().trim();
                    final String phoneNumber = etPhoneNumber.getText().toString().trim();
                    FireBaseRepo.I.uploadFile(SessionData.getInstance().getLocalData().getName() + ".jpg", imageUri, new ServerResponse<String>() {
                        @Override
                        public void onSuccess(String body) {
                            UserData userData = new UserData();
                            userData.setEmail(email);
                            userData.setName(fullName);
                            userData.setPhoneNumber(phoneNumber);
                            userData.setUserName(userName);
                            userData.setGender(gender);
                            userData.setUserImage(body);
                            userData.setPassword(SessionData.getInstance().getLocalData().getPassword());
                            SessionData.getInstance().saveLocalData(userData);
                            FireBaseRepo.I.setProfile(userData, new ServerResponse<String>() {
                                @Override
                                public void onSuccess(String body) {
                                    show.longMsg(getActivity(), body);
                                    isEditable = false;
                                    setEditTextEditable(false);
                                    btnUpdate.setText("Edit Profile");
                                }

                                @Override
                                public void onFailure(Throwable error) {

                                }
                            });

                        }

                        @Override
                        public void onFailure(Throwable error) {

                        }
                    });
                } else {
                    isEditable = true;
                    setEditTextEditable(true);
                    btnUpdate.setText("Update");
                }
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                showLogOutDialog();
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showLogOutDialog() {
        new SimpleDialog.Builder(Objects.requireNonNull(getActivity()))
                .setTitle("Log Out")
                .setContent("Are you Sure ?")
                .setBtnConfirmText("Log Out")
                .setBtnCancelText("Cancel")
                .setCancelable(true)
                .onConfirm(new SimpleDialog.BtnCallback() {
                    @Override
                    public void onClick(@NonNull SimpleDialog dialog, @NonNull SimpleDialog.BtnAction which) {
                        SessionData.getInstance().saveLogin(false);
                        SessionData.getInstance().clearSessionData();
                        go.to(getActivity(), LoginActivity.class);
                        dialog.dismiss();
                    }
                })
                .show();

    }

    private void setEditTextEditable(boolean isEditable) {
        etEmail.setEnabled(isEditable);
        etFullName.setEnabled(isEditable);
        etPhoneNumber.setEnabled(isEditable);
        etUserName.setEnabled(isEditable);
        rbMale.setEnabled(isEditable);
        rbFemale.setEnabled(isEditable);
    }

    private void setData() {
        etEmail.setText(SessionData.getInstance().getLocalData().getEmail());
        etUserName.setText(SessionData.getInstance().getLocalData().getUserName());
        etFullName.setText(SessionData.getInstance().getLocalData().getName());
        etPhoneNumber.setText(SessionData.getInstance().getLocalData().getPhoneNumber());
        if (SessionData.getInstance().getLocalData().getUserImage() != null)
            Picasso.get().load(SessionData.getInstance().getLocalData().getUserImage()).into(imageView);
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etEmail.getText().toString().trim().isEmpty()) {
            isValid = false;
            etEmail.setError("Please fill this field");
        }
        if (etUserName.getText().toString().trim().isEmpty()) {
            isValid = false;
            etUserName.setError("Please fill this field");
        }
        if (etFullName.getText().toString().trim().isEmpty()) {
            isValid = false;
            etFullName.setError("Please fill this field");
        }
        if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            isValid = false;
            etPhoneNumber.setError("Please fill this field");
        }
        if (imageUri == null) {
            isValid = false;
        }
        if (rbMale.isChecked()) {
            gender = "Male";
        } else if (rbFemale.isChecked()) {
            gender = "Female";
        } else {
            //  show.longMsg(ProfileActivity.this, "Please Select gender");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_CODE) {
                if (resultCode == Activity.RESULT_OK) {
                    imageView.setImageURI(data.getData());
                    imageUri = data.getData();
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.e("TAG", "Selecting picture cancelled");
                }
            }
        } catch (Exception e) {
            Log.e("TAG", "Exception in onActivityResult : " + e.getMessage());
        }
    }

}