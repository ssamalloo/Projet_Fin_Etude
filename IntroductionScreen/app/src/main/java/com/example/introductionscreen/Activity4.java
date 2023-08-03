package com.example.introductionscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.introductionscreen.databinding.Activity4Binding;

public class Activity4 extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;

    Activity4Binding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = Activity4Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);

        image.setAnimation(bottomAnim);

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirmpassword.getText().toString();

                if (email.equals("") || password.equals("") || confirmPassword.equals(""))
                    Toast.makeText(Activity4.this, "All fields are mandatory !", Toast.LENGTH_SHORT).show();
                else {
                    if (password.equals(confirmPassword)){
                        Boolean checkUserEmail = databaseHelper.checkEmail(email);

                        if (checkUserEmail == false){
                            Boolean insert = databaseHelper.insertData(email,password);

                            if (insert == true){
                                Toast.makeText(Activity4.this, "User Successfully Signed Up !", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(Activity4.this, "Sign Up Failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Activity4.this, "User already exists ! Please login.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Activity4.this, "Passwords do not match !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), Activity3.class);
                startActivity(intent);

            }
        });
    }
}