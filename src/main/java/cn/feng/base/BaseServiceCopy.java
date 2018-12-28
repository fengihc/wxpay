package cn.feng.base;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 16:12
 *  在这里重写service中需要的与现有BaseServiceImpl不同逻辑的方法
 *      并且子类继承本抽象类,并加入@Service注解
 *          不继承BaseServiceImpl类
 */
public abstract class BaseServiceCopy <Mapper,Bean> implements BaseService<Bean>{

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }
}
