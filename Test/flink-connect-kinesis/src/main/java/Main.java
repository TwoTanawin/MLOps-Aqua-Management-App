import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kinesis.FlinkKinesisConsumer;

import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties kinesisProps = new Properties();
        kinesisProps.setProperty("aws.region", "ap-southeast-1");
        kinesisProps.setProperty("aws.profile", "mlops"); // Optional: only used if credentials are local
        kinesisProps.setProperty("stream.initial.position", "LATEST"); // Or TRIM_HORIZON

        DataStream<String> kinesisStream = env.addSource(
                new FlinkKinesisConsumer<>(
                        "iot-data-stream",          // stream name
                        new SimpleStringSchema(),   // deserialization schema
                        kinesisProps
                )
        );

        kinesisStream.print(); // Output to console/log

        env.execute("Kinesis to Flink");
    }
}
