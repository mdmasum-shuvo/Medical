package com.app.shova.medical.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;

public class RegisterUserActivity extends BaseActivity {
    //View
    private EditText eName, eEmail, ePhone, eAddress, ePass;
    private TextView tvGenderError, tvAgeError, tvBloodError;
    private ImageButton btnCapture;
    private ImageView imgChooseImage;
    private ImageView btnSave;
    private ProgressDialog progressDialog;
    private Activity mActivity;
    private Context mContext;
    private Spinner spGender, spAge;
    private RadioGroup radioGroupBg;
    private RadioButton radioBg;

    // Creating URI.
    private Uri filePathUri;
    //variable
    private ArrayAdapter adapterAge;
    private ArrayList<String> ageList;
    // Creating StorageReference and DatabaseReference object.
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth userSignUpAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initVariable();
        initToolbar();
        setToolbarTitle("Registration");
        enableBackButton();
        initListener();
        initFunctionality();
    }

    private void initVariable() {
        mActivity = RegisterUserActivity.this;
        mContext = getApplicationContext();
        //firebase database
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(AppConstants.uDATABASE_PATH);
        userSignUpAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(mActivity);
        ageList = new ArrayList<>();
        //adapter
        adapterAge = new ArrayAdapter(mActivity, R.layout.support_simple_spinner_dropdown_item, ageList);
        spAge.setAdapter(adapterAge);
        ArrayAdapter adapterGender = new ArrayAdapter(mActivity, R.layout.support_simple_spinner_dropdown_item, AppConstants.GENDER_SPINNER);
        spGender.setAdapter(adapterGender);
    }

    private void initView() {
        setContentView(R.layout.activity_register_user);
        //editText
        eName = findViewById(R.id.editext_user_name);
        eEmail = findViewById(R.id.edit_user_email);
        ePass = findViewById(R.id.edit_password_user);
        ePhone = findViewById(R.id.edit_user_phone);
        eAddress = findViewById(R.id.edit_user_address);
        //textview
        tvGenderError = findViewById(R.id.textView_gender);
        tvAgeError = findViewById(R.id.textView_age);
        tvBloodError = findViewById(R.id.textView_blood_group);
        //button
        btnSave = findViewById(R.id.btn_save_user);
        spGender = findViewById(R.id.spinner_user_gender);
        spAge = findViewById(R.id.spinner_user_age);
        imgChooseImage = findViewById(R.id.btn_choose_image_user);
        btnCapture = findViewById(R.id.btn_camera_user);
        radioGroupBg = findViewById(R.id.blood_radio_group);
    }

    private void initListener() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadUserIntoToFirebase();
            }
        });

        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePicture, AppConstants.USER_REQUEST_IMAGE_CAPTURE);

                }
            }
        });

        imgChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating intent.
                Intent intent = new Intent();
                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, AppConstants.SELECT_IMAGE_TITLE), AppConstants.USER_IMAGE_REQ_CODE);

            }
        });
    }


    private void initFunctionality() {
        createAgeArray();
    }


    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void uploadUserIntoToFirebase() {
        String uName = this.eName.getText().toString().trim();
        String uEmail = eEmail.getText().toString().trim();
        String uPhone = ePhone.getText().toString().trim();
        String uAddress = eAddress.getText().toString();
        String uGender = spGender.getSelectedItem().toString();
        String uAge = spAge.getSelectedItem().toString();
        String uBlood = getBloodGroup();

//        if (!validationcheck(uName, uPhone, uAddress, uGender, uAge, uBlood)) {
//            onLoginFailed();
//            return;
//        }

        // Checking whether FilePathUri Is empty or not.
        if (filePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setIndeterminate(true);
            progressDialog.setTitle("Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(AppConstants.uSTORAGE_PATH + System.currentTimeMillis() + "." + GetFileExtension(filePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(filePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.


                            // Hiding the progressDialog after done uploading.


                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            //uName,uEmail,uPhone,uAge,uAddress,uBg,uGender
                            @SuppressWarnings("VisibleForTests")
                            User imageUploadInfo = new User(taskSnapshot.getDownloadUrl().toString(), uName, uEmail, uPhone, uAge, uAddress, uBlood, uGender);

                            // Getting image upload ID.
                            String ImageUploadId = databaseReference.push().getKey();

                            // Adding image upload id s child element into databaseReference.
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Hiding the progressDialog.
                            progressDialog.dismiss();

                            // Showing exception erro message.
                            Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            // Setting progressDialog Title.
                            progressDialog.setIndeterminate(true);
                            progressDialog.setTitle("Uploading...");

                        }
                    });
        } else {

            Toast.makeText(mContext, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }

        uploadUserLoginData();
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private boolean validationcheck(String uName, String uPhone, String uAddress, String uGender, String uAge, String uBlood) {
        boolean valid = true;
        String email = eEmail.getText().toString();
        String pass = ePass.getText().toString();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eEmail.setError("enter a valid email address");
            valid = false;
        } else {
            eEmail.setError("");
            valid = true;
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            ePass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            ePass.setError(null);
        }

        if (uName.isEmpty()) {
            eName.setError("field can't be empty");
            valid = false;
        }

        if (uAddress.isEmpty()) {
            eAddress.setError("field can't be empty");
            valid = false;
        }

//        if (uGender.isEmpty()) {
//            tvGenderError.setError("field can't be empty");
//            valid = false;
//        }
//        if (uBlood.isEmpty()) {
//            tvBloodError.setError("field can't be empty");
//            valid = false;
//        }
//        if (uAge.isEmpty()) {
//            tvGenderError.setError("field can't be empty");
//            valid = false;
//        }

        if (uPhone.isEmpty()) {
            ePhone.setError("field can't be empty");
            valid = false;
        } else if (!Patterns.PHONE.matcher(uPhone).matches() || Integer.parseInt(uPhone) < 11) {
            ePhone.setError("invalid number");
            valid = false;
        }

        return valid;
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Sign Up failed", Toast.LENGTH_LONG).show();

    }

    private void uploadUserLoginData() {
        //loginDatabase.child(AppConstants.LOGIN_REFERENCE)
        String email = eEmail.getText().toString().trim().toLowerCase();
        String pass = ePass.getText().toString().trim();
        userSignUpAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Toast.makeText(mContext, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                ///progressBar.setVisibility(View.GONE);
                progressDialog.dismiss();
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(mContext, "Authentication failed." + task.getException(),
                            Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(mActivity, MainActivity.class));
                    finish();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;
        if (requestCode == AppConstants.USER_REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK && data != null) {

            bitmap = (Bitmap) data.getExtras().get("data");
            imgChooseImage.setImageBitmap(bitmap);
        } else if (requestCode == AppConstants.USER_IMAGE_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathUri);
                // Setting up bitmap selected image into ImageView.
                imgChooseImage.setImageBitmap(bitmap);

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void createAgeArray() {
        for (int i = 15; i <= 80; i++) {
            ageList.add(String.valueOf(i));

        }

        adapterAge.notifyDataSetChanged();

    }


    private String getBloodGroup() {
        String bloodGroup = null;
        if (radioGroupBg.getCheckedRadioButtonId() == -1) {
            tvBloodError.setError("please select a blood group");
            return bloodGroup;
        } else {
            int selectId = radioGroupBg.getCheckedRadioButtonId();
            radioBg = findViewById(selectId);
            bloodGroup = radioBg.getText().toString();
            return bloodGroup;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
