package src;

import lombok.NoArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

@NoArgsConstructor
public class GlobalConstants {

    public static String FILES_LOCATION = "src/main/java/src/multithreading/data/";
    public static String KAFKA_TOPIC_APARTMENT_SALE = "RSApartmentSale";
    public static final Properties PROPERTIES = new Properties();

    static {
        PROPERTIES.setProperty("bootstrap.servers", "localhost:9092");
        PROPERTIES.setProperty("key.serializer", StringSerializer.class.getName());
        PROPERTIES.setProperty("value.serializer", StringSerializer.class.getName());
    }

}
