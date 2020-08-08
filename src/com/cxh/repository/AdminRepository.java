package com.cxh.repository;

import com.cxh.entity.Admin;

public interface AdminRepository {
    public Admin login(String username,String password);
}
