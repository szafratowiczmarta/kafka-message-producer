import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class KafkaMessageProducer {

    private volatile Long counter;
    private static final String MSG_SaleApartment = "{\"ApartmentID\":\"2\",\"Sold\":\"true\"}";

    public static void main(String[] args) throws InterruptedException {
        KafkaMessageProducer producer = new KafkaMessageProducer();
        producer.produce(1L, producer.getMessagesToProduce());
    }

    private void produce(long messagesPerTopic, List<Message> messages){
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        Producer<String, String> producer = new KafkaProducer<>(properties);

        messages.forEach(message -> {
            counter = 1L;
            while(counter <= messagesPerTopic){
                produceNext(producer, message);
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(10, 50));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        producer.close();
    }

    private void produceNext(Producer<String, String> producer, Message message){
        try{
            producer.send(new ProducerRecord<>(message.topic, message.key, message.value));
            System.out.println(String.format("Message %s send to topic %s", message.value, message.topic));
            counter++;
        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getLocalizedMessage());
        }
    }

    private List<Message> getMessagesToProduce(){

        List<Message> messages = new LinkedList<>();
        Message message = new Message("RSApartmentSale", "2", MSG_SaleApartment, 1);
        messages.add(message);
        return messages;

    }

}