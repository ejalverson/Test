package com.example.MusicPlayer.Controllers;

import com.example.MusicPlayer.Daos.GenreDao;
import com.example.MusicPlayer.Models.Genre;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private GenreDao genreDao;

    public GenreController(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @GetMapping("")
    public List<Genre> listGenres(){return genreDao.getGenres();
    }
}
