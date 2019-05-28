package org.russow.service;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public interface GoodService {

    boolean addGoodsFromFile(File file);
}
