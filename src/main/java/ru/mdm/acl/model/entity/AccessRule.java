package ru.mdm.acl.model.entity;

import lombok.Data;
import org.springframework.data.annotation.*;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ru.mdm.acl.model.RuleType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Сущность правила доступа.
 */
@Data
@Table("mdm_access_rules")
public class AccessRule {

    /**
     * Идентификатор правила.
     */
    @Id
    @Column("id")
    private UUID id;

    /**
     * Идентификатор объекта, на который распространяется правило.
     */
    @Column("object_id")
    private UUID objectId;

    /**
     * Действие.
     */
    @Column("action")
    private String action;

    /**
     * Тип правила (разрешающий/запрещающий).
     */
    @Column("rule_type")
    private RuleType ruleType;

    /**
     * Идентификаторы ролей, на которые распространяется правило.
     */
    @Column("roles")
    private List<String> roles;

    /**
     * Идентификаторы групп, на которые распространяется правило.
     */
    @Column("groups")
    private List<String> groups;

    /**
     * Идентификаторы пользователей, на которые распространяется правило.
     */
    @Column("users")
    private List<String> users;

    /**
     * Кто создал правило.
     */
    @CreatedBy
    @Column("created_by")
    private String createdBy = "admin";

    /**
     * Дата и время создания.
     */
    @CreatedDate
    @Column("creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * Кто последний изменил правило.
     */
    @LastModifiedBy
    @Column("modified_by")
    private String modifiedBy = "admin";

    /**
     * Дата и время последнего изменения.
     */
    @LastModifiedDate
    @Column("modification_date")
    private LocalDateTime modificationDate = LocalDateTime.now();
}
