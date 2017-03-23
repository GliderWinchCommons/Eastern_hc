package reader;

/**
 * This is the main workhorse of the Reader. It does the message processing and populating of the table view
 * of all of the processed messages. It also controls the export feature that is used to export to a
 * delimited file format that will allow for running simulations.
 */

import Factories.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;


import java.io.BufferedWriter;
import java.text.DecimalFormat;
import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Controller
{

    //TODO If the system is 0, then the leftmost bit identifies if it is from the host controller or master controller
    //TODO Status will be displayed as a bitmap(binary). If leading bit is set, then highlight line according to
    //TODO it's value. If leading bit is set(number is negative), line is red, if number > 0
    //TODO If status is 0, then no highlight(it's good).
    //TODO Highlight ID field if it's from the Host Controller

    @FXML
    private AnchorPane parent;
    @FXML
    private VBox table_container;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private MenuItem exportMenuItem;
    @FXML
    CheckBox all_checkbox;
    @FXML
    CheckBox tension_checkbox;
    @FXML
    CheckBox unknown_checkbox;
    @FXML
    Button applyFiltersBtn;
    @FXML
    ListView launches_list_view;

    private final ArrayList<CheckBox> filters = new ArrayList<>();
    private final String EXPORT_DELIMETER = " ";


    private TableView<Message> data_table = new TableView<>();
    //original_backup is the backup of the original list of messages to be used to restore
    //messages deleted by any applied filters in the message display window.
    private ObservableList<Message> original_backup = FXCollections.observableArrayList();
    private ObservableList<Message> processed_messages = FXCollections.observableArrayList();
    private ObservableList<String> launches = FXCollections.observableArrayList();


    private final int ID_OFFSET = 21;
    private final int ID_SCALE = 1 << ID_OFFSET;
    private final int TIME_MESSAGE_ID = 256 * ID_SCALE;              // 0x200
    private final int MOTOR_MESSAGE_ID = 292 * ID_SCALE;             // 0x248
    private final int TORQUE_COMMAND_MESSAGE_ID = 300 * ID_SCALE;    // 0x258
    private final int STATE_MESSAGE_ID = 304 * ID_SCALE;             // 0x260
    private final int PARAM_REQUEST_MESSAGE_ID = 312 * ID_SCALE;     // 0x270
    private final int LAUNCH_PARAM_MESSAGE_ID = 320 * ID_SCALE;      // 0x280
    private final int CP_CL_RMT_MESSAGE_ID = 328 * ID_SCALE;         // 0x290
    private final int CP_CL_LCL_MESSAGE_ID = 329 * ID_SCALE;         // 0x292
    private final int CP_INPUTS_RMT_MESSAGE_ID = 330 * ID_SCALE;     // 0x294
    private final int CP_INPUTS_LCL_MESSAGE_ID = 331 * ID_SCALE;     // 0x296
    private final int CP_OUTPUTS_MESSAGE_ID = 336 * ID_SCALE;        // 0x2A0
    private final int ORIENTATION_ID = 385 * ID_SCALE;               // 0x302
    private final int DRUM_MESSAGE_ID = 432 * ID_SCALE;              // 0x360
    private final int TENSION_MESSAGE_ID = 448 * ID_SCALE;           // 0x380
    private final int CABLE_ANGLE_MESSAGE_ID = 464 * ID_SCALE;       // 0x3a0
    private final int ZERO_ODOMETER_ID = 672 * ID_OFFSET;            // 0x540
    private final int ZERO_TENSIOMETER_ID = 681 * ID_OFFSET;         // 0x552
    private final int DENSITY_ALTITUDE_ID = 689 * ID_SCALE;          // 0x562
    private final int WIND_ID = 690 * ID_SCALE;                      // 0x564
    private final int BATTERY_SYSTEM_ID = 704 * ID_SCALE;            // 0x580


    /*
        This HashMap is used to determine what type of MessageDescriptor object is needed to process a given message.
        It is keyed by the ID's above, with the value being the MessageDescriptor that knows how to process the type
        of message with that ID.
     */
    private final HashMap<Integer, MessageDescriptor> messageDescriptors = new HashMap<Integer, MessageDescriptor>()
    {{
        put(TENSION_MESSAGE_ID, new CableTensionMessageDescriptor());
    }};

    @FXML
    public void initialize()
    {
        filters.add(all_checkbox);
        filters.add(tension_checkbox);
        filters.add(unknown_checkbox);
    }

    /**
     *  Opens a FileChooser dialog window that allows the user to browse for and select the
     *  log file they want to display.
     */
    @FXML
    void browse_for_file() throws IOException
    {
        FileChooser file_chooser = new FileChooser();
        File file = file_chooser.showOpenDialog(null);
        if(file != null)
        {
            prepare_table_data(file);
            applyFiltersBtn.setDisable(false);
        }

    }

    /**
     * Formats the data into a delimited format for export and use in simulations via software like Matlab.
     */
    @FXML
    void export_to_file()
    {
        FileChooser export_chooser = new FileChooser();
        export_chooser.setTitle("Export File:");
        export_chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Space Delimited File (*.ssv)", "*.ssv"));
        File file = export_chooser.showSaveDialog(null);
        if (file != null)
        {
            StringBuilder builder = new StringBuilder();
            for(Message message : processed_messages)
            {
                if(!message.getName().equals("Unknown"))
                {
                    //Placeholder below for the time message formatting. This is the way to format it. All
                    //that needs to be done is to make the check if there is a time message and
                    //add it in if there is. I don't have a reliable way to try to implement this
                    //without time messages to experiment with.
                    builder.append(new DecimalFormat("0000000000.000").format(0.0));
                    builder.append(EXPORT_DELIMETER);
                    //Placeholder below for the relative time
                    builder.append(new DecimalFormat("0000000000.000").format(0.0));
                    builder.append(EXPORT_DELIMETER);
                    //Log System
                    builder.append(message.getLogSystem());
                    builder.append(EXPORT_DELIMETER);
                    //ID
                    builder.append(new DecimalFormat("0000000000").format(message.getOriginalID()));
                    builder.append(EXPORT_DELIMETER);
                    //DLC
                    builder.append(String.format("%02d", message.getDlc()));
                    builder.append(EXPORT_DELIMETER);
                    //Processes and returns the payloads for the appropriate message type
                    MessageDescriptor descriptor = messageDescriptors.get(Integer.parseInt(Integer.toString(message.getOriginalID())));
                    builder.append(descriptor.exportMessage(message));
                    builder.append("\n");
                }
            }

            save_file(builder, file);
        }
    }

    /**
     * This method saves the String that is passed in to the passed in file.
     *
     * @param content String content to be written to file
     * @param file File the content is to be written to.
     */
    private void save_file(StringBuilder content, File file)
    {
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.append(content);
            writer.flush();
            writer.close();
        }
        catch(IOException err)
        {
            System.out.println("Error exporting file");
            System.out.println(err.getMessage());
        }
    }

    /**
     * Initializes the TableView and then processes the messages and populates the TableView with the
     * results.
     *
     */
    private void prepare_table_data(File log_file) throws IOException
    {
        try
        {
            //int launch_number = 1;
            Message processed_row;
            Scanner input = new Scanner(log_file);
            int displayIndex = 0;
            while (input.hasNext())
            {
                processed_row = processMessage(input.nextLine(), displayIndex);
                if(processed_row != null)
                {
                    /*
                    This is where you can add the functionality to identify launches as you process the messages

                    if(launch found)
                    {
                        launches.add("Launch " + launch_number + ": Index " + displayIndex);
                        launch_number++;
                    }
                     */
                    processed_messages.add(processed_row);
                    displayIndex++;
                }
            }

            /*
            here is where you will add the items to the choice box

            launches_choice_box.getItems.addAll(launches);
             */
            //Below is some test code to test functionality of choice box to navigate to selected index.
            //delete when real functionality is implemented.

            launches.addAll("Launch 1: Index 300", "Launch 2: Index 1000", "Launch 3: Index 10");
            launches_list_view.getItems().addAll(launches);

            initialize_table();
            original_backup.clear();
            original_backup.addAll(processed_messages);
            display_table(processed_messages);

        }
        catch(Exception ex)
        {
            System.out.println("Error preparing data");
            //System.out.println(ex.getStackTrace());
            throw ex;
        }
    }

    @FXML
    void close_window()
    {
        System.exit(0);
    }

    private Message processMessage(String raw_message, int messageIndex)
    {
        if(raw_message.startsWith("*"))
        {
            return null;
        }
        UnrecognizedMessageDescriptor unknownMessage = new UnrecognizedMessageDescriptor();
        String [] row = raw_message.split(" ");
        Canmsg2j canmsg2j = new Canmsg2j();
        int err = canmsg2j.convert_msgtobin(row[1]);
        MessageDescriptor messageDescriptor = messageDescriptors.get(canmsg2j.id);
        if (messageDescriptor == null)
        {
            return unknownMessage.processMessage(row, canmsg2j, messageIndex);
        }
        return messageDescriptor.processMessage(row, canmsg2j, messageIndex);
    }

    private void initialize_table()
    {
        try
        {
            TableColumn logSystemCol = new TableColumn("Log System");
            logSystemCol.setCellValueFactory(new PropertyValueFactory<Message,String>("logSystem"));

            TableColumn idCol = new TableColumn("ID");
            idCol.setCellValueFactory(new PropertyValueFactory<Message,String>("id"));

            TableColumn messageCol = new TableColumn("Message");
            messageCol.setCellValueFactory(new PropertyValueFactory<Message,String>("message"));

            TableColumn nameCol = new TableColumn("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<Message,String>("name"));

            TableColumn dlcCol = new TableColumn("DLC");
            dlcCol.setCellValueFactory(new PropertyValueFactory<Message, Integer>("dlc"));

            TableColumn p0Col = new TableColumn("Payload0");
            p0Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload0"));

            TableColumn p1Col = new TableColumn("Payload1");
            p1Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload1"));

            TableColumn p2Col = new TableColumn("Payload2");
            p2Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload2"));

            TableColumn p3Col = new TableColumn("Payload3");
            p3Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload3"));

            TableColumn p4Col = new TableColumn("Payload4");
            p4Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload4"));

            TableColumn p5Col = new TableColumn("Payload5");
            p5Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload5"));

            TableColumn p6Col = new TableColumn("Payload6");
            p6Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload6"));

            TableColumn p7Col = new TableColumn("Payload7");
            p7Col.setCellValueFactory(new PropertyValueFactory<Message, String>("payload7"));

            TableColumn indexCol = new TableColumn("Index");
            indexCol.setCellValueFactory(new PropertyValueFactory<Message, Integer>("index"));


            data_table.getColumns().addAll(logSystemCol, idCol, dlcCol, nameCol,
                    p0Col, p1Col, p2Col, p3Col, p4Col, p5Col, p6Col, p7Col, messageCol, indexCol);

        }
        catch(Exception ex)
        {
            System.out.println("Error initializing table");
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }

    }

    private void display_table(ObservableList<Message> messages) throws IOException
    {
        //TODO Have time of top item currently displayed on the list displayed above the TextField
        //TODO Have a "block" highlighting launches in the reader display in scroll bar, like IDE does that allows to be clicked to go to.
        //TODO Add ability to add filters and remove portions of the lines displayed in the reader.
        //TODO default to display entire file.
        //TODO for displaying the message, want all payloads of a message on a single line.
        //TODO want all of the fields to be lined up, like columns
        Parent root;
        try
        {
            //This commented out code can be used if you want to start the application in another window and
            //open this window with a button or other control in that other window. Just uncomment this code,
            //then delete or comment out the 3 lines below, and have the control call this method onAction.

            /*FXMLLoader loader = new FXMLLoader(getClass().getResource("exportwindow.fxml"));
            root = loader.load();
            Controller export_controller = loader.getController();
            Stage exportStage = new Stage();
            exportStage.setTitle("Selection Display");
            exportStage.setScene(new Scene(root, 800, 600));

            data_table.setItems(messages);
            data_table.minHeightProperty().bind(export_controller.table_container.heightProperty());
            export_controller.table_container.getChildren().add(data_table);
            exportStage.show();
            */

            data_table.setItems(messages);
            data_table.minHeightProperty().bind(table_container.heightProperty());
            table_container.getChildren().add(data_table);
        }
        catch (Exception ex)
        {
            System.out.println("Error displaying table");
            System.out.println(ex.getStackTrace());
            System.out.println(ex.getMessage());
        }
    }

    /**
     * This method ensures that if the user selects All, the rest of the checkboxes are deselected,
     * and if one of the specific checkboxes is selected, All is deseltected.
     *
     * @param event This is the CheckBox that was checked that triggered the method call
     */
    @FXML
    private void check_filter(ActionEvent event)
    {
        CheckBox checkedbox = (CheckBox)event.getSource();
        // If All is checked, then it iterates through the list, skipping the 0th index which is the
        // All checkbox and deselects them all. If it does not skip the All box, then the All box is
        // uncheckable as checking it would cause it to enter the for loop and be deselected immediately
        if(checkedbox.getText().equals("All"))
        {
            for (CheckBox filter : filters.subList(1,filters.size()))
            {
                filter.setSelected(false);
            }
        }
        //If anything but All is selected, simple makes sure All is deselected.
        else
        {
            filters.get(0).setSelected(false);
        }
    }

    @FXML
    private void apply_filter()
    {
        processed_messages.clear();
        processed_messages.addAll(original_backup);
        ArrayList<Message> filteredMessages = new ArrayList<>();
        int newIndex = 0;
        if(!filters.get(0).isSelected())
        {
            for (Message row : processed_messages)
            {
                for(CheckBox filter : filters)
                {
                    if (row.getName().equals(filter.getText()) && filter.isSelected())
                    {
                        row.setIndex(newIndex);
                        filteredMessages.add(row);
                        newIndex++;
                        break;
                    }
                }
            }
            processed_messages.clear();
            processed_messages.addAll(filteredMessages);
        }
        else
        {
            for (Message row : processed_messages)
            {
                row.setIndex(newIndex++);
            }
        }
    }

    @FXML
    private void save_filter_state()
    {
        FilterState filterState = new FilterState(filters);
        filterState.saveFilterState();
    }

    @FXML
    private void load_filter_state()
    {
        FilterState filterState = new FilterState();
        ArrayList<String> filterStrings = filterState.loadFilter();
        for (CheckBox filterBox : filters)
        {
            filterBox.setSelected(false);
            for (String filterString : filterStrings)
            {
                if (filterBox.getText().equals(filterString))
                {
                    filterBox.setSelected(true);
                }
            }
        }
    }

    @FXML
    private void go_to_selected_launch()
    {
        String selection = launches_list_view.getSelectionModel().getSelectedItem().toString();
        String[] split_selection = selection.split(" ");
        try
        {
            int index_of_launch = Integer.parseInt(split_selection[split_selection.length - 1]);
            data_table.scrollTo(index_of_launch);
        }
        catch(NumberFormatException numEx)
        {
            System.out.println("Cannot parse " + split_selection[split_selection.length - 1]);
        }
    }



}
