package me.alexanderhodes.blocktrace.businesslogic;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by alexa on 30.09.2017.
 */
@WebListener
public class BackgroundJob implements ServletContextListener {

    private ScheduledExecutorService scheduledExecutorService;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SomeJob(), 1, 5, TimeUnit.MINUTES);
        System.out.println("######## Job initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        scheduledExecutorService.shutdown();
        System.out.println("######## Job destroyed");
    }

}
