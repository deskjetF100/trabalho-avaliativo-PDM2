package com.example.trabalhoavaliativopdm2;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.trabalhoavaliativopdm2.adapter.RankAdapter;
import com.example.trabalhoavaliativopdm2.pokemon.PokemonsData;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {

    private FirebaseDatabase firedb;
    private DatabaseReference rank_db;
    private List<RankScore> list_rankScore;

    private ListView listRank;
    private RankAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        List<RankScore> listaTeste = new ArrayList<>();
        listaTeste.add(new RankScore("TEste", 5));
        listaTeste.add(new RankScore("TEste", 5));
        listaTeste.add(new RankScore("TEste", 5));
        listaTeste.add(new RankScore("TEste", 5));

        listRank = findViewById(R.id.listRank);
        adapter = new RankAdapter(listaTeste, getApplicationContext());
        listRank.setAdapter(adapter);

        /*list_rankScore = new ArrayList<>();
        firedb = FirebaseDatabase.getInstance("https://trabalhoavaliativopdm2-default-rtdb.firebaseio.com/");
        rank_db = firedb.getReference("rank");
        PokemonsData pokemonsData = PokemonsData.getInstace();
        RankScore newScore = new RankScore(pokemonsData.getUserNikeName(), pokemonsData.getScore());
        setData(newScore);*/
    }

    private void getData(){
        ValueEventListener changeListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    RankScore rankScore = ds.getValue(RankScore.class);
                    list_rankScore.add(rankScore);
                    System.out.println(rankScore.toString());
                }
                //carregar spinner
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        rank_db.addValueEventListener(changeListner);
    }

    private void setData(RankScore rankScore){
        rank_db.child(rankScore.getUserNikeName()).setValue(rankScore);
    }
}