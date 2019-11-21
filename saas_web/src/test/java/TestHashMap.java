
import org.junit.Test;

public class TestHashMap {

    @Test
    public void test() {

        IntHashMap<String> map = new IntHashMap<>();
        map.put(1, "你好");

        map.put(21, "你好");
        map.put(112, "你好");
        map.put(13, "你好");

        map.put(12, "你好");


        for (String value : map.values()) {
            System.out.println(value);

        }
    }

}
