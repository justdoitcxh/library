package com.cxh.service.impl;

import com.cxh.entity.Reader;
import com.cxh.repository.AdminRepository;
import com.cxh.repository.ReaderRepository;
import com.cxh.repository.impl.AdminRepositoryImpl;
import com.cxh.repository.impl.ReaderRepositoryImpl;
import com.cxh.service.LoginService;

public class LoginServiceImpl implements LoginService {
    private ReaderRepository readerRepository = new ReaderRepositoryImpl();
    private AdminRepository adminRepository = new AdminRepositoryImpl();
    @Override
    public Object login(String username, String password,String type) {
        Object object = null;
        switch (type){
            case "reader":
                object = readerRepository.login(username,password);
                break;
            case "admin":
                object = adminRepository.login(username,password);
                break;
        }
        return object;
    }
}
