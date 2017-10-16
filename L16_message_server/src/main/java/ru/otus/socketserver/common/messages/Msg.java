package ru.otus.socketserver.common.messages;

import java.util.Optional;

public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String className;


    protected Msg(Class<?> klass) {
        this.className = klass.getName();
    }



    public String getClassName(){
        return className;
    }

}
