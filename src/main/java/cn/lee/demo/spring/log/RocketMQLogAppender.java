package cn.lee.demo.spring.log;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.io.Serializable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author li wei  <liwei_yy1@migu.cn>
 * @Description: TODO
 * Package Name:cn.lee.demo.spring.log
 * Date: 2017/9/21 14:00
 * All rights Reserved, Designed By MiGu
 * Copyright: Copyright(C) 2016-2020
 * Company    MiGu  Co., Ltd.
 **/
@Plugin(name = "RocketMQ", category = "Core", elementType = "appender", printObject = true)
public class RocketMQLogAppender extends AbstractAppender {
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    protected RocketMQLogAppender(String name, Filter filter, Layout<? extends Serializable> layout, boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions);
    }

    @Override
    public void append(LogEvent logEvent) {
        try {
            readLock.lock();
            System.out.println(this.getFilter()==null?"not filter":this.getFilter().getOnMatch());
            this.getFilter().filter(logEvent);
            final byte[] bytes = getLayout().toByteArray(logEvent);//日志二进制文件，输出到指定位置就行
            //下面这个是要实现的自定义逻辑
            String msg=new String(bytes);
            StringBuilder builder=new StringBuilder();
            builder.append("Thread name:").append(Thread.currentThread().getId()+"->"+Thread.currentThread().getName()).append("|");
            builder.append("LOG:").append(msg).append("\n");
            System.out.print(builder);
        } catch (Exception ex) {
            if (!ignoreExceptions()) {
                throw new AppenderLoggingException(ex);
            }
        } finally {
            readLock.unlock();
        }
    }

    @PluginFactory
    public static RocketMQLogAppender createAppender(@PluginAttribute("name") String name,
                                                     @PluginElement("Filter") final Filter filter,
                                                     @PluginElement("Layout") Layout<? extends Serializable> layout,
                                                     @PluginAttribute("ignoreExceptions") boolean ignoreExceptions) {
        if (name == null) {
            LOGGER.error("No name provided for MyCustomAppenderImpl");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new RocketMQLogAppender(name, filter, layout, ignoreExceptions);
    }

}
