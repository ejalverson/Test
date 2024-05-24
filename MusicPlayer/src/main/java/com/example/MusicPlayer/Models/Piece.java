package com.example.MusicPlayer.Models;

import jakarta.validation.constraints.NotBlank;

public class Piece {
    @NotBlank
    private int id;
    @NotBlank
    private String name;
    @NotBlank
    private int composerId;
    @NotBlank
    private int genreId;

    public Piece() {
    }

    public Piece(int id, String name, int composerId, int genreId) {
        this.id = id;
        this.name = name;
        this.composerId = composerId;
        this.genreId = genreId;
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

    public int getComposerId() {
        return composerId;
    }

    public void setComposerId(int composerId) {
        this.composerId = composerId;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
    public String toString(){
        return this.getName();
    }
}
