package cn.feng.base;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 14:30
 *  通用的service层
 */
public interface BaseService<Bean> {

    /**
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    int addOrder(Bean bean);
}
