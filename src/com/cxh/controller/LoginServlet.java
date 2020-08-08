package com.cxh.controller;

import com.cxh.entity.Admin;
import com.cxh.entity.Book;
import com.cxh.entity.Borrow;
import com.cxh.entity.Reader;
import com.cxh.service.BookService;
import com.cxh.service.LoginService;
import com.cxh.service.impl.BookServiceImpl;
import com.cxh.service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private LoginService loginService = new LoginServiceImpl();
    private BookService bookService = new BookServiceImpl();
    /**
     * 处理登录的业务逻辑
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String type = req.getParameter("type");
        Object object = loginService.login(username,password,type);
        if(object != null){
            HttpSession session = req.getSession();
            switch (type){
                case "reader":
                    Reader reader = (Reader) object;
                    session.setAttribute("reader",reader);
//                    //跳转到读者的首页,先传数据进去再进行跳转
//                    List<Book> list = bookService.findAll(1);
//                    req.setAttribute("list",list);
//                    req.setAttribute("dataPrePage",6);
//                    req.setAttribute("currentPage",1);
//                    req.setAttribute("pages",bookService.getPages());
//                    req.getRequestDispatcher("index.jsp").forward(req,resp);
                    //把验证用户名密码和加载图书信息两个功能分开，避免高耦合，进行解耦合，提高代码的可扩展性
                    resp.sendRedirect("/book?page=1");

                    break;
                case "admin":
                    Admin admin = (Admin) object;
                    session.setAttribute("admin",admin);
                    //跳转到管理员的首页
                    //resp.sendRedirect("/admin?method=findAllBorrow&page=1");
                    resp.sendRedirect("/admin?method=findAllBorrow&page=1");

                    break;
            }
        }else{
            resp.sendRedirect("login.jsp");
        }

    }

}
