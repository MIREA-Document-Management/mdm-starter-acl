package ru.mdm.acl.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(basePackages = "ru.mdm.acl.repository")
public class AclStarterR2dbcConfig {
}
