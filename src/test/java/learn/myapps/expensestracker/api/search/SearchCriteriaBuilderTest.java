package learn.myapps.expensestracker.api.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class SearchCriteriaBuilderTest {

    @Test
    void buildSearchCriteriaTest() {

        List<SearchCriteria> expected = new ArrayList<>();
        expected.add(new SearchCriteria("firstName", "==", "ravindra"));
        expected.add(new SearchCriteria("lastName", "!=", "Ambati"));

        List<SearchCriteria> actual = new ArrayList<>();
        actual = SearchCriteriaBuilder.buildSearchCriteria("firstName==ravindra,lastName!=Ambati");
        Assertions.assertEquals(expected, actual);

    }
}