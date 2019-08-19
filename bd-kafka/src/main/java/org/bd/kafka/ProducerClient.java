package org.bd.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;
import org.bd.kafka.utils.PropertiesUtil;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> kafka生产者客户端<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2017年11月26日 下午1:29:19 |创建
 */
public class ProducerClient {

    private KafkaProducer<String, String> producer = null;
    private String topic;

    public ProducerClient(String topic) {
        this.topic = topic;
        init();
    }

    /**
     * <b>描述：</b> 初始化生产者
     *
     * @return void
     * @author wpk | 2017年11月26日 下午1:32:09 |创建
     */
    public void init() {
        if (null == producer) {
            Properties properties = PropertiesUtil.getInstance().getProperties("producer.properties");
            producer = new KafkaProducer<String, String>(properties);
        }
    }

    /**
     * <b>描述：</b> 获取分区信息
     *
     * @return List<PartitionInfo>
     * @author wpk | 2017年11月26日 下午1:32:01 |创建
     */
    public List<PartitionInfo> getPartition() {
        List<PartitionInfo> list = producer.partitionsFor(topic);
        for (PartitionInfo p : list) {
            System.out.println(p.toString());
        }
        return list;
    }

    /**
     * <b>描述：</b> 发送信息
     *
     * @param msg
     * @return Future<RecordMetadata>
     * @throws ExecutionException
     * @throws InterruptedException
     * @author wpk | 2017年11月26日 下午1:34:23 |创建
     */
    public RecordMetadata sendMsg(String msg) throws InterruptedException, ExecutionException {
        Future<RecordMetadata> fture = producer.send(new ProducerRecord<String, String>(topic, msg));
        RecordMetadata rm = fture.get();
        return rm;
    }

    /**
     * <b>描述：</b> 关闭生产者
     *
     * @return void
     * @author wpk | 2017年11月26日 下午1:42:35 |创建
     */
    public void close() {
        if (null != producer) {
            producer.close();
        }
    }

}   
