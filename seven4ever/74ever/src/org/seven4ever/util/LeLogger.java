/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.seven4ever.util;

/**
 *
 * @author marius
 */

import lejos.nxt.Sound;
import lejos.nxt.LCD;
import java.text.DateFormat;
import java.util.Date;

public class LeLogger {

    /**
     * * This singleton class provides methods that prints  messages and exceptions
     * of different log levels to the nxt screen. The log level is configured in the
     * {@link Configuration} class. A message is only logged if the log level is
     * the same or higher than the configured log level.
     */
    
    /**
     * The SYSTEM log level. <br>
     * Basic engine status messages like engine startup, player
     * connected/disconnected, starting/finishing loading resources and so on.
     */
    public static final int SYSTEM = 0x00;
    /**
     * The ERROR log level. <br>
     * Critical errors that caused an engine shutdown.<br>
     * <br>
     * ALWAYS log an error message first explaining what went wrong and then the
     * exception.
     */
    public static final int ERROR = 0x01;
    /**
     * The WARN log level. Warnings (Exceptions) <br>
     * Non critical exceptions that don't cause an engine shutdown but shouldn't
     * have happened at all. <br>
     * <br>
     * ALWAYS log an warn message first explaining what went wrong and then the
     * exception.
     */
    public static final int WARN = 0x02;
    /**
     * The DEBUG log level. <br>
     * Used to debug stuff during development.<br>
     * Do whatever you want but try to log self-explaining messages.
     */
    public static final int DEBUG = 0x03;
    /**
     * The ALL log level.<br>
     * Theres no method for this. Its just used to set the Log Level in the
     * configuration so that everything is logged.
     */
    public static final int ALL = 0xFF;
    /**
     * The singleton instance of Logger.
     */
    private static Logger instance;
    /**
     * The date format.
     */
    private DateFormat logDateFormat;
    public static int loglevel = Logger.ALL;
    public static String newline = System.getProperty("line.separator");

    /**
     * Instantiates a new logger.
     */
    private void writeMessage(String message, int logLevel) {
        if (logLevel <= loglevel) {
            LCD.drawString(getLogPrefix(logLevel) + message, 0, 0);
            Sound.twoBeeps();

        }
    }

    /**
     * Write an exception to the log.
     *
     * @param Exception the exception
     * @param logLevel the log level
     */
    private void writeException(Exception exception, int logLevel) {
        if (logLevel <= loglevel) {
            LCD.drawString(getLogPrefix(logLevel) + getStackTraceString(exception), 0, 0);
            Sound.twoBeeps();

        }
    }

    /**
     * Gets the log prefix containing the formatted date, the log level and the
     * error message.
     *
     * @param logLevel the log level
     * @return the log prefix
     */
    private String getLogPrefix(int logLevel) {
        StringBuilder sb = new StringBuilder();
        sb.append(logDateFormat.format(new Date()));
        switch (logLevel) {
            case ERROR:
                sb.append(" - ERROR: ");
                break;
            case WARN:
                sb.append(" - WARN: ");
                break;
            case DEBUG:
                sb.append(" - DEBUG: ");
                break;
            case SYSTEM:
                sb.append(" - SYSTEM: ");
        }
        return sb.toString();
    }

    public String getStackTraceString(Exception exception) {
        StringBuilder sb = new StringBuilder();
        sb.append(exception.getMessage());
        for (StackTraceElement sTE : exception.getStackTrace()) {
            sb.append("\n");
            sb.append(sTE.toString());
        }
        return sb.toString();
    }

    public void error(Object object) {
        writeMessage(object.toString(), ERROR);
    }

    /**
     * Gets the single instance of Logger.
     *
     * @return single instance of Logger
     */
    public static Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }

    /**
     * Log an exception on the ERROR level.
     *
     * @param exception the exception
     */
    public void error(Exception exception) {
        writeException(exception, ERROR);
    }

    /**
     * Log a message on the WARN level.
     *
     * @param message the message
     */
    public void warn(Object object) {
        writeMessage(object.toString(), WARN);
    }

    /**
     * Log an exception on the WARN level.
     *
     * @param exception the exception
     */
    public void warn(Exception exception) {
        writeException(exception, WARN);
    }

    /**
     * Log a message on the DEBUG level.
     *
     * @param message the message
     */
    public void debug(Object object) {
        writeMessage(object.toString(), DEBUG);
    }

    /**
     * Log an exception on the DEBUG level.
     *
     * @param exception the exception
     */
    public void debug(Exception exception) {
        writeException(exception, DEBUG);
    }

    /**
     * Log a message on the SYSTEM level.
     *
     * @param message the message
     */
    public void system(Object object) {
        writeMessage(object.toString(), SYSTEM);
    }

    /**
     * Log an exception on the SYSTEM level.
     *
     * @param exception the exception
     */
    public void system(Exception exception) {
        writeException(exception, SYSTEM);
    }
}

