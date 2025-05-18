package ru.mdm.acl.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.mdm.acl.model.entity.AccessRule;

import java.util.UUID;

/**
 * Репозиторий для работы с правилами доступа.
 */
@Repository
public interface AccessRuleRepository extends ReactiveSortingRepository<AccessRule, UUID>,
        ReactiveCrudRepository<AccessRule, UUID> {

    Mono<AccessRule> findByObjectIdAndAction(UUID objectId, String action);

    Flux<AccessRule> findByObjectId(UUID objectId, Pageable pageable);

    Mono<AccessRule> findFirstByObjectId(UUID objectId);

    Mono<Void> deleteByObjectId(UUID objectId);
}
