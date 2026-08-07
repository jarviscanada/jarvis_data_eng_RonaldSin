package ca.jrvs.apps.grep;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.util.stream.IntStream;


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
        assertEquals(list, listE);
    }

    @Test
    public void testToUpperCase() {
        List<String> list = lambda.toUpperCase("test", "test2", "test3").collect(Collectors.toList());
        List<String> listE = Arrays.asList("TEST", "TEST2", "TEST3");
        assertEquals(list, listE);
    }

    @Test
    public void testFilter() {
        List<String> list = lambda.filter(lambda.createStrStream("test", "test2", "test3"), "test").collect(Collectors.toList());
        List<String> listE = Arrays.asList("test2", "test3");
        assertEquals(list, listE);
    }

    @Test
    public void testCreateIntStream() {
        List<Integer> list = lambda.createIntStream(new int[]{1,2,3}).boxed().collect(Collectors.toList());
        List<Integer> listE = Arrays.asList(1,2,3);
        assertEquals(list, listE);
    }

    @Test
    public void testToList() {
        List<Integer> list = lambda.toList(IntStream.of(1, 2, 3, 4, 5));
        List<Integer> listE = Arrays.asList(1,2,3,4,5);
        assertEquals(list, listE);
    }

    @Test
    public void testSquareRootIntStream() {
        List<Double> list = lambda.squareRootIntStream(IntStream.of(4, 9 , 16)).boxed().collect(Collectors.toList());
        List<Double> listE = Arrays.asList(Math.sqrt(4), Math.sqrt(9), Math.sqrt(16));
        assertEquals(list, listE);
    }

    @Test
    public void testGetOdd() {
        List<Integer> list = lambda.getOdd(IntStream.of(4, 9 , 16)).boxed().collect(Collectors.toList());
        List<Integer> listE = Arrays.asList(9);
        assertEquals(list, listE);
    }

    @Test
    public void TestFlatNestedInt() {
        List<List<Integer>> listListInt = Arrays.asList(
            Arrays.asList(1, 2),
            Arrays.asList(3, 4)
        );

        List<Integer> list = lambda.flatNestedInt(listListInt.stream()).collect(Collectors.toList());
        List<Integer> listE = Arrays.asList(1,4,9,16);

        assertEquals(list, listE);
    }

    @Test
    public void TestCreateIntStream() {
        int[] arr = {1,2,3,4};

        List<Integer> list = lambda.createIntStream(3, 6).boxed().collect(Collectors.toList());
        List<Integer> listE = Arrays.asList(3, 4, 5);

        assertEquals(list, listE);
    }

}
