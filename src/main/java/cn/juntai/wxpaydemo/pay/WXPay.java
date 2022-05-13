package cn.juntai.wxpaydemo.pay;

import cn.juntai.wxpaydemo.bean.Order;
import cn.juntai.wxpaydemo.sdk.WXPayUtil;
import cn.juntai.wxpaydemo.service.OrderService;
import cn.juntai.wxpaydemo.service.impl.OrderServiceImpl;
import cn.juntai.wxpaydemo.util.DateUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WXPay {

    String id;
    String name;
    String price;
    String amount;

    private static Log log = LogFactory.getLog(WXPay.class);

    private static final String PAY_SUCCESS = "SUCCESS";
    private static final String PAY_USERPAYING = "USERPAYING";

    /*public static void main(String[] args) throws Exception {

        // 生成二维码，完成支付
        unifiedOrder();
        // 商家扫用户手机的条形码
        //scanCodeToPay("");

    }*/

    /**
     * 扫码支付
     *
     * @throws Exception
     */
    public static String scanCodeToPay(String auth_code,String id,String name,String price,String amount) throws Exception {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String spbill_create_ip = addr.getHostAddress();

        MyConfig config = new MyConfig();
        cn.juntai.wxpaydemo.sdk.WXPay wxpay = new cn.juntai.wxpaydemo.sdk.WXPay(config);
        String out_trade_no = DateUtil.getCurrentTime();
        Map<String, String> map = new HashMap<>(16);
        map.put("attach", "id,"+id+";price,"+price+";amount,"+amount+";");
        map.put("auth_code", auth_code);
        map.put("body", name);
        map.put("device_info", "013467007045764");
        String nonce_str=WXPayUtil.generateNonceStr();
        map.put("nonce_str", nonce_str);
        map.put("out_trade_no", out_trade_no);
        map.put("spbill_create_ip", spbill_create_ip);
        float price_f=Float.valueOf(price)*100;
        int count=Integer.valueOf(amount);
        int total=(int)price_f*count;
        String total_fee =String.valueOf(total);
        map.put("total_fee", total_fee);
        //生成签名
        String sign = WXPayUtil.generateSignature(map, config.getKey());
        map.put("sign", sign);
        String mapToXml = null;
        try {
            //调用微信的扫码支付接口
            Map<String, String> resp = wxpay.microPay(map);
            System.out.println("扫码支付结果：" + resp);
            mapToXml = WXPayUtil.mapToXml(resp);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("微信支付失败" + e);
        }
        Map<String, String> data = new HashMap<>(16);
        data.put("out_trade_no", out_trade_no);
        data.put("appid","wxd9a46e74fc279fcc");
        data.put("mch_id", "1623889015");
        data.put("nonce_str",nonce_str);
        data.put("sign",sign);

        //调用微信的查询接口
        Thread.sleep(10000);/*线程停止10秒，即支付时间*/

        Map<String, String> orderQuery = wxpay.orderQuery(data);
        String orderResp = WXPayUtil.mapToXml(orderQuery);
        System.out.println("回调结果:::::::"+orderResp);

        String transaction_id=orderResp.substring(83,111);
        String mch_id=orderResp.substring(459,469);

        System.out.println(transaction_id);
        System.out.println(mch_id);

        String pattern=".+<time_end>(\\d{14})";
//        String pattern="";
//                ".+<mch_id>(\\d{10})";
//                ".+<out_trade_no>(\\d{14})"

//                ".+<transaction_id>(.+)</transaction_id>.+"+
//                        "<mch_id>(.+)</mch_id>.+"+
//                        "<out_trade_no>(.+)</out_trade_no>.+"+
//                        "<attach>(.+)</attach>.+"+
//                        "<time_end>(.+)</time_end>";

//        String pattern =
//                        ".+<transaction_id><!\\[CDATA\\[(\\d{28})"+
//                        "<mch_id><!\\[CDATA\\[(\\d{10}).+" +
//                        "<out_trade_no><!\\[CDATA\\[(.{32}).+" +
//                        "<attach><!\\[CDATA\\[(.+);.+" +
//                        "<time_end><!\\[CDATA\\[(\\d{14}).+";
//        String pattern =
//                ".+<attach><!\\[CDATA\\[(.+);.+" +
//                        "<mch_id><!\\[CDATA\\[(\\d{10}).+" +
//                        "<out_trade_no><!\\[CDATA\\[(.{32}).+" +
//                        "<time_end><!\\[CDATA\\[(\\d{14}).+" +
//                        "<transaction_id><!\\[CDATA\\[(\\d{28})";
        //System.out.println(pattern);
        Pattern r = Pattern.compile(pattern);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(orderResp);


        if (m.find()){
//            String transaction_id = m.group(1);
//            System.out.println(transaction_id);
//
//            String mch_id=m.group(2);
//            System.out.println(mch_id);

            String orderTime=m.group(1);
            System.out.println(orderTime);


//            String item_id = id;
//            System.out.println(item_id);
//
//            String item_price = price;
//            System.out.println(item_price);
//
//            String item_amount = amount;
//            System.out.println(item_amount);


            float Order_price=Float.parseFloat(price)*Integer.parseInt(amount);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Timestamp timestamp = new Timestamp(sdf.parse(orderTime).getTime());

            Order order = new Order();
            String ID = UUID.randomUUID().toString().replace("-", "");
            order.setId(ID);//会自动生成32个字符
            order.setMch_id(Integer.parseInt(mch_id));
            order.setOut_trade_no(out_trade_no);
            order.setOrder_time(timestamp);
            order.setTransaction_id(transaction_id);
            order.setUserId(0);
            order.setItem_id(Integer.parseInt(id));
            order.setItem_price(Float.parseFloat(price));
            order.setAmount(Integer.parseInt(amount));
            order.setOrder_price(Order_price);

            OrderService orderService = new OrderServiceImpl();
            orderService.newOrder(order, id, amount);

        }
        //判断支付是否成功
        /*String return_code = null;
        String result_code = null;
        String err_code_des = null;
        String err_code = null;
        //获取Document对象（主要是获取支付接口的返回信息）
        Document doc = DocumentHelper.parseText(mapToXml);
        //获取对象的根节点<xml>
        Element rootElement = doc.getRootElement();
        //获取对象的子节点
        List<Element> elements = rootElement.elements();
        for (Element element : elements) {
            if (element.getName().equals("return_code")) {
                return_code = element.getTextTrim();
            } else if (element.getName().equals("result_code")) {
                result_code = element.getTextTrim();
            } else if (element.getName().equals("err_code_des")) {
                err_code_des = element.getTextTrim();
            } else if (element.getName().equals("err_code")) {
                err_code = element.getTextTrim();
            }
        }*/
        /*if (PAY_SUCCESS.equals(return_code) && PAY_SUCCESS.equals(result_code)) {
            log.info("微信免密支付成功！");
            return PAY_SUCCESS;
        } else if (PAY_USERPAYING.equals(err_code)) {
            for (int i = 0; i < 4; i++) {
                Thread.sleep(3000);
                Map<String, String> data = new HashMap<>(16);
                data.put("out_trade_no", out_trade_no);
                //调用微信的查询接口
                Map<String, String> orderQuery = wxpay.orderQuery(data);
                String orderResp = WXPayUtil.mapToXml(orderQuery);
                String trade_state = null;
                //获取Document对象
                Document orderDoc = DocumentHelper.parseText(orderResp);
                //获取对象的根节点<xml>
                Element rootElement1 = orderDoc.getRootElement();
                //获取对象的子节点
                List<Element> elements1 = rootElement1.elements();
                for (Element element : elements1) {
                    if (element.getName().equals("trade_state")) {
                        trade_state = element.getTextTrim();
                    }
                }
                if (PAY_SUCCESS.equals(trade_state)) {
                    log.info("微信加密支付成功！");
                    return PAY_SUCCESS;
                }
                log.info("正在支付" + orderResp);
            }
        }
        log.error("微信支付失败！");*/
        return "";
    }
    /*
    下单：生成二维码
     */

    public static void unifiedOrder(String id,String name,String price,String amount) {
        Map<String, String> resultMap = new HashMap();
        String openid = "ouR0E1oP5UGTEBce8jZ_sChfH26g";
        MyConfig config = null;
        cn.juntai.wxpaydemo.sdk.WXPay wxpay = null;
        try {
            config = new MyConfig();
            wxpay = new cn.juntai.wxpaydemo.sdk.WXPay(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //生成的随机字符串
        String nonce_str = WXPayUtil.generateNonceStr();
        //获取客户端的ip地址
        //获取本机的ip地址
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String spbill_create_ip = addr.getHostAddress();
        //支付金额，需要转成字符串类型，否则后面的签名会失败
        float price_f=Float.valueOf(price)*100;
        int count=Integer.valueOf(amount);
        int total=(int)price_f*count;
        String total_fee =String.valueOf(total);//金额
        //商品描述
        String body = name;
        //商户订单号
        String out_trade_no = WXPayUtil.generateNonceStr();
        //统一下单接口参数
        SortedMap<String, String> data = new TreeMap<String, String>();
        data.put("appid", "wxd9a46e74fc279fcc");
        data.put("body", body);
        data.put("mch_id", "1623889015");
        // 回调接口，必须是一个域名，不能使用IP
        // 腾讯会自动调用你（程序自己提供的接口）的接口，给你发送支付结果的数据，数据格式：xml格式
        data.put("notify_url", "http://4774k86o55.zicp.fun/result");
        data.put("out_trade_no", out_trade_no);//交易号
        data.put("spbill_create_ip", spbill_create_ip);//下单的电脑IP地址
        data.put("trade_type", "NATIVE");//支付类型
        data.put("total_fee",total_fee);
        String p="id,"+id+";price,"+price+";amount,"+amount+";";
        data.put("attach",p);
        //data.put("openid", openid);

        try {
            Map<String, String> rMap = wxpay.unifiedOrder(data);
            System.out.println("统一下单接口返回: " + rMap);
            createQRCode(rMap);//生成二维码
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createQRCode(Map<String, String> map) throws Exception {

        File outputFile = new File("/Users/123/" + File.separator + "new.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        String url = map.get("code_url");
        System.out.println("生成二维码的url：" + url);
        try {
            Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            hints.put(EncodeHintType.MARGIN, 0);
            BitMatrix bitMatrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE, 200, 200, hints);

            MatrixToImageWriter.writeToStream(bitMatrix, "jpg", fileOutputStream);
        } catch (Exception e) {
            throw new Exception("生成二维码失败！");
        } finally {
            fileOutputStream.close();
        }
    }
}