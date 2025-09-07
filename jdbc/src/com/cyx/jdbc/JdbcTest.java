package com.cyx.jdbc;

import java.sql.*;
import java.util.ArrayList;

public class JdbcTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String url = "jdbc:mysql://localhost:3306/exercise?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";

        //加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection(url,username,password);
        //连接上sql语句执行器
        Statement statement = connection.createStatement();


        /*//使用执行器执行查询获得一个结果集
        String sql = "SELECT account, balance, state FROM account";
        ResultSet result = statement.executeQuery(sql);
        ArrayList<Account> accounts = new ArrayList<>();
        while(result.next()) {//移动光标
            //通过名称获取列的值
            String account = result.getString("account");
            double balance = result.getDouble(2);
            String state = result.getString("state");
            Account a = new Account(account,balance,state);
            accounts.add(a);
        }
        accounts.forEach(System.out::println);
        result.close();*/

        String updateSql = "UPDATE account SET balance = balance + 1000 WHERE account=123457";
        //执行更新时返回的都是受影响的行数
        int affectedRows = statement.executeUpdate(updateSql);
        System.out.println(affectedRows);

        statement.close();
        connection.close();
    }
}
