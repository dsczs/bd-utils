package org.bd.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> 创建spark程序起点<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年7月21日下午2:13:27 |创建
 */
public class SessionDrive {

    public final static String OTHER = "other";
    public final static String HIVE = "hive";
    public final static String SUPPORT_TYPE = "support.type";
    public volatile static SessionDrive ssd;
    public static Map<String, SparkSession> ssMap = new HashMap<String, SparkSession>();

    /**
     * 默认构造类
     */
    public SessionDrive() {
    }

    /**
     * <b>描述：获取实例</b>
     *
     * @return
     * @author wpk | 2017年7月21日下午2:16:24 |创建
     */
    public static SessionDrive getInstance() {
        if (ssd == null) {
            synchronized (SessionDrive.class) {
                if (ssd == null) {
                    ssd = new SessionDrive();
                }
            }
        }
        return ssd;
    }

    /**
     * <b>描述：获取SparkSession</b>
     *
     * @param conf
     * @return
     * @throws Exception
     * @author wpk | 2017年7月21日下午2:41:44 |创建
     */
    public SparkSession getSparkSession(SparkConf conf) throws Exception {
        SparkSession spark = null;
        try {
            if (!conf.contains(SUPPORT_TYPE)) {
                if (!ssMap.containsKey(OTHER)) {
                    spark = SparkSession.builder().config(conf).getOrCreate();
                } else {
                    spark = ssMap.get(OTHER);
                }
            } else {
                String opType = conf.get(SUPPORT_TYPE);
                if (HIVE.equals(opType)) {
                    if (!ssMap.containsKey(HIVE)) {
                        spark = SparkSession.builder().config(conf).enableHiveSupport().getOrCreate();
                    } else {
                        spark = ssMap.get(HIVE);
                    }
                } else {
                    throw new Exception(String.format("获取SparkSeesion失败,传入的类型[%s]不存在。", opType));
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
        return spark;
    }

    /**
     * <b>描述：关闭SparkSession</b>
     *
     * @param opType
     * @throws Exception
     * @author wpk | 2017年7月24日下午5:11:49 |创建
     */
    public void stop(String opType) throws Exception {
        try {
            if (ssMap.containsKey(opType)) {
                ssMap.get(opType).stop();//停止
                ssMap.remove(opType);//移除
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

}
