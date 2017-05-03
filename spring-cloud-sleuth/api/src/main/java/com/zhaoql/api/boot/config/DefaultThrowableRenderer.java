package com.zhaoql.api.boot.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.helpers.Transform;
import ch.qos.logback.core.html.IThrowableRenderer;

public class DefaultThrowableRenderer implements IThrowableRenderer<ILoggingEvent> {

    static final String TRACE_PREFIX = "\t        ";

    public void render(StringBuilder sbuf, ILoggingEvent event) {
        IThrowableProxy tp = event.getThrowableProxy();
        while (tp != null) {
            render(sbuf, tp);
            tp = tp.getCause();
        }
    }

    void render(StringBuilder sbuf, IThrowableProxy tp) {
        printFirstLine(sbuf, tp);

        int commonFrames = tp.getCommonFrames();
        StackTraceElementProxy[] stepArray = tp.getStackTraceElementProxyArray();

        for (int i = 0; i < stepArray.length - commonFrames; i++) {
            StackTraceElementProxy step = stepArray[i];
            sbuf.append(TRACE_PREFIX);
            sbuf.append(Transform.escapeTags(step.toString()));
            sbuf.append(CoreConstants.LINE_SEPARATOR);
        }

        if (commonFrames > 0) {
            sbuf.append(TRACE_PREFIX);
            sbuf.append("\t... ").append(commonFrames).append(" common frames omitted").append(CoreConstants.LINE_SEPARATOR);
        }
    }

    public void printFirstLine(StringBuilder sb, IThrowableProxy tp) {
        int commonFrames = tp.getCommonFrames();
        if (commonFrames > 0) {
            sb.append("\t").append(CoreConstants.CAUSED_BY);
        }
        sb.append(tp.getClassName()).append(": ").append(Transform.escapeTags(tp.getMessage()));
        sb.append(CoreConstants.LINE_SEPARATOR);
    }

}