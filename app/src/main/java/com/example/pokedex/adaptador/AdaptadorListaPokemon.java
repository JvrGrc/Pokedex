package com.example.pokedex.adaptador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.entidades.pokemon.Pokemon;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import java.util.ArrayList;

public class AdaptadorListaPokemon extends RecyclerView.Adapter<AdaptadorListaPokemon.ViewHolder> {
    
    private Context contexto;
    private ArrayList<Pokemon> datosPokemon;
    
    public AdaptadorListaPokemon(Context contexto) {
        this.contexto = contexto;
        datosPokemon = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View pokemonLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon, parent, false);
        return new ViewHolder(pokemonLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = datosPokemon.get(position);
        holder.setNombrePokemon(pokemon.getName());
        holder.setSpritePokemon(pokemon.getId());
    }

    @Override
    public int getItemCount() {
        return datosPokemon.size();
    }

    public void agregarListaPokemon(ArrayList<Pokemon> lista) {
        datosPokemon.addAll(lista);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nombrePokemon;
        private ImageView spritePokemon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombrePokemon = itemView.findViewById(R.id.pokemonNombre);
            spritePokemon = itemView.findViewById(R.id.pokemonSprite);
        }

        public void setNombrePokemon(String nombre) {
            nombrePokemon.setText(nombre);
        }

        public void setSpritePokemon(int idPokemon) {
            Picasso.get().load(
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + idPokemon + ".png"
            ).into(spritePokemon);
        }
    }
}
