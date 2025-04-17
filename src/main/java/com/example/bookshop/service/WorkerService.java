package com.example.bookshop.service;

import com.example.bookshop.Sha256PasswordEncoder;
import com.example.bookshop.dao.GenreDao;
import com.example.bookshop.dao.WorkerDao;
import com.example.bookshop.entity.Genre;
import com.example.bookshop.entity.Worker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WorkerService {
    private final WorkerDao workerDao;
    //private final PasswordEncoder passwordEncoder;


    public WorkerService(WorkerDao workerDao){//, PasswordEncoder passwordEncoder) {
        this.workerDao = workerDao;
        //this.passwordEncoder = passwordEncoder;
    }

    public List<Worker> getAllWorkers() {
        return workerDao.findAll();
    }

    public void saveWorker(Worker newWorker) {
        workerDao.saveWorker(newWorker);
    }

    public void editWorker(Worker newWorker, String oldTabNumber, String newPassword) {
        if(!Objects.equals(newPassword, "")) {
            Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();
            String hashed = encoder.encode(newPassword);
            newWorker.setPassword(hashed); // зберігаємо
            workerDao.editWorker(newWorker, oldTabNumber);
            return;
        }
        workerDao.editWorkerWithoutPassword(newWorker, oldTabNumber);
    }


    public boolean existsByTabNumber(String tabNumber){
        return workerDao.existsByTabNumber(tabNumber);
    }

    public void delete(String tabNumber) {
        workerDao.deleteByTabNo(tabNumber);
    }

    public Worker getByTabNumber(String tabNumber) {
        return workerDao.findByTabNumber(tabNumber).orElse(null);
    }
}
