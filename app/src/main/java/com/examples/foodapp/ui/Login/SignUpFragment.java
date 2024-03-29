package com.examples.foodapp.ui.Login;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.examples.foodapp.R;
import com.examples.foodapp.data.UploadUserListener;
import com.examples.foodapp.data.UploadUserToFirebase;
import com.examples.foodapp.databinding.FragmentSignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class SignUpFragment extends Fragment {
    private FirebaseAuth mAuth;
    private UploadUserToFirebase uploadUserToFirebase;
    private String mName;
    private String mEmail;
    private String mPassword;
    private final int REQUEST_CODE_ADD_IMG = 1;
    private FragmentSignUpBinding binding;
    private backPagerListener backPagerListener;
    CircularProgressButton btnRegister;
    private Uri imageUri;

    public void setBackPagerListener(backPagerListener backPagerListener) {
        this.backPagerListener = backPagerListener;
    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance(backPagerListener backPagerListener) {
        SignUpFragment fragment = new SignUpFragment();
        fragment.setBackPagerListener(backPagerListener);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        uploadUserToFirebase = new UploadUserToFirebase(getActivity(), "ImagesUsers");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater);
        btnRegister = binding.btnRegister;
        binding.tvSignIn.setOnClickListener(view -> {
            backPagerListener.onBackPager(view);
        });
        binding.nameTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0 && !binding.emailTxt.getText().toString().isEmpty() && !binding.passwordTxt.getText().toString().isEmpty()) {
                    binding.btnRegister.setEnabled(true);
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorPrimary))));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.white));
                } else {
                    binding.btnRegister.setEnabled(false);
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.home))));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.notEnabledButton));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.emailTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0 && !binding.nameTxt.getText().toString().isEmpty() && !binding.passwordTxt.getText().toString().isEmpty()) {
                    binding.btnRegister.setEnabled(true);
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorPrimary))));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.white));
                } else {
                    binding.btnRegister.setEnabled(false);
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.home))));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.notEnabledButton));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.passwordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 6 && !binding.emailTxt.getText().toString().isEmpty() && !binding.nameTxt.getText().toString().isEmpty()) {
                    binding.btnRegister.setEnabled(true);
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorPrimary))));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.white));
                } else {
                    binding.btnRegister.setEnabled(false);
                    binding.btnRegister.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.home))));
                    binding.btnRegister.setTextColor(getResources().getColor(R.color.notEnabledButton));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        btnRegister.setOnClickListener(view -> {
//            if (TextUtils.isEmpty(binding.nameTxt.getText()) || TextUtils.isEmpty(binding.emailTxt.getText()) || TextUtils.isEmpty(binding.emailTxt.getText())) {
//                Toast.makeText(getContext(), "Please fill all field", Toast.LENGTH_SHORT).show();
//            } else {
//                if (binding.emailTxt.getText().length() < 8) {
//                    Toast.makeText(getContext(), "Password must be 8 characters or more", Toast.LENGTH_SHORT).show();
//                } else {}
            //     progressDialog.create();}
            btnRegister.startAnimation();
            mName = binding.nameTxt.getText().toString();
            mEmail = binding.emailTxt.getText().toString();
            mPassword = binding.passwordTxt.getText().toString();
            createUser(mName, mEmail, mPassword);


        });
        binding.btnAddImage.setOnClickListener(view -> {
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, REQUEST_CODE_ADD_IMG);
        });

        return binding.getRoot();
    }


    private void createUser(String mName, String mEmail, String mPassword) {
        mAuth.createUserWithEmailAndPassword(mEmail, mPassword).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();
                    if (imageUri != null && user != null) {
                        uploadUserToFirebase.uploadToFireBase(imageUri, user.getUid(), mName, mEmail, new UploadUserListener() {
                            @Override
                            public void OnSuccessUpload(boolean success) {
                                if (success) {
                                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chek_green);
                                    btnRegister.doneLoadingAnimation(getResources().getColor(R.color.white), bitmap);
                                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                                    CreateUserSuccess();
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnRegister.revertAnimation();
                                            btnRegister.setBackground(getResources().getDrawable(R.drawable.borer_rait));
                                        }
                                    }, 2000);
                                }
                            }

                            @Override
                            public void OnProgress(boolean progress) {

                            }
                        });
                    }
                } else {
                    btnRegister.revertAnimation();
                    btnRegister.setBackground(getResources().getDrawable(R.drawable.borer_rait));
                    Log.w("signIn", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getContext(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }


    private void CreateUserSuccess() {
        Toast.makeText(getContext(), "createUserWithEmail:success", Toast.LENGTH_SHORT).show();
        binding.emailTxt.setText("");
        binding.passwordTxt.setText("");
        binding.nameTxt.setText("");
        binding.imageUser.setImageResource(R.drawable.userimg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_ADD_IMG && data != null) {
            imageUri = data.getData();
            binding.imageUser.setImageURI(imageUri);
        }
    }
}