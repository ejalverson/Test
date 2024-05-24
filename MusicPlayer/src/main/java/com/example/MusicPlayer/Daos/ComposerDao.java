package com.example.MusicPlayer.Daos;

import com.example.MusicPlayer.Models.Composer;
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
public class ComposerDao {
    private JdbcTemplate jdbcTemplate;

    public ComposerDao(DataSource dataSouce) {
        this.jdbcTemplate = new JdbcTemplate(dataSouce);
    }

    public Composer getComposerById(int id) {
        Composer composer = null;
        String sql = "SELECT * FROM composer WHERE composer_id = ?;";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
            if (rowSet.next()) {
                composer = mapRowToComposer(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return composer;
    }

    public List<Composer> getComposers() {
        List<Composer> composers = new ArrayList<>();
        String sql = "SELECT * from composer";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            composers.add(mapRowToComposer(results));
        }

        return composers;
    }

    public List<Composer> getComposersByPiece(int composerId) {
        List<Composer> composers = new ArrayList<>();
        String sql = "Select * from composer JOIN composer.composer_id = piece.composer_id WHERE composer.composer_id = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, composerId);
        while (results.next()) {
            Composer composerResult = mapRowToComposer(results);
            composers.add(composerResult);
        }
        return composers;
    }

    public Composer createComposer(Composer composer) {
        Composer newComposer = null;
        String sql = "INSERT INTO composer (name)" +
                "VALUES (?) RETURNING composer_id;";
        try {
            int newComposerId = jdbcTemplate.queryForObject(sql, int.class, newComposer.getName());
            newComposer = getComposerById(newComposerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newComposer;
    }

    public Composer updateComposer(Composer composer) {
        Composer updatedComposer = null;

        String sql = "Update composer set name = ?;";

        try {
            jdbcTemplate.update(sql, composer.getName());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedComposer;
    }

    public int deleteComposerById(int id) {
        int numberOfRows = 0;

        String sql = "Delete FROM composer where composer_id = ?;";

        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }

    private Composer mapRowToComposer(SqlRowSet result) {
        Composer composer = new Composer();
        composer.setId(result.getInt("composer_id"));
        composer.setName(result.getString("name"));
        composer.setNationality(result.getString("nationality"));
        composer.setEra(result.getString("era"));
        return composer;
    }
}

