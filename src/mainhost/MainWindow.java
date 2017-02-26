package mainhost;

import Configuration.DatabaseExportFrame;
import Configuration.DatabaseImportFrame;
import Configuration.ProfileManagementFrame;
import DashboardInterface.CableOutSpeedDial;
import DashboardInterface.FlightDashboard;
import DashboardInterface.StateMachineDiagram;
import DataObjects.CurrentDataObjectSet;
import DataObjects.Operator;
import ParameterSelection.CurrentScenario;
import ParameterSelection.DEBUGWinchEditPanel;
import ParameterSelection.EnvironmentalWindow;
import ParameterSelection.ParameterSelectionPanel;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;

public class MainWindow {
    private String version = "2.0.1";
    private static Stage theStage;

    private String                  currentProfile;
    private ParameterSelectionPanel ParameterSelectionPanel_;
    private ProfileManagementFrame  ProfileManagementFrame;
    private FlightDashboard         FlightDashboard_;
    private DatabaseExportFrame     DatabaseExportFrame;
    private DatabaseImportFrame     DatabaseImportFrame;
    private EnvironmentalWindow     EnvironmentalWindow_;
    private CurrentDataObjectSet    currentData;

    private DEBUGWinchEditPanel winchPanel;

    public MainWindow(Stage primaryStage)
    {
        theStage = primaryStage;
        currentData = CurrentDataObjectSet.getCurrentDataObjectSet();

        currentProfile = "NO PROFILE";
        winchPanel = new DEBUGWinchEditPanel(ParameterSelectionPanel_);
        ParameterSelectionPanel_ = new ParameterSelectionPanel();
        FlightDashboard_ = new FlightDashboard();
    }

    private void initializeDefaultProfile()
    {
        Operator defaultProfile = new Operator(0, "Default", "{}");
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
        
        defaultProfile.setUnitSetting("avgWindSpeed", 1);
        defaultProfile.setUnitSetting("crosswind", 1);
        defaultProfile.setUnitSetting("gustWindSpeed", 1);
        defaultProfile.setUnitSetting("headwind", 1);
        defaultProfile.setUnitSetting("launchWeight", 1);
        defaultProfile.setUnitSetting("densityAltitude", 1);
        defaultProfile.setUnitSetting("runLength", 1);
        defaultProfile.setUnitSetting("pressure", 4);
        defaultProfile.setUnitSetting("temperature", 1);
        defaultProfile.setUnitSetting("runDirection", 1);
        defaultProfile.setUnitSetting("windDirection", 1);
                
        currentData.setCurrentProfile(defaultProfile);
    }

    //TODO (jtroxel): remove this guy...necessary?
    public static void run() {

    }

    //==============================================================================================================================================================================================

    @FXML SubScene currentScenario;
    @FXML SubScene environmentalWindowScene;
    @FXML SubScene profileManagementFrame;

    @FXML LineChart lineChart;
    @FXML SwingNode cableOutSpeedSwingNode;
    @FXML SwingNode stateMachineSwingNode;

    @FXML Pane      mainPane;
    @FXML TextField wp1TextField;

    public static final int BASE_WIDTH = 1100, BASE_HEIGHT = 825, WIDTH_OFFSET = 16, HEIGHT_OFFSET = 39;
    public static final double  WIDTH_TO_HEIGHT_RATIO = .75;
    public static final double  HEIGHT_TO_WIDTH_RATIO = 1.25;
    private             boolean isScaling             = false;

    @FXML
    protected void initialize() throws IOException
    {
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

        wp1TextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue && oldValue)
            {
                try
                {
                    wp1TextField.setText("" + DecimalFormat.getInstance().parse(wp1TextField.getText()));
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ParameterSelection/CurrentScenario.fxml"));
        loader.setController(new CurrentScenario());
        Parent root = loader.load();
        currentScenario.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/ParameterSelection/EnvironmentalWindowScene.fxml"));
        EnvironmentalWindow_ = new EnvironmentalWindow();
        loader.setController(EnvironmentalWindow_);
        root = loader.load();
        environmentalWindowScene.setRoot(root);

        loader = new FXMLLoader(getClass().getResource("/Configuration/ProfileManagementFrame.fxml"));
        loader.setController(new ProfileManagementFrame());
        root = loader.load();
        profileManagementFrame.setRoot(root);

        mainPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (theStage.getWidth() > 0)
            {
                double scale = (theStage.getWidth() - WIDTH_OFFSET) / BASE_WIDTH;
                mainPane.setScaleX(scale);
                mainPane.setTranslateX(-(mainPane.getWidth() - scale * mainPane.getWidth()) / 2);
            }
        });
        mainPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (theStage.getHeight() > 0)
            {
                double scale = (theStage.getHeight() - HEIGHT_OFFSET) / BASE_HEIGHT;
                mainPane.setScaleY(scale);
                mainPane.setTranslateY(-(mainPane.getHeight() - scale * mainPane.getHeight()) / 2);
            }
        });
    }
}
