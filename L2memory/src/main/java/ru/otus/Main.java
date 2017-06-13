package ru.otus;


import ru.otus.agent.AgentMemoryCounter;
import java.util.function.Supplier;

public class Main {
    //jvm options -XX:-UseTLAB -Xmx512m -javaagent:Agent.jar
    public static int times = 10;
    public static int elements = 1000000;
    public static void main(String[] args) {
        //runTest(emptyStringSupplier);
        //runTest(fullStringSupplier);
        //runTest(integerSupplier);
        runTest(emptyIntArraySupplier);
        //runTest(emptyIntArraySupplier);
        //runTest(emptyObjectSupplier);
        //runTest(personSupplier);
    }

    static void runTest(Supplier supplier){
        String className = supplier.get().getClass().getCanonicalName();
        long resultSize = 0;
        for (int i = 0; i < times; i++) {
            System.gc();
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Runtime runtime = Runtime.getRuntime();
            long startMem = runtime.totalMemory() -runtime.freeMemory();
            Object[] result = new Object[elements];
            for (int j = 0; j < elements; j++) {
                result[j] = supplier.get();
            }
            long stopMem = runtime.totalMemory() - runtime.freeMemory();
            resultSize += (stopMem - startMem)/elements;
        }
        System.out.println("Test for "+className);
        System.out.println("Objects created: "+elements);
        System.out.println("Memory per object: "+resultSize/times);
        System.out.println(String.format("From instruments %s, size=%s", className, AgentMemoryCounter.getSize(supplier.get())));

    }



    static Supplier<String> emptyStringSupplier = String::new;
    static Supplier<String> fullStringSupplier = ()->"qwertt";
    static Supplier<Object> emptyObjectSupplier = Object::new;
    static Supplier<Person> personSupplier = Person::new;
    static Supplier<int[]> emptyIntArraySupplier = () -> new int[0];
    static Supplier<Integer> integerSupplier = ()-> 5;

    public static class Person{
        String name;
        int age;

        Person(){
            this.name = "john";
            this.age = 12;
        }
    }
}
