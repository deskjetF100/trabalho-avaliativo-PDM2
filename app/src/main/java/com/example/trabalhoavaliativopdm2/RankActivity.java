package com.example.trabalhoavaliativopdm2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.trabalhoavaliativopdm2.adapter.RankAdapter;
import com.example.trabalhoavaliativopdm2.notification.AlarmReceiver;
import com.example.trabalhoavaliativopdm2.notification.MyNotification;
import com.example.trabalhoavaliativopdm2.notification.NotifyNewRecord;
import com.example.trabalhoavaliativopdm2.pokemon.PokemonsData;
import com.google.firebase.database.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;


public class RankActivity extends AppCompatActivity {

    private FirebaseDatabase firedb;
    private DatabaseReference rank_db;
    private List<RankScore> list_rankScore;

    private Button playAgain;
    private ListView listRank;
    private RankAdapter adapter;
    private RankScore rankScore;
    private long idCount = 0;

    private boolean saved = false;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        list_rankScore = new ArrayList<>();
        listRank = findViewById(R.id.listRank);
        playAgain = findViewById(R.id.playButton);
//        playAgain.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), SplashSreenActivity.class));
//            }
//        });

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
//                List<RankScore> list_orderByScore = list_rankScore.stream()
//                        .sorted(Comparator.comparing(RankScore::getScore))
//                        .collect(Collectors.toList());
//                Collections.reverse(list_orderByScore);
                Collections.sort(list_rankScore, Comparator.comparing(RankScore::getScore));
                Collections.reverse(list_rankScore);
                verifyNewRecord();
                adapter = new RankAdapter( list_rankScore, getApplicationContext(), rankScore.getDate());
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

    private void verifyNewRecord(){
        System.out.println("item postição 0 = "+list_rankScore.get(0).toString());
        Log.i("item postição 0 = ", list_rankScore.get(0).toString());
      //  if(rankScore.getScore() >= list_rankScore.get(0).getScore()){
            createNotify();
      //  }
    }

    private void createNotify(){
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext() , AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
        Calendar calendar = Calendar.getInstance();

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis()+(1* 60*1000), pendingIntent);

    }
}