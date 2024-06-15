package com.example.pokedex.entidades.tipos;

public class Type {

    private String name;
    private String url;

    public String getName() {
        return nombreEspanyol(name);
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

    private String nombreEspanyol(String name){
        name = name.toLowerCase();
        switch(name){
            case "steel":
                return "Acero";
            case "water":
                return "Agua";
            case "bug":
                return "Bicho";
            case "dragon":
                return "Dragón";
            case "electric":
                return "Electrico";
            case "ghost":
                return "Fantasma";
            case "fire":
                return "Fuego";
            case "fairy":
                return "Hada";
            case "ice":
                return "Hielo";
            case "fighting":
                return "Lucha";
            case "normal":
                return "Normal";
            case "grass":
                return "Planta";
            case "psychic":
                return "Psiquico";
            case "rock":
                return "Roca";
            case "dark":
                return "Siniestro";
            case "ground":
                return "Tierra";
            case "poison":
                return "Veneno";
            case "flying":
                return "Volador";
        }
        return name;
    }
}
