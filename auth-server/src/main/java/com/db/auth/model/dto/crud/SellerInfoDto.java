package com.db.auth.model.dto.crud;

import com.db.auth.model.entity.SellerInfo;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerInfoDto extends BaseCrudDto<SellerInfo, Long> {

    @NotBlank(message = "address is mandatory")
    private String address;
    @Digits(integer = 5, fraction = 0, message = "postalCode should be all digits")
    @NotBlank(message = "postalCode is mandatory")
    private String postalCode;
    @NotBlank(message = "iban is mandatory")
    private String iban;
    @Min(value = 1, message = "the minimum acceptable value for userId is 1")
    private long userId;


}
