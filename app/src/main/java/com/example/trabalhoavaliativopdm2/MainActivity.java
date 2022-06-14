package com.example.trabalhoavaliativopdm2;

import android.util.Log;
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
    private ArrayAdapter<String> adapterSpinnerExperiences;
    private Button newxtButton;
    private List<Pokemon> pokemons;
    private List<Pokemon> pokemonGame;
    private Pokemon pokemonRound;
    private PokemonsData pokemonsData;
    private int points = 0;
    private int count = 0;
    private String responseName = "";
    private int responseExperience = 0;

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
        loadSpinner();
        changePokemon();
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
        //Para testes
        for (Pokemon pokemon : pokemonGame) {
            System.out.println(pokemon.toString());
        }
    }

    private ArrayList<String> createListSpinner(List<Pokemon> pokemonList, Function<Pokemon, String> getValue){
        ArrayList<String> newList = new ArrayList<>();
        for (Pokemon pokemon : pokemonList) {
            newList.add(getValue.apply(pokemon));
        }
        return newList;
    }
    private void nextRandom(){
        if(count > 5){
            Toast.makeText(this, points+" acertos", Toast.LENGTH_SHORT).show();
            return;
        }
        verifyScore();
        changePokemon();
    }

    private void changePokemon() {
        Random random = new Random();
        pokemonRound = pokemonGame.get(random.nextInt(pokemonGame.size()));
        Picasso.get().load(pokemonRound.getFront_default()).into(imagePokemon);
        Log.i("Pokemon = ", pokemonRound.toString());
        count++;
    }

    private void loadSpinner() {
        ArrayList<String> listOptionsNames = createListSpinner(pokemonGame, Pokemon::getName);
        adapterSpinnerNames = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listOptionsNames);
        spinnerNames.setAdapter(adapterSpinnerNames);
        spinnerNames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "você clicou em um NOME", Toast.LENGTH_SHORT).show();
                responseName = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Faz nada
            }
        });
        ArrayList<String> listOptinoExperience = createListSpinner(pokemonGame, Pokemon::getBase_experienceString);
        adapterSpinnerExperiences = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, listOptinoExperience);
        spinnerExperiences.setAdapter(adapterSpinnerExperiences);
        spinnerExperiences.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, "você clicou em uma EXPERIENCIA", Toast.LENGTH_SHORT).show();
                responseExperience = Integer.parseInt((String) adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Faz nada
            }
        });
    }

    private void verifyScore() {
        if(pokemonRound.getName().equals(responseName) && pokemonRound.getBase_experience() == responseExperience){
            points++;
            Toast.makeText(this, "acertou" + points, Toast.LENGTH_SHORT).show();
            Log.i("pokemonacert", "acertou pokemon"+count+" nome = "+responseName);
        }
        return;
    }
}