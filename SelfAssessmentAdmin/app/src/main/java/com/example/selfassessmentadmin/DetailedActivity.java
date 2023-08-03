package com.example.selfassessmentadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailedActivity extends AppCompatActivity {

    TextView detailDesc, detailType;
    //TextView detailTitle;
    ImageView detailImage;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detailed);

        detailDesc = findViewById(R.id.detailDesc);
        //detailTitle = findViewById(R.id.detailTitle);
        detailImage = findViewById(R.id.detailImage);
        detailType = findViewById(R.id.detailType);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){

            detailDesc.setText(bundle.getString("Description"));
            //detailTitle.setText(bundle.getString("Title"));
            detailType.setText(bundle.getString("Type"));
            key = bundle.getString("key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Topics");
                FirebaseStorage storage = FirebaseStorage.getInstance();

                if (key != null && !key.isEmpty()) {
                    if (imageUrl != null) {
                        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                reference.child(key).removeValue();
                                Toast.makeText(DetailedActivity.this, "Deleted.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), ResourceActivity.class));
                                finish();
                            }
                        });
                    } else {
                        Toast.makeText(DetailedActivity.this, "Image URL is null. Unable to delete.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DetailedActivity.this, "Key is null or empty. Unable to delete.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailedActivity.this, UpdateActivity.class)
                        .putExtra("Description", detailDesc.getText().toString())
                        .putExtra("Type", detailType.getText().toString())
                        .putExtra("Image", imageUrl)
                        .putExtra("key", key);
                startActivity(intent);
            }
        });

    }
}