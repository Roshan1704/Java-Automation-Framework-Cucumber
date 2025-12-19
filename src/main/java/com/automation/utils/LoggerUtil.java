package com.automation.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Logger utility wrapper for Log4j2
 */
public class LoggerUtil {
    
    private static LoggerUtil instance;
    private Logger logger;

    private LoggerUtil() {
        logger = LogManager.getLogger(LoggerUtil.class);
    }

    public static synchronized LoggerUtil getInstance() {
        if (instance == null) {
            instance = new LoggerUtil();
        }
        return instance;
    }

    public void info(String message) {
        logger.info(message);
    }

    public void debug(String message) {
        logger.debug(message);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
