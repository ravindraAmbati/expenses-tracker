package learn.myapps.expensestracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@SuperBuilder(toBuilder = true)
public class PaymentModeDetails extends BasicDetails {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("paymentMode")
    private String paymentMode;
    @JsonProperty("cardDetails")
    private String cardDetails;
    @JsonProperty("cardType")
    private String cardType;
    @JsonProperty("upiDetails")
    private String upiDetails;
    @JsonProperty("accountDetails")
    private String accountDetails;
}
