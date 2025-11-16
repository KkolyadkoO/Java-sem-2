package com.lab11.services;

import com.lab11.models.Film;
import com.lab11.repositories.FilmRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class FilmService {
    private final FilmRepository repository;
    public FilmService(FilmRepository repository) { this.repository = repository; }

    public List<Film> findAll() { return repository.findAll(); }
    public Optional<Film> findById(Long id) { return repository.findById(id); }
    public Film create(Film film) { return repository.save(film); }
    public Film update(Long id, Film updated) {
        return repository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            existing.setCountry(updated.getCountry());
            existing.setDirector(updated.getDirector());
            existing.setTheme(updated.getTheme());
            existing.setHasOscars(updated.isHasOscars());
            existing.setReleaseDate(updated.getReleaseDate());
            existing.setTapeCost(updated.getTapeCost());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Film not found"));
    }
    public void delete(Long id) { repository.deleteById(id); }
}
