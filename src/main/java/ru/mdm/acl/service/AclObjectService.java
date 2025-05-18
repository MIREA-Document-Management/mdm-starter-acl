package ru.mdm.acl.service;

import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Сервис для работы объектом, на который накладываются правила.
 */
public interface AclObjectService {

    /**
     * Получить логин создателя объекта по идентификатору.
     *
     * @param objectId идентификатор объекта
     * @return логин создателя
     */
    Mono<String> getOwnerLogin(UUID objectId);
}
