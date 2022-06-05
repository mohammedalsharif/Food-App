package com.examples.foodapp.data;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.annotation.NonNull;

import com.examples.foodapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadUserToFirebase {
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;
    private Context context;

    public UploadUserToFirebase(Context context, String storagePath) {
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference(storagePath);
        this.context = context;

    }

    public void uploadToFireBase(Uri imageUri, String uId, String userName, String userEmail, UploadUserListener uploadUserListener) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        StoreUserInFireStore(uId, uri.toString(), userName, userEmail, "", uploadUserListener);
                        //    binding.imageUser.setImageResource(R.drawable.userimg);
                    }
                });


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                uploadUserListener.OnProgress(true);
            }
        });

    }

    private String getFileExtension(Uri imageUri) {
        ContentResolver resolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(resolver.getType(imageUri));
    }

    private void StoreUserInFireStore(String uId, String urlImage, String mName, String mEmail, String mPassword, UploadUserListener listener) {
        firebaseFirestore.collection("users").add(new User(uId, urlImage, mName, mEmail, mPassword))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        listener.OnSuccessUpload(true);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.OnSuccessUpload(false);
                        Log.w("Failure", "Error adding document", e);
                    }
                });


    }
}
