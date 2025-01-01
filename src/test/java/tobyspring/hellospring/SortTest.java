package tobyspring.hellospring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class SortTest {
    Sort sort;
    @BeforeEach
    void beforeEach() { // 각 테스트를 실행할 때 필요로하는 준비를 실행함.
        sort = new Sort();
        System.out.println(this); // 각 테스트별로 테스트별 독립적인 실행을 위해 새로운 인스턴스를 생성함.
    }

    @Test
    void sort() {
        // 준비(given)
        sort = new Sort();

        // 실행(when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "b"));

        // 검증(then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa"));

    }

    @Test
    void sort3Items(){
        // 준비(given)
        sort = new Sort();

        // 실행(when)
        List<String> list = sort.sortByLength(Arrays.asList("aa", "ccc", "b"));

        // 검증(then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));

    }

    @Test
    void sortAlreadySorted(){
        // 준비(given)
        sort = new Sort();

        // 실행(when)
        List<String> list = sort.sortByLength(Arrays.asList("b", "aa", "ccc"));

        // 검증(then)
        Assertions.assertThat(list).isEqualTo(List.of("b", "aa", "ccc"));

    }
}
