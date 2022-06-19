package com.example.trabalhoavaliativopdm2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.speech.tts.TextToSpeech;
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
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


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
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TextToSpeech.OnInitListener  onInitListener = new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i == TextToSpeech.SUCCESS){
                            Locale locale = new Locale("pt","br");
                            int result = textToSpeech.setLanguage(locale);
                            textToSpeech.setSpeechRate(0.8f);

                            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.i("problema", "Linguagem não suportada");
                            }else{
                                int scores = PokemonsData.getInstace().getScore();
                                String text = "Você fez "+scores+" pontos";
                                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
                                }else{
                                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                }
                                return;
                            }
                        }else{
                            Log.e("problema", "Erro com o TextToSpeak");
                        }
                    }
                };
                textToSpeech = new TextToSpeech(getApplicationContext(), onInitListener);
            }
        });

        list_rankScore = new ArrayList<>();
        listRank = findViewById(R.id.listRank);
        playAgain = findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SplashSreenActivity.class));
            }
        });

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
                }

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
        if(rankScore.getScore() >= list_rankScore.get(0).getScore()){
            createNotify(1);
        }else if(rankScore.getScore() < 3){
            createNotify(2);
        }
    }

    private void createNotify(int option){
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext() , AlarmReceiver.class);

        i.putExtra("typeNotification", option);
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendar = Calendar.getInstance();

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis()+(1* 60*1000), pendingIntent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(textToSpeech.isSpeaking()){
            textToSpeech.stop();
        }
    }
}