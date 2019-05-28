package org.russow.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Good;
import org.russow.service.GoodService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Slf4j
public class GoodServiceImpl implements GoodService {

    @PersistenceContext
    private EntityManager entityManager;

    private EntityTransaction transaction;
    private Gson gson;

    @Override
    public boolean addGoodsFromFile(File file) {
        boolean result = false;

        Type itemsListType = new TypeToken<List<Good>>() {}.getType();

        try (Reader reader = new FileReader(file)) {
            List<Good> newGoods = gson.fromJson(reader, itemsListType);

            for (Good good : newGoods) {
                result = addGoodFromBase(good);
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return result;
    }

    private boolean addGoodFromBase(Good good) {
        boolean result;

        transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(good);
        transaction.commit();

        entityManager.close();

        result = true;

        return result;
    }
}
