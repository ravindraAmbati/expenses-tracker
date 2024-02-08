package learn.myapps.expensestracker.api;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ApiController<T> {

    ResponseEntity<Long> create(T t);

    T update(T t);

    boolean isDeleted(Long id);

    T findById(Long id);

    Page<T> findAll(int pageNumber, int pageSize, String sortBy);

}
