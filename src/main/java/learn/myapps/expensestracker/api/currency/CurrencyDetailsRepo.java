package learn.myapps.expensestracker.api.currency;

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
public interface CurrencyDetailsRepo extends CrudRepository<CurrencyDetails, Long>, PagingAndSortingRepository<CurrencyDetails, Long>, QuerydslPredicateExecutor<CurrencyDetails>, QuerydslBinderCustomizer<QCurrencyDetails> {
    @Override
    default void customize(QuerydslBindings bindings, QCurrencyDetails root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
