package com.studios.truhbel.mylib.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.studios.truhbel.mylib.R;

import java.util.ArrayList;
import java.util.Arrays;

public class UserLandingActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String>arrayList;

    TextView userEmail;
    EditText addBook;
    ImageView addbtn;
    Button signout, deleaccount;
    //  EditText newPasswordField;
    //, confirmNewPw,changePw;

    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        auth = FirebaseAuth.getInstance();

        listView = findViewById(R.id.listview);
        String[] myBooks = {"full moon", "date night"};
        arrayList = new ArrayList<>(Arrays.asList(myBooks));
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        userEmail = findViewById(R.id.users_email_tv);
        signout = findViewById(R.id.signout_btn);
        deleaccount = findViewById(R.id.deleacc_btn);
        addbtn = findViewById(R.id.add_btn);
        addBook = findViewById(R.id.book_et);



        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);



        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(UserLandingActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = addBook.getText().toString();
                arrayList.add(newEntry);
                adapter.notifyDataSetChanged();
            }
        });


//         changePw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                newPasswordField.setVisibility(View.VISIBLE);
//
//                confirmNewPw.setVisibility(View.VISIBLE);
//
//                deleaccount.setVisibility(View.GONE);
//            }
//        });
//
//        confirmNewPw.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              //  progressBar.setVisibility(View.VISIBLE);
//                if (user != null && !newPasswordField.getText().toString().trim().equals("")) {
//                    if (newPasswordField.getText().toString().trim().length() < 6) {
//                        newPasswordField.setError("Password too short, enter minimum 6 characters");
//                        //progressBar.setVisibility(View.GONE);
//                    } else {
//                        user.updatePassword(newPasswordField.getText().toString().trim())
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(UserLandingActivity.this, "Password is updated, sign in with new password!", Toast.LENGTH_SHORT).show();
//                                            signOut();
//                                            //progressBar.setVisibility(View.GONE);
//                                        } else {
//                                            Toast.makeText(UserLandingActivity.this, "Failed to update password!", Toast.LENGTH_SHORT).show();
//                                           // progressBar.setVisibility(View.GONE);
//                                        }
//                                    }
//                                });
//                    }
//                } else if (newPasswordField.getText().toString().trim().equals("")) {
//                    newPasswordField.setError("Enter password");
//                }
//            }
//        });

        deleaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(UserLandingActivity.this, "Your profile is deleted:( Create a account now!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(UserLandingActivity.this, RegisterActivity.class));
                                        finish();
                                    } else {
                                        Toast.makeText(UserLandingActivity.this, "Failed to delete your account!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });


        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(FirebaseUser user) {

        userEmail.setText("User Email: " + user.getEmail());


    }

    // this listener will be called when there is change in firebase user session
    FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user == null) {
                // user auth state is changed - user is null
                // launch login activity
                startActivity(new Intent(UserLandingActivity.this, LoginActivity.class));
                finish();
            } else {
                setDataToView(user);

            }
        }


    };

    //sign out method
    public void signOut() {
        auth.signOut();


// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(UserLandingActivity.this, LoginActivity.class));
                    finish();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}
