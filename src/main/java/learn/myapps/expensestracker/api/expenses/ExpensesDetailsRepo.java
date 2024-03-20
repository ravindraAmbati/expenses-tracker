package learn.myapps.expensestracker.api.expenses;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpensesDetailsRepo extends CrudRepository<ExpensesDetails, Long>, PagingAndSortingRepository<ExpensesDetails, Long>, QuerydslPredicateExecutor<ExpensesDetails>, QuerydslBinderCustomizer<QExpensesDetails> {
    @Override
    default void customize(QuerydslBindings bindings, QExpensesDetails root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
