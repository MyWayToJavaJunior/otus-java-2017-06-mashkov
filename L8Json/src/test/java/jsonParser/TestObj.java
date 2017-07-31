package jsonParser;

import java.util.HashMap;
import java.util.Map;

public class TestObj {
    int num;
    String string;
    Map<Integer, String> map;


    public TestObj(){
        string = "Hello!";
        num = 24;
        map = new HashMap<>();
        map.put(1,"1");
        map.put(2,"2");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestObj)) return false;

        TestObj testObj = (TestObj) o;

        if (num != testObj.num) return false;
        return string != null ? string.equals(testObj.string) : testObj.string == null;
    }

    @Override
    public int hashCode() {
        int result = string != null ? string.hashCode() : 0;
        result = 31 * result + num;
        return result;
    }
}
