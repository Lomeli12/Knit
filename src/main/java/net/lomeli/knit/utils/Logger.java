package net.lomeli.knit.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class Logger {
    private static final String PREFIX_FORMAT = "[%s]: ";

    private final org.apache.logging.log4j.Logger logger;
    private final String name;

    public Logger(String name) {
        this.logger = LogManager.getLogger(name);
        this.name = name;
    }

    private void log(Level level, Object msg, Object... args) {
        if (args != null && args.length > 0)
            logger.log(level, getPrefix() + msg.toString(), args);
        else logger.log(level, getPrefix() + msg.toString());
    }

    public void info(Object msg, Object... args) {
        log(Level.INFO, msg, args);
    }

    public void warn(Object msg, Object... args) {
        log(Level.WARN, msg, args);
    }

    public void error(Object msg, Object... args) {
        log(Level.ERROR, msg, args);
    }

    public void exception(Object msg, Exception ex, Object... args) {
        if (args != null && args.length > 0)
            logger.log(Level.ERROR, String.format(msg.toString(), args), ex);
        else logger.log(Level.ERROR, msg.toString(), ex);
    }

    public void debug(Object msg, Object... args) {
        log(Level.DEBUG, msg, args);
    }

    private String getPrefix() {
        return String.format(PREFIX_FORMAT, name);
    }
}
