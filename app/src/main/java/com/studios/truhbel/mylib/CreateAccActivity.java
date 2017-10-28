package com.studios.truhbel.mylib;

import android.annotation.SuppressLint;
import android.content.Intent;
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

public class CreateAccActivity extends AppCompatActivity {

    Button FbButton;
    EditText userName, idNumber, nameEt;
    private DatabaseReference mDataBase;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mDataBase = FirebaseDatabase.getInstance().getReference();

        FbButton = findViewById(R.id.fire_base_btn);
        userName = findViewById(R.id.username_et);
        idNumber = findViewById(R.id.user_id_et);
        nameEt = findViewById(R.id.last_name_et);


        FbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets data to string
                String nameField = nameEt.getText().toString().trim();
                String uName = userName.getText().toString().trim();
                String uID = idNumber.getText().toString().trim();


                //Will put both edit text field data into the db to create a child obj
               HashMap< String, String> userDataMap = new HashMap<>();
                userDataMap.put("First Name, Last Name", nameField);
                userDataMap.put("User Name", uName);
                userDataMap.put("User ID", uID);

                mDataBase.push().setValue(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Check for error
                        if(task.isSuccessful()){
                            Toast.makeText(CreateAccActivity.this, "Looking good..",Toast.LENGTH_LONG);
                        }else{
                            Toast.makeText(CreateAccActivity.this, "Something went wrong..",Toast.LENGTH_LONG);

                        }
                    }
                });

                Intent intent = new Intent(CreateAccActivity.this,HomeScrnActivity.class);
                startActivity(intent);


            }
        });



    }
}
