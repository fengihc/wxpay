package cn.feng.base;


/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 14:30
 *  实现service接口的基类
 */
public abstract class BaseServiceImpl<Mapper,Bean> implements BaseService<Bean>{

    public Mapper mapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }
    //mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass())      实体类中的单个属性
    //mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", bean.getClass())    实体类

}
