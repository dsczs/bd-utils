package org.bd.hive.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.lang.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> hive连接池<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年10月31日 下午2:36:55 |创建
 */
public class HiveDruidUtils {

    private static HiveDruidUtils ic = null;
    private DruidDataSource dataSource = null;

    public HiveDruidUtils() {
        SysVarsUtils sysVarsUtils = SysVarsUtils.getInstance();
        String initialsize = sysVarsUtils.getVarByName(Consts.initialsize);
        String maxActive = sysVarsUtils.getVarByName(Consts.maxactive);
        String removeAbandonedTimeout = sysVarsUtils.getVarByName(Consts.removeAbandonedTimeout);
        String timeBetweenEvictionRunsMillis = sysVarsUtils.getVarByName(Consts.timeBetweenEvictionRunsMillis);
        String minEvictableIdleTimeMillis = sysVarsUtils.getVarByName(Consts.minEvictableIdleTimeMillis);

        int minA = StringUtils.isNotEmpty(initialsize) ? Integer.parseInt(initialsize) : 5;
        int maxA = StringUtils.isNotEmpty(maxActive) ? Integer.parseInt(maxActive) : 50;
        long minevictableidletimemillis = StringUtils.isNotEmpty(minEvictableIdleTimeMillis) ? Long.parseLong(minEvictableIdleTimeMillis) : 30000;
        long timebetweenevictionrunsmillis = StringUtils.isNotEmpty(timeBetweenEvictionRunsMillis) ? Long.parseLong(timeBetweenEvictionRunsMillis) : 30000;
        int removeabandonedtimeout = StringUtils.isNotEmpty(removeAbandonedTimeout) ? Integer.parseInt(removeAbandonedTimeout) : 360;

        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(SysVarsUtils.getInstance().getVarByName(Consts.impala_driver));
        dataSource.setUrl(SysVarsUtils.getInstance().getVarByName(Consts.impala_url));
        dataSource.setUsername(SysVarsUtils.getInstance().getVarByName(Consts.impala_user));
        dataSource.setPassword(SysVarsUtils.getInstance().getVarByName(Consts.impala_password));
        dataSource.setInitialSize(minA);//初始化连接池连接数
        dataSource.setMinIdle(minA);//连接池最小连接数
        dataSource.setMaxActive(maxA);//连接池最大连接数
        dataSource.setMinEvictableIdleTimeMillis(minevictableidletimemillis);//30s最小生存时间30 * 1000
        dataSource.setRemoveAbandoned(true);//开启强行回收功能
        dataSource.setRemoveAbandonedTimeout(removeabandonedtimeout);//连接废弃超过1小时未关闭，就会被强行回收
        dataSource.setTimeBetweenEvictionRunsMillis(timebetweenevictionrunsmillis);//30秒检测一次需要强行回收的连接

        dataSource.setTestOnBorrow(true);//申请连接时执行validationQuery检测连接是否有效，配置为true会降低性能
        dataSource.setTestWhileIdle(true);//申请连接的时候检测
        dataSource.setValidationQuery("select 1");//用来检测连接是否有效的sql，要求是一个查询语句

        try {
            dataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized HiveDruidUtils getInstance() {
        if (ic == null) {
            ic = new HiveDruidUtils();
        }
        return ic;
    }

    /**
     * <b>描述：</b> 获取连接
     *
     * @return Connection
     * @throws SQLException
     * @author wpk | 2017年10月31日 下午2:40:20 |创建
     */
    public Connection getConn() throws SQLException {
        Connection conn = dataSource.getConnection();
        System.out.println(String.format("正在获取Druid管理的hive连接(%s/%s).....", dataSource.getActiveCount(), dataSource.getMaxActive()));
        return conn;
    }
}