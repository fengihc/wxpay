package cn.feng.util;

public class RandomUtils {
    public static int getRandom(){
        //获取一个6位数的随机数
        return  (int)((Math.random()*9+1)*100000);
    }
}
