package com.example.trabalhoavaliativopdm2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.trabalhoavaliativopdm2.connection.MyConnection;
import com.example.trabalhoavaliativopdm2.pokemon.Pokemon;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private MyConnection connection;
    private List<Pokemon> pokemons;
    private Pokemon pokemonSelected;
    private List<Pokemon> pokemonGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connection = MyConnection.getInstance();
        pokemons = new ArrayList<>();
        Executor executor = Executors.newSingleThreadExecutor();

    }



    private List<Pokemon> getSortPokemons(int amount, List<Pokemon> listPokemon){
        List<Pokemon> newList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            int num = random.nextInt(listPokemon.size());
            newList.add(listPokemon.get(num));
            listPokemon.remove(num);
        }
        return newList;
    }

    private void joinGame(){

    }
}