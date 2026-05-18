package ca.jrvs.apps.grep;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LambdaStreamExcImpUnitTest {

    LambdaStreamExcImp lambda;

    @Before
    public void init() {
        lambda = new LambdaStreamExcImp();
    }

    @Test
    public void testCreateStrStream() {
        List<String> list = lambda.createStrStream("test", "test2", "test3").collect(Collectors.toList());
        List<String> listE = Arrays.asList("test", "test2", "test3");
        assert(list.equals(listE));
    }

    @Test
    public void toUpperCase() {
        List<String> list = lambda.toUpperCase("test", "test2", "test3").collect(Collectors.toList());
        List<String> listE = Arrays.asList("TEST", "TEST2", "TEST3");
        assert(list.equals(listE));
    }

    @Test
    public void filter() {
        List<String> list = lambda.filter(lambda.createStrStream("test", "test2", "test3"), "test").collect(Collectors.toList());
        List<String> listE = Arrays.asList("test2", "test3");
        assert(list.equals(listE));
    }

    @Test
    public void createIntStream() {

    }

    @Test
    public void toList() {
    }

    @Test
    public void testToList() {
    }

    @Test
    public void testCreateIntStream() {
    }

    @Test
    public void squareRootIntStream() {
    }

    @Test
    public void getOdd() {
    }

    @Test
    public void getLambdaPrinter() {
    }

    @Test
    public void printMessages() {
    }

    @Test
    public void printOdd() {
    }

    @Test
    public void flatNestedInt() {
    }

}
