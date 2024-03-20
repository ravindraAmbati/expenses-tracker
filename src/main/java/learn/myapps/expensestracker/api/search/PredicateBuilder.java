package learn.myapps.expensestracker.api.search;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PredicateBuilder {

    private List<SearchCriteria> searchCriteriaList;

    public PredicateBuilder() {
        this.searchCriteriaList = new ArrayList<>();
    }

    public PredicateBuilder(List<SearchCriteria> searchCriteriaList) {
        this.searchCriteriaList = searchCriteriaList;
    }

    void with(SearchCriteria searchCriteria) {
        searchCriteriaList.add(searchCriteria);
    }

    public void with(String key, String operation, Object value) {
        with(new SearchCriteria(key, operation, value));
    }

    public BooleanExpression build(PathBuilder PathBuilder) {
        if (searchCriteriaList.isEmpty()) {
            throw new NullPointerException("Empty Search Criteria List");
        }

        List<BooleanExpression> predicates = searchCriteriaList.stream().map(searchCriteria -> {
            SearchPredicate predicate = new SearchPredicate(searchCriteria);
            return predicate.getPredicate(PathBuilder);
        }).filter(Objects::nonNull).toList();

        BooleanExpression result = Expressions.asBoolean(true).isTrue();
        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }
        return result;
    }
}
