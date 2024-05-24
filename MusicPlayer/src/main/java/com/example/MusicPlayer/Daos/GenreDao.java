package com.example.MusicPlayer.Daos;

import com.example.MusicPlayer.Models.Genre;
import com.example.MusicPlayer.Exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenreDao {
    private JdbcTemplate jdbcTemplate;

    public GenreDao(DataSource dataSouce) {
        this.jdbcTemplate = new JdbcTemplate(dataSouce);
    }
    public Genre getGenreById(int id){
        Genre genre = null;
        String sql = "SELECT * FROM genre WHERE genre_id = ?;";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
            if (rowSet.next()) {
                genre = mapRowToGenre(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return genre;
    }

    public List<Genre> getGenres() {
        List<Genre> genres = new ArrayList<>();
        String sql = "SELECT * from genre";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            genres.add(mapRowToGenre(results));
        }

        return genres;
    }
    public Genre createGenre(Genre genre) {
        Genre newGenre = null;
        String sql = "INSERT INTO genre (name)" +
                "VALUES (?) RETURNING genre_id;";
        try {
            int newPieceId = jdbcTemplate.queryForObject(sql, int.class, newGenre.getName());
            newGenre = getGenreById(newPieceId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newGenre;
    }

    public Genre updateGenre(Genre genre) {
        Genre updatedGenre = null;

        String sql = "Update genre set name = ?;";

        try{
            jdbcTemplate.update(sql, genre.getName());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedGenre;
    }

    public int deleteGenreById(int id) {
        int numberOfRows = 0;

        String sql = "Delete FROM genre where genre_id = ?;";

        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    private Genre mapRowToGenre (SqlRowSet result){
        Genre genre = new Genre();
        genre.setId(result.getInt("genre_id"));
        genre.setName(result.getString("name"));
        return genre;
    }
}
