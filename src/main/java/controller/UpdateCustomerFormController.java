package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class UpdateCustomerFormController implements Initializable {
    public JFXTextField txtSearchID;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtAddress;
    public DatePicker dateDob;
    public ComboBox cmbTitle;
    public JFXTextField txtSalary;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;



    public void btnOnActionUpdate(ActionEvent actionEvent) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");

            String updateSQL = "UPDATE customer SET CustName = ?, CustAddress = ?, DOB = ?, salary = ?, City = ?, Province = ?, PostalCode = ? WHERE CustID = ?";
            PreparedStatement updateStmt = connection.prepareStatement(updateSQL);

            updateStmt.setString(1, txtName.getText()); // CustName
            updateStmt.setString(2, txtAddress.getText()); // CustAddress
            updateStmt.setDate(3, java.sql.Date.valueOf(dateDob.getValue())); // DOB
            updateStmt.setDouble(4, Double.parseDouble(txtSalary.getText())); // salary
            updateStmt.setString(5, txtCity.getText()); // City
            updateStmt.setString(6, txtProvince.getText()); // Province
            updateStmt.setString(7, txtPostalCode.getText()); // PostalCode
            updateStmt.setString(8, txtId.getText()); // CustID to identify the record to update

            // Execute the update operation
            int rowsAffected = updateStmt.executeUpdate();

            // Check if the update was successful
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Customer updated successfully!");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Update Error");
                alert.setHeaderText(null);
                alert.setContentText("No customer found with the provided ID.");
                alert.showAndWait();
            }

            // Close the connection
            updateStmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while updating the customer.");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please ensure all fields are filled out correctly.");
            alert.showAndWait();
        }
    }



    public void btnOnActionSearch(ActionEvent actionEvent) {
        String searchId = txtSearchID.getText();

        if (searchId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a Customer ID to search.");
            alert.showAndWait();
            return;
        }

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");

            String searchSQL = "SELECT * FROM customer WHERE CustID = ?";
            PreparedStatement searchStmt = connection.prepareStatement(searchSQL);
            searchStmt.setString(1, searchId);

            ResultSet resultSet = searchStmt.executeQuery();

            if (resultSet.next()) {

                txtId.setText(resultSet.getString("CustID"));
                txtName.setText(resultSet.getString("CustName"));
                txtAddress.setText(resultSet.getString("CustAddress"));
                txtSalary.setText(String.valueOf(resultSet.getDouble("salary")));
                dateDob.setValue(resultSet.getDate("DOB").toLocalDate());
                txtCity.setText(resultSet.getString("City"));
                txtProvince.setText(resultSet.getString("Province"));
                txtPostalCode.setText(resultSet.getString("PostalCode"));

            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Search Error");
                alert.setHeaderText(null);
                alert.setContentText("No customer found with the provided ID.");
                alert.showAndWait();

                clearSearchFields();
            }

            // Close the connection
            resultSet.close();
            searchStmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while searching for the customer.");
            alert.showAndWait();
        }
    }

    private void clearSearchFields() {
        txtId.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtSalary.setText("");
        dateDob.setValue(null);
        txtCity.setText("");
        txtProvince.setText("");
        txtPostalCode.setText("");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setEditable(false);
        cmbTitle.setDisable(true);
    }
}
