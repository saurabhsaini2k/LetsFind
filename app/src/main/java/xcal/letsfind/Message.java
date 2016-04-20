package xcal.letsfind;

/**
 * Created by ILLUMINOUS on 26-02-2016.
 */
public class Message {


        private String message;
        private long timestamp;

        public Message() {
        }

        public Message(String message, long timestamp) {
            this.message = message;
            this.timestamp = timestamp;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }
    }
