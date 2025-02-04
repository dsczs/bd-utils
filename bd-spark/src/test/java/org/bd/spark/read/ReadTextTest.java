package org.bd.spark.read;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.bd.spark.ReadComm;
import org.bd.spark.enums.FormatType;

/**
 * <b>版权信息:</b> big data module<br>
 * <b>功能描述:</b> 从test读取数据<br>
 * <b>版本历史:</b>
 *
 * @author wpk | 2018年1月16日 上午11:24:28 |创建
 */
public class ReadTextTest {

    public static void readText(SparkSession spark) throws Exception {
        Dataset<Row> text = ReadComm.getInstance().
                readSource(spark, FormatType.TEXT, "D:/wpk/devToll/workspace/nkzjProject1/idata-spark/test-file/people.txt");
//		Dataset<Row> text = SparkReadCommon.getInstance().
//				readSource(spark, ReadFormat.TEXT.value(), "D:/wpk/devToll/workspace/nkzjProject1/idata-spark/test-file/people.txt",
//						"D:/wpk/devToll/workspace/nkzjProject1/idata-spark/test-file/people2.txt");

        text.show();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
