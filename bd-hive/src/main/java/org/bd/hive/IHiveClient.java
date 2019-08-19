package org.bd.hive;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> hive接口类<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年10月31日 上午10:14:45 |创建
 */
public interface IHiveClient {

    /**
     * <b>描述：</b> 执行语句获取结果集
     *
     * @param hql
     * @return void
     * @throws IOException
     * @author wpk | 2017年10月31日 上午10:28:29 |创建
     */
    public ResultSet executeQuery(String hql) throws Exception;

    /**
     * <b>描述：</b> 执行单条语句
     *
     * @param hql
     * @return void
     * @throws IOException
     * @author wpk | 2017年10月31日 下午12:08:26 |创建
     */
    public int executeUpdate(String hql) throws Exception;

    /**
     * <b>描述：</b> 执行多条语句
     *
     * @param hqls
     * @return void
     * @throws IOException
     * @author wpk | 2017年10月31日 下午12:08:45 |创建
     */
    public int executeUpdate(String[] hqls) throws Exception;

    /**
     * <b>描述：</b> 获取数据库名列表
     *
     * @return List<String>
     * @throws Exception
     * @author wpk | 2017年10月31日 下午12:09:05 |创建
     */
    public List<String> getHiveDbNames() throws Exception;

    /**
     * <b>描述：</b> 获取指定数据库下的表
     *
     * @param database
     * @return List<String>
     * @throws Exception
     * @author wpk | 2017年10月31日 下午12:09:39 |创建
     */
    public List<String> getHiveTableNames(String database) throws Exception;

    /**
     * <b>描述：</b> 获取指定表的详细信息
     *
     * @param database
     * @param tableName
     * @return HiveTableMeta
     * @throws Exception
     * @author wpk | 2017年10月31日 下午12:10:01 |创建
     */
    public HiveTableMeta getHiveTableMeta(String database, String tableName) throws Exception;

    /**
     * <b>描述：</b> 获取hive表中的数据量
     *
     * @param database
     * @param tableName
     * @return long
     * @throws Exception
     * @author wpk | 2017年10月31日 下午12:10:17 |创建
     */
    public long getHiveTableRows(String database, String tableName) throws Exception;

    /**
     * <b>描述：</b> 删除数据库（注意：删除数据库将同时删除库中所有表）
     *
     * @param database
     * @return int
     * @throws Exception
     * @author wpk | 2017年10月31日 下午4:02:39 |创建
     */
    public int dropHiveDbName(String database) throws Exception;

    /**
     * <b>描述：</b> 删除指定数据库下的表
     *
     * @param database
     * @param tableName
     * @return int
     * @throws Exception
     * @author wpk | 2017年10月31日 下午4:04:49 |创建
     */
    public int dropHiveTableName(String database, String tableName) throws Exception;

    /**
     * <b>描述：</b> 判断是否存在指定数据库
     *
     * @param database
     * @return boolean
     * @throws Exception
     * @author wpk | 2017年10月31日 下午4:19:38 |创建
     */
    public boolean checkHiveDbName(String database) throws Exception;

    /**
     * <b>描述：</b> 判断是否存在指定的表
     *
     * @param database
     * @param tableName
     * @return boolean
     * @throws Exception
     * @author wpk | 2017年10月31日 下午4:37:57 |创建
     */
    public boolean checkHiveTableName(String database, String tableName) throws Exception;

    /**
     * <b>描述：</b> 关闭资源
     *
     * @return void
     * @throws Exception
     * @author wpk | 2017年10月31日 下午5:36:13 |创建
     */
    public void close() throws Exception;
}
