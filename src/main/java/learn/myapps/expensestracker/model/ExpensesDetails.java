package learn.myapps.expensestracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder(toBuilder = true)
public class ExpensesDetails extends BasicDetails {

    @JsonProperty("id")
    private long id;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("paidBy")
    private String paidBy;
    @JsonProperty("paidTo")
    private String paidTo;
    @JsonProperty("dateAndTime")
    private LocalDateTime dateAndTime;
    @JsonProperty("paymentMode")
    private PaymentModeDetails paymentMode;
    @JsonProperty("category")
    private ExpensesCategoryDetails category;
    @JsonProperty("currency")
    private CurrencyDetails currency;
}
