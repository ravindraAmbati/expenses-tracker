package learn.myapps.expensestracker.api.expenses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import learn.myapps.expensestracker.api.basic.BasicDetails;
import learn.myapps.expensestracker.api.category.ExpensesCategoryDetails;
import learn.myapps.expensestracker.api.currency.CurrencyDetails;
import learn.myapps.expensestracker.api.payment.PaymentModeDetails;
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
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "expenses_details", schema = "expenses_tracker")
public class ExpensesDetails extends BasicDetails {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_id_sequence")
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
