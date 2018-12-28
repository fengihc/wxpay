package cn.feng.annotation;

import java.lang.annotation.*;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 15:17
 *  初始化继承BaseService的service
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BaseService {
}