package gui;

import backend.Component;
import backend.KnowledgeBase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txaPrescriptions;

    @FXML
    private Button btnGenerate;

    @FXML
    private Button btnRemoveLast;

    @FXML
    private TextField txtQuantity;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnQuit;

    @FXML
    private ComboBox<String> cboChooseComponent;

    @FXML
    private TextArea txaComponents;

    // declare and ArrayList with components
    private ArrayList<Component> componentArrayList;

    // declare an ArrayList with components and prescriptions
    private KnowledgeBase knowledgeBase;

    @FXML
    void btnAddOnAction(ActionEvent event) {
        txaPrescriptions.setText("");

        // validate that the user has entered a quantity
        // if not then show an alert with message for error and return
        if (txtQuantity.getText() == null || txtQuantity.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Please, Enter a Valid Quantity!");
            alert.showAndWait();
            return;
        }
        // validate that the user has entered a valid name of a component
        // if not then show an alert with message for error and return
        if (!(cboChooseComponent.getItems().contains(cboChooseComponent.getValue()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Component");
            alert.setHeaderText("Please, Choose a Valid Component!");
            alert.showAndWait();
            txtQuantity.setText("");
            cboChooseComponent.setValue(null);
            return;
        }

        // validate that the user cannot enter a component twice
        if (txaComponents.getText().contains(cboChooseComponent.getValue())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("This Component Has Already Been Entered!");
            alert.setHeaderText("Please, Choose Another Component!");
            alert.showAndWait();
            txtQuantity.setText("");
            cboChooseComponent.setValue(null);
            return;
        }

        // else, add each component in the text area
        for (Component p : componentArrayList) {
            if (p.getName().equals(cboChooseComponent.getValue())) {
                txaComponents.setText(txaComponents.getText() + p.toString() + ", Entered quantity: " + txtQuantity.getText() + '\n');
            }
        }
        // finally, make the text null, so the user can enter e new component
        txtQuantity.setText("");
        cboChooseComponent.setValue(null);
    }

    @FXML
    void btnGenerateOnAction(ActionEvent event) {
        // validate that the user has entered some components
        // if nothing's been entered, then display an alert with an error message + return;
        if (txaComponents.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("The Component List Is Empty!");
            alert.setHeaderText("Please, Enter at Least Two Medical Components!");
            alert.showAndWait();
            return;
        }
        ArrayList<Component> input = new ArrayList<>();
        String[] components = txaComponents.getText().split("[\n]"); // output each component on a new line
        for (String p : components) { // for each string in the list of component
            String[] subComponents = p.split("[,][ ]"); // remove the ',' and the space
            input.add(new Component(
                    subComponents[0].split("[:][ ]")[1], // add get the name of a component
                    null,
                    null,
                    Double.parseDouble(subComponents[subComponents.length - 1].split("[:][ ]")[1]) // and its quantity
            ));
        }
        // at the end, show all prescriptions in the text area
        txaPrescriptions.setText(knowledgeBase.getPrescriptions(input).toString());
        if (txaPrescriptions.getText().equals("[]")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Prescriptions Available For the Listed Components!");
            alert.setHeaderText("Try Again With Different Components!");
            alert.showAndWait();
            return;
        }
        txaComponents.setText("");
    }

    @FXML
    void btnQuitOnAction(ActionEvent event) {
        Platform.exit(); // quit the program
    }

    @FXML
    void btnRemoveLastOnAction(ActionEvent event) {
        // validate that the user has entered at least 2 components in the text area for the components list
        // if nothing's been entered, then display an alert with an error message + return;
        if (txaComponents.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid operation");
            alert.setHeaderText("The list of components is empty!");
            alert.showAndWait();
            return;
        }

        StringBuilder result = new StringBuilder();
        String[] tmp = txaComponents.getText().split("[\n]"); // contains all components from the text area for components
        // get the last entered component with tmp.length - 1 and remove it
        for (int i = 0; i < tmp.length - 1; i++) {
            result.append(tmp[i]).append('\n');
        }
        txaComponents.setText(result.toString());
    }

    @FXML
    void initialize() {
        assert txaPrescriptions != null : "fx:id=\"txaPrescriptions\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnGenerate != null : "fx:id=\"btnGenerate\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnRemoveLast != null : "fx:id=\"btnRemoveLast\" was not injected: check your FXML file 'sample.fxml'.";
        assert txtQuantity != null : "fx:id=\"txtQuantity\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnAdd != null : "fx:id=\"btnAdd\" was not injected: check your FXML file 'sample.fxml'.";
        assert btnQuit != null : "fx:id=\"btnQuit\" was not injected: check your FXML file 'sample.fxml'.";
        assert cboChooseComponent != null : "fx:id=\"cboChooseComponent\" was not injected: check your FXML file 'sample.fxml'.";
        assert txaComponents != null : "fx:id=\"txaComponents\" was not injected: check your FXML file 'sample.fxml'.";

        // read the files consisting of components and prescriptions
        knowledgeBase = new KnowledgeBase("components.csv", "prescriptions.csv");
        componentArrayList = knowledgeBase.getComponents(); // get all components from the knowledge base

        // add all names of components in the combo box, so the user can pick from them
        for (Component component : componentArrayList) {
            cboChooseComponent.getItems().add(component.getName());
        }

        // additional formatting of the text areas and the combo box
        txaComponents.setWrapText(Boolean.TRUE);
        txaPrescriptions.setWrapText(Boolean.TRUE);
        cboChooseComponent.setEditable(Boolean.TRUE);
    }
} // end of class Controller


