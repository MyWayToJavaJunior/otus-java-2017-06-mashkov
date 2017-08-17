package ru.otus.DBService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.DAO.UserDataSetDao;
import ru.otus.interfaces.DBService;
import ru.otus.models.AddressDataSet;
import ru.otus.models.PhoneDataSet;
import ru.otus.models.UserDataSet;

import java.util.List;
import java.util.function.Function;

public class H2DBService implements DBService {

    private final SessionFactory sessionFactory;

    public H2DBService(){
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/myBase");
        configuration.setProperty("hibernate.connection.username", "anton");
        configuration.setProperty("hibernate.connection.password", "anton");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        configuration.setProperty("hibernate.jdbc.time_zone", "UTC");

        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    @Override
    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    @Override
    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            UserDataSetDao dao = new UserDataSetDao(session);
            dao.save(dataSet);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDao dao = new UserDataSetDao(session);
            return dao.read(id);
        });
    }

    @Override
    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDao dao = new UserDataSetDao(session);
            return dao.readByName(name);
        });
    }

    @Override
    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDao dao = new UserDataSetDao(session);
            return dao.readAll();
        });
    }

    @Override
    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }
}
