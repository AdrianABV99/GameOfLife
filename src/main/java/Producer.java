import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer {

    private KafkaProducer<Integer, String> producer;
    public  Producer() {

        createProducer();
    }

    public void createProducer() {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        producer = new KafkaProducer<>(prop);
    }

    public void send(Integer key, String value) {
        ProducerRecord<Integer, String> record = new ProducerRecord<>("app-topic",key,value);
        producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {

                Logger logger=LoggerFactory.getLogger(Producer.class);
                if ( e == null) {
                    logger.info("Successfully received the details as: \n" +
                            "Topic:" + recordMetadata.topic() + "\n" +
                            "Partition:" + recordMetadata.partition() + "\n" +
                            "Offset" + recordMetadata.offset() + "\n" +
                            "Timestamp" + recordMetadata.timestamp());
                }
                else {
                   logger.error("Can't produce,getting error",e);
                }
            }

        });
    }

    public void close() {
        producer.flush();
        producer.close();

    }
}
