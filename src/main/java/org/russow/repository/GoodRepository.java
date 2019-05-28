package org.russow.repository;

import org.russow.model.Good;

import java.util.List;

public interface GoodRepository<Entity extends Good> {

    List<Entity> getGoodsFromCategory(int id);
    boolean addGood(Entity entity);
}
