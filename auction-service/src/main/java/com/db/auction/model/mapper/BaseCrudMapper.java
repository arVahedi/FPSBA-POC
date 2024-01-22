package com.db.auction.model.mapper;

import com.db.auction.model.dto.crud.BaseCrudDto;
import com.db.auction.model.entity.BaseEntity;
import com.db.lib.utility.mapping.ObjectMapperUtility;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Map;

/**
 * A parent for all crud mappers.
 * <p>
 * A crud mapper is a mapper which is responsible to handle mapping of an entity to a {@link BaseCrudDto crud DTO} and vice versa
 *
 * @param <E> the target entity (basically a child of {@link BaseEntity})
 * @param <D> the target DTO (basically a child of {@link BaseCrudDto})
 */

public interface BaseCrudMapper<E extends BaseEntity<?>, D extends BaseCrudDto<E, ?>> {

    E toEntity(D dto);

    @InheritConfiguration(name = "toEntity")
    void updateEntity(@MappingTarget E entity, D dto);

    D toDto(E entity);

    List<E> toEntities(List<D> dtos);

    List<D> toDtos(List<E> entities);

    default void patchDto(@MappingTarget D dto, Map<String, Object> properties) {
        ObjectMapperUtility.convertMapToObject(properties, dto);
    }
}
