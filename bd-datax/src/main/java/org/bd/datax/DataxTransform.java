package org.bd.datax;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bd.datax.DataxException;
import org.bd.datax.bean.Content;
import org.bd.datax.bean.Job;
import org.bd.datax.bean.Read;
import org.bd.datax.bean.Script;
import org.bd.datax.bean.Setting;
import org.bd.datax.bean.SettingBean;
import org.bd.datax.bean.Transformer;
import org.bd.datax.bean.TransformerBean;
import org.bd.datax.bean.Write;
import org.bd.datax.utils.JsonBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> datax脚本转换类<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年8月7日 下午2:50:13 |创建
 */
@SuppressWarnings("rawtypes")
public class DataxTransform {

    private DataxBean readBean;
    private DataxBean writeBean;

    public DataxTransform(DataxBean readBean, DataxBean writeBean) {
        this.readBean = readBean;
        this.writeBean = writeBean;
    }

    /**
     * <b>描述：</b> 获取json脚本
     *
     * @param setMap   全局参数
     * @param readMap  读取参数
     * @param writeMap 写入参数
     * @return JSONObject
     * @throws Exception
     * @author wpk | 2017年9月14日 上午9:38:31 |创建
     */
    public String getJobJson(Map setMap, Map readMap, Map writeMap) {
        return getJobJson(setMap, readMap, writeMap, null);
    }

    /**
     * <b>描述：</b> 获取json脚本
     *
     * @param setMap         全局参数
     * @param readMap        读取参数
     * @param writeMap       写入参数
     * @param transformerMap 转换参数
     * @return JSONObject
     * @throws Exception
     * @author wpk | 2017年9月14日 上午9:49:32 |创建
     */
    public String getJobJson(Map setMap, Map readMap, Map writeMap, Map transformerMap) {
        Setting setting = getSettingBean(setMap);
        Read read = getReadBean(readMap);
        Write write = getWriteBean(writeMap);
        List<Transformer> transformer = getTransformerBean(readMap);
        Script script = AssembleJob(setting, read, transformer, write);

        String str = "";
        try {
            ObjectMapper om = JsonBinder.buildNonNullBinder().getMapper();
            str = om.writeValueAsString(script);
        } catch (Exception e) {
            throw new DataxException("对象脚本->字符串脚本异常", e);
        }
        return str;
    }

    /**
     * <b>描述：</b> 获取脚本对象
     *
     * @param json
     * @return Job
     * @author wpk | 2017年10月13日 下午11:09:00 |创建
     */
    public Job getJobBean(String json) {
        Job job = null;
        try {
            ObjectMapper om = JsonBinder.buildNormalBinder().getMapper();
            job = om.readValue(json, Job.class);
        } catch (Exception e) {
            throw new DataxException("字符串脚本->对象脚本异常", e);
        }
        return job;
    }

    /**
     * <b>描述：</b> 获取全局参数
     *
     * @param map
     * @return Setting
     * @author wpk | 2017年10月13日 下午11:09:19 |创建
     */
    private Setting getSettingBean(Map map) {
        return new SettingBean().getBean(map);
    }

    /**
     * <b>描述：</b> 获取读取对象
     *
     * @param map
     * @return Read
     * @author wpk | 2017年10月13日 下午11:09:26 |创建
     */
    private Read getReadBean(Map map) {
        return readBean.readBean(map);
    }

    /**
     * <b>描述：</b> 获取写入对象
     *
     * @param map
     * @return Write
     * @author wpk | 2017年10月13日 下午11:09:45 |创建
     */
    private Write getWriteBean(Map map) {
        return writeBean.writeBean(map);
    }

    /**
     * <b>描述：</b> 获取字段转化对象
     *
     * @param map
     * @return List<Transformer>
     * @author wpk | 2017年10月15日 上午11:00:17 |创建
     */
    private List<Transformer> getTransformerBean(Map map) {
        List<Transformer> list = new ArrayList<Transformer>();
        TransformerBean tb = new TransformerBean();
        Transformer t = tb.getBean(map);
        list.add(t);
        return list;
    }

    /**
     * <b>描述：</b> 组装对象
     *
     * @param setting
     * @param read
     * @param transformer
     * @param write
     * @return Job
     * @author wpk | 2017年10月13日 下午11:09:57 |创建
     */
    private Script AssembleJob(Setting setting, Read read, List<Transformer> transformer, Write write) {
        Script script = new Script();
        Job job = new Job();
        List<Content> list = new ArrayList<Content>();
        Content content = new Content();
        content.setReader(read);
        content.setTransformer(transformer);
        content.setWriter(write);
        list.add(content);
        job.setContent(list);
        job.setSetting(setting);
        script.setJob(job);
        return script;
    }

}
