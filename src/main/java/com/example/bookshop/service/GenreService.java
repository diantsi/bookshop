package com.example.bookshop.service;

import com.example.bookshop.dao.GenreDao;
import com.example.bookshop.dto.GenreDto;
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
}
