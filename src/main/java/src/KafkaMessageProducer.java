package src;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.LinkedList;
import java.util.List;

import static src.GlobalConstants.KAFKA_TOPIC_APARTMENT_SALE;
import static src.GlobalConstants.PROPERTIES;

@Slf4j
public class KafkaMessageProducer {

    public static void main(String[] args) {
        KafkaMessageProducer kafkaMessageProducer = new KafkaMessageProducer();
        kafkaMessageProducer.sentMessages(kafkaMessageProducer.getMessages());
    }

    private void sentMessages(List<Message> messages){
        Producer<String, String> producer = new KafkaProducer<>(PROPERTIES);

        messages.forEach(message -> {
            producer.send(new ProducerRecord<>(message.topic, message.key, message.value));
            log.info(String.format("Message %s send to topic %s", message.value, message.topic));

            /*try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(10, 50));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }*/

        });
        producer.close();
    }

    private List<Message> getMessages(){
        String messageApartmentSale = "{\"ApartmentID\":\"2\",\"Sold\":\"true\"}";
        String messageApartmentSale2 = "{\"ApartmentID\":\"2\",\"Sold\":\"true\"}";

        List<Message> messages = new LinkedList<>();
        Message message = new Message(KAFKA_TOPIC_APARTMENT_SALE, "2", messageApartmentSale, 1);
        Message message2 = new Message(KAFKA_TOPIC_APARTMENT_SALE, "3", messageApartmentSale2, 1);
        messages.add(message);
        messages.add(message2);

        return messages;
    }

}