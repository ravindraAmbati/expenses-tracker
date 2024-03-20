package learn.myapps.expensestracker.api;

import learn.myapps.expensestracker.Exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface ApiService<T> {

    Long create(T t);

    T update(T t) throws ResourceNotFoundException;

    boolean isDeleted(Long id);

    T findById(Long id) throws ResourceNotFoundException;

    Page<T> findAll(int page, int size, Sort sort);

    Page<T> search(String search, int page, int size, Sort sort);

}
