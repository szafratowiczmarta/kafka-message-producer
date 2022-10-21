package src.multithreading;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.File;
import java.util.Scanner;

import static src.GlobalConstants.FILES_LOCATION;
import static src.GlobalConstants.KAFKA_TOPIC_APARTMENT_SALE;

@Slf4j
@AllArgsConstructor
public class Dispatcher implements Runnable {
    private final KafkaProducer<String, String> producer;
    private final String dataFileName;

    @Override
    public void run() {
        String filePath = FILES_LOCATION + dataFileName;
        File file = new File(filePath);
        int msgCounter = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] message = scanner.nextLine().trim().split(",");
                producer.send(new ProducerRecord<>(KAFKA_TOPIC_APARTMENT_SALE, message[0], message[1]));
                log.info(String.format("Message sent -> key:%s, value:%s", message[0], message[1]));
                msgCounter++;
            }
            log.info(String.format("Finished sending %s messages from %s.", msgCounter, dataFileName));
        } catch (Exception e) {
            log.error(String.format("Exception in thread %s", dataFileName));
            throw new RuntimeException(e);
        }
    }
}
