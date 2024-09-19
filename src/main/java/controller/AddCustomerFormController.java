package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.Customer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    public JFXTextField txtNumber;

    @FXML
    private ComboBox<String> cmbTitle;

    @FXML
    private DatePicker dateDob;

    @FXML
    private JFXTextField txtAddress;

    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> titles = FXCollections.observableArrayList();
        titles.add("Mr. ");
        titles.add("Miss. ");

        cmbTitle.setItems(titles);
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        List<Customer> customerList = DBConnection.getInstance().getConnection();

        boolean idExists = customerList.stream()
                .anyMatch(customer -> customer.getId().equals(txtId.getText()));

        if (idExists) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Duplicate ID Error");
            alert.setHeaderText(null);
            alert.setContentText("The customer ID already exists. Please enter a unique ID.");
            alert.showAndWait();
        } else {
//            customerList.add(
//                    new Customer(
//                            txtId.getText(),
//                            txtName.getText(),
//                            txtAddress.getText(),
//                            txtNumber.getText(),
//                            cmbTitle.getValue(),
//                            dateDob.getValue()
//                    )
//            );

            txtId.setText("");
            txtName.setText("");
            txtAddress.setText("");
            txtNumber.setText("");
            dateDob.setValue(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Customer added successfully!");
            alert.showAndWait();
        }
    }

}
