package com.example.introductionscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.introductionscreen.Adapters.GrideAdapter;
import com.example.introductionscreen.databinding.ActivitySetBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class SetActivity extends AppCompatActivity {

    ActivitySetBinding binding;

    FirebaseDatabase database;

    GrideAdapter adapter;

    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow() .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivitySetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        key = getIntent().getStringExtra("key");

        adapter = new GrideAdapter(getIntent().getIntExtra("sets",0),
                getIntent().getStringExtra("category"));

        binding.gridView.setAdapter(adapter);

    }
}