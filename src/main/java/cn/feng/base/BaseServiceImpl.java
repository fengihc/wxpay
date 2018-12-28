package cn.feng.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 14:30
 *  实现service接口的基类
 */
public abstract class BaseServiceImpl<Mapper,Bean> implements BaseService<Bean>{

    //mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass())      实体类中的单个属性
    //mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", bean.getClass())    实体类
    public Mapper mapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        try {
            Method deleteByPrimaryKey = mapper.getClass().getDeclaredMethod("deleteByPrimaryKey", id.getClass());
            Object invoke = deleteByPrimaryKey.invoke(mapper);
            return Integer.valueOf(String.valueOf(invoke));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int addOrder(Bean bean) {
        return 0;
    }

    @Override
    public Bean selectBean(Bean bean) {
        try {
            Method selectBean = mapper.getClass().getDeclaredMethod("selectBean", bean.getClass());
            Object invoke = selectBean.invoke(mapper);
            return (Bean) invoke;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Bean> list(Bean bean) {
        return null;
    }


}
