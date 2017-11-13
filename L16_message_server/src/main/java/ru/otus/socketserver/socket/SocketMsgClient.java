package ru.otus.socketserver.socket;


import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.ApplicationContext;
import ru.otus.socketserver.messages.Msg;
import ru.otus.testFramework.ReflectionHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Created by tully.
 */
public class SocketMsgClient implements MsgClient, Addressee {
    private static final Logger logger = Logger.getLogger(SocketMsgClient.class.getName());
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<Msg> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<Msg> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;
    private final List<Runnable> shutdownRegistrations;

    private ApplicationContext applicationContext;

    private Address address;

    public SocketMsgClient(Socket socket) {
        this.socket = socket;
        this.shutdownRegistrations = new CopyOnWriteArrayList<>();
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    @Override
    public void send(Msg msg) {
        output.add(msg);
    }

    @Override
    public Msg pool() {
        return input.poll();
    }

    @Override
    public Msg take() throws InterruptedException {
        return input.take();
    }

    @Override
    public void close() {
        shutdownRegistrations.forEach(Runnable::run);
        shutdownRegistrations.clear();

        executor.shutdown();
    }

    @Override
    public void register() {

    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    public void addShutdownRegistration(Runnable runnable) {
        this.shutdownRegistrations.add(runnable);
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                //System.out.println("Message received: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json, applicationContext);

                    input.add(msg);
                    stringBuilder = new StringBuilder();
                }
            }
        } catch (IOException | ParseException | ClassNotFoundException e) {
            logger.log(Level.SEVERE, e.getMessage());
        } finally {
            close();
        }
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                Msg msg = output.take(); //blocks
                String json = new Gson().toJson(msg);
                out.println(json);
                out.println(); //end of message
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    public static Msg getMsgFromJSON(String json, ApplicationContext context) throws ParseException, ClassNotFoundException {
        /*JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Class<?> msgClass = Class.forName(className);


        return (Msg) new Gson().fromJson(json, msgClass);*/
        Class clazz;

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
        String className = (String) jsonObject.get(Msg.CLASS_NAME_VARIABLE);
        Msg msg = null;

        try {
            clazz = Class.forName(className);
            Msg a = (Msg)context.getBean(clazz);
            //System.out.println(Arrays.toString(ReflectionHelper.getFields(clazz)));
            Stream.of(ReflectionHelper.getFields(clazz)).forEach(f->{
                System.out.println(((Field)f).getName());
                //JSONParser jsonParser = new JSONParser();
                //JSONObject jsonObject = null;
                /*try {
                    jsonParser.parse(json);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                Object value = jsonObject.get(((Field)f).getName());
                //((Msg)a).setName(value);
                ReflectionHelper.setFieldValue(a, (Field) f, value);
                //ReflectionHelper.setFieldValue(a,((Field)f).getName(), value);

            });
            msg = (Msg) a;
            //((A)a).exec();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public Address getAddress() {
        return address;
    }
    @Override
    public void setAddress(Address address){
        this.address = address;
    }

}
