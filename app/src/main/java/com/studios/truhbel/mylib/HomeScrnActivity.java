package com.studios.truhbel.mylib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class HomeScrnActivity extends AppCompatActivity {

    EditText userNameField,userIdField;
    TextView createAcc;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        userIdField = findViewById(R.id.user_id_et);
        userNameField = findViewById(R.id.username_et);
        createAcc = findViewById(R.id.create_acc_tv);
        signIn = findViewById(R.id.signin_button);

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeScrnActivity.this,CreateAccActivity.class);
                startActivity(intent);
            }
        });


    }
}
