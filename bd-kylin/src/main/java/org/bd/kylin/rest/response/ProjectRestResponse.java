package org.bd.kylin.rest.response;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bd.kylin.response.ProjectInfo;
import org.bd.kylin.rest.ProjectRest;
import org.bd.kylin.utils.JsonBinder;

import java.io.IOException;
import java.util.List;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> kylin项目接口<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年11月21日 下午3:23:13 |创建
 */
public class ProjectRestResponse extends ProjectRest {

    /**
     * <b>描述：</b> 获取项目信息
     *
     * @return List<ProjectInfo>
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonParseException
     * @author wpk | 2017年11月21日 下午3:24:05 |创建
     */
    @SuppressWarnings("unchecked")
    public static List<ProjectInfo> getProjectsResp() throws JsonParseException, JsonMappingException, IOException {
        String result = getProjects();
        ObjectMapper om = JsonBinder.buildNonNullBinder().getMapper();
        List<ProjectInfo> list = (List<ProjectInfo>) om.readValue(result, new TypeReference<List<ProjectInfo>>() {
        });
        return list;
    }
}
