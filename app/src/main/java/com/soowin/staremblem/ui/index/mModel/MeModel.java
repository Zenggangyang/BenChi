package com.soowin.staremblem.ui.index.mModel;

import android.content.Intent;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.soowin.staremblem.http.HttpTool;
import com.soowin.staremblem.utils.PublicApplication;
import com.soowin.staremblem.utils.StrUtils;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by hxt on 2018/3/15 0015.
 */

public class MeModel {
    private static final String TAG = MeModel.class.getCanonicalName();
/*POST*/

    /**
     * 测试地址
     */
    public static String testPost() throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("deviceid", "imei")
                .add("mobile", "test")
                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.testPost);
        Logger.e(TAG + "测试地址" + result);
        return result;
    }

    /**
     * 测试地址
     */
    public static String testGet() throws Exception {
        String parmes = "?" + "cardNo=PAADQHEF&cityCode=PEK&serviceType=1";
        String result = HttpTool.okGet(PublicApplication.urlData.carlist + parmes);
        Logger.e(TAG + "测试地址" + result);
        return result;
    }

    /**
     * 上传头像
     */
    public static String LoginInfo(String mobile, String name, String sex, String headimgurl) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("mobile", mobile)
                .add("name", name)
                .add("sex", sex)
                .add("headimgurl", headimgurl)
                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.loginInfo);
        Logger.e(TAG + "上传头像" + result);
        return result;
    }

    /**
     * 删除卡券
     */
    public static String DelteCard(String CardNo) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("CardNo", CardNo)

                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.DeleteCard);
        Logger.e(TAG + "删除卡券" + result);
        return result;
    }

    /**
     * 订单支付
     * post
     */
    public static String OrderPay(String orderID, String payPrice, String payNo, String payType, String PriceDetails) throws Exception {

        /*联合支付*/
        RequestBody body1 = new FormBody.Builder()
                .add("orderID", orderID)
                .add("payPrice", payPrice)
                .add("payNo", payNo)
                .add("payType", payType)
                .add("PriceDetails", PriceDetails)
                .build();
        String result = HttpTool.okPost(body1, PublicApplication.urlData.pay);
        Logger.e(TAG + "订单支付" + result);
        return result;
    }

    /**
     * 生成订单信息  还缺少的参数 CarCity CarCityThree CarBrand ServiceCar PaymentType
     * <p>
     * post
     */
    public static String CreateOrder(int serviceType, int orderSource, String productID, String carModelID, String CarCity, String CarCityThree, String CarBrand
            , String TimeBoard, String ServiceCar, String PassengerName, String PassengerMobile, String PaymentType, String StartPlace, String Destination, String ReturnTimeBoard
            , String ReturnStartPlace, String ReturnDestination, String FlightNumber, String CardNo, String CardValue, int CarTypeID, String Remark) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("serviceType", serviceType + "")
                .add("orderSource", orderSource + "")
                .add("productID", productID)
                .add("carModelID", carModelID)
                .add("CarCity", CarCity)
                .add("CarCityThree", CarCityThree)
                .add("CarBrand", CarBrand)
                .add("TimeBoard", TimeBoard)
                .add("ServiceCar", ServiceCar)
                .add("PassengerName", PassengerName)
                .add("PassengerMobile", PassengerMobile)
                .add("PaymentType", PaymentType)
                .add("StartPlace", StartPlace)
                .add("Destination", Destination)
                .add("ReturnTimeBoard", ReturnTimeBoard)
                .add("ReturnStartPlace", ReturnStartPlace)
                .add("ReturnDestination", ReturnDestination)
                .add("FlightNumber", FlightNumber)
                .add("CardNo", CardNo)
                .add("CardValue", CardValue)
                .add("CarTypeID", CarTypeID + "")
                .add("Remark", Remark)
                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.createOrder);
        Logger.e(TAG + "生成订单信息" + result);
        return result;
    }

    public static String CreateOrder(int serviceType, int orderSource, JSONObject OrderString) throws Exception {
        RequestBody body = new FormBody.Builder()
                .add("serviceType", serviceType + "")
                .add("orderSource", orderSource + "")
                .add("orderString", String.valueOf(OrderString))
                .build();
        String result = HttpTool.okPost(body, PublicApplication.urlData.createOrder);
        Logger.e(TAG + "生成订单信息" + result);
        return result;
    }
    /*GET*/

    /**
     * 欢迎图 协议h5
     * <p>
     * PageSign
     * <p>
     * 返回页面区别参数（loginxy:登陆协议页 userxy:个人中心协议 kv:登陆起始页）
     * Example: loginxy.
     */
    public static String LoadPageHtml(String str) throws Exception {
        String parmes = "?" + "PageSign=" + str;
        String result = HttpTool.okGet(PublicApplication.urlData.LoadPageHtml + parmes);
        Logger.e(TAG + "欢迎图 协议h5" + result);
        return result;
    }

    /**
     * 资源检测
     */
    public static String sourceupdate() throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.sourceupdate);
        Logger.e(TAG + "资源检测" + result);
        return result;
    }

    /**
     * 资源下载
     */
    public static String sourcelist() throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.sourcelist);
        Logger.e(TAG + "资源下载" + result);
        return result;
    }


    /**
     * MyCard    个人中心 / 我的卡劵 / 个人卡劵列表 GET https://private-a96e4a-benzv2.apiary-mock.com/card/MyCard?IsOverdue=0&orderId=1111
     */
    public static String MyCardList(String IsOverdue, String orderId) throws Exception {
        int id = -1;
        if (!StrUtils.isEmpty(orderId))
            id = Integer.parseInt(orderId);
        String result = HttpTool.okGet(PublicApplication.urlData.MyCard + "?IsOverdue=" + IsOverdue + "&orderId=" + id);
        Log.e(TAG, " 个人中心 / 我的卡劵 / 个人卡劵列表" + result);
        return result;
    }

    public static String UserInfo() throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.userinfo);
        Log.e(" 个人用户信息", result);
        return result;
    }

    public static String LoadPageHtml() throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.LoadPageHtml + "?PageSign=userxy");
        Log.e(" 个人中心获取协议页面", result);
        return result;
    }

    /**
     * 登录界面获取用户协议
     *
     * @return
     * @throws Exception
     */
    public static String LoadLoginHtml() throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.LoadPageHtml + "?PageSign=loginxy");
        Log.e(" 登录界面获取用户协议", result);
        return result;
    }

    /**
     * 订单列表
     */
    public static String MyOrders(String OrderStatus) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.MyOrders + "?OrderStatus=" + OrderStatus);
        Logger.e(TAG + " 我的订单列表" + result);
        return result;
    }

    /**
     * 取消订单
     */
    public static String CancelOrder(String OrderId) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.CancelOrder + "?OrderId=" + OrderId);
        Log.e(TAG, " 取消订单" + result);
        return result;
    }

    /**
     * 删除订单
     */
    public static String RemoveOrder(String OrderId) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.RemoveOrder + "?OrderId=" + OrderId);
        Log.e(TAG, " 删除订单" + result);
        return result;
    }

    /**
     * 订单详情
     */
    public static String OrderDetail(String OrderId) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.OrderDetail + "?OrderId=" + OrderId);
        Log.e(TAG, " 订单详情" + result);
        return result;
    }

    /**
     * 获取城市列表
     */
    public static String CityList(String cardNo, String serviceType) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.cities + "?cardNo=" + cardNo + "&serviceType=" + serviceType);
        Log.e(TAG, " 获取城市列表" + result);
        return result;
    }

    /**
     * 获取调查问卷接口
     */
    public static String OrderEvaluationIndex(String OrderId) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.OrderEvaluationIndex + "?OrderId=" + OrderId);
        Log.e(TAG, " 获取调查问卷接口" + result);
        return result;
    }

    /**
     * 获取消息列表
     */
    public static String MessageList() throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.messageList);
        Log.e(TAG, " 获取消息列表" + result);
        return result;
    }

    /**
     * 更新消息状态
     *
     * @return
     * @throws Exception
     */
    public static String MessageUpdata(String messageid) throws Exception {
        RequestBody body1 = new FormBody.Builder()
                .add("messageid", messageid)
                .build();
        String result = HttpTool.okPost(body1, PublicApplication.urlData.messageUpdate);
        Logger.e(TAG + "更新消息状态" + result);
        return result;
    }

    /**
     * 关键词地址搜索
     */
    public static String Address(String query, String region) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.address + "/?query=" + query + "&region=" + region + "&city_limit=" + true);
        Log.e(TAG, " 关键词地址搜索" + result);
        return result;
    }

    /**
     * 获取车型列表
     */
    public static String carList(String cardNo, String cityCode, int serviceType) throws Exception {
        String result = HttpTool.okGet(PublicApplication.urlData.carlist + "?cardNo=" + cardNo + "&cityCode=" + cityCode + "&serviceType=" + serviceType);
        Logger.e(TAG + " 获取车型列表" + result);
        return result;
    }

    /**
     * 获取航班信息
     */
    public static String flightinfo(String flightno, String depdate, int serviceType, String carCity) throws Exception {
        String parameter = "?" + "flightno=" + flightno + "&depdate=" + depdate + "&serviceType=" + serviceType + "&carCity=" + carCity;
        String result = HttpTool.okGet(PublicApplication.urlData.flightinfo + parameter);
        Log.e(TAG, " 获取航班信息" + result);
        return result;
    }

    /**
     * bug日志
     *
     * @param method
     * @param message
     * @return
     * @throws Exception
     */
    public static String LogInfo(String method, String message) throws Exception {
        RequestBody body1 = new FormBody.Builder()
                .add("method", method)
                .add("message", message)
                .build();
        String result = HttpTool.okPost(body1, PublicApplication.urlData.bugInfo);
        Logger.e(TAG + "bug日志提交" + result);
        return result;
    }
}
