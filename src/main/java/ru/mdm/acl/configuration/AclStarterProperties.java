package ru.mdm.acl.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "mdm.acl.starter")
public class AclStarterProperties {

    private List<String> actions;
}
