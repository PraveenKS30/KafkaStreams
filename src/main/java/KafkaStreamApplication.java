import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;

import java.util.Properties;

public class KafkaStreamApplication {

    public static void main(String[] args) {

        //setting up config properties
        Properties props = new Properties();
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "con-kstream-app-id");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        // create streambuilder
        StreamsBuilder builder = new StreamsBuilder();

        // subsribe to source topic
        final KStream<Object, Object> kStream = builder.stream("source-word-topic");

        // perform transformation on KStream and place it into different topic
        kStream.to("dest-word-topic");

        // create a topology
        Topology topology = builder.build();

        // create kafkaStreams and start it
        KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();


    }
}
