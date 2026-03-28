package org.slf4j;
public class LoggerFactory {
    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }
    public static Logger getLogger(String name) {
        return new Logger() {
            public void info(String msg) {}
            public void warn(String msg) {}
            public void error(String msg) {}
            public void debug(String msg) {}
            public void info(String msg, Throwable t) {}
            public void warn(String msg, Throwable t) {}
            public void error(String msg, Throwable t) {}
            public void debug(String msg, Throwable t) {}
            public void trace(String msg) {}
            public void trace(String msg, Throwable t) {}
            public boolean isInfoEnabled() { return false; }
            public boolean isWarnEnabled() { return false; }
            public boolean isErrorEnabled() { return false; }
            public boolean isDebugEnabled() { return false; }
            public boolean isTraceEnabled() { return false; }
        };
    }
}
