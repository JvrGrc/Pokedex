package com.example.pokedex.vistas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.pokedex.R;
import com.example.pokedex.adaptador.AdaptadorListaPokemon;
import com.example.pokedex.adaptador.RecyclerItemClickListener;
import com.example.pokedex.conexionPokeApi.ServicioPokeApi;
import com.example.pokedex.entidades.pokemon.ListaPokemonAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit conexionRetrofit;
    private boolean isLoading = false;
    private Context contexto;

    private AdaptadorListaPokemon adaptadorListaPokemon;
    private int offset;
    private ServicioPokeApi servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contexto = this;

        RecyclerView listaPokemon = findViewById(R.id.listaPokemon);
        adaptadorListaPokemon= new AdaptadorListaPokemon(contexto);
        listaPokemon.setAdapter(adaptadorListaPokemon);
        final GridLayoutManager layoutManager = new GridLayoutManager(contexto, 3);
        listaPokemon.setLayoutManager(layoutManager);

        listaPokemon.addOnItemTouchListener(new RecyclerItemClickListener(this, listaPokemon, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int posicion) {
                Intent intent = new Intent(v.getContext(), DetallesPokemon.class);
                intent.putExtra("id", posicion + 1);
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View v, int posicion) {
            }
        }));

        listaPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0){
                    int visibles = layoutManager.getChildCount();
                    int total = layoutManager.getItemCount();
                    int pasado = layoutManager.findFirstVisibleItemPosition();

                    if (!isLoading && (visibles + pasado) >= total){
                        isLoading = true;
                        offset += 100;
                        Log.e("aplicación", "Offset: " + offset);
                        obtenerDatos(offset);
                    }
                }
            }
        });
        conexionRetrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        offset = 0;
        servicio  = conexionRetrofit.create(ServicioPokeApi.class);
        obtenerDatos(offset);
    }

    private void obtenerDatos(int offset){

        Call<ListaPokemonAPI> pokemonAPICall = servicio.hazteConTodos(100, offset);

        pokemonAPICall.enqueue(new Callback<ListaPokemonAPI>() {
            @Override
            public void onResponse(Call<ListaPokemonAPI> call, Response<ListaPokemonAPI> response) {

                if (response.isSuccessful()){

                    ListaPokemonAPI listaPokemonAPI = response.body();

                    adaptadorListaPokemon.agregarListaPokemon(listaPokemonAPI.getResults());
                    isLoading = false;
                }else {
                    Toast.makeText(contexto, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                    Log.e("aplicación", "Respuesta: " + response.toString());
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<ListaPokemonAPI> call, Throwable t) {
                Log.e("aplicación","Fallo: " + t.getMessage());
                isLoading = false;
            }
        });
    }
}