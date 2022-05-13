package cn.juntai.wxpaydemo.service.impl;


import cn.juntai.wxpaydemo.bean.Order;
import cn.juntai.wxpaydemo.dao.OrderDao;
import cn.juntai.wxpaydemo.dao.StockDao;
import cn.juntai.wxpaydemo.dao.impl.OrderDaoImpl;
import cn.juntai.wxpaydemo.dao.impl.StockDaoImpl;
import cn.juntai.wxpaydemo.service.OrderService;
import cn.juntai.wxpaydemo.util.ConnectionHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {


    @Override
    public void newOrder(Order order, String item_id, String stock) {
        Connection conn = null;
        try {
            OrderDao orderDao = new OrderDaoImpl();
            StockDao stockDao = new StockDaoImpl();

            conn = ConnectionHandler.getConn();
            System.out.println("OrderService：" + conn.hashCode());
            //开启事务（必须先有Connection）
            conn.setAutoCommit(false);

            orderDao.newOrder(order);

            stockDao.updateStock(Integer.parseInt(item_id), Integer.parseInt(stock));

            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            try {
                conn.rollback();
                System.out.println("事务回滚");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                ConnectionHandler.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
