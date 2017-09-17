package ru.otus.simpleserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.cachedDBservice.CachedDbService;
import ru.otus.dbhelper.DbHelper;
import ru.otus.executor.Executor;
import ru.otus.interfaces.DBService;
import ru.otus.models.UserDataSet;

import ru.otus.simpleserver.servlets.AdminServlet;
import ru.otus.simpleserver.servlets.CacheStateServlet;
import ru.otus.simpleserver.servlets.LoginServlet;
import ru.otus.softcache.CacheFactory;
import ru.otus.softcache.SimpleCache;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SimpleServer {
    static final private String PUBLIC_HTML = "L12_Jetty/public_html";
    static final private int PORT = 8090;

    private SimpleCache<Long, UserDataSet> cache;
    private DBService dbService;
    private Map<String, UserDataSet> authorized;

    public void start() throws Exception {

        configureDBService();
        authorized = new HashMap<>();

        ResourceHandler resourceHandler = new ResourceHandler();
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        resourceHandler.setResourceBase(PUBLIC_HTML);
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(authorized)),"/admin");
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(dbService, authorized)), "/login");
        servletContextHandler.addServlet(new ServletHolder(new CacheStateServlet(cache)), "/status");

        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, servletContextHandler));

        server.start();

        testingCache();

        server.join();

    }

    private void configureDBService(){
        Executor executor = new Executor(DbHelper.getConnection());
        try {
            executor.execUpdate("drop table if exists users2");
            executor.execUpdate("create table if not exists users2 (id  bigserial not null, age int, name varchar(256), pass varchar(256),status varchar(256), primary key (id))");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cache = CacheFactory
                .getLifeCache(20, 2000);
        dbService = new CachedDbService(cache);
        UserDataSet user = new UserDataSet("anton", 29);
        user.setPass("admin");
        user.setStatus("admin");
        dbService.save(user);
    }

    private void testingCache(){
        new Thread(() -> {
            int size = 100;

            while (true){
                for (int i = 10; i < size; i++) {
                    cache.put((long) i, new UserDataSet("user"+i, i));
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 10; i < size; i++) {

                    cache.get((long)i);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SimpleServer simpleServer = new SimpleServer();
        try {
            simpleServer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
