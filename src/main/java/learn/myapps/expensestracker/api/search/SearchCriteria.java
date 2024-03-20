package learn.myapps.expensestracker.api.search;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;
}
