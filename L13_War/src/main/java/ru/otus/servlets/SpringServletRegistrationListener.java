package ru.otus.servlets;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import java.util.Map;

public class SpringServletRegistrationListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext ();
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext (servletContext);
        Map<String, Servlet> servlets = ((Map<String, Servlet>)appContext.getBean ("servlets", Map.class));

        for (Map.Entry<String, Servlet> bean : servlets.entrySet ()){
            ServletRegistration.Dynamic dynamic = servletContext.addServlet (bean.getKey (), bean.getValue ());
            if (bean.getValue ().getClass ().isAnnotationPresent (MultipartConfig.class))
                dynamic.setMultipartConfig (new MultipartConfigElement ( bean.getValue ().getClass ().getAnnotation (MultipartConfig.class)));
            dynamic.addMapping (bean.getKey ());
        }



    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
