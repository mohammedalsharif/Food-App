package com.examples.foodapp.ui.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.examples.foodapp.databinding.FragmentLoginBinding;
import com.examples.foodapp.ui.home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class LoginFragment extends Fragment {
    public final static String str_sp_checkBox="SpCheckBox";
    public static final String CHECK_BOX_STATE ="chekState" ;
    private FirebaseAuth mAuth;
    private String mEmail;
    private String mPassWord;
    private FragmentLoginBinding binding;
    public backPagerListener backPagerListener;
    CircularProgressButton loginBtn;
    SharedPreferences sp ;
    SharedPreferences.Editor editor ;


    public void setBackPagerListener(backPagerListener backPagerListener) {
        this.backPagerListener = backPagerListener;
    }

    public LoginFragment() {
        // Required empty public constructor
    }



    public static LoginFragment newInstance(backPagerListener backPagerListener) {

        Bundle args = new Bundle();
        LoginFragment fragment = new LoginFragment();
        fragment.setBackPagerListener(backPagerListener);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        sp=getActivity().getSharedPreferences(LoginFragment.str_sp_checkBox, Context.MODE_PRIVATE);
        editor=sp.edit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        loginBtn = (CircularProgressButton) binding.loginBtn;

        //  getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        binding.tvNewAccount.setOnClickListener(view -> {
            backPagerListener.onBackPager(view);
        });
        CheckedTxtInEmail();
        CheckedTxtInPassword();


//[do some async task. When it finishes]
//You can choose the color and the image after the loading is finished
        // btn.doneLoadingAnimation(fillColor, bitmap);

//[or just revert de animation]

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   startActivity(new Intent(getContext(), HomeActivity.class));
                loginBtn.startAnimation();
                mEmail = binding.emailTxt.getText().toString();
                mPassWord = binding.passwordTxt.getText().toString();
                Toast.makeText(getContext(), "" + mEmail, Toast.LENGTH_SHORT).show();
                signIn(mEmail, mPassWord);
                 if (binding.checkBRememberMe.isChecked()){
                            editor.putBoolean(CHECK_BOX_STATE,true);
                            editor.apply();
                        }
//                if (TextUtils.isEmpty(binding.emailTxt.getText()) || TextUtils.isEmpty(binding.passwordTxt.getText())) {
//                    Toast.makeText(getContext(), "Pleas fill all Data", Toast.LENGTH_SHORT).show();
//
//
//                } else {
//
//                }

            }
        });

        return binding.getRoot();
    }

    private void CheckedTxtInPassword() {
        binding.passwordTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 6 && !binding.emailTxt.getText().toString().isEmpty()) {
                    binding.loginBtn.setEnabled(true);
                    binding.loginBtn.setTextColor(getResources().getColor(R.color.white));
                    //     binding.loginBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    binding.loginBtn.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorPrimary))));

                } else {
                    binding.loginBtn.setTextColor(getResources().getColor(R.color.notEnabledButton));
                    binding.loginBtn.setEnabled(false);
                    //  binding.loginBtn.setBackgroundColor(getResources().getColor(R.color.home));
                    binding.loginBtn.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.home))));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void CheckedTxtInEmail() {
        binding.emailTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0 && !binding.passwordTxt.getText().toString().isEmpty()) {
                    binding.loginBtn.setEnabled(true);
                    binding.loginBtn.setTextColor(getResources().getColor(R.color.white));

                    binding.loginBtn.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.colorPrimary))));

                } else {
                    binding.loginBtn.setEnabled(false);
                    binding.loginBtn.setBackgroundTintList(ColorStateList.valueOf((getResources().getColor(R.color.home))));
                    binding.loginBtn.setTextColor(getResources().getColor(R.color.notEnabledButton));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void signIn(String mEmail, String mPassWord) {
        mAuth.signInWithEmailAndPassword(mEmail, mPassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d("Login", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user);
                } else {
                    Log.e("Login", task.getException().toString(), task.getException());
                    Toast.makeText(getActivity(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                    updateUI(null);
                }
            }
        });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.chek_green);
            loginBtn.doneLoadingAnimation(getResources().getColor(R.color.white), bitmap);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }
            }, 1000);

            saveSignInSharedPreferences();
        } else {
            loginBtn.revertAnimation();
            loginBtn.setBackground(getResources().getDrawable(R.drawable.borer_rait));
        }

    }

    private void saveSignInSharedPreferences() {
    }


}