package br.pegz.pocigniteboot.dsl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AlertManagerConfiguration {

    @Value("${mail.service.url}")
    private String baseUrl;

    @Value("${mail.service.user}")
    private String user;

    @Value("${mail.service.pwd}")
    private String password;

    @Value("${enableFilePersistence}")
    private boolean enableFilePersistence;

    @Value("${ignite.connector.port}")
    private int ignitePort;

    @Value("${ignite.connector.portrange}")
    private String ignitePortRange;

    @Value("${ignite.filepath}")
    private String ignitePersistenceFilePath;
}
