package com.credibanco.cards.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BalanceRechargeRequest {

    @NotBlank
    @Length(min = 16, max = 16)
    @ApiModelProperty(example = "1020301234567890")
    private String cardId;

    @Positive
    @ApiModelProperty(example = "100")
    private BigDecimal balance;
}
