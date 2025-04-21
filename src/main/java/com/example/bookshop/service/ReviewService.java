package com.example.bookshop.service;

import com.example.bookshop.dao.GenreDao;
import com.example.bookshop.dao.ReviewDao;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Review;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {
    private final ReviewDao reviewDao;

    public ReviewService(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public List<Review> getAllReviews() {
        return reviewDao.findAll();
    }

    public void saveReview(Review review) {
        reviewDao.saveReview(review);
    }

    public void delete(Integer id) {
        reviewDao.deleteById(id);
    }

    /*public Review getById(Integer id) {
        return reviewDao.findById(id).orElse(null);
    }*/
}
