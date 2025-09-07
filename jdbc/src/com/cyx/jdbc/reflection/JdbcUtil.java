package com.cyx.jdbc.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUtil {

    private static final String url = "jdbc:mysql://localhost:3306/exercise?serverTimezone=Asia/Shanghai";
    private static final String username = "root";
    private static final String password = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("驱动加载失败");
        }

    }

    public static void main(String[] args) {
        String sql = "SELECT id, name, number, price, agent_id FROM goods WHERE name LIKE ? AND price > ?";
        Object[] params = {"%魅%", 2000.00};
        List<Goods> goodsList = query(sql, Goods.class, params);
        //goodsList.forEach(System.out::println);

        sql = "SELECT id, name, region_id regionId FROM agent WHERE name LIKE ?";
        params = new Object[]{"%魅%"};
        List<Agent> agentList = query(sql, Agent.class, params);
        agentList.forEach(System.out::println);
    }

    public static int update(String sql, Object... params) {
        int result = 0;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = createPreparedStatement(connection, sql, params);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(preparedStatement, connection);
        }
        return result;
    }

    /**
     * 关闭连接，执行器，结果集
     * @param closeables
     */
    public static void close(AutoCloseable... closeables) {
        if(closeables != null && closeables.length > 0) {
            for (AutoCloseable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 创建执行器PreparedStatement对象
     * @param connection
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static PreparedStatement createPreparedStatement(Connection connection,String sql, Object... params) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        return preparedStatement;
    }

    /**
     * 万能查询通过反射实现，必须保证类的字段名与查询结果展示的类名称保持一致
     *
     * @param sql
     * @param clazz
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> query(String sql, Class<T> clazz, Object... params) {

        List<T> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = createPreparedStatement(connection, sql, params);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                T t = createInstance(clazz, resultSet);
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            close(resultSet, preparedStatement, connection);
        }
        return list;
    }

    /**
     * 根据反射创建对象
     * @param clazz
     * @param resultSet
     * @return
     * @param <T>
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static<T> T createInstance(Class<T> clazz, ResultSet resultSet) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<T> c = clazz.getConstructor();
        T t = c.newInstance();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String filedName = field.getName();
            String methodName = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
            Method method = clazz.getMethod(methodName, field.getType());
            method.setAccessible(true);
            try {
                Object value = resultSet.getObject(filedName, field.getType());
                method.invoke(t, value);
            } catch (Exception e) {
            }
        }
        return t;
    }

    /*public static List<Goods> getGoods() {

        ArrayList<Goods> goodsList = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT id, name, number, price, agent_id FROM goods WHERE name LIKE ? AND price > ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%米%");
            preparedStatement.setDouble(2, 2000.00);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Goods goods = new Goods();
                goods.setId(resultSet.getInt("id"));
                goods.setName(resultSet.getString("name"));
                goods.setNumber(resultSet.getInt("number"));
                goods.setPrice(resultSet.getDouble("price"));
                goods.setAgentId(resultSet.getInt("agent_id"));
                goodsList.add(goods);
            }
            close(connection, preparedStatement, resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        goodsList.forEach(System.out::println);
        return goodsList;
    }

    public static List<Agent> getAgents() {
        ArrayList<Agent> agents = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            String sql = "SELECT id, name, region_id FROM agent WHERE name LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%米%");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Agent agent = new Agent();
                agent.setId(resultSet.getInt("id"));
                agent.setName(resultSet.getString("name"));
                agent.setRegionId(resultSet.getInt("region_id"));
                agents.add(agent);
            }
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        agents.forEach(System.out::println);
        return agents;
    }*/
}
