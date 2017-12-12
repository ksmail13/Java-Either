package util;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EitherTest {

    static class TestClass {
        public Either getTen() {
            return Either.right(10);
        }
    }

    @Mock
    TestClass test;

    @Test
    public void test_right_flatMap() {
        //given
        willReturn(Either.right(10))
                .given(test).getTen();

        //when
        Either ten = test.getTen();

        //then
        ten
                .<Integer, Integer>map(i -> i - 5).right()
                .<Integer>ifPresent(i -> assertThat(i, is(5)));
    }

    @Test
    public void test_left_flatMap() {
        //given
        willReturn(Either.left(new Exception("exception")))
                .given(test).getTen();

        //when
        Either ten = test.getTen();
        //then
        ten
                .<Integer>flatMap(i -> Either.right(i - 5)).right()
                .<Integer>ifPresent(i -> assertThat(i, is(5)));
    }
}