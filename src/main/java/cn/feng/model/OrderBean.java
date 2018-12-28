package cn.feng.model;

import lombok.Data;
/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 14:49
 *  使用lombok插件的@Data标签代替普通的get,set,tostring等方法
 *      提高代码简洁性,提高开发效率
 *      这里只给出了最基础的实体类,其他需求自己添加
 */
@Data
public class OrderBean {

    private Integer id;

    private Long orderid;

    private Integer status;

    private Double prize;

    private String openid;

}