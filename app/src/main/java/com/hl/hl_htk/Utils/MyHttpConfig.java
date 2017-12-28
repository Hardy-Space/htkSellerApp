package com.hl.hl_htk.Utils;

/**
 * Created by Administrator on 2017/7/5.
 */

public class MyHttpConfig {

    public static String tag = "TAG-->";

//    private static final String baseUrl = "http://192.168.0.7:8080/htkApp/API/";//内网
    private static final String baseUrl = "http://120.27.5.36:8080/htkApp/API/";//外网

    private static final String normal_url = baseUrl + "accountShopMessageAPI";


    public static final String login = normal_url + "/appAccountShopLoginByUserName";
    public static final String forgetPass = normal_url + "/forgetPasswordBySMS";
    public static final String userInfo = normal_url + "/getAppAccountShopData";
    public static final String playingWork = normal_url + "/changeShopState";
    public static final String getAuth = baseUrl + "AccountMessage/sendSms/";

    public static final String allTreadOrder = normal_url + "/getAllProcessedOrderList";
    public static final String treatedTg = normal_url + "/getGroupBuyOrderListByAccountShopToken";
    public static final String treatedWm = normal_url + "/getTakeoutOrderListByAccountShopToken";

    public static final String unAllOrder = normal_url + "/getAllUntreatedOrderList";
    public static final String unTuanGou = normal_url + "/getUntreatedGroupBuyOrderList";
    public static final String unWaiMai = normal_url + "/getUntreatedTakeoutOrderList";
    public  static  final  String  jiedan  = normal_url +"/handlesTakeoutOrders";
    public  static  final  String  peisong  = normal_url +"/deliveryTakeout";
    public  static  final  String useTg = normal_url +"/groupBuyConsumption";
    public  static  final  String  upDataUrl  = normal_url +"/checkAppUpdate";

    /**
     * 确认收货
     */
    public  static  final  String  confirmReceipt  = normal_url +"/confirmReceipt";

    /**
     * 取消订单
     */
    public  static  final  String  cancelOrder  = normal_url +"/cancelOrder";

}
