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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankActivity extends AppCompatActivity {

    private FirebaseDatabase firedb;
    private DatabaseReference rank_db;
    private List<RankScore> list_rankScore;

    private ListView listRank;
    private RankAdapter adapter;
    private RankScore rankScore;
    private long idCount = 0;

    private boolean saved = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        list_rankScore = new ArrayList<>();
        listRank = findViewById(R.id.listRank);
        firedb = FirebaseDatabase.getInstance("https://trabalhoavaliativopdm2-default-rtdb.firebaseio.com/");
        rank_db = firedb.getReference("rank");
        PokemonsData pokemonsData = PokemonsData.getInstace();
        rankScore = new RankScore(pokemonsData.getUserNikeName(), pokemonsData.getScore());
        getData();
    }

    private void getData(){
        ValueEventListener changeListner = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                idCount = snapshot.getChildrenCount();
                if(!saved){
                    setData(rankScore);
                    saved = true;
                    return;
                }
                for (DataSnapshot ds: snapshot.getChildren()) {
                    RankScore rankScore = ds.getValue(RankScore.class);
                    list_rankScore.add(rankScore);
                    System.out.println(rankScore.toString());
                }
                List<RankScore> list_orderByScore = list_rankScore.stream()
                        .sorted(Comparator.comparing(RankScore::getScore))
                        .collect(Collectors.toList());
                Collections.reverse(list_orderByScore);
                adapter = new RankAdapter( list_orderByScore, getApplicationContext(), rankScore.getDate());
                listRank.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        };
        rank_db.addValueEventListener(changeListner);
    }

    private void setData(RankScore rankScore){
        rank_db.child(String.valueOf(idCount+1)).setValue(rankScore);
    }
}