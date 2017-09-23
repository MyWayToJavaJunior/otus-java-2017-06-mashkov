package ru.otus.threads;

/**
 * @autor slonikmak on 24.09.2017.
 */
public class DeadLock {

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        new Thread(()-> a.doSome(b)).start();
        new Thread(()-> b.doSome(a)).start();
    }
}

class A{
    void doSome(B b){
        synchronized (b){
            b.doSome(this);
        }
    }
}

class B{
    void doSome(A a){
        synchronized (a){
            a.doSome(this);
        }
    }
}

