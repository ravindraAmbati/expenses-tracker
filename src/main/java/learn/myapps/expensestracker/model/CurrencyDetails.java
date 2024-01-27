package learn.myapps.expensestracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder(toBuilder = true)
public class CurrencyDetails extends BasicDetails {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("exchangeRate")
    private BigDecimal exchangeRate;
    @JsonProperty("defaultCurrency")
    private String defaultCurrency;
}
