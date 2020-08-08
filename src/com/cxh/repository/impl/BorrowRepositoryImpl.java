package com.cxh.repository.impl;

import com.cxh.entity.Book;
import com.cxh.entity.BookCase;
import com.cxh.entity.Borrow;
import com.cxh.entity.Reader;
import com.cxh.repository.BorrowRepository;
import com.cxh.utils.JDBCTools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BorrowRepositoryImpl implements BorrowRepository {
    @Override
    public void insert(Integer bookid, Integer readerid, String borrowtime, String returntime, Integer adminid, Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "insert into borrow(bookid,readerid,borrowtime,returntime,state) values(?,?,?,?,0)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,bookid);
            preparedStatement.setInt(2,readerid);
            preparedStatement.setString(3,borrowtime);
            preparedStatement.setString(4,returntime);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(connection,preparedStatement,null);
        }
    }

    @Override
    public List<Borrow> findAllByReaderId(Integer id,Integer index,Integer limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT br.id,b.name,b.author,b.publish,br.borrowtime,br.returntime,r.name,r.tel,r.cardid,br.state FROM borrow br,book b,reader r WHERE readerid = ? AND b.id = br.bookid AND r.id = br.readerid limit ?,?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Borrow> list = new ArrayList<>();
        try {
            statement =connection.prepareStatement(sql);
            statement.setInt(1,id);
            statement.setInt(2,index);
            statement.setInt(3,limit);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                //取出所有的素材
//                int borrowId = resultSet.getInt(1);
//                String bookName = resultSet.getString(2);
//                String author = resultSet.getString(3);
//                String publish = resultSet.getString(4);
//                String borrowTime = resultSet.getString(5);
//                String returnTime = resultSet.getString(6);
//                String readerName = resultSet.getString(7);
//                String tel = resultSet.getString(8);
//                String cardId= resultSet.getString(9);
//                //封装
//                Book book = new Book(bookName,author,publish);
//                Reader reader = new Reader(readerName,tel,cardId);
//                Borrow borrow = new Borrow(borrowId,book,reader,borrowTime,returnTime);
//                list.add(borrow);
                //节省栈内存空间的简化代码
                list.add(new Borrow(resultSet.getInt(1),new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
                        new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)),resultSet.getString(5),resultSet.getString(6),resultSet.getInt(10)));
//                new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
//                new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9));
//                new Borrow(resultSet.getInt(1),new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
//                        new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)),resultSet.getString(5),resultSet.getString(6));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return list;
    }

    @Override
    public int count(Integer readerid) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT count(*) FROM borrow br,book b,reader r WHERE readerid = ? AND b.id = br.bookid AND r.id = br.readerid";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,readerid);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return count;
    }

    @Override
    public List<Borrow> findAllByState(Integer state,Integer index,Integer limit) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT br.id,b.name,b.author,b.publish,br.borrowtime,br.returntime,r.name,r.tel,r.cardid,br.state FROM borrow br,book b,reader r WHERE state = ? AND b.id = br.bookid AND r.id = br.readerid limit ?,?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Borrow> list = new ArrayList<>();
        try {
            statement =connection.prepareStatement(sql);
            statement.setInt(1,state);
            statement.setInt(2,index);
            statement.setInt(3,limit);
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                list.add(new Borrow(resultSet.getInt(1),new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
                        new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)),resultSet.getString(5),resultSet.getString(6),resultSet.getInt(10)));
//                new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4));
//                new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9));
//                new Borrow(resultSet.getInt(1),new Book(resultSet.getString(2),resultSet.getString(3),resultSet.getString(4)),
//                        new Reader(resultSet.getString(7),resultSet.getString(8),resultSet.getString(9)),resultSet.getString(5),resultSet.getString(6));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return list;
    }

    @Override
    public int countByState(Integer state) {
        Connection connection = JDBCTools.getConnection();
        String sql = "SELECT count(*) FROM borrow br,book b,reader r WHERE state = ? AND b.id = br.bookid AND r.id = br.readerid";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int count = 0;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,state);
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                count = resultSet.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,resultSet);
        }
        return count;
    }

    @Override
    public void handle(Integer borrowId, Integer state, Integer adminId) {
        Connection connection = JDBCTools.getConnection();
        String sql = "update borrow set state = ?,adminid = ? where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1,state);
            statement.setInt(2,adminId);
            statement.setInt(3,borrowId);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCTools.release(connection,statement,null);
        }
    }
}
