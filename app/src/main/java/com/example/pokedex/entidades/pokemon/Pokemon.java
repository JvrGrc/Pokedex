package com.example.pokedex.entidades.pokemon;

import com.example.pokedex.entidades.estadisticas.Stats;
import com.example.pokedex.entidades.habilidades.Abilities;
import com.example.pokedex.entidades.tipos.Types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pokemon implements Serializable {

    private int id;
    private String name;
    private String url;
    private List<Types> types = new ArrayList<>();
    private List<Stats> stats = new ArrayList<>();
    private List<Abilities> abilities = new ArrayList<>();

    public int getId() {
        String [] partesURL = url.split("/");

        return Integer.parseInt(partesURL[partesURL.length-1]);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        char[] c = name.toCharArray();
        c[0] = Character.toUpperCase(c[0]);
        return new String(c);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Types> getTypes() {
        return types;
    }

    public void setTypes(List<Types> types) {
        this.types = types;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public List<Abilities> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Abilities> abilities) {
        this.abilities = abilities;
    }
}
