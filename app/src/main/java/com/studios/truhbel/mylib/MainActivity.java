package com.studios.truhbel.mylib;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    Button signIn;
    EditText userName, idNumber;
    private DatabaseReference mDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        signIn = findViewById(R.id.fire_base_btn);
        userName = findViewById(R.id.username_et);
        idNumber = findViewById(R.id.user_id_et);


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets data to string
                String uName = userName.getText().toString().trim();
                String uID = idNumber.getText().toString().trim();

                //Will put both edit text field data into the db to create a child obj
                HashMap<String, String> userDataMap = new HashMap<>();
                userDataMap.put("User Name", uName);
                userDataMap.put("User ID", uID);

                mDataBase.push().setValue(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Check for error
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "Looking good..",Toast.LENGTH_LONG);
                        }else{
                            Toast.makeText(MainActivity.this, "Something went wrong..",Toast.LENGTH_LONG);

                        }
                    }
                });

            }
        });

    }
}
