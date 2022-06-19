package com.example.trabalhoavaliativopdm2;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import com.example.trabalhoavaliativopdm2.pokemon.PokemonsData;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SplashSreenActivity extends AppCompatActivity {

    private EditText nikename;
    private Button jogarButton;
    private CardView form_nikename;
    private Executor executor;
    private Handler handler;
    private final int ID_MESSAGE_FORM_NIKENAME = 1;
    private PokemonsData pokemonsData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen);

        form_nikename = findViewById(R.id.form_nikename);
        nikename = findViewById(R.id.nikename);
        jogarButton = findViewById(R.id.playButton);
        executor = Executors.newSingleThreadExecutor();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == ID_MESSAGE_FORM_NIKENAME){
                    form_nikename.setVisibility(View.VISIBLE);
                    jogarButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String userNikeName = "";
                            userNikeName = nikename.getText().toString();
                            if(!userNikeName.isEmpty()){
                                pokemonsData.setUserNikeName(userNikeName);
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else{
                                Toast.makeText(SplashSreenActivity.this, "Informe um nome de jogado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        };

        pokemonsData = PokemonsData.getInstace();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                boolean busted = pokemonsData.catchPokemonOnline().isEmpty();
                if (!busted){
                    Message msg = new Message();
                    msg.what = ID_MESSAGE_FORM_NIKENAME;
                    handler.sendMessage(msg);
                }else{
                    Toast.makeText(SplashSreenActivity.this,
                            "Não conseguimos capturar pokemons pela internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}