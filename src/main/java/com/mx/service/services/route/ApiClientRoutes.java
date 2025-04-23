package com.mx.banorte.services.route;

import javax.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

@ApplicationScoped
public class ApiClientRoutes extends RouteBuilder {

@Override
public void configure() throws Exception {
        //Configuracion de Rest BindingMode para recibir y enviar respuesta JSON
                restConfiguration().bindingMode(RestBindingMode.json);

                rest("/service")
                
                .get("hello")
                .description("endpoint Hello test")
                .to("direct:llamada");
  
                from("direct:llamada")
                .routeId("callService")
                .log("Probando endpoint hello")
                .doTry()
                .convertBodyTo(String.class)
                .setBody(simple("Hola"))
                .log("Body de llamada: ${body}")
                .doCatch(Exception.class)
                .log("Error llamar api externa ${exception.message}")
                .setBody(constant("Error llamar api externa "))
                .end();

    }
    


}
