package com.example.MusicPlayer.Daos;

import com.example.MusicPlayer.Models.Piece;
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
public class PieceDao {
    private JdbcTemplate jdbcTemplate;

    public PieceDao(DataSource dataSouce) {
        this.jdbcTemplate = new JdbcTemplate(dataSouce);
    }

    public Piece getPieceById(int id){
        Piece piece = null;
        String sql = "SELECT * FROM piece WHERE piece_id = ?;";

        try {
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
            if (rowSet.next()) {
                piece = mapRowToPiece(rowSet);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return piece;
    }

    public List<Piece> getPieces() {
        List<Piece> pieces = new ArrayList<>();
        String sql = "SELECT * from piece";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            pieces.add(mapRowToPiece(results));
        }

        return pieces;
    }
    public Piece createPiece(Piece piece) {
        Piece newPiece = null;
        String sql = "INSERT INTO piece (name)" +
                "VALUES (?) RETURNING piece_id;";
        try {
            int newPieceId = jdbcTemplate.queryForObject(sql, int.class, newPiece.getName());
            newPiece = getPieceById(newPieceId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return newPiece;
    }

    public Piece updatePiece(Piece piece) {
        Piece updatedPiece = null;

        String sql = "Update piece set name = ?;";

        try{
            jdbcTemplate.update(sql, piece.getName());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return updatedPiece;
    }

    public int deletePieceById(int id) {
        int numberOfRows = 0;

        String sql = "Delete FROM piece where piece_id = ?;";

        try {
            numberOfRows = jdbcTemplate.update(sql, id);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return numberOfRows;
    }
    private Piece mapRowToPiece(SqlRowSet result) {
        Piece piece = new Piece();
        piece.setId(result.getInt("piece_id"));
        piece.setName(result.getString("name"));
        piece.setComposerId(result.getInt("composer_id"));
        piece.setGenreId(result.getInt("genre_id"));
        return piece;
    }
}

