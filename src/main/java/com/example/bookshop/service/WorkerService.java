package com.example.bookshop.service;

import com.example.bookshop.security.Sha256PasswordEncoder;
import com.example.bookshop.dao.WorkerDao;
import com.example.bookshop.entity.Worker;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkerService {
    private final WorkerDao workerDao;

    public WorkerService(WorkerDao workerDao){
        this.workerDao = workerDao;
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

    public Optional<Worker> findByTabEmail(String email){
        return workerDao.findByEmail(email);
    }

    public void delete(String tabNumber) {
        workerDao.deleteByTabNo(tabNumber);
    }

    public Worker getByTabNumber(String tabNumber) {
        return workerDao.findByTabNumber(tabNumber).orElse(null);
    }
}
