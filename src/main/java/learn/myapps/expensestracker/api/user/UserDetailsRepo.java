package learn.myapps.expensestracker.api.user;

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
public interface UserDetailsRepo extends CrudRepository<UserDetails, Long>, PagingAndSortingRepository<UserDetails, Long>, QuerydslPredicateExecutor<UserDetails>, QuerydslBinderCustomizer<QUserDetails> {
    @Override
    default void customize(QuerydslBindings bindings, QUserDetails root) {
        bindings.bind(String.class).first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
