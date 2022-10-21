package src;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class Message {

    public String topic;
    public String key;
    public String value;
    public int duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(topic, message.topic) &&
                Objects.equals(key, message.key) &&
                Objects.equals(value, message.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, key, value);
    }
}
