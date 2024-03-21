package learn.myapps.expensestracker.api.search;

import com.querydsl.core.types.dsl.*;
import lombok.RequiredArgsConstructor;

import static learn.myapps.expensestracker.api.search.SearchCriteria.*;
import static org.apache.commons.lang3.StringUtils.isNumeric;


@RequiredArgsConstructor
public class SearchPredicate {

    private final SearchCriteria searchCriteria;

    BooleanExpression getPredicate(PathBuilder pathBuilder) {
        //handle date comparisons date range i.e, between
        //handle number and decimal comparisons including range i.e., between
        if (isNumeric(searchCriteria.getValue().toString())) {
            int searchValue = Integer.parseInt(searchCriteria.getValue().toString());
            NumberPath<Integer> numberPath = pathBuilder.getNumber(searchCriteria.getKey(), Integer.class);
            return switch (searchCriteria.getOperation()) {
                case EQUALS -> numberPath.eq(searchValue);
                case NOT_EQUALS -> numberPath.eq(searchValue).not();
                case GREATER_THAN -> numberPath.goe(searchValue);
                case LESS_THAN -> numberPath.loe(searchValue);
                default -> throw new IllegalStateException("Unexpected value: " + searchCriteria.getOperation());
            };
        } else {
            String searchValue = searchCriteria.getValue().toString();
            StringPath stringPath = pathBuilder.getString(searchCriteria.getKey());
            return switch (searchCriteria.getOperation()) {
                case EQUALS -> stringPath.equalsIgnoreCase(searchValue);
                case NOT_EQUALS -> stringPath.notEqualsIgnoreCase(searchValue);
                case LIKE -> stringPath.containsIgnoreCase(searchValue);
                default -> throw new IllegalStateException("Unexpected value: " + searchCriteria.getOperation());
            };
        }
    }

    //not in use.
    NumberExpression getNumericPredicate(PathBuilder pathBuilder) {
        NumberPath<Integer> numberPath = pathBuilder.getNumber(searchCriteria.getKey(), Integer.class);
        if (searchCriteria.getValue().toString().equals(searchCriteria.getKey().toString())) {
            return switch (searchCriteria.getOperation()) {
                case "(-)" -> numberPath.count();
                case "(--)" -> numberPath.countDistinct();
                case "(5)" -> numberPath.avg();
                case "(9)" -> numberPath.max();
                case "(0)" -> numberPath.min();
                case "(+)" -> numberPath.sum();
                default -> throw new IllegalStateException("Unexpected value: " + searchCriteria.getOperation());
            };
        }
        throw new IllegalStateException("Unexpected value: " + searchCriteria.getOperation());
    }
}
