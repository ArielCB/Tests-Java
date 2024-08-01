package es.codeurjc.ais.nitflex.notification;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

@Component
public class NotificationService {

    private static final Logger logger = Logger.getLogger("Logger");

    public void notify(String message) {
        logger.info(message);
    }

}
