package com.zyx.contants;

/**
 * Created by zyx on 2016/2/11.
 */

import java.util.Map;

public interface OnGetConfigure {
    /**
     * 成功传递配置文件
     *
     * @param configure
     */
    public void onGetConfigure(Map<String, Map<String, Object>> configure);

    /**
     * 数据解析异常
     */
    public void onDataException();

    /**
     * 网络连接异常
     */
    public void onNetworkExecption();
}
