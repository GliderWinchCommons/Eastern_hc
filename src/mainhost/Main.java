/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainhost;

import Communications.MessagePipeline;
import Configuration.OperatorLoginPanel;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import DataObjects.Winch;
import static DatabaseUtilities.DatabaseVerification.verifyDatabase;
import com.sun.javafx.binding.StringFormatter;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.Timer;

public class Main extends Application {

    private AtomicInteger isScaling;
    private int widthCount;

    @Override
    public void start(Stage primaryStage) throws Exception {
        verifyDatabase();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mockup3.fxml"));
        MainWindow mainWindow = new MainWindow(primaryStage);
        loader.setController(mainWindow);
        Parent root = loader.load();
        Scene theScene = new Scene(root, MainWindow.BASE_WIDTH, MainWindow.BASE_HEIGHT);
        adjustFont(theScene);
        isScaling = new AtomicInteger(0);
        widthCount = 0;

        Timer time = new Timer(1000, (ActionEvent e) -> {
            Platform.runLater(() -> {
                Operator operator = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentProfile();
                Winch winch = CurrentDataObjectSet.getCurrentDataObjectSet().getCurrentWinch();
                if (operator != null) {
                    operator = OperatorLoginPanel.isLoggedIn() ? operator : null;
                }
                primaryStage.setTitle(StringFormatter.format("%85s%51s%55s", Calendar.getInstance().getTime(),
                        "Operator: " + (operator == null ? "Not Selected" : operator.getFirst() + " " + operator.getMiddle() + " " + operator.getLast()),
                        "Winch: " + (winch == null ? "Not Selected" : winch.getName())).getValue());
            });
        });
        time.start();

        MessagePipeline pipe = MessagePipeline.getInstance();
        pipe.connect("127.0.0.1", 32123);

        Thread pipeThread = new Thread(pipe);
        pipeThread.start();
        primaryStage.setScene(theScene);
        primaryStage.setOnCloseRequest((WindowEvent ew) -> {
            System.exit(0);
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void adjustFont(Scene scene) {
        //if the operating system is not windows
        if (!System.getProperty("os.name").startsWith("Windows")) {
            Pane rootPane = (Pane) scene.getRoot();
            adjustFontHelper(rootPane);

        }
    }

    private static void ajustFontForTabPane(TabPane tp) {
        Node temp;
        ObservableList<Tab> tabs = tp.getTabs();
        for (int i = 0; i < tabs.size(); i++) {
            temp = tabs.get(i).getContent();
            if (temp instanceof Pane) {
                adjustFontHelper((Pane) temp);
            } else if (temp instanceof SubScene) {
                adjustFontForSubScene((SubScene) temp);
            }
        }
    }

    private static void adjustFontForSubScene(SubScene scene) {
        Pane p = (Pane) scene.getRoot();
        adjustFontHelper(p);
    }

    private static void adjustFontHelper(Pane p) {
        ObservableList<Node> children = p.getChildren();
        adjustFontListIterator(children);
    }

    private static void adjustFontHelper(Group g) {
        ObservableList<Node> children = g.getChildren();
        adjustFontListIterator(children);
    }

    private static void adjustFontListIterator(ObservableList<Node> children) {
        Node currentChild;
        for (int i = 0; i < children.size(); i++) {
            if (i == 14) {
                System.out.println();
            }
            currentChild = children.get(i);
            if (currentChild instanceof Pane) {
                adjustFontHelper((Pane) currentChild);
            } else if (currentChild instanceof TextField) {
                TextField tf = (TextField) currentChild;
                tf.setStyle(applyFontProportions(tf.getStyle()));
            } else if (currentChild instanceof Label) {
                Label l = (Label) currentChild;
                if (l.getText().equalsIgnoreCase("Run Description")) {
                    System.err.println("");
                }
                l.setStyle(applyFontProportions(l.getStyle()));
            } else if (currentChild instanceof Button) {
                Button b = (Button) currentChild;
                b.setStyle(applyFontProportions(b.getStyle()));
            } else if (currentChild instanceof RadioButton) {
                RadioButton rb = (RadioButton) currentChild;
                rb.setStyle(applyFontProportions(rb.getStyle()));
            } else if (currentChild instanceof TabPane) {
                ajustFontForTabPane((TabPane) currentChild);
            } else if (currentChild instanceof SubScene) {
                adjustFontForSubScene((SubScene) currentChild);
            } else if (currentChild instanceof Group) {
                adjustFontHelper((Group) currentChild);
            }
        }
    }

    private static String applyFontProportions(String style) {
        String[] split = style.split(";");
        //if the font is bold
        if (style.contains("Bold")) {
            return "-fx-font-size: " + getSize(split) / 1.2975 + "em; -fx-font-weight: Bold;";
        }
        return "-fx-font-size: " + getSize(split) / 1.23 + "em;";
    }

    private static double getSize(String[] split) {
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains("size")) {
                int colonIndex = split[i].indexOf(":");
                int emIndex = split[i].indexOf("em");
                return Double.parseDouble(split[i].substring(colonIndex + 1, emIndex));
            }
        }
        return 1;
    }
}
