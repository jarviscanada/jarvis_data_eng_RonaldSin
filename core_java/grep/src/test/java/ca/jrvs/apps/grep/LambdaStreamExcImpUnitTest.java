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
    public void testToUpperCase() {
        List<String> list = lambda.toUpperCase("test", "test2", "test3").collect(Collectors.toList());
        List<String> listE = Arrays.asList("TEST", "TEST2", "TEST3");
        assert(list.equals(listE));
    }

    @Test
    public void testFilter() {
        List<String> list = lambda.filter(lambda.createStrStream("test", "test2", "test3"), "test").collect(Collectors.toList());
        List<String> listE = Arrays.asList("test2", "test3");
        assert(list.equals(listE));
    }

    @Test
    public void testCreateIntStream() {
        List<Integer> list = lambda.createIntStream([1, 2, 3]).collect(Collectors.toList());
        List<Integer> listE = Arrays.asList(1,2,3);
        assert(list.equals(listE));
    }

    @Test
    public void testToList() {
        List<Integer> list = lambda.toList(Stream<int> stream = Stream.of(1, 2, 3, 4, 5));
        List<Integer> listE = Arrays.asList(1,2,3,4,5);
        assert(list.equals(listE));
    }

    @Test
    public void testSquareRootIntStream() {
        List<Integer> list = lambda.squareRootIntStream(Stream<int> stream = Stream.of(4, 9 , 16)).collect(Collectors.toList());
        List<Integer> listE = Arrays.asList(Math.sqrt(4), Math.sqrt(9), Math.sqrt(16));
        assert(list.equals(listE));
    }

    @Test
    public void testGetOdd() {
        List<Integer> list = lambda.getOdd(Stream<int> stream = Stream.of(4, 9 , 16)).collect(Collectors.toList());
        List<Integer> listE = Arrays.asList(9);
        assert(list.equals(listE));
    }

    @Test
    public void TestGetLambdaPrinter() {
        String text = tapSystemOut(() -> {
            lambda.createStrStream("test").getLambdaPrinter("a>", "<b");
        });

        assert(text, "a>test<b");
    }

    @Test
    public void TestPrintMessages() {
        List<String> list = ["test", "test2"]
        String text = tapSystemOut(() -> {
            lambda.printMessages(list, lambda.getLambdaPrinter);
        });

        assert(text, "a>test<b\na>test2<b");

    }

    @Test
    public void TestPrintOdd() {
        List<Integer> list = [3, 4]
        String text = tapSystemOut(() -> {
            lambda.printOdd(list, lambda.getLambdaPrinter);
        });

        assert(text, "a>3<b");
    }

    @Test
    public void TestFlatNestedInt() {
        List<List<Integer>> listListInt = Arrays.asList(
            Arrays.asList(1, 2, 3),
            Arrays.asList(4, 5),
            Arrays.asList(6, 7, 8)
        );

        List<int> list = lambda.getOdd(lambda.flatNestedInt(listListInt.stream())).collect(Collectors.toList());
        List<int> listE = Arrays.asList(1,2,3,4,5,6,7,8);
        assert(list.equals(listE));
    }

}
