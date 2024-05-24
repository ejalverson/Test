package com.example.MusicPlayer.Controllers;

import com.example.MusicPlayer.Daos.ComposerDao;
import com.example.MusicPlayer.Models.Composer;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/composers")
public class ComposerController {
    private ComposerDao composerDao;

    public ComposerController(ComposerDao composerDao) {
        this.composerDao = composerDao;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public List<Composer> listComposers(){
        return composerDao.getComposers();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public Composer getComposerById(@PathVariable int id) { return composerDao.getComposerById(id); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Composer addComposer(@Valid @RequestBody Composer composer) { return composerDao.createComposer(composer); }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public Composer updateComposer(@PathVariable int id, @Valid @RequestBody Composer composer) {
        composer.setId(id);
        return composerDao.updateComposer(composer);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteComposer(@PathVariable int id) {
        composerDao.deleteComposerById(id);
    }

}
