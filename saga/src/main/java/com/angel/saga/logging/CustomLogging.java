package com.angel.saga.logging;

import java.util.logging.Logger;

public final class CustomLogging {
    public static void log(Class cl, String message) {
        Logger logger = Logger.getLogger(cl.getClass().getSimpleName());
        logger.info(message);
    }
}
