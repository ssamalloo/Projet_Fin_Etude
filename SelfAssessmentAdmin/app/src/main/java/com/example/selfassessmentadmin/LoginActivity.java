package com.example.selfassessmentadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Animation bottomAnim;

    TextView signintitle;

    EditText signinPassword;

    boolean passwordVisibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        //Animations
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        signintitle = findViewById(R.id.signin);
        signintitle.setAnimation(bottomAnim);

        signinPassword = findViewById(R.id.password);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        Button signin_button = (Button) findViewById(R.id.signin_button);

        signin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("Admin") && password.getText().toString().equals("Password1234!")){

                    //correct
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(LoginActivity.this, "Admin Logged In", Toast.LENGTH_SHORT).show();
                }
                else {

                    //wrong
                    Toast.makeText(LoginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //Hide and show Password
        signinPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right=2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if (motionEvent.getRawX()>=signinPassword.getRight()-signinPassword.getCompoundDrawables()[Right].getBounds().width()){
                        int selection=signinPassword.getSelectionEnd();
                        if (passwordVisibility){
                            //set drawbale image here
                            signinPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_off_24,0);
                            //to hide password
                            signinPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisibility=false;
                        }else {
                            //set drawbale image here
                            signinPassword.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,R.drawable.ic_baseline_visibility_24,0);
                            //to show password
                            signinPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisibility=true;
                        }
                        signinPassword.setSelection(selection);
                        return true;
                    }
                }

                return false;
            }
        });

    }
}