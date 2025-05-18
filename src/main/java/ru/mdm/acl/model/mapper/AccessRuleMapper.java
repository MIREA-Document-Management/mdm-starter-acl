package ru.mdm.acl.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.mdm.acl.model.dto.AccessRuleDto;
import ru.mdm.acl.model.dto.CreateAccessRuleDto;
import ru.mdm.acl.model.dto.UpdateAccessRuleDto;
import ru.mdm.acl.model.entity.AccessRule;

@Mapper
public interface AccessRuleMapper {

    AccessRule toEntity(CreateAccessRuleDto dto);

    AccessRuleDto toDto(AccessRule entity);

    AccessRule updateEntity(@MappingTarget AccessRule entity, UpdateAccessRuleDto dto);
}
