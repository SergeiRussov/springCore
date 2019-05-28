package org.russow.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.russow.model.Good;
import org.russow.repository.GoodRepository;
import org.russow.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

@Service
@Slf4j
public class GoodServiceImpl implements GoodService {

    private GoodRepository goodRepository;

    @Autowired
    public GoodServiceImpl(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private Gson gson;

    @Override
    public boolean addGoodsFromFile(File file) {
        boolean result = false;

        Type itemsListType = new TypeToken<List<Good>>() {}.getType();

        try (Reader reader = new FileReader(file)) {
            List<Good> newGoods = gson.fromJson(reader, itemsListType);

            for (Good good : newGoods) {
                result = goodRepository.addGood(good);
            }
        } catch (FileNotFoundException e) {
            log.error(e.getMessage());
            result = false;
        } catch (IOException e) {
            log.error(e.getMessage());
            result = false;
        } catch (Exception e) {
            log.error(e.getMessage());
            result = false;
        }

        return result;
    }
}
