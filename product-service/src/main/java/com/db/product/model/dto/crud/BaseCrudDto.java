package com.db.product.model.dto.crud;


import com.db.product.model.dto.BaseDto;
import com.db.product.model.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * This DTO is presented as the parent of all crud DTOs.
 * A crud DTO is a DTO class that completely corresponds to an entity class.
 *
 * @param <E> The corresponding entity class. (Please consider this parameter is not used so far apparently, but it's not
 *            redundant, it's used as a reference check of I to make sure the type of identifier of entity and DTO are the same)
 * @param <I> The Identifier type of the corresponding entity class in order to be used as the same identifier type at DTO
 */

@Getter
@Setter
public abstract class BaseCrudDto<E extends BaseEntity<I>, I extends Number> extends BaseDto {
    private I id;

}
