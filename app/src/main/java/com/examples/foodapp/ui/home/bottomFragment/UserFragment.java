package com.examples.foodapp.ui.home.bottomFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.examples.foodapp.databinding.FragmentUserBinding;
import com.examples.foodapp.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class UserFragment extends Fragment {
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    public UserFragment() {
        // Required empty public constructor
    }

    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentUserBinding binding = FragmentUserBinding.inflate(inflater);
        firebaseFirestore.collection("users")
                .whereEqualTo("uId", firebaseAuth.getCurrentUser().getUid())
                .limit(1).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            User mUser = document.toObject(User.class);
                            binding.userName.setText(mUser.getuName());
                            binding.userEmail.setText(mUser.getuEmail());
                            Picasso.get().load(mUser.getImageUrl()).fit().into(binding.imUserDetails);

                        }
                        binding.progressDetails.setVisibility(View.GONE);
                        binding.linearDetails.setVisibility(View.VISIBLE);

                    }
                });





        return binding.getRoot();
    }
}





//                         Map<String, Object> values= document.getData();
//                         binding.userName.setText(values.get("uName").toString());
//                         binding.userEmail.setText(values.get("uEmail").toString());