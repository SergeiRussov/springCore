package org.russow.repository;

import org.russow.model.Good;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface GoodRepository<Entity extends Good> {

    List<Entity> getGoodsFromCategory(int id);
}
