package com.example.dell.Dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public interface Dao {
    Connection getConn() throws Exception;
    void close(ResultSet res, Statement stm, Connection conn) throws Exception;
}
