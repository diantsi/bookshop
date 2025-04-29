package com.example.bookshop.service;

import com.example.bookshop.dao.AuthorDao;
import com.example.bookshop.entity.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public List<Author> getAllAuthors() {
        return authorDao.findAll();
    }

    public Author getAuthorById(Long id) {
        return authorDao.findById(id);
    }

    public void saveAuthor(Author author) {
        authorDao.save(author);
    }

    public void updateAuthor(Author author) {
        authorDao.update(author);
    }

    public void deleteAuthor(Long id) {
        authorDao.delete(id);
    }
    public boolean hasBooks(Long authorId) {
        return authorDao.hasBooks(authorId);
    }

    public List<Author> getAuthorsByIds(List<Long> ids) {
        return getAllAuthors().stream()
                .filter(author -> ids.contains(author.getId()))
                .collect(Collectors.toList());
    }
}