package xcal.letsfind;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ILLUMINOUS on 26-02-2016.
 */
public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
