package com.example.MusicPlayer.Models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Composer {
    @NotBlank
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private String nationality;
    @NotBlank
    private String era;

    public Composer() {
    }

    public Composer(int id, String name, String nationality, String era) {
        this.id = id;
        this.name = name;
        this.nationality = nationality;
        this.era = era;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEra() {
        return era;
    }

    public void setEra(String era) {
        this.era = era;
    }
    public String toString(){
        return this.getName() + " " + this.getNationality() + " " + this.getEra();
    }
}
