package ru.otus.messageserver;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @autor slonikmak on 27.09.2017.
 */
public class EmbeddedJettySpringContextLoaderListener extends ContextLoader implements ApplicationContextAware, ServletContextListener {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Returns the parent application context as set by the
     * {@link ApplicationContextAware} interface.
     *
     * @return The initial ApplicationContext that loads the Jetty messageserver.
     */
    @Override
    protected ApplicationContext loadParentContext(ServletContext servletContext) {
        return this.applicationContext;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.initWebApplicationContext(sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //not needed
    }
}
