package ru.mdm.acl.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Автоконфигурация сервиса прав доступа.
 */
@Configuration
@ComponentScan(basePackages = {"ru.mdm.acl"})
public class MdmAclAutoConfiguration {
}
