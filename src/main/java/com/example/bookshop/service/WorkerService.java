package com.example.bookshop.service;

import com.example.bookshop.dao.GenreDao;
import com.example.bookshop.dao.WorkerDao;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Worker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    private final WorkerDao workerDao;

    public WorkerService(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    public List<Worker> getAllWorkers() {
        return workerDao.findAll();
    }

    public void saveWorker(Worker newWorker) {
        workerDao.saveWorker(newWorker);
    }

    public boolean existsByTabNumber(String tabNumber){
        return workerDao.existsByTabNumber(tabNumber);
    }

    /*public void delete(Long id) {
        workerDao.deleteById(id);
    }

    public Genre getById(Long id) {
        return workerDao.findById(id).orElse(null);
    }*/
}
