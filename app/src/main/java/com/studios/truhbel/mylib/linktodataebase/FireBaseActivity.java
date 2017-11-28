package com.studios.truhbel.mylib.linktodataebase;

import android.annotation.SuppressLint;
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
import com.studios.truhbel.mylib.R;

import java.util.HashMap;

public class FireBaseActivity extends AppCompatActivity {

    Button FbButton;
    EditText authorET, returnDate, bookTitleET;
   private DatabaseReference mDataBase;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        mDataBase = FirebaseDatabase.getInstance().getReference();


        FbButton = findViewById(R.id.fire_base_btn);
        authorET = findViewById(R.id.et_author);
        returnDate = findViewById(R.id.et_return_date);
        bookTitleET = findViewById(R.id.et_book_title);


        FbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sets data to string
                String bookTitle = bookTitleET.getText().toString().trim();
                String author = authorET.getText().toString().trim();
                String returnBkDate = returnDate.getText().toString().trim();


                //Will put both edit text field data into the db to create a child obj
               HashMap< String, String> userDataMap = new HashMap<>();
                userDataMap.put("Book Title", bookTitle);
                userDataMap.put("Author", author);
                userDataMap.put("Return-Date", returnBkDate);

                mDataBase.push().setValue(userDataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Check for error
                        if(task.isSuccessful()){
                            Toast.makeText(FireBaseActivity.this, "Looking good..",Toast.LENGTH_LONG);
                        }else{
                            Toast.makeText(FireBaseActivity.this, "Something went wrong..",Toast.LENGTH_LONG);

                        }
                    }
                });


            }
        });



    }
}
