package com.cc.libra.utils;

/**
 * Created by chenchen39 on 2018/9/29.
 */
public class ExceptionUtil {

    public static String getExceptionStack(Exception e) {
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        StringBuffer buffer = new StringBuffer();
        buffer.append(e.toString()).append("\n");
        for (int index = stackTraceElements.length - 1; index >= 0; --index) {
            buffer.append("at [").append(stackTraceElements[index].getClassName()).append(",");
            buffer.append(stackTraceElements[index].getFileName()).append(",");
            buffer.append(stackTraceElements[index].getMethodName()).append(",");
            buffer.append(stackTraceElements[index].getLineNumber()).append("]\n");
        }
        return buffer.toString();
    }
}
