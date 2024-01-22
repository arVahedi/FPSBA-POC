package com.db.product.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * A general parent of all possible DTOs
 */

@Getter
@Setter
public abstract class BaseDto implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
