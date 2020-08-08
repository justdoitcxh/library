package com.cxh.service;

import com.cxh.entity.Book;
import com.cxh.entity.Borrow;
import com.cxh.service.impl.BookServiceImpl;
import org.omg.CORBA.INTERNAL;

import java.util.List;

public interface BookService {

    public List<Book> findAll(int page);
    public int getPages();
    public int getBorrowPages(Integer readerid);
    public void addBorrow(Integer bookid,Integer readerid);
    public List<Borrow> findAllBorrowByReaderId(Integer id,Integer page);
    public List<Borrow> findAllBorrowByState(Integer state,Integer page);
    public int getBorrowPagesByState(Integer state);
    public void handleBorrow(Integer borrowId, Integer state, Integer adminId);

}
