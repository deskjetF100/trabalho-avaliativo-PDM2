package com.example.trabalhoavaliativopdm2.pokemon;

import com.example.trabalhoavaliativopdm2.connection.MyConnection;

import kotlin.jvm.Throws;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class PokemonsData {
    private static PokemonsData instace;

    private List<Pokemon> pokemons;
    private String userNikeName;
    private MyConnection connection;
    private final String URL = "https://pokeapi.co/api/v2/pokemon?";

    private PokemonsData() {
    }

    public static PokemonsData getInstace(){
        if(instace == null){
            instace = new PokemonsData();
            instace.setPokemons(new ArrayList<>());
        }
        return instace;
    }

    private String newRandomUrlPokemon(){
        Random random = new Random();
        return URL+"limit="+(random.nextInt(60)+10) +"&offset="+random.nextInt(1000);
    }

    public List<Pokemon> catchPokemonOnline(){
        if(!pokemons.isEmpty()){
            boolean removed = pokemons.removeAll(pokemons);
            if (!removed){
                throw new RuntimeException("Não foi possível limpar a lista de pokemons");
            }
        }
        connection = MyConnection.getInstance();
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
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return pokemons;
    }

    public String getUserNikeName() {
        return userNikeName;
    }

    public void setUserNikeName(String userNikeName) {
        this.userNikeName = userNikeName;
    }

    public List<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
}
