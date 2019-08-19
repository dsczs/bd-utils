package org.bd.datax.bean;

import org.bd.datax.DataxPara;
import org.bd.datax.bean.Setting.ErrorLimit;
import org.bd.datax.bean.Setting.Speed;

import java.util.Map;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> setting转换类<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年10月13日 下午10:54:36 |创建
 */
@SuppressWarnings("rawtypes")
public class SettingBean {

    /**
     * <b>描述：</b> 获取全局对象
     *
     * @param map
     * @return Setting
     * @author wpk | 2017年10月13日 下午10:55:03 |创建
     */
    public Setting getBean(Map map) {
        Setting bean = new Setting();
        Speed speed = bean.new Speed();
        ErrorLimit errorLimit = bean.new ErrorLimit();

        if (map.containsKey(DataxPara.channel) && map.get(DataxPara.channel) != null) {
            speed.setChannel(Integer.parseInt(map.get(DataxPara.channel).toString()));
        }

        if (map.containsKey(DataxPara.record) && map.get(DataxPara.record) != null) {
            errorLimit.setRecord(Integer.parseInt(map.get(DataxPara.record).toString()));
        }
        if (map.containsKey(DataxPara.percentage) && map.get(DataxPara.percentage) != null) {
            errorLimit.setPercentage(Double.parseDouble(map.get(DataxPara.percentage).toString()));
        }

        bean.setSpeed(speed);
        bean.setErrorLimit(errorLimit);
        return bean;
    }
}
