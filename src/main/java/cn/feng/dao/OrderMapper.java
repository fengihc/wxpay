package cn.feng.dao;

import cn.feng.model.OrderBean;

import java.util.List;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 15:10
 *  用到的方法与BaseService中的参数和返回值对应就行
 *      用不到的方法可以不用写
 *      根据需求加入和配置参数和返回值
 */
public interface OrderMapper {

    int deleteByPrimaryKey(OrderBean record);

    int insert(OrderBean record);

    OrderBean selectByPrimaryKey(long orderid);

    List<OrderBean> selectAll(OrderBean record);

    int updateByPrimaryKey(OrderBean record);

    int getCount(OrderBean record);

    List<OrderBean> getList(OrderBean orderBean);
}
