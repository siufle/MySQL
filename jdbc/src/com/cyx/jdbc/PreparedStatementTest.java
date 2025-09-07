package com.cyx.jdbc;

import java.sql.*;
import java.util.Scanner;

public class PreparedStatementTest {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入商品名称");
        String goodsName = sc.next();

        String url = "jdbc:mysql://localhost:3306/exercise?serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "root";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url,username,password);
        String sql = "SELECT id, name, number, price, agent_id FROM goods WHERE name = ? LIMIT 0, 20";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,goodsName);

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            int number = resultSet.getInt("number");
            double price = resultSet.getDouble("price");
            long agentId = resultSet.getLong("agent_id");
            System.out.println(id + ", " + name + ", " + number + ", " + price + ", " + agentId);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
