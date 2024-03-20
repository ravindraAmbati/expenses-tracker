package learn.myapps.expensestracker.api.basic;

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
public interface BasicDetailsRepo extends CrudRepository<BasicDetails, Long>, PagingAndSortingRepository<BasicDetails, Long>, QuerydslPredicateExecutor<BasicDetails>, QuerydslBinderCustomizer<QBasicDetails> {
    @Override
    default void customize(QuerydslBindings bindings, QBasicDetails root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
