package com.appsdeveloperblog.reactive.ws.users.infrastructure;

import org.h2.tools.Server;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;

import java.sql.SQLException;

@Configuration
//@Profile({"dev","test"})
@Profile({"!prod & !production"}) //this enables h2 console ONLY to not production environments
public class H2ConsoleConfiguration {
    //creating a web server to run our H2 database

    //2.web server object
    private Server webServer;

    //3.Add an eventListener that will be executed when the spring context starts
    @EventListener(ApplicationStartedEvent.class)
    public void start() throws SQLException {
        //1.define web server port
        String WEB_PORT = "8082";
        this.webServer = Server.createWebServer("-webPort", WEB_PORT).start();
    }

    //4.Close the event when the spring context finished
    @EventListener(ContextClosedEvent.class)
    public void stop(){
        this.webServer.stop();
    }
}
