package ru.otus.socketserver.messages;


public abstract class MsgToServer extends Msg {

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String className;


    protected MsgToServer(Class<?> klass) {
        this.className = klass.getName();
    }

    public String getClassName() {
        return className;
    }

}
