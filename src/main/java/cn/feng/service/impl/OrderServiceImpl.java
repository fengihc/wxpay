package cn.feng.service.impl;

import cn.feng.annotation.BaseService;
import cn.feng.base.BaseServiceImpl;
import cn.feng.dao.OrderMapper;
import cn.feng.model.OrderBean;
import cn.feng.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 14:39
 *  同OrderService 基本逻辑都写在BaseServiceImpl中
 *      如果需要重写某个方法建议继承BaseServiceCopy
 *          或者写入BaseServiceImpl中
 */
@Service
@Transactional
@BaseService
public class OrderServiceImpl extends BaseServiceImpl<OrderMapper, OrderBean> implements OrderService {

    @Autowired
    OrderMapper orderMapper;
}
