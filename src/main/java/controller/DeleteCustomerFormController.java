package controller;

import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.Customer;

import java.net.URL;
import java.sql.*;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteCustomerFormController implements Initializable {
    public JFXTextField txtSearchID;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public DatePicker dateDob;
    public JFXTextField txtAddress;
    public ComboBox cmbTitle;
    public JFXTextField txtSalary;
    public JFXTextField txtCity;
    public JFXTextField txtProvince;
    public JFXTextField txtPostalCode;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtId.setEditable(false);
        txtName.setEditable(false);
        txtAddress.setEditable(false);
        txtSalary.setEditable(false);
        txtCity.setEditable(false);
        dateDob.setDisable(true);
        txtProvince.setEditable(false);
        txtPostalCode.setEditable(false);
        cmbTitle.setDisable(true);
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

    public void btnOnActionDelete(ActionEvent actionEvent) {
        String deleteId = txtId.getText();

        // Check if the customer ID field is not empty
        if (deleteId.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a Customer ID to delete.");
            alert.showAndWait();
            return;
        }

        try {
            // Establish a connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/thogakade", "root", "12345");

            // SQL Delete query to remove the customer record
            String deleteSQL = "DELETE FROM customer WHERE CustID = ?";
            PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL);
            deleteStmt.setString(1, deleteId);

            // Execute the delete operation
            int rowsAffected = deleteStmt.executeUpdate();

            // Check if the deletion was successful
            if (rowsAffected > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Customer deleted successfully!");
                alert.showAndWait();

                // Clear the input fields after successful deletion
                clearSearchFields();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Delete Error");
                alert.setHeaderText(null);
                alert.setContentText("No customer found with the provided ID.");
                alert.showAndWait();
            }

            // Close the connection
            deleteStmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while deleting the customer.");
            alert.showAndWait();
        }
    }
}
