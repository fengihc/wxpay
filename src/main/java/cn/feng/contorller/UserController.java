package cn.feng.contorller;

import cn.feng.config.WxPayProperties;
import cn.feng.model.OrderBean;
import cn.feng.model.UserBean;
import cn.feng.service.OrderService;
import cn.feng.util.DateUtils;
import cn.feng.util.JsonResult;
import cn.feng.util.RandomUtils;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.EntPayService;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.EntPayServiceImpl;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 13:50
 *  微信授权
 *      报错是因为user使用的相关类都没有写,如有需要自己写入即可
 */
@RequestMapping("/user")
@Controller
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    //@Autowired
    //UserService userService;
    //没有经过测试,如果报错找不到Service使用接口实现类不使用@Autowired
    /*WxMpService wxMpService=new WxMpServiceImpl();*/
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    WxPayProperties wxPayProperties;

    private WxPayService payService = new WxPayServiceImpl();

    private EntPayService entPayService = new EntPayServiceImpl(payService);

    /**
     * 访问http://域名.com/user/authorize?returnUrl=http://asdfg.wezoz.com/你的首页展示地址
     * 微信网页授权流程:
     * 1. 用户同意授权,获取 code
     * 2. 通过 code 换取网页授权 access_token
     * 3. 使用获取到的 access_token 和 openid 拉取用户信息
     *
     *      String url="http:///yilianmengbi.wezoz.com/user/userInfo";
     *      本人在使用内网穿透的时候需要使用3个/
     *      为什么是3个/原因未知,2个/会报returnUrl参数错误
     *
     */
    @RequestMapping("/authorize")
    public String getAccessToken(@RequestParam("returnUrl") String returnUrl) {
        //使用的weNet内网穿透
        String url="http:///yilianmengbi.wezoz.com/user/userInfo";
        log.info(url);
        String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, returnUrl);
        log.info("【微信网页授权】获取code,redirectUrl={}",redirectUrl);
        //发出获取全部信息的授权链接 重定向到下面一个方法
        return "redirect:"+redirectUrl;
    }

    /**
     * @param code          上面方法重定向到本方法
     * @param returnUrl
     * @return    根据需求更改,建议重定向给openid,
     *              再让前端请求到其他方法获取该用户数据
     *              或者其他数据等返回给前端
     */
    @RequestMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl){
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=null;
        try {
            //wxMpOAuth2AccessToken一个实体类 建议看一下源码,可以知道其中的参数
            wxMpOAuth2AccessToken=wxMpService.oauth2getAccessToken(code);
            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            //wxMpUser中获取到实体类数据,可以直接存入数据库
            //也可以自定义userBean对象从wxMpUser中取出数据
            //userService.insert(wxMpUser); 或者  userService.insert(userBean);

        }catch (WxErrorException e){
            log.error("【微信网页授权】,{}",e);
        }
        //"你想跳转的网页或者放入json对象传回前端";
        return "redirect:"+returnUrl+"其他参数比如openID";

    }


    /**
     * 提现
     *   企业付款到零钱
     */
    @ResponseBody
    @RequestMapping(value = "/cash",method = RequestMethod.POST)
    public JsonResult cashUser(UserBean userBean) throws WxPayException {
        String openid = userBean.getOpenid();
        //本控制层中的一个根据openID从数据库获取单个实体类的方法
        //获取到用户最新数据
        UserBean bean =null;
        //bean = (UserBean)selectUser(openid).getObject();
        if(userBean.getWallet()<bean.getWallet()){
            //提现金额有错误
            return JsonResult.buildFailedJsonResult("提现金额有误,异常");
        }
        //这里请以自己的方式对数据库进行操作

        //执行提现操作
        String nonceStr =OrderPayController.createNonceStr();
        EntPayRequest payRequest = new EntPayRequest();
        payRequest.setMchAppid(wxPayProperties.getAppId());
        payRequest.setMchId(wxPayProperties.getMchId());
        payRequest.setNonceStr(nonceStr);
        //生成订单号
        String order = String.valueOf(System.currentTimeMillis())+ RandomUtils.getRandom();
        payRequest.setPartnerTradeNo(order);
        payRequest.setOpenid(bean.getOpenid());
        payRequest.setCheckName("NO_CHECK");
        payRequest.setAmount((int)(userBean.getWallet()*100));
        payRequest.setDescription("提现");
        payRequest.setSpbillCreateIp("120.193.23.130");
        EntPayResult payResult = entPayService.entPay(payRequest);
        log.info("返回结果参数",payResult);
        if (payResult.getPaymentTime()==null){
            //企业付款失败
            return JsonResult.buildFailedJsonResult("企业付款失败");
        }
        //对数据库进行操作

        return JsonResult.buildSuccessJsonResult("提现申请成功");
    }
}
