package org.bd.kylin.rest.response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bd.kylin.request.SQLRequest;
import org.bd.kylin.response.SQLResponse;
import org.bd.kylin.response.TableMeta;
import org.bd.kylin.rest.QueryRest;
import org.bd.kylin.utils.JsonBinder;

import java.io.IOException;
import java.util.List;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> kylin语句查询<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年11月21日 下午3:00:27 |创建
 */
public class QueryRestResponse extends QueryRest {

    /**
     * <b>描述：</b> 获取kylin上的所有表元数据信息<br>
     * 所有表指的是用于创建模型和cube的表
     *
     * @param project
     * @return List<TableMeta>
     * @throws JsonParseException
     * @throws JsonMappingException
     * @throws IOException
     * @author wpk | 2017年11月21日 下午3:01:11 |创建
     */
    @SuppressWarnings("unchecked")
    public static List<TableMeta> getMetadatasResp(String project) throws JsonParseException, JsonMappingException, IOException {
        String result = getMetadatas(project);
        ObjectMapper om = JsonBinder.buildNonNullBinder().getMapper();
        List<TableMeta> list = (List<TableMeta>) om.readValue(result, new TypeReference<List<TableMeta>>() {
        });
        return list;
    }

    /**
     * <b>描述：</b> 查询语句
     *
     * @param sr
     * @return SQLResponse
     * @throws IOException
     * @author wpk | 2017年11月21日 下午5:06:21 |创建
     */
    public static SQLResponse queryResp(SQLRequest sr) throws IOException {
        String result = query(sr);
        ObjectMapper om = JsonBinder.buildNonNullBinder().getMapper();
        SQLResponse sqlResult = om.readValue(result, SQLResponse.class);
        return sqlResult;
    }

    /**
     * <b>描述：</b> 查询语句
     *
     * @param project
     * @param sql
     * @return SQLResponse
     * @throws IOException
     * @author wpk | 2017年11月21日 下午5:22:47 |创建
     */
    public static SQLResponse queryResp(String project, String sql) throws IOException {
        SQLRequest item = new SQLRequest();
        item.setProject(project);
        item.setSql(sql);
        return queryResp(item);
    }
}
