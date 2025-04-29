package com.example.bookshop.service;

import com.example.bookshop.dao.TranslatorDao;
import com.example.bookshop.entity.Translator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslatorService {

    private final TranslatorDao translatorDao;

    public TranslatorService(TranslatorDao translatorDao) {
        this.translatorDao = translatorDao;
    }

    public List<Translator> getAllTranslators() {
        return translatorDao.findAll();
    }

    public void saveTranslator(Translator translator) {
        translatorDao.saveTranslator(translator);
    }

    public List<Translator> getTranslatorsByIds(List<Long> ids) {
        return getAllTranslators().stream()
                .filter(translator -> ids.contains(translator.getId()))
                .collect(Collectors.toList());
    }
}