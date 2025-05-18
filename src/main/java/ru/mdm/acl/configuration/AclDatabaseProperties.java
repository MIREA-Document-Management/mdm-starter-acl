package ru.mdm.acl.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mdm.acl.starter.db")
public class AclDatabaseProperties {

    private String host;
    private int port;
    private String name;
    private String schema;
    private String user;
    private String password;
}
