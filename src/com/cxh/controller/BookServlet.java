package com.cxh.controller;

import com.cxh.entity.Book;
import com.cxh.entity.Borrow;
import com.cxh.entity.Reader;
import com.cxh.service.BookService;
import com.cxh.service.impl.BookServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private BookService bookService = new BookServiceImpl();

    /**
     * 加载图书数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        if (method == null){
            method = "findAll";
        }
        HttpSession session = req.getSession();
        Reader reader = (Reader) session.getAttribute("reader");
        //流程控制
        switch (method){
            case "findAll":
                String pageStr = req.getParameter("page");
                Integer page = Integer.parseInt(pageStr);
                List<Book> list = bookService.findAll(page);
                req.setAttribute("list",list);
                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages",bookService.getPages());
                req.getRequestDispatcher("index.jsp").forward(req,resp);
                break;
            case "addBorrow":
                //如何判断一个方法里面调用多少次业务：看bookService调用了多少次
                //一般只允许调用一次，不然多次调用会有高度耦合
                String bookidStr = req.getParameter("bookid");
                Integer bookid = Integer.parseInt(bookidStr);
                //添加借书请求
                bookService.addBorrow(bookid,reader.getId());
                resp.sendRedirect("/book?method=findAllBorrow&page=1");
                break;
            case "findAllBorrow":
                pageStr = req.getParameter("page");
                page = Integer.parseInt(pageStr);
                //展示当前用户的所有借书记录
                //得到的model数据
                List<Borrow> borrowList = bookService.findAllBorrowByReaderId(reader.getId(),page);
                //传输到jsp页面上进行展示
                req.setAttribute("list",borrowList);
                req.setAttribute("dataPrePage",6);
                req.setAttribute("currentPage",page);
                req.setAttribute("pages",bookService.getBorrowPages(reader.getId()));
                req.getRequestDispatcher("borrow.jsp").forward(req,resp);
                break;
        }

    }

}
