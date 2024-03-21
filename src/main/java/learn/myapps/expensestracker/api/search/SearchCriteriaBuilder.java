package learn.myapps.expensestracker.api.search;

import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class SearchCriteriaBuilder {
    private static Pattern pattern = null;
    private static List<String> operatorsList = new ArrayList<>();

    public static List<String> getOperators() {
        if (operatorsList.isEmpty()) {
            List<String> operations = new ArrayList<>();
            operations.add(SearchCriteria.EQUALS);
            operations.add(SearchCriteria.NOT_EQUALS);
            operations.add(SearchCriteria.GREATER_THAN);
            operations.add(SearchCriteria.LESS_THAN);
            operations.add(SearchCriteria.LIKE);
            operatorsList = operations;
        }
        return operatorsList;
    }

    public static Pattern getPattern() {
        if (null == pattern) {
            StringJoiner joiner = new StringJoiner("|", "(", ")");
            getOperators().forEach(joiner::add);
            pattern = Pattern.compile("(\\w+?)" + joiner + "(\\w+?),");
        }
        return pattern;
    }


    public static List<SearchCriteria> buildSearchCriteria(String search) {
        List<SearchCriteria> searchCriteriaList = new ArrayList<>();
        if (search != null) {
            Matcher matcher = getPattern().matcher(search + ",");
            while (matcher.find()) {
                SearchCriteria searchCriteria = SearchCriteria.builder()
                        .key(matcher.group(1))
                        .operation(matcher.group(2))
                        .value(matcher.group(3))
                        .build();
                searchCriteriaList.add(searchCriteria);
            }
            if (searchCriteriaList.size() != search.split(",").length) {
                throw new IllegalArgumentException(MessageFormat.format("Invalid Search string, please re-check your search string: {0}", search));
            }
        }
        return searchCriteriaList;
    }
}
