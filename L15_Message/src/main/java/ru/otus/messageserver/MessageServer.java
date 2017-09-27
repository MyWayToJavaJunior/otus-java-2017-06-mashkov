package ru.otus.messageserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Collection;

/**
 * @autor slonikmak on 25.09.2017.
 */

public class MessageServer {
    static final private String PUBLIC_HTML = "./L15_Message/public_html";
    static final private int PORT = 8090;

    void startServer() throws Exception {

        /**FIXME непонятно как получить тут Spring context для дополнительной инициализации бинов,
         например запустить start у messageSystem*/
        Server server = new Server(PORT);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        //EmbeddedJettySpringContextLoaderListener loaderListener = new EmbeddedJettySpringContextLoaderListener();

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);
        context.setContextPath("/");
        context.addEventListener(new ContextLoaderListener());
        context.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        context.setInitParameter("contextConfigLocation", AppConfig.class.getName());

        final ServletHolder defaultHolder = new ServletHolder(new DefaultServlet());
        defaultHolder.setInitParameter("resourceBase", PUBLIC_HTML);
        context.addServlet(defaultHolder, "/");

        server.setHandler(context);
        WebSocketServerContainerInitializer.configureContext(context);


        server.start();
        server.join();

    }

    public static void main(String[] args) {
        MessageServer messageServer = new MessageServer();
        try {
            messageServer.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
