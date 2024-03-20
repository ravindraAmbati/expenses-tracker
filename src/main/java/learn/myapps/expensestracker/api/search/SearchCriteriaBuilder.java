package learn.myapps.expensestracker.api.search;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchCriteriaBuilder {

    private static final Pattern pattern = Pattern.compile("(\\w+?)(==|!=|>|<|-)(\\w+?|'%\\w+?%'),");

    public static List<SearchCriteria> buildSearchCriteria(String search) {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        if (search != null) {
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                SearchCriteria searchCriteria = SearchCriteria.builder()
                        .key(matcher.group(1))
                        .operation(matcher.group(2))
                        .value(matcher.group(3))
                        .build();
                searchCriteriaList.add(searchCriteria);
            }
        }
        return searchCriteriaList;
    }
}
