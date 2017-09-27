package ru.otus.messageserver;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;


import org.eclipse.jetty.websocket.jsr356.server.AnnotatedServerEndpointConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.context.WebApplicationContext;

/**
 * @autor slonikmak on 26.09.2017.
 */
@Configuration
@ComponentScan("ru.otus.messageserver")
@ImportResource("classpath:/appConfig.xml")
public class AppConfig {
    @Inject private WebApplicationContext context;

    private ServerContainer container;

    public class SpringServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
        @Override
        public < T > T getEndpointInstance( Class< T > endpointClass )
                throws InstantiationException {
            return context.getAutowireCapableBeanFactory().createBean( endpointClass );
        }
    }

    @Bean
    public ServerEndpointConfig.Configurator configurator() {
        return new SpringServerEndpointConfigurator();
    }

    @PostConstruct
    public void init() throws DeploymentException {
        container = ( ServerContainer )context.getServletContext().
                getAttribute( javax.websocket.server.ServerContainer.class.getName() );
        container.addEndpoint(new AnnotatedServerEndpointConfig(MySocket.class, MySocket.class.getAnnotation(ServerEndpoint.class)){
            @Override
            public Configurator getConfigurator() {
                return configurator();
            }
        });
    }
}
