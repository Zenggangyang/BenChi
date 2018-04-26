package com.soowin.staremblem.http;

/**
 * Created by Administrator on 2017/6/9.
 */

public class HttpUrl {
    /**
     * 测试地址
     **/
//    public static String hostUrl = "https://private-9d1762-benzv2.apiary-mock.com/";
//    public static String hostUrl = "https://private-b2f32-benzv2.apiary-mock.com/";
//    public static String hostUrl = "https://private-a96e4a-benzv2.apiary-mock.com/";
//
    /**
     * 正式地址
     **/
    public String hostUrl = "https://api.benz.wx.fractalist.com.cn/v1/";

    //test******************************************************
    /**
     * 测试接口
     * Get
     */
    public String test = "account/login";
    /**
     * 测试接口
     * post
     */
    public String testPost = "account/login";
    //用户账号*****************************************************
    /**
     * 登陆
     * post
     */
    public String login = "account/login";
    /**
     * 完善登录
     * post
     */
    public String loginInfo = "account/loginInfo";
    /**
     * 短信通道
     * post
     */
    public String mobilecode = "account/mobilecode";
    //系统信息****************************************************
    /**
     * 关键词地址搜索
     * get
     */
    public String address = "address";
    /**
     * 获取城市列表
     * get
     */
    public String cities = "system/cities";
    /**
     * 车型列表
     * get
     */
    public String carlist = "system/carlist";
    /**
     * 航班信息
     * get
     */
    public String flightinfo = "system/flightinfo";
    //订单信息*************************************************
    /**
     * 订单金额
     * post
     */
    public String orderprice = "order/orderprice";
    /**
     * 生成订单
     * post
     */
    public String createOrder = "order/Index";
    /**
     * 订单支付
     * post
     */
    public String pay = "pay/Index";
    /**
     * 订单列表
     * get
     */
    public String MyOrders = "order/MyOrders";
    /**
     * 取消订单
     * get
     */
    public String CancelOrder = "order/CancelOrder";
    /**
     * 删除订单
     * post
     */
    public String RemoveOrder = "order/RemoveOrder";
    /**
     * 订单详情
     * get
     */
    public String OrderDetail = "order/orderDetail";
    /**
     * 调查问卷
     * post
     */
    public String CreateOrderEvaluation = "order/CreateOrderEvaluation";
    /**
     * 获取调查问卷接口
     * get
     */
    public String OrderEvaluationIndex = "order/OrderEvaluationIndex";
    //个人中心***************************************************
    /**
     * 个人信息
     * get
     */
    public String userinfo = "person/userinfo";
    /**
     * 协议
     * get
     */
    public String LoadPageHtml = "person/LoadPageHtml";
    /**
     * 卡卷列表
     * get
     */
    public String MyCard = "card/MyCard";
    /**
     * 删除卡卷
     * post
     */
    public String DeleteCard = "card/DeleteCard";
    //消息**************************************************************
    /**
     * 消息列表
     * get
     */
    public String messageList = "message/list";
    /**
     * 更新消息状态
     * post
     */
    public String messageUpdate = "message/update";
    //bug日志*************************************************************
    /**
     * bug日志
     * post
     */
    public String bugInfo = "system/LogInfo";
    /**
     * 检测资源
     */
    public String sourceupdate = "system/sourceupdate";
    /**
     * 下载资源
     */
    public String sourcelist = "system/sourcelist";

}
