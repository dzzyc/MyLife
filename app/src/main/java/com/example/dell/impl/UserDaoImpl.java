package com.example.dell.impl;

import com.example.dell.Dao.UserDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDao implements UserDao {

    public List<User> getUser(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<User> users = null;
        try{
            connection = getConn();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from user order by user_id");
            users = new ArrayList<User>();
            while (resultSet.next()){
                User user = new User();
                user.getId(resultSet.getString("user_id"));
                user.getName(resultSet.getString("user_name"));
                boolean add = users.add(user);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            close(resultSet,statement,connection);
        }
        return users;
    }
}
