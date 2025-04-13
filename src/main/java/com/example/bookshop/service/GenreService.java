package com.example.bookshop.service;

import com.example.bookshop.dao.GenreDao;
import com.example.bookshop.entity.Genre;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public List<Genre> getAllGenres() {
        return genreDao.findAll();
    }

    public void saveGenre(Genre genre) {
        genreDao.saveGenre(genre);
    }

    public void delete(Long id) {
        genreDao.deleteById(id);
    }
}
