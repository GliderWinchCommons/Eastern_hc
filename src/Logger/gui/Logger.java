package Logger.gui;

import Logger.Logger.LogThread;
import Logger.Logger.MessageListenerThread;
import Logger.Logger.SystemsList;
import Logger.Logger.TimeThread;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 * This class is the Controller class for the Logger window.
 */

public class Logger {

    @FXML
    private Button sub_conn_btn;
    @FXML
    private Button embed_conn_btn;
    @FXML
    private TextField embedded_address;
    @FXML
    private TextField sub_address;
    @FXML
    private Button start;
    @FXML
    private Button stop;
    @FXML
    private Button reset;
    @FXML
    private ListView<String> systems_list;

    private PrintWriter fout;
    private TimeThread tt;
    private Queue<String> messages_to_log = new ConcurrentLinkedQueue<>();
    private ObservableList<String> list_of_systems;
    private LogThread logThread;
    private List<MessageListenerThread> system_listener_threads;

    @FXML
    public void initialize() {
        try {
            start_logging();
        } catch (Exception e) {

        };
    }

    /**
     * This is the primary function of the Logger. This creates the threads needed to listen to the systems
     * that are listed in SystemsList.java and connects them to their sockets and then starts the logging process.
     */
    @FXML
    private void connect_to_systems() {
        Scanner system_listener;
        SystemsList sys = new SystemsList();
        //List of systems displays the systems that are connected in the GUI.
        list_of_systems = FXCollections.observableArrayList();
        system_listener_threads = new ArrayList<>();
        systems_list.getItems().clear();
        //loops through and creates a thread for each system and then connects them.
        for (int i = 0; i < sys.systems.size(); i++) {
            system_listener = connect_to_socket("localhost", sys.systems.get(i).port);
            system_listener_threads.add(new MessageListenerThread(sys.systems.get(i).name,
                    Integer.toHexString(sys.systems.get(i).id).toUpperCase(),
                    system_listener, messages_to_log));
            list_of_systems.add(sys.systems.get(i).toString());
        }
        systems_list.setItems(list_of_systems);
        start_logging();
    }

    @FXML
    private void start_logging() {
        try {
            fout = new PrintWriter(LocalDate.now() + "_"
                    + convert_local_time(LocalTime.now().toString()) + ".wlg");
            logThread = new LogThread("Logger", messages_to_log, fout);
            logThread.start();
            for (MessageListenerThread message_thread : system_listener_threads) {
                message_thread.start();
            }
            stop.setDisable(false);
            reset.setDisable(false);
            start.setDisable(true);
        } catch (IOException exception) {
            //TODO add a popup window or alert that the logging failed to start.
            //TODO remove exit and add recovery.
            System.exit(-1);
        }

    }

    @FXML
    public void stop_logging() {
        for (MessageListenerThread system_thread : system_listener_threads) {
            system_thread.endLoop();
        }
        logThread.endLoop();
        stop.setDisable(true);
        reset.setDisable(true);
        start.setDisable(false);
    }

    @FXML
    void reset_logging() {
        try {
            stop_logging();
            //The time unit here forces the application to wait to give the threads time to wrap up before
            //connect to systems is called again. Without it connect to systems runs over the final
            //processes of the threads and it does not start correctly.
            TimeUnit.MILLISECONDS.sleep(100);
            connect_to_systems();
        } catch (InterruptedException ex) {
            //TODO Make better exception handle
            System.out.println("interrupted");
        }
    }

    private Scanner connect_to_socket(String address, int portNum) {
        Scanner reader;
        try {
            Socket input = new Socket(address, portNum);
            reader = new Scanner(new InputStreamReader(input.getInputStream()));
        } catch (Exception ex) {
            reader = null;
        }
        return reader;
    }

    private String convert_local_time(String local_time) {
        String converted_time = "";
        String time_to_seconds = local_time.split("\\.")[0];
        String[] split_time = time_to_seconds.split(":");
        converted_time += split_time[0];
        for (int i = 1; i < split_time.length; i++) {
            converted_time += "-";
            converted_time += split_time[i];
        }

        return converted_time;
    }

}
