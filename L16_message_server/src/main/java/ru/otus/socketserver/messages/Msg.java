package ru.otus.socketserver.messages;

import ru.otus.socketserver.socket.Address;

public abstract class Msg {
    public static final String CLASS_NAME_VARIABLE = "className";

    private Address addressTo;
    private Address addressFrom;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String className;


    protected Msg(Class<?> klass) {
        this.className = klass.getName();
    }

    public String getClassName() {
        return className;
    }

    public Address getAddressTo() {
        return addressTo;
    }

    public void setAddressTo(Address addressTo) {
        this.addressTo = addressTo;
    }

    public Address getAddressFrom() {
        return addressFrom;
    }

    public void setAddressFrom(Address addressFrom) {
        this.addressFrom = addressFrom;
    }

    public abstract void exec();
}
