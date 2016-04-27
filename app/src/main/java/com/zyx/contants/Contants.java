package com.zyx.contants;

/**
 * Created by zyx on 2016/2/11.
 */
public final class Contants {


    /** 验证码获权 */
    public static final String MSG_AUTHORIZATION = "CAPool/GetTokenBySMS.aspx?mphone=%s&smscode=%s";


    /**
     * Get
     */
    /** 总配置文件接口 */
    public static final String STATIC_DATA = "xml/version.xml";

    /** 域名 */
    public static final String DOMAIN="http://192.168.100.106/ecshop/App_API/";

    /** 新用户注册 */
    public static final String USER_REGSTEP1= "RegStep1.action";

    /** 用户登录 */
    public static final String USER_LOGIN =	"LoginAction.action";

    /** 判断用户存在 */
    public static final String USER_IsExit ="IsUserExit.action";

    /**获取验证码*/
    public static final String GetMsgCode ="GetMsgCode.action";

    /** 修改密码资料 */
    public static final String UPDATE_USER_INFO = "ModifyLoginPwd.action";


    /** 商品集合*/
    public static final String Product_List = "GetProducts.action";

    /** 获得某商品*/
    public static final String Product_One = "GetProduct.action";

    /** 创建商品订单*/
    public static final String CreateOrder = "CreateOrder.action";

    /** 创建商品订单*/
    public static final String CreateLoan = "loanAction.action";


    /** 订单List*/
    public static final String OrderList = "OrderList.action";

    /**订单详情*/
    public static final String OrdersOfOne = "OrdersOfOne.action";

    /**修改订单状态订单*/
    public static final String UpdateOrderStatus = "UpdateOrders.action";

    /** 搜索商品*/
    public static final String SearchProducts = "searchProducts.action";


    /**repay状态订单,,逾期/还款*/
    public static final String RepayAction = "repayAction.action";


    /** 获得某商品*/
    public static final String ZhongchouList = "getZhongchous.action";

    /** 用户文件夹 */
    public static final String USER_PATH = "USER/user";

    /** 用户文件夹（私有） */
    public static final String USER_PATH_PRIVATE = USER_PATH + "/private";

    /** 用户信息文件 */
    public static final String USER_INFO = "userInfo";

    /** 用户账号与密码文件名 */
    public static final String USER_FILE = "login";

    /** 用户账号的KEY */
    public static final String UN = "userName";

    /** 用户密码的KEY */
    public static final String UPWD = "userPWD";

    /** 与Activity的id对应的Key */
    public static final String ACTIVITY_KEY = "ID";


    /** 服务器配置文件名 */
    public static final String CONFIGURE = "configure";
}
