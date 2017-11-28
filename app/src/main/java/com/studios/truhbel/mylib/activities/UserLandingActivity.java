package com.studios.truhbel.mylib.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.studios.truhbel.mylib.R;
import com.studios.truhbel.mylib.linktodataebase.FireBaseActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class UserLandingActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;

    TextView userEmail;
    EditText addBook;
    ImageView addbtn,dbBkbtn;
    Button signout, deleaccount;
    SharedPreferences sharedPreferences;


    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth auth;
    DatabaseReference mDataBase;

    @SuppressLint("WrongViewCast")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        auth = FirebaseAuth.getInstance();
        mDataBase = FirebaseDatabase.getInstance().getReference();


        listView = findViewById(R.id.listview);
        String[] myBooks = {"full moon", "date night"};
        arrayList = new ArrayList<>(Arrays.asList(myBooks));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);

        userEmail = findViewById(R.id.users_email_tv);
        signout = findViewById(R.id.signout_btn);
        deleaccount = findViewById(R.id.deleacc_btn);
        addbtn = findViewById(R.id.add_btn);
        addBook = findViewById(R.id.book_et);
        dbBkbtn = findViewById(R.id.iv_bk);

        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

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

                sharedPreferences = getSharedPreferences("PREFS",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("arrayList",newEntry);
                //editor.putInt("book_title", 0);
                editor.commit();

            }
        });

        dbBkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserLandingActivity.this, FireBaseActivity.class);
                startActivity(intent);
            }
        });


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

        userEmail.setText("User: " + user.getEmail());


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