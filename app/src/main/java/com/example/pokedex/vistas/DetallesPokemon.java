package com.example.pokedex.vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pokedex.R;
import com.example.pokedex.conexionPokeApi.ServicioPokeApi;
import com.example.pokedex.entidades.habilidades.Habilidad;
import com.example.pokedex.entidades.pokemon.Pokemon;
import com.example.pokedex.entidades.estadisticas.Stat;
import com.example.pokedex.entidades.estadisticas.Stats;
import com.example.pokedex.entidades.habilidades.Abilities;
import com.example.pokedex.entidades.tipos.Types;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetallesPokemon extends AppCompatActivity {

    TextView nombrePokemon, idPokemon, tiposPokemon, habilidadesPokemon, ataquePokemon, ataqueEspecialPokemon , defensaPokemon, defensaEspecialPokemon;
    TextView saludPokemon ,velocidadPokemon;
    ImageView spritePokemon, spriteShiny;
    Retrofit conexionRetrofit;
    Context context;
    ProgressBar progressBar;
    LinearLayout contentLayout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_pokemon);
        context = this;

        nombrePokemon = findViewById(R.id.nombrePokemon);
        idPokemon = findViewById(R.id.idPokemon);
        spritePokemon = findViewById(R.id.spritePokemon);
        spriteShiny = findViewById(R.id.spriteShiny);
        tiposPokemon = findViewById(R.id.tiposPokemon);
        habilidadesPokemon = findViewById(R.id.habilidadesPokemon);
        ataquePokemon = findViewById(R.id.ataquePokemon);
        ataqueEspecialPokemon = findViewById(R.id.ataqueEspecialPokemon);
        defensaPokemon = findViewById(R.id.defensaPokemon);
        defensaEspecialPokemon = findViewById(R.id.defensaEspecialPokemon);
        saludPokemon = findViewById(R.id.saludPokemon);
        velocidadPokemon = findViewById(R.id.velocidadPokemon);
        progressBar = findViewById(R.id.progressBar);
        contentLayout = findViewById(R.id.contentLayout);


        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);


        conexionRetrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ServicioPokeApi servicio = conexionRetrofit.create(ServicioPokeApi.class);

        Call<Pokemon> pokemonCall = servicio.pokemonPorID(id);

        pokemonCall.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                if (response.isSuccessful()){

                    Pokemon pokemon = response.body();
                    ActualizarUI(pokemon, id);

                }else {
                    Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                    Log.e("aplicación", "Respuesta: " + response);
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("aplicación","Fallo: " + t.getMessage());
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private void ActualizarUI(Pokemon pokemon, int id){
        nombrePokemon.setText(pokemon.getName());
        idPokemon.setText(String.format("#%d", id));

        Picasso.get().load(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id + ".png"
        ).into(spritePokemon);
        Picasso.get().load(
                "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/" + id + ".png"
        ).into(spriteShiny);

        setTiposPokemon(pokemon);
        setStatsPokemon(pokemon);
        setAbilitiesPokemon(pokemon);

        progressBar.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
    }

    private void setTiposPokemon(Pokemon pokemon){
        if (pokemon.getTypes().size() > 1){
            String t = null;
            for (Types tipos : pokemon.getTypes()){
                t = t + tipos.getType().getName() + " ";
            }
            t = t.replace("null", "");
            t = t.replaceFirst(" ", ", ");
            tiposPokemon.setText(t);
        }else {
            tiposPokemon.setText(pokemon.getTypes().get(0).getType().getName());
        }
    }

    private void setStatsPokemon(Pokemon pokemon){
        for (Stats stats : pokemon.getStats()){
            Stat stat = stats.getStat();
            switch (stat.getName()){
                case "hp":
                    saludPokemon.setText(String.valueOf(stats.getBase_stat()));
                    break;
                case "attack":
                    ataquePokemon.setText(String.valueOf(stats.getBase_stat()));
                    break;
                case "defense":
                    defensaPokemon.setText(String.valueOf(stats.getBase_stat()));
                    break;
                case "special-attack":
                    ataqueEspecialPokemon.setText(String.valueOf(stats.getBase_stat()));
                    break;
                case "special-defense":
                    defensaEspecialPokemon.setText(String.valueOf(stats.getBase_stat()));
                    break;
                case "speed":
                    velocidadPokemon.setText(String.valueOf(stats.getBase_stat()));
                    break;
            }
        }
    }

    private void setAbilitiesPokemon(Pokemon pokemon){
        List<String> habilidades = new ArrayList<>();
        for (Abilities abilities : pokemon.getAbilities()){
            habilidades.add(abilities.getAbility().getName());
        }

        habilidadEspanyol(habilidades);
    }

    private void habilidadEspanyol(List<String> habilidades){
        ServicioPokeApi servicio = conexionRetrofit.create(ServicioPokeApi.class);
        for (String habilidad : habilidades) {
            Call<Habilidad> habilidadCall = servicio.nombreHabilidad(habilidad);
            habilidadCall.enqueue(new Callback<Habilidad>() {
                @Override
                public void onResponse(Call<Habilidad> call, Response<Habilidad> response) {
                    if (response.isSuccessful()) {

                        Habilidad habilidad1 = response.body();

                        if(habilidadesPokemon.getText().equals("")){
                            habilidadesPokemon.setText(habilidad1.getNames().get(5).getName());
                        }else{
                            habilidadesPokemon.setText(habilidadesPokemon.getText() + ", " + habilidad1.getNames().get(5).getName());
                        }

                    } else {
                        Toast.makeText(context, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                        Log.e("aplicación", "Respuesta: " + response);
                    }
                }
                @Override
                public void onFailure(Call<Habilidad> call, Throwable t) {
                    Log.e("aplicación", "Fallo: " + t.getMessage());
                }
            });
        }
    }
}