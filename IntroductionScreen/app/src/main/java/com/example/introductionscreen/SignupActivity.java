package com.example.introductionscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    //Variables
    Animation topAnim, bottomAnim;
    ImageView image;

    EditText signupEmail;
    EditText signupPassword;
    EditText signupConfirmPassword;
    Button signupButton;
    TextView loginRedirectText;

    boolean passwordVisibility;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        image = findViewById(R.id.imageView);

        image.setAnimation(bottomAnim);

        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupConfirmPassword = findViewById(R.id.signup_confirmpassword);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);

        mAuth = FirebaseAuth.getInstance();

        signupButton.setOnClickListener(view -> {
            createUser();
        });

        loginRedirectText.setOnClickListener(view -> {
            startActivity(new Intent(SignupActivity.this, SigninActivity.class));
        });

        //Hide and show Password
        signupPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=signupPassword.getRight()-signupPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=signupPassword.getSelectionEnd();
                        if (passwordVisibility){
                            //set drawbale image here
                            signupPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            //to hide password
                            signupPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisibility=false;
                        }else {
                            //set drawbale image here
                            signupPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            //to show password
                            signupPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisibility=true;
                        }
                        signupPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

        //Hide and show Confirm Password
        signupConfirmPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=signupConfirmPassword.getRight()-signupConfirmPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=signupConfirmPassword.getSelectionEnd();
                        if (passwordVisibility){
                            //set drawbale image here
                            signupConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            //to hide password
                            signupConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisibility=false;
                        }else {
                            //set drawbale image here
                            signupConfirmPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            //to show password
                            signupConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisibility=true;
                        }
                        signupConfirmPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });
    }

    private void createUser(){
        String email = signupEmail.getText().toString();
        String password = signupPassword.getText().toString();
        String confirmpassword = signupConfirmPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            signupEmail.setError("Email cannot be empty");
            signupEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)) {
            signupPassword.setError("Password cannot be empty");
            signupPassword.requestFocus();
        }else if (!password.equals(confirmpassword)){
            signupConfirmPassword.setError("Password does not match");
            signupConfirmPassword.requestFocus();
        }else {
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                    }else {
                        Toast.makeText(SignupActivity.this, "Weak Password. Use a stronger one", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}