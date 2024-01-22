package com.db.user.configuration;


import com.db.user.model.dto.crud.BaseCrudDto;
import com.db.user.model.entity.BaseEntity;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;


/**
 * This is global configuration of mapstruct mappers.
 * <p>
 * For further information take a look at <a href="https://mapstruct.org/documentation/stable/reference/html/#shared-configurations">its official documentation</a>
 */

@MapperConfig(
        componentModel = "spring",
        mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG
)
public interface GlobalMapperConfig extends BaseConfig {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "insertDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    BaseEntity<?> anyCrudDtoToDomainObjectConfiguration(BaseCrudDto<?, ?> dto);
}
