package com.cxh.repository;

import com.cxh.entity.Reader;

public interface ReaderRepository {
    public Reader login(String username,String password);
}
