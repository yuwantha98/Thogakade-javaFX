package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddCustomerFormController implements Initializable {

    @FXML
    public JFXTextField txtSalary;

    @FXML
    public JFXTextField txtCity;

    @FXML
    public JFXTextField txtProvince;

    @FXML
    public JFXTextField txtPostalCode;

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
        titles.add("Mr ");
        titles.add("Miss ");

        cmbTitle.setItems(titles);
    }

    @FXML
    void btnAddCustomerOnAction(ActionEvent event) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");

            String insertSQL = "INSERT INTO customer (CustID, CustTitle, CustName, CustAddress, DOB, salary, City, Province, PostalCode) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertStmt = connection.prepareStatement(insertSQL);

            insertStmt.setString(1, txtId.getText());
            insertStmt.setString(2, cmbTitle.getValue());
            insertStmt.setString(3, txtName.getText());
            insertStmt.setString(4, txtAddress.getText());
            insertStmt.setDate(5, java.sql.Date.valueOf(dateDob.getValue()));
            insertStmt.setDouble(6, Double.parseDouble(txtSalary.getText()));
            insertStmt.setString(7, txtCity.getText());
            insertStmt.setString(8, txtProvince.getText());
            insertStmt.setString(9, txtPostalCode.getText());

            int rowsAffected = insertStmt.executeUpdate();

            if (rowsAffected > 0) {

                txtId.setText("");
                txtName.setText("");
                txtAddress.setText("");
                txtSalary.setText("");
                txtCity.setText("");
                txtProvince.setText("");
                txtPostalCode.setText("");
                dateDob.setValue(null);
                cmbTitle.setValue(null);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Customer added successfully!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while adding the customer to the database.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please ensure all fields are filled out correctly.");
            alert.showAndWait();
        }
    }


}
