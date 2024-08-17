package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import model.Customer;

import java.time.LocalDate;
import java.util.List;

public class UpdateCustomerFormController {
    public JFXTextField txtSearchDOBU;
    public JFXTextField txtSearchNumberU;
    public JFXTextField txtSearchAddressU;
    public JFXTextField txtSearchNameU;
    public JFXTextField txtSearchIDU;
    public JFXTextField txtSearchID;

    public void btnOnActionUpdate(ActionEvent actionEvent) {

        String searchId = txtSearchIDU.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        Customer foundCustomer = customerList.stream()
                .filter(customer -> customer.getId().equals(searchId))
                .findFirst()
                .orElse(null);

        if (foundCustomer != null) {
            foundCustomer.setName(txtSearchNameU.getText());
            foundCustomer.setAddress(txtSearchAddressU.getText());
            foundCustomer.setNumber(txtSearchNumberU.getText());
            foundCustomer.setDob(LocalDate.parse(txtSearchDOBU.getText()));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Update Success");
            alert.setHeaderText(null);
            alert.setContentText("Customer details updated successfully!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Error");
            alert.setHeaderText(null);
            alert.setContentText("No customer found with the provided ID.");
            alert.showAndWait();
        }

    }

    public void btnOnActionSearch(ActionEvent actionEvent) {

        String searchId = txtSearchID.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        Customer foundCustomer = customerList.stream()
                .filter(customer -> customer.getId().equals(searchId))
                .findFirst()
                .orElse(null);

        if (foundCustomer != null) {
            txtSearchIDU.setText(foundCustomer.getId());
            txtSearchNameU.setText(foundCustomer.getName());
            txtSearchAddressU.setText(foundCustomer.getAddress());
            txtSearchNumberU.setText(foundCustomer.getNumber());
            txtSearchDOBU.setText(foundCustomer.getDob().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setHeaderText(null);
            alert.setContentText("No customer found with the provided ID.");
            alert.showAndWait();

            txtSearchIDU.setText("");
            txtSearchNameU.setText("");
            txtSearchAddressU.setText("");
            txtSearchNumberU.setText("");
            txtSearchDOBU.setText("");
        }
    }
}
