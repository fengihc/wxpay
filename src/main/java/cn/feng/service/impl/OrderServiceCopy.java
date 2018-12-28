package cn.feng.service.impl;

import cn.feng.base.BaseServiceCopy;
import cn.feng.dao.OrderMapper;
import cn.feng.model.OrderBean;
import cn.feng.service.OrderService;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 15:25
 *  不使用OrderServiceImpl类使用本类
 *      需要加入@Service注解,并去掉OrderServiceImpl中的@Service和Autowired注解
 */
public class OrderServiceCopy extends  BaseServiceCopy<OrderMapper, OrderBean>implements OrderService {

}
