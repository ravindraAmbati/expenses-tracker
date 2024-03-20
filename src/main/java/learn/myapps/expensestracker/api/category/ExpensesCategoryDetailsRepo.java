package learn.myapps.expensestracker.api.category;

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
public interface ExpensesCategoryDetailsRepo extends CrudRepository<ExpensesCategoryDetails, Long>, PagingAndSortingRepository<ExpensesCategoryDetails, Long>, QuerydslPredicateExecutor<ExpensesCategoryDetails>, QuerydslBinderCustomizer<QExpensesCategoryDetails> {
    @Override
    default void customize(QuerydslBindings bindings, QExpensesCategoryDetails root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
