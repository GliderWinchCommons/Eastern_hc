package mainhost;

import Configuration.DatabaseExportFrame;
import Configuration.DatabaseImportFrame;
import Configuration.NewOperatorPanel;
import Configuration.OperatorLoginPanel;
import Configuration.ProfileManagementFrame;
import DashboardInterface.CableOutSpeedDial;
import DashboardInterface.FlightDashboard;
import DashboardInterface.StateMachineDiagram;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import Logger.gui.Logger;
import ParameterSelection.CurrentScenario;
import ParameterSelection.DEBUGWinchEditPanel;
import ParameterSelection.EnvironmentalWindow;
import ParameterSelection.ParameterSelectionPanel;
import Winch.AddEditWinchPanel;
import Winch.WinchPanel;
import java.awt.*;
import java.io.IOException;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.*;

public class MainWindow {

    private String version = "2.0.1";
    private static Stage theStage;

    private String currentProfile;
    private ParameterSelectionPanel ParameterSelectionPanel_;
    private ProfileManagementFrame ProfileManagementFrame;
    private FlightDashboard FlightDashboard_;
    private DatabaseExportFrame DatabaseExportFrame;
    private DatabaseImportFrame DatabaseImportFrame;
    private EnvironmentalWindow EnvironmentalWindow_;
    private CurrentDataObjectSet currentData;

    private DEBUGWinchEditPanel winchPanel;

    public MainWindow(Stage primaryStage) {
        theStage = primaryStage;
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();

        currentProfile = "NO PROFILE";
        winchPanel = new DEBUGWinchEditPanel(ParameterSelectionPanel_);
        ParameterSelectionPanel_ = new ParameterSelectionPanel();
        FlightDashboard_ = new FlightDashboard();
    }

    private void initializeDefaultProfile() {
        Operator defaultProfile = new Operator(0, "First", "Middle", "Last", true, "", "{}");
        defaultProfile.setUnitSetting("flightWeight", 1);

        defaultProfile.setUnitSetting("emptyWeight", 1);
        defaultProfile.setUnitSetting("maxGrossWeight", 1);
        defaultProfile.setUnitSetting("stallSpeed", 1);
        defaultProfile.setUnitSetting("ballastWeight", 1);
        defaultProfile.setUnitSetting("baggageWeight", 1);
        defaultProfile.setUnitSetting("passengerWeight", 1);
        defaultProfile.setUnitSetting("maxTension", 1);
        defaultProfile.setUnitSetting("weakLinkStrength", 1);
        defaultProfile.setUnitSetting("winchingSpeed", 1);

        defaultProfile.setUnitSetting("airfieldAltitude", 1);
        defaultProfile.setUnitSetting("gliderPosAltitude", 1);
        defaultProfile.setUnitSetting("runwayMagneticHeading", 1);
        defaultProfile.setUnitSetting("winchPosAltitude", 1);

        defaultProfile.setUnitSetting("cableLength", 1);
        defaultProfile.setUnitSetting("coreDiameter", 6);

        defaultProfile.setUnitSetting("avgWindSpeed", 3);
        defaultProfile.setUnitSetting("crosswind", 3);
        defaultProfile.setUnitSetting("gustWindSpeed", 3);
        defaultProfile.setUnitSetting("headwind", 3);
        defaultProfile.setUnitSetting("launchWeight", 1);
        defaultProfile.setUnitSetting("densityAltitude", 1);
        defaultProfile.setUnitSetting("runLength", 1);
        defaultProfile.setUnitSetting("pressure", 6);
        defaultProfile.setUnitSetting("temperature", 1);
        defaultProfile.setUnitSetting("runDirection", 1);
        defaultProfile.setUnitSetting("windDirection", 1);

        currentData.setCurrentProfile(defaultProfile);
    }

    //==============================================================================================================================================================================================
    @FXML
    TabPane tabPane;
    @FXML
    SubScene loginSubScene;
    @FXML
    SubScene newOperatorScene;
    @FXML
    SubScene currentScenario;
    @FXML
    SubScene environmentalWindowScene;
    @FXML
    SubScene loggerScene;
    @FXML
    SubScene profileManagementFrame;
    @FXML
    SubScene winchSubScene;
    @FXML
    SubScene addEditWinchSubScene;

    @FXML
    LineChart lineChart;
    @FXML
    SwingNode cableOutSpeedSwingNode;
    @FXML
    SwingNode stateMachineSwingNode;

    @FXML
    Pane mainPane;

    @FXML
    private Tab currentScenarioTab;
    @FXML
    private Tab recentLaunchesTab;
    @FXML
    private Tab flightDashboardTab;
    @FXML
    private Tab editWinchTab;
    @FXML
    private Tab loggerTab;
    @FXML
    private Tab operatorTab;
    @FXML
    private Tab replaysTab;

    public static final int BASE_WIDTH = 1100, BASE_HEIGHT = 825, WIDTH_OFFSET = 16, HEIGHT_OFFSET = 39;
    public static final double WIDTH_TO_HEIGHT_RATIO = .75;
    public static final double HEIGHT_TO_WIDTH_RATIO = 1.25;
    private boolean isScaling = false;

    @FXML
    protected void initialize() throws IOException {
        initializeDefaultProfile();

        JPanel stateMachine = new StateMachineDiagram();

        SwingUtilities.invokeLater(() -> {
            javafx.scene.paint.Color fxBackgroundCol = javafx.scene.paint.Color.WHITESMOKE;
            Color awtBackground = new Color((float) fxBackgroundCol.getRed(), (float) fxBackgroundCol.getGreen(), (float) fxBackgroundCol.getBlue(), (float) fxBackgroundCol.getOpacity());
            JPanel cableOut = new CableOutSpeedDial(awtBackground);
            cableOutSpeedSwingNode.setContent(cableOut);
            stateMachineSwingNode.setContent(stateMachine);
            stateMachineSwingNode.getContent().setBackground(awtBackground);
        });

        NewOperatorPanel newOperatorPanel = new NewOperatorPanel(newOperatorScene);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Configuration/NewOperatorPanel.fxml"));
        loader.setController(newOperatorPanel);
        Parent root = loader.load();
        newOperatorScene.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileManagementFrame.fxml"));
        ProfileManagementFrame managementFrame = new ProfileManagementFrame(loginSubScene);
        currentData.attach(managementFrame);
        //operatorLoginPanel.attach(managementFrame);
        loader.setController(managementFrame);
        root = loader.load();
        profileManagementFrame.setRoot(root);

        OperatorLoginPanel operatorLoginPanel = new OperatorLoginPanel(this, loginSubScene, newOperatorPanel, profileManagementFrame);
        newOperatorPanel.attach(operatorLoginPanel);
        loader = new FXMLLoader(getClass().getResource("/Configuration/OperatorLoginPanel.fxml"));
        loader.setController(operatorLoginPanel);
        root = loader.load();
        loginSubScene.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/CurrentScenario.fxml"));
        CurrentScenario currScenario = new CurrentScenario();
        currentData.attach(currScenario);
        loader.setController(currScenario);
        root = loader.load();
        currentScenario.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/Winch/AddEditWinchPanel.fxml"));
        AddEditWinchPanel addEditWinch = new AddEditWinchPanel(winchSubScene);
        loader.setController(addEditWinch);
        root = loader.load();
        addEditWinchSubScene.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/Winch/WinchPanel.fxml"));
        WinchPanel wp = new WinchPanel(addEditWinchSubScene, addEditWinch);
        loader.setController(wp);
        root = loader.load();
        winchSubScene.setRoot(root);

        currentData.attach(addEditWinch);
        currentData.attach(wp);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/EnvironmentalWindowScene.fxml"));
        EnvironmentalWindow_ = new EnvironmentalWindow();
        loader.setController(EnvironmentalWindow_);
        currentData.attach(EnvironmentalWindow_);
        root = loader.load();
        environmentalWindowScene.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/Logger/gui/loggerui.fxml"));
        loader.setController(new Logger());
        root = loader.load();
        loggerScene.setRoot(root);

        mainPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (theStage.getWidth() > 0) {
                double scale = (theStage.getWidth() - WIDTH_OFFSET) / BASE_WIDTH;
                mainPane.setScaleX(scale);
                mainPane.setTranslateX(-(mainPane.getWidth() - scale * mainPane.getWidth()) / 2);
            }
        });
        mainPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (theStage.getHeight() > 0) {
                double scale = (theStage.getHeight() - HEIGHT_OFFSET) / BASE_HEIGHT;
                mainPane.setScaleY(scale);
                mainPane.setTranslateY(-(mainPane.getHeight() - scale * mainPane.getHeight()) / 2);
            }
        });
        tabPane.getSelectionModel().select(operatorTab);
        enableTabs(false);
    }

    public void enableTabs(boolean value) {
        currentScenarioTab.disableProperty().set(!value);
        recentLaunchesTab.disableProperty().set(!value);
        flightDashboardTab.disableProperty().set(!value);
        editWinchTab.disableProperty().set(!value);
        loggerTab.disableProperty().set(!value);
        replaysTab.disableProperty().set(!value);
    }
}
