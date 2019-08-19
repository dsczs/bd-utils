package org.bd.spark.utils;

import org.apache.commons.lang.StringUtils;
import org.bd.spark.enums.Consts;

import java.util.HashMap;
import java.util.Map;


/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> 获取系统常量配置参数<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年7月20日下午3:00:24 |创建
 */
public class SysVarsUtils {

    private volatile static SysVarsUtils instance;
    private Map<String, String> configCache = new HashMap<String, String>();

    /**
     * <b>描述：获取对象</b>
     *
     * @return
     * @author wpk | 2017年7月25日下午6:27:50 |创建
     */
    public static SysVarsUtils getInstance() {
        if (instance == null) {
            synchronized (SysVarsUtils.class) {
                if (instance == null) {
                    instance = new SysVarsUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        configCache.clear();
    }

    /**
     * <b>描述：根据参数名取得参数值</b>
     *
     * @param name
     * @return
     * @author wpk | 2017年7月25日下午6:28:24 |创建
     */
    public String getVarByName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String config = configCache.get(name);
        if (config != null) {
            return config;
        }
        PropertiesUtil propertiesUtil = PropertiesUtil.getInstance();
        config = propertiesUtil.getPropertyValue(Consts.spark_properties, name);
        if (config != null) {
            configCache.put(name, config);
        }
        return config;
    }

}
