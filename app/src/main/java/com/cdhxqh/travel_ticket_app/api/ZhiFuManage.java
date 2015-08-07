package com.cdhxqh.travel_ticket_app.api;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.cdhxqh.travel_ticket_app.app.PayResult;
import com.cdhxqh.travel_ticket_app.app.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Administrator on 2015/8/6.
 */
public class ZhiFuManage {
    /**
     *支付宝
     */


    private ProgressDialog progressDialog;

    //商户PID
    public static final String PARTNER = "2088021273300361";
    //商户收款账号
    public static final String SELLER = "help@bjhxqh.com";
    //商户私钥，pkcs8格式
    public static final String RSA_PRIVATE =
            "MIICXQIBAAKBgQCvzFxwulUyYy/6lRFMyo2zk/SE5hxOGBoGs00WbzTEC18lnnPw" +
                    "TOTO7rbZEsaA2iJ5RQL/91YiODYUG44w1583vSIW+jlsxiqkccdkcSa0GL2RwbIC" +
                    "ydqo9cJW9qlGjZVrEuMJLqA6grC3OJ27ZB8sQHP33hw4n5BrqrV9hyb3/wIDAQAB" +
                    "AoGBAJkux7Xqh2Zai2ocDnENZp/94mkTYcKiubrw7dEIob/Z8NSE152iQxYVZ/qK" +
                    "o7GPYc6t1clYqhMzS9wFjsb8e9zSCgXXyHliUYu77B2v/73RVt5OGy4h9flEA+mc" +
                    "BCEq/52SDimGuUseujpxPDCK7R4ofy0H/47BoFHT86jPyO7RAkEA2A9cKl0WxXdL" +
                    "/fWT+kg0Y4e4fEgRl9cQQtSbMfoOx7539gc59KG07AvupCrqS8cyiyh80km9M/cz" +
                    "4AA7uNelQwJBANBLsJwZjJCfxeHW4bkTNaL9Ifz9lKU7m/QdExGFulYhpmcoxgzy" +
                    "Sw9KSWAxbilhUmZBZ7zScV+SEND8g4AsmJUCQDj0c5tHRsVvGT0tC4Ein4zNLji0" +
                    "1s5sBwFXAkI+ZL8K2pykcwalygefbZ0hIvou3IgKeD0G6zVTqP0Xkqa7UfkCQDmM" +
                    "oAgQ+wg+TJMAJq9Wwlv86jYW7cnVNVp16f3OX9RKScp4tnd/PgPEd49vOMuQCw8b" +
                    "hQjjb9WC6siC6+Yu7B0CQQCSFVh+zQcOb890qdb3dxgqpxnvnWDv4aqbm5t1aDSH" +
                    "HVyXW9qTwVlquVQV53ga6VFeqt8t0i7wHlyFRLkmbN7K";
    //支付宝公钥
    public static final String RSA_PUBLIC = "";

    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;


    /**
     * call alipay sdk pay. 调用SDK支付
     *
     */
    public static void pay( final Activity cxt, final Handler mHandler, String out_trade_no, String subject, String body, String price) {
        // 订单
        String orderInfo = getOrderInfo(out_trade_no, subject, body, price);
        // 对订单做RSA 签名
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(cxt);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * create the order info. 创建订单信息
     *
     */
    public static String getOrderInfo(String out_trade_no, String subject, String body, String price) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     *
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
