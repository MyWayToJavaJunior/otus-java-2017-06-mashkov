package main;


import ru.otus.dbhelper.DbHelper;
import ru.otus.executor.Executor;

public class Main {
    public static void main(String[] args) {
        DbHelper.example();

        Executor executor = new Executor(DbHelper.getConnection());

        String query = "create table test "+
                " (name  VARCHAR(10),"+
                " age INTEGER)";

        System.out.println(executor.execUpdate(query));
    }
}
