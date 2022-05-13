package cn.juntai.wxpaydemo.service;

import cn.juntai.wxpaydemo.bean.Order;

import java.sql.SQLException;

public interface OrderService {
    void newOrder(Order order, String item_id, String stock) throws SQLException;
}
