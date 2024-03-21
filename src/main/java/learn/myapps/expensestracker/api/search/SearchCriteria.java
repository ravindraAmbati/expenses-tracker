package learn.myapps.expensestracker.api.search;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SearchCriteria {

    public static final String EQUALS = "==";
    public static final String NOT_EQUALS = "!=";
    public static final String GREATER_THAN = ">";
    public static final String LESS_THAN = "<";
    public static final String LIKE = "%";

    private String key;
    private String operation;
    private Object value;
}
