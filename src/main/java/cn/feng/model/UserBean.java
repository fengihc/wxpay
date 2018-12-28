package cn.feng.model;

import lombok.Data;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/28 11:22
 *  这里只给出了最基础的实体类,其他需求自己添加
 */
@Data
public class UserBean {
    private Integer id;

    private String nickname;

    private String accessToken;

    private String refreshToken;

    private String openid;

    private String sex;

    private Double wallet;

    private String headimgurl;

    private String province;

    private String city;

}
