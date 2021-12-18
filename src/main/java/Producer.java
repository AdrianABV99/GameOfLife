import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer {

    private KafkaProducer<Integer, String> producer;
    private String Topic;
    public  Producer(String Topic) {
        this.Topic = Topic;
        createProducer();
    }

    public void createProducer() {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        producer = new KafkaProducer<>(prop);
    }

    public void send(Integer key, String value) {
        ProducerRecord<Integer, String> record = new ProducerRecord<>(Topic,key,value);
        producer.send(record);
    }

    public void close() {
        producer.flush();
        producer.close();

    }
}
