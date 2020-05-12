import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestArrayDequeGold {
    @Test
    public void randomTest() {
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();

        ArrayList<String> operations = new ArrayList<>();
        operations.add("\n");

        int i = 0;
        while (true) {
            if (sad.isEmpty() || ads.isEmpty()) {
                operations.add("addFirst(" + i + ")");
                sad.addFirst(i);
                ads.addFirst(i);
            } else {
                int val = StdRandom.uniform(4);
                if (val == 0) {
                    operations.add("addFirst(" + i + ")");
                    sad.addFirst(i);
                    ads.addFirst(i);
                } else if (val == 1) {
                    operations.add("addLast(" + i + ")");
                    sad.addLast(i);
                    ads.addLast(i);
                } else if (val == 2) {
                    Integer expected = ads.removeFirst();
                    Integer actual = sad.removeFirst();
                    operations.add("removeFirst(): " + expected);
                    String errorMessage = String.join("\n", operations);
                    assertEquals(errorMessage, expected, actual);
                } else {
                    Integer expected = ads.removeLast();
                    Integer actual = sad.removeLast();
                    operations.add("removeLast(): " + expected);
                    String errorMessage = String.join("\n", operations);
                    assertEquals(errorMessage, expected, actual);
                }
            }
            i += 1;
        }
    }
}
