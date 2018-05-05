package com.example.dell.servlet;

import com.example.dell.Dao.UserDao;
import com.example.dell.impl.User;
import com.example.dell.impl.UserDaoImpl;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = -7116871746214279602L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        UserDao dao = new UserDaoImpl();
        List<User> list = dao.getUser();
        ResponseObject result = null;
        if (list != null && list.size() > 0){
            result = new ResponseObject(1,list);
        }else{
            result = new ResponseObject(0,"没有数据");
        }
        out.println(new GsonBuilder().create().toJson(result));
        out.flush();
        out.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{
        doGet(request,response);
    }
}
