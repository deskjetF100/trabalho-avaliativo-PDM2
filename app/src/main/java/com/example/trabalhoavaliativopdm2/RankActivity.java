package com.example.trabalhoavaliativopdm2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RankActivity extends AppCompatActivity {

    private FirebaseDatabase firedb;
    private DatabaseReference rank_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        firedb = FirebaseDatabase.getInstance("https://trabalhoavaliativopdm2-default-rtdb.firebaseio.com/");
        rank_db = firedb.getReference("rank");
    }
}