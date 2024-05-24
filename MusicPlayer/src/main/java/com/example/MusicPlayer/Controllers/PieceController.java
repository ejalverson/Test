package com.example.MusicPlayer.Controllers;

import com.example.MusicPlayer.Daos.PieceDao;
import com.example.MusicPlayer.Models.Piece;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pieces")
public class PieceController {

    private PieceDao pieceDao;

    public PieceController(PieceDao pieceDao) {
        this.pieceDao = pieceDao;
    }

    @GetMapping("")
    public List<Piece> listPieces(){return pieceDao.getPieces();
    }
}

