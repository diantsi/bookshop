package com.example.bookshop.service;

import com.example.bookshop.dao.ClientCardDao;
import com.example.bookshop.dao.WorkerDao;
import com.example.bookshop.entity.ClientCard;
import com.example.bookshop.entity.Worker;
import com.example.bookshop.security.Sha256PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClientCardService {
    private final ClientCardDao clientCardDao;

    public ClientCardService(ClientCardDao clientCardDao){
        this.clientCardDao = clientCardDao;
    }

    public List<ClientCard> getAllClientCards() {
        return clientCardDao.findAll();
    }

    public void saveClientCard(ClientCard clientCard) {
        clientCardDao.saveClientCard(clientCard);
    }

    /*public void editWorker(Worker newWorker, String oldTabNumber, String newPassword) {
        if(!Objects.equals(newPassword, "")) {
            Sha256PasswordEncoder encoder = new Sha256PasswordEncoder();
            String hashed = encoder.encode(newPassword);
            newWorker.setPassword(hashed); // зберігаємо
            clientCardDao.editWorker(newWorker, oldTabNumber);
            return;
        }
        clientCardDao.editWorkerWithoutPassword(newWorker, oldTabNumber);
    }*/


    public boolean existsByIdNumber(String idNumber){
        return clientCardDao.existsByIdNumber(idNumber);
    }

    public Optional<ClientCard> findByPhoneNumber(String phoneNumber){
        return clientCardDao.findByPhoneNumber(phoneNumber);
    }

    /*public void delete(String tabNumber) {
        clientCardDao.deleteByTabNo(tabNumber);
    }

    public Worker getByTabNumber(String tabNumber) {
        return clientCardDao.findByTabNumber(tabNumber).orElse(null);
    }*/
}
