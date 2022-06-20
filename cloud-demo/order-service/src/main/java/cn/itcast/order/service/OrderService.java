package cn.itcast.order.service;

import cn.itcast.order.mapper.OrderMapper;
import cn.itcast.order.pojo.Order;
import feign.clients.UserClient;
import feign.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedList;
import java.util.Queue;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserClient userClient;

    public Order queryOrderById(Long orderId) {
        // 1.查询订单
        Order order = orderMapper.findById(orderId);
        //2.利用RestTemplate发起Http请求，查询用户
        //2.1 url路径
        //String url="http://localhost:8081/user/"+order.getUserId();
        //String url="http://userservice/user/"+order.getUserId();//发现微服务，避免硬编码ip地址
        //2.2 发送http请求
        //User user=restTemplate.getForObject(url, User.class);

        //2.使用Feign远程调用
        User user=userClient.findById(order.getUserId());

        //3. 封装user到Order
        order.setUser(user);

        // 4.返回
        return order;
    }
}
