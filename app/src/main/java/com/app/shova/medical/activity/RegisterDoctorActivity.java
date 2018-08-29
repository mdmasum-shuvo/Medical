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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.shova.medical.R;
import com.app.shova.medical.appConstant.AppConstants;
import com.app.shova.medical.model.Doctor;
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

public class RegisterDoctorActivity extends BaseActivity {
    private EditText eName,eEmail,ePhone,eSpecialist,eDescription,ePass;
    private ImageButton btnCapture;
    private ImageView imgChooseImage;
    private ImageView btnSave;
    private ProgressDialog progressDialog ;
    private Activity mActivity;
    private Context mContext;

    // Creating URI.
    private Uri filePathUri;

    // Creating StorageReference and DatabaseReference object.
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseAuth doctorSignUpAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initVariable();
        initListener();

    }

    private void initVariable() {
        mActivity=RegisterDoctorActivity.this;
        mContext=getApplicationContext();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(AppConstants.dDATABASE_PATH);
        doctorSignUpAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setIndeterminate(true);
    }


    private void initView() {
        setContentView(R.layout.activity_register_doctor);
        eName=findViewById(R.id.editext_doctor_name);
        eEmail=findViewById(R.id.edit_doctor_email);
        ePass=findViewById(R.id.edit_password_doc);
        ePhone=findViewById(R.id.edit_doctor_phone);
        eSpecialist=findViewById(R.id.edit_doctor_specility);
        eDescription=findViewById(R.id.edit_doctor_description);
        btnCapture=findViewById(R.id.btn_camera);
        btnSave=findViewById(R.id.btn_save_doctor);
        imgChooseImage=findViewById(R.id.btn_choose_image);
        initToolbar();
        enableBackButton();

    }

    private void initListener() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "click on save button", Toast.LENGTH_SHORT).show();
                UploadImageFileToFirebaseStorage();

            }
        });


        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicture.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePicture, AppConstants.DOCTOR_REQUEST_IMAGE_CAPTURE);

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
                startActivityForResult(Intent.createChooser(intent, AppConstants.SELECT_IMAGE_TITLE), AppConstants.DOCTOR_IMAGE_REQ_CODE);

            }

        });
    }

    private void uploadDoctorLoginData() {
        //loginDatabase.child(AppConstants.LOGIN_REFERENCE)
        String email=eEmail.getText().toString().trim().toLowerCase();
        String pass=ePass.getText().toString().trim();
        doctorSignUpAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(mActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               // Toast.makeText(mContext, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                //progressBar.setVisibility(View.GONE);
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
        Bitmap bitmap=null;
        if (requestCode==AppConstants.DOCTOR_REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK && data != null){

            bitmap = (Bitmap) data.getExtras().get("data");
            imgChooseImage.setImageBitmap(bitmap);


        }
        else if (requestCode == AppConstants.DOCTOR_IMAGE_REQ_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePathUri);
                // Setting up bitmap selected image into ImageView.
                imgChooseImage.setImageBitmap(bitmap);

            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }


    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {
        String dName = eName.getText().toString().trim();
        String dEmail = eEmail.getText().toString().trim();
        String dPhone = ePhone.getText().toString().trim();
        String dSpecialist = eSpecialist.getText().toString();
        String dDescription = eDescription.getText().toString();
        if (!validationcheck(dName,dPhone,dSpecialist,dDescription)){
            onLoginFailed();
            return;
        }

        // Checking whether FilePathUri Is empty or not.
        if (filePathUri != null) {

            // Setting progressDialog Title.
            progressDialog.setTitle("Uploading...");

            // Showing progressDialog.
            progressDialog.show();

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(AppConstants.dSTORAGE_PATH + System.currentTimeMillis() + "." + GetFileExtension(filePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(filePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            // Getting image name from EditText and store into string variable.


                            // Hiding the progressDialog after done uploading.


                            // Showing toast message after done uploading.
                            Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();

                            @SuppressWarnings("VisibleForTests")
                            Doctor imageUploadInfo = new Doctor(taskSnapshot.getDownloadUrl().toString(),dName,dEmail,dPhone,dSpecialist,dDescription );

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
                            progressDialog.setTitle("Uploading...");

                        }
                    });
        }
        else {

            Toast.makeText(mContext, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }

        uploadDoctorLoginData();
    }


    private boolean validationcheck(String dName,String dPhone,String dSpecialist,String dDescription){
        boolean valid=true;
        String email=eEmail.getText().toString();
        String pass=ePass.getText().toString();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            eEmail.setError("enter a valid email address");
            valid=false;
        }
        else {
            eEmail.setError(null);
            valid=true;
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10 ){
            ePass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            ePass.setError(null);
        }

        if (dName.isEmpty()){
            eName.setError("field can't be empty");
            valid = false;
        }

        if (dPhone.isEmpty()){
            ePhone.setError("field can't be empty");
            valid = false;
        }

        if (dDescription.isEmpty()){
            eDescription.setError("field can't be empty");
            valid = false;
        }

        if (dSpecialist.isEmpty()){
            eSpecialist.setError("field can't be empty");
            valid = false;
        }

        return valid;
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Sign Up failed", Toast.LENGTH_LONG).show();

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
