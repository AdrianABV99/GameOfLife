import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

public class Producer {

    private KafkaProducer<Long, String> producer;
    public  Producer() {

        createProducer();
    }

    public void createProducer() {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        producer = new KafkaProducer<Long, String>(prop);
    }

    public void send(Long key, String value) {
        ProducerRecord<Long, String> record = new ProducerRecord<>("app-topic",key,value);
        producer.send(record);
    }

    public void close() {
        producer.flush();
        producer.close();

    }
}
