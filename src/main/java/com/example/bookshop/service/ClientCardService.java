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
        return clientCardDao.findAllWithReceiptCount();
    }

    public void saveClientCard(ClientCard clientCard) {
        clientCardDao.saveClientCard(clientCard);
    }

    public void editClientCard(ClientCard newClientCard) {
        clientCardDao.editClientCard(newClientCard);
    }


    public boolean existsByIdNumber(String idNumber){
        return clientCardDao.existsByIdNumber(idNumber);
    }

    public Optional<ClientCard> findByPhoneNumber(String phoneNumber){
        return clientCardDao.findByPhoneNumber(phoneNumber);
    }

    /*public void countReceiptsByClientId(String idNumber) {
        clientCardDao.countReceiptsByClientId(idNumber);
    }*/

    public void delete(String idNumber) {
        clientCardDao.deleteById(idNumber);
    }

    public ClientCard getById(String id) {
        return clientCardDao.findById(id).orElse(null);
    }

    public ClientCard getBySurname(String surname) {
        return clientCardDao.findBySurname(surname).orElse(null);
    }
}
