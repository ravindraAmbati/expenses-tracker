package learn.myapps.expensestracker.api;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import learn.myapps.expensestracker.api.search.SearchCriteria;
import learn.myapps.expensestracker.api.search.SearchCriteriaBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface ApiService<T> {

    Long create(T t);

    T update(T t) throws ResourceNotFoundException;

    boolean isDeleted(Long id);

    T findById(Long id) throws ResourceNotFoundException;

    Page<T> findAll(int page, int size, Sort sort);

    Page<T> search(String search, int page, int size, Sort sort);

    default void validateSearchCriteria(List<SearchCriteria> searchCriteriaList, Field[] fields) {
        validateSearchKeys(searchCriteriaList, fields);
        validateSearchOperators(searchCriteriaList);
    }

    default void validateSearchKeys(List<SearchCriteria> searchCriteriaList, Field[] fields) {
        List<String> keys = new ArrayList<>();
        if (!searchCriteriaList.isEmpty()) {
            searchCriteriaList.forEach(searchCriteria -> keys.add(searchCriteria.getKey()));
        }
        List<String> fieldNames = Arrays.stream(fields).map(Field::getName).toList();
        keys.forEach(key -> {
                    if (!fieldNames.contains(key)) {
                        throw new IllegalArgumentException(MessageFormat.format("Invalid Search Param: {0}", key));
                    }
                }
        );
    }

    default void validateSearchOperators(List<SearchCriteria> searchCriteriaList) {
        List<String> searchOperators = new ArrayList<>();
        if (!searchCriteriaList.isEmpty()) {
            searchCriteriaList.forEach(searchCriteria -> searchOperators.add(searchCriteria.getOperation()));
        }
        List<String> operators = SearchCriteriaBuilder.getOperators();
        searchOperators.forEach(operator -> {
                    if (!operators.contains(operator)) {
                        throw new IllegalArgumentException(MessageFormat.format("Invalid Search operator: {0}", operator));
                    }
                }
        );
    }

    default void validatePageResults(int pageNumber, int pageSize, Sort sort, Page<T> all) {
        Assert.isTrue(pageNumber == all.getNumber(), MessageFormat.format("Failed to get requested entities from page number: {0}", pageNumber));
        Assert.isTrue(pageSize == all.getSize(), MessageFormat.format("Failed to get requested entities of page size: {0}", pageSize));
        Assert.isTrue(sort.equals(all.getSort()), MessageFormat.format("Failed to get requested entities by sort: {0}", sort));
    }

}
