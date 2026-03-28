package org.slf4j;
public interface Logger {
    void info(String msg);
    void warn(String msg);
    void error(String msg);
    void debug(String msg);
    void info(String msg, Throwable t);
    void warn(String msg, Throwable t);
    void error(String msg, Throwable t);
    void debug(String msg, Throwable t);
    void trace(String msg);
    void trace(String msg, Throwable t);
    boolean isInfoEnabled();
    boolean isWarnEnabled();
    boolean isErrorEnabled();
    boolean isDebugEnabled();
    boolean isTraceEnabled();
}
