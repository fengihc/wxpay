package cn.feng.contorller;

import cn.feng.model.OrderBean;
import cn.feng.service.OrderService;
import cn.feng.util.JsonResult;
import cn.feng.util.RandomUtils;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @Author feng hihc@163.com
 * @Date 2018/12/27 13:50
 *  微信jsapi支付目录为http://xxx.com/.视个人设置喜好是否需要加项目名./order/
 *      该目录为二级或者三级目录
 */
@RequestMapping("/order")
@Controller
public class OrderPayController {

    private static final Logger log = LoggerFactory.getLogger(OrderPayController.class);

    @Autowired
    OrderService orderService;
    //没有经过测试,如果报错找不到Service使用接口实现类不使用@Autowired
    /*WxPayService wxPayService=new WxPayServiceImpl();*/
    @Autowired
    private WxPayService wxPayService;

    /**
     * @param orderBean  前端拿到订单参数请求过来
     *      获取预支付id
     * @return  返给前端包括预支付id等信息,前端获取后唤起微信支付
     */
    @RequestMapping(value = "/pay",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult addOrder(OrderBean orderBean) {
        //生成订单号
        String orderid = String.valueOf(System.currentTimeMillis())+ RandomUtils.getRandom();
        orderBean.setOrderid(Long.parseLong(orderid));
        String nonceStr = createNonceStr();
        //开始支付
        try{
            WxPayUnifiedOrderRequest orderRequest = new WxPayUnifiedOrderRequest();
            //放入微信必须获取的参数,如需要其他参数自行set
            orderRequest.setBody("任意支付订单");
            orderRequest.setOutTradeNo(String.valueOf(orderBean.getOrderid()));
            //  元转成分.微信要求int的以分为单位的类型,如果是double必须先强转为int
            // 不然会出现100.0造成sign签名异常
            orderRequest.setTotalFee(BaseWxPayRequest.yuanToFen(String.valueOf(orderBean.getPrize())));
            orderRequest.setOpenid(orderBean.getOpenid());
            //或者本地IP不建议使用127.0.0.1
            orderRequest.setSpbillCreateIp("服务器公网IP");
            orderRequest.setTradeType("JSAPI");
            orderRequest.setNotifyUrl(nonceStr);
            //看个人需求在该方法中某一步添加该订单至数据库
            orderService.addOrder(orderBean);
            return JsonResult.buildSuccessJsonResult("获取到预支付id",wxPayService.createOrder(orderRequest));
        } catch (Exception e) {
            log.error("微信支付失败！订单号：{},原因:{}",orderid, e.getMessage());
            e.printStackTrace();}
        return JsonResult.buildFailedJsonResult("支付失败，请稍后重试！");
    }
    /**
     *  微信异步回调
     * @param request
     * @param orderBean
     * @return
     */
    @ResponseBody
    @RequestMapping("/notify")
    public String payNotify(HttpServletRequest request, OrderBean orderBean,
                            HttpSession session) {
        try {
            String xmlResult = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());
            WxPayOrderNotifyResult result = wxPayService.parseOrderNotifyResult(xmlResult);
            System.out.println("微信异步回调"+request);
            // 结果正确
            String orderId = result.getOutTradeNo();
            //自己处理订单的业务逻辑，需要判断订单是否已经支付过，否则可能会重复调用
            WxPayOrderQueryResult result1 = wxPayService.queryOrder("", orderId);
            if (result1.getTradeState()!="SUCCESS"){
                return WxPayNotifyResponse.fail("处理失败");
            }
            Object sessionAttribute = session.getAttribute(orderId);
            if (sessionAttribute==null){
                //对数据库进行处理
                session.setAttribute(orderId,orderId);
            }
            return WxPayNotifyResponse.success("处理成功!");
        } catch (Exception e) {
            //log.error("微信回调结果异常,异常原因{}", e.getMessage());
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }

    /**
     *  取消订单和退款
     * @param orderBean
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public JsonResult delete( OrderBean orderBean) throws WxPayException {
        //在这里操作数据库或者使用其他后调用本方法进行操作
        //创建发送给微信参数的对象
        WxPayRefundRequest payRequest = new WxPayRefundRequest();
        //里面预填写信息
        String nonceStr = createNonceStr();
        //填写退款所需要的参数
        payRequest.setNonceStr(nonceStr);
        payRequest.setOutTradeNo(String.valueOf(orderBean.getOrderid()));
        payRequest.setTotalFee(BaseWxPayRequest.yuanToFen(String.valueOf(orderBean.getPrize())));
        payRequest.setRefundFee(BaseWxPayRequest.yuanToFen(String.valueOf(orderBean.getPrize())));
        //执行退款
        wxPayService.refund(payRequest);
        return JsonResult.buildSuccessJsonResult("订单已取消");
    }
    /**
     *      可以放在其他工具类中
     * @return 生成随机字符串,微信官方模板中也有
     *              (并没有什么鬼用,我百度找到的.官方东西看不懂系列)
     */
    public static String createNonceStr() {
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb=new StringBuffer();
        Random random=new Random();
        for (int i = 0; i < 16; i++) {
            int number  = random.nextInt(62);
            sb.append(chars.charAt(number));
        }
        return sb.toString();
    }
}
