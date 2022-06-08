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

    private final String URL = "https://pokeapi.co/api/v2/pokemon?";
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

        new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println("========================");
                getPokemons();
                System.out.println("========================\nRodando...");
            }
        }.start();
    }

    private String newRandomUrlPokemon(){
        Random random = new Random();
        return URL+"limit="+(random.nextInt(60)+10) +"&offset="+random.nextInt(1000);
    }

    private void getPokemons(){
        String data = connection.getStringResponseHTTPS(newRandomUrlPokemon());
        System.out.println("connectoin = "+ data);
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            System.out.println("JSONARRAY = "+ jsonArray.length());// TESTE
            for (int i = 0; i < jsonArray.length(); i++) {
                String url = jsonArray.getJSONObject(i).getString("url");
                JSONObject jsonPokemon = new JSONObject(connection.getStringResponseHTTPS(url));
                String name = jsonPokemon.getString("name");
                String front_default = jsonPokemon.getJSONObject("sprites").getString("front_default");
                int base_experience = jsonPokemon.getInt("base_experience");
                Pokemon pokemon = new Pokemon(name, front_default, base_experience);
                pokemons.add(pokemon);
            }
            pokemonGame = getSortPokemons(10, pokemons);
            for (Pokemon pokemon : pokemonGame) {
                pokemon.toString();
            }
            System.out.println("size: "+ pokemonGame.size());
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
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