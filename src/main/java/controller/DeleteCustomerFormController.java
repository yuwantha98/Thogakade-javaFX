package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import model.Customer;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteCustomerFormController implements Initializable {
    public JFXTextField txtSearchID;
    public JFXTextField txtSearchIDD;
    public JFXTextField txtSearchNameD;
    public JFXTextField txtSearchAddressD;
    public JFXTextField txtSearchNumberD;
    public JFXTextField txtSearchDOBD;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtSearchIDD.setEditable(false);
        txtSearchNameD.setEditable(false);
        txtSearchAddressD.setEditable(false);
        txtSearchNumberD.setEditable(false);
        txtSearchDOBD.setEditable(false);
    }

    public void btnOnActionSearch(ActionEvent actionEvent) {

        String searchId = txtSearchID.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        Customer foundCustomer = customerList.stream()
                .filter(customer -> customer.getId().equals(searchId))
                .findFirst()
                .orElse(null);

        if (foundCustomer != null) {
            txtSearchIDD.setText(foundCustomer.getId());
            txtSearchNameD.setText(foundCustomer.getName());
            txtSearchAddressD.setText(foundCustomer.getAddress());
//            txtSearchNumberD.setText(foundCustomer.getNumber());
            txtSearchDOBD.setText(foundCustomer.getDob().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setHeaderText(null);
            alert.setContentText("No customer found with the provided ID.");
            alert.showAndWait();

            clearFields();
        }
    }

    public void btnOnActionDelete(ActionEvent actionEvent) {
        String searchId = txtSearchIDD.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        Customer foundCustomer = customerList.stream()
                .filter(customer -> customer.getId().equals(searchId))
                .findFirst()
                .orElse(null);

        if (foundCustomer != null) {
            customerList.remove(foundCustomer);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Success");
            alert.setHeaderText(null);
            alert.setContentText("Customer deleted successfully!");
            alert.showAndWait();

            clearFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Error");
            alert.setHeaderText(null);
            alert.setContentText("No customer found with the provided ID.");
            alert.showAndWait();
        }
    }

    private void clearFields() {
        txtSearchIDD.setText("");
        txtSearchNameD.setText("");
        txtSearchAddressD.setText("");
        txtSearchNumberD.setText("");
        txtSearchDOBD.setText("");
    }
}
