package com.example.trabalhoavaliativopdm2;

import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.trabalhoavaliativopdm2.connection.MyConnection;
import com.example.trabalhoavaliativopdm2.pokemon.Pokemon;
import com.example.trabalhoavaliativopdm2.pokemon.PokemonsData;
import com.squareup.picasso.Picasso;
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
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    private TextView userNikename;
    private ImageView imagePokemon;
    private Spinner spinnerNames;
    private Spinner spinnerExperiences;
    private ArrayAdapter<String> adapterSpinnerNames;
    private ArrayAdapter<Integer> adapterSpinnerExperiences;
    private Button newxtButton;
    private List<Pokemon> pokemons;
    private List<Pokemon> pokemonGame;
    private PokemonsData pokemonsData;
    private String responseName;
    private int responseExperience;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNikename = findViewById(R.id.userNikname);
        imagePokemon = findViewById(R.id.imagePokemon);
        spinnerNames = findViewById(R.id.names);
        spinnerExperiences = findViewById(R.id.experiences);
        newxtButton = findViewById(R.id.nextButton);
        pokemons = new ArrayList<>();
        Executor executor = Executors.newSingleThreadExecutor();
        pokemonsData = PokemonsData.getInstace();
        pokemons = pokemonsData.getPokemons();
        userNikename.setText(pokemonsData.getUserNikeName());
        getPokemonsGame();
        nextRandom();
        newxtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextRandom();
            }
        });
    }

    private void getPokemonsGame(){
        Random random = new Random();
        pokemonGame = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int randomNum = random.nextInt(pokemons.size());
            pokemonGame.add(pokemons.get(randomNum));
            pokemons.remove(randomNum);
        }

        for (Pokemon pokemon : pokemonGame) {
            System.out.println(pokemon.toString());
        }
    }

    private void nextRandom(){
        Pokemon pokemon = pokemonGame.get(count);
        Picasso.get().load(pokemon.getFront_default()).into(imagePokemon);
        ArrayList<String> listOptionsNames = new ArrayList<>();
        listOptionsNames.add("-");
        for (Pokemon p: pokemonGame) {
            listOptionsNames.add(p.getName());
        }
        adapterSpinnerNames = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listOptionsNames);
        spinnerNames.setAdapter(adapterSpinnerNames);
        ArrayList<Integer> listOptinoExperience = new ArrayList<>();
        listOptinoExperience.add(0);
        for (Pokemon p: pokemonGame) {
            listOptinoExperience.add(p.getBase_experience());
        }
        adapterSpinnerExperiences = new ArrayAdapter<Integer>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listOptinoExperience);
        spinnerExperiences.setAdapter(adapterSpinnerExperiences);
        count++;
    }

}