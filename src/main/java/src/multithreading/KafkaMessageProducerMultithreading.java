package src.multithreading;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.io.File;

import static src.GlobalConstants.FILES_LOCATION;
import static src.GlobalConstants.PROPERTIES;

@Slf4j
@AllArgsConstructor
public class KafkaMessageProducerMultithreading {

    public static void main(String[] args) {

        String[] dataFiles = new File(FILES_LOCATION).list();
        int numberOfThreads = dataFiles.length;

        KafkaProducer<String, String> producer = new KafkaProducer<>(PROPERTIES);

        Thread[] dispatchers = new Thread[numberOfThreads];

        for (int i = 0; i < numberOfThreads; i++) {
            dispatchers[i] = new Thread(new Dispatcher(producer, dataFiles[i]));
            dispatchers[i].start();
        }

        try {
            for (Thread t : dispatchers)
                t.join();
        } catch (InterruptedException e) {
            log.error("Thread Interrupted");
        } finally {
            producer.close();
            log.info("Finished dispatcher demo - Closing Kafka Producer.");
        }
    }

}
