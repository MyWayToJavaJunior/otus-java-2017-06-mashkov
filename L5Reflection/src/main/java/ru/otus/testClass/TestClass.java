package ru.otus.testClass;

public class TestClass {
    private String string;

    public TestClass(){
        string = "string from test class";
    }

    public String getString() {
        return string;
    }

    public void setString(String s){
        this.string = s;
    }
}
