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

public class SearchCustomerFormController implements Initializable {
    public JFXTextField txtSearchID;
    public JFXTextField txtSearchIDV;
    public JFXTextField txtSearchNameV;
    public JFXTextField txtSearchAddressV;
    public JFXTextField txtSearchNumberV;
    public JFXTextField txtSearchDOBV;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtSearchIDV.setEditable(false);
        txtSearchNameV.setEditable(false);
        txtSearchAddressV.setEditable(false);
        txtSearchNumberV.setEditable(false);
        txtSearchDOBV.setEditable(false);
    }

    public void btnOnActionSearch(ActionEvent actionEvent) {

        String searchId = txtSearchID.getText();

        List<Customer> customerList = DBConnection.getInstance().getConnection();

        Customer foundCustomer = customerList.stream()
                .filter(customer -> customer.getId().equals(searchId))
                .findFirst()
                .orElse(null);

        if (foundCustomer != null) {
            txtSearchIDV.setText(foundCustomer.getId());
            txtSearchNameV.setText(foundCustomer.getName());
            txtSearchAddressV.setText(foundCustomer.getAddress());
            txtSearchNumberV.setText(foundCustomer.getNumber());
            txtSearchDOBV.setText(foundCustomer.getDob().toString());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setHeaderText(null);
            alert.setContentText("No customer found with the provided ID.");
            alert.showAndWait();

            txtSearchIDV.setText("");
            txtSearchNameV.setText("");
            txtSearchAddressV.setText("");
            txtSearchNumberV.setText("");
            txtSearchDOBV.setText("");
        }
    }
}
