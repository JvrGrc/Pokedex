package com.example.pokedex.conexionPokeApi;

import com.example.pokedex.entidades.habilidades.Habilidad;
import com.example.pokedex.entidades.pokemon.ListaPokemonAPI;
import com.example.pokedex.entidades.pokemon.Pokemon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ServicioPokeApi {

    @GET("pokemon")
    Call<ListaPokemonAPI> hazteConTodos(@Query("limit") int limit, @Query("offset") int offset );

    @GET("pokemon/{pokemon}")
    Call<Pokemon> pokemonPorID(@Path("pokemon") int idPokemon);

    @GET("ability/{ability}")
    Call<Habilidad> nombreHabilidad(@Path("ability") String habilidad);
}
