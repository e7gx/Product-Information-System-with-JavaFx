package Project_2;

import javafx.application.Application;      // Provides the basic framework for a JavaFX application.
import javafx.application.Platform;         // Provides access to the JavaFX application lifecycle and platform-specific operations.
import javafx.collections.FXCollections;    // Provides utility methods for creating and modifying observable collections.
import javafx.collections.ObservableList;   // Represents a list that allows listeners to track changes when they occur.
import javafx.geometry.Insets;              // Defines the padding of a layout container.
import javafx.geometry.Pos;                 // Defines the position of a node within a layout.
import javafx.scene.Scene;                  // Represents the container for all content in a scene graph.
import javafx.scene.control.*;              // Provides a set of UI controls, such as buttons, labels, and text fields.
import javafx.scene.layout.GridPane;        // Represents a flexible grid of nodes in a layout container.
import javafx.scene.text.*;                 // Defines the text-related classes, such as Font and Text.
import javafx.stage.Stage;                  // Represents the main window of a JavaFX application.
import java.sql.*;                          // Provides JDBC functionality for connecting to a database and executing SQL queries.
import java.time.LocalDate;                  // Represents a date without a time-zone in the ISO-8601 calendar system.
import java.util.Optional;                  // Represents an optional value that may or may not be present.

public class ProductManagementApp extends Application {

    // Database connection variables
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private String sql;

    // Product input variables
    private String selectedType;
    private String enteredModel;
    private String enteredPrice;
    private int affectedRows;
    private int enteredCount;
    private int enteredID;
    private int searchBy;

    private final Label nameLabel1 = new Label("Student Name:Abdullah Ibrahim Al-Ghamdi");
    private final Label studentNumberLabel1 = new Label("Student Number: 441003562");
    private final Label groupNumberLabel1 = new Label("Group Number: 2");

    private final Label nameLabel2 = new Label("Student Name: Hassan Abdullah Maajeeni ");
    private final Label studentNumberLabel2 = new Label("Student Number: :442000511");
    private final Label groupNumberLabel2 = new Label("Group Number: 2");

    public static void main(String[] args) {
        launch(args);// launches the JavaFX runtime and your JavaFX application
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Products Information System");

        // Create menu bar and menu items
        MenuBar menuBar = new MenuBar();
        Menu productsMenu = new Menu("Products");
        MenuItem addMenuItem = new MenuItem("Add");
        MenuItem searchMenuItem = new MenuItem("Search");
        MenuItem deleteMenuItem = new MenuItem("Delete");
        MenuItem exitMenuItem = new MenuItem("Exit");

        // Create sub-menu for product operations
        Menu productMenu = new Menu("Product");
        productMenu.getItems().addAll(addMenuItem, searchMenuItem, deleteMenuItem);

        // Add menu items to the main menu
        productsMenu.getItems().addAll(productMenu, new SeparatorMenuItem(), exitMenuItem);

        // Set actions for menu items
        addMenuItem.setOnAction(e -> showAddProductScene(primaryStage, menuBar));
        searchMenuItem.setOnAction(e -> showSearchProductScene(primaryStage, menuBar));
        deleteMenuItem.setOnAction(e -> showDeleteProductScene(primaryStage, menuBar));
        exitMenuItem.setOnAction(e -> Platform.exit());

        // Add the main menu to the menu bar
        menuBar.getMenus().add(productsMenu);
        menuBar.setMinWidth(650);

        // Create the main layout
        GridPane mainLayout = new GridPane();
        mainLayout.setAlignment(Pos.TOP_CENTER);
        mainLayout.setVgap(10); // Set vertical gap between rows

        // Add UI elements to the main layout
        mainLayout.add(menuBar, 0, 0, 3, 1);
        mainLayout.add(nameLabel1, 0, 1);
        mainLayout.add(studentNumberLabel1, 1, 1);
        mainLayout.add(groupNumberLabel1, 2, 1);
        mainLayout.add(nameLabel2, 0, 2);
        mainLayout.add(studentNumberLabel2, 1, 2);
        mainLayout.add(groupNumberLabel2, 2, 2);

        // Set main layout alignment
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Create the main scene
        Scene scene = new Scene(mainLayout, 650, 500);

        // Set the main scene as the primary stage's scene
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAddProductScene(Stage primaryStage, MenuBar menuBar) {
        // Create the layout for the add product scene
        GridPane addLayout = new GridPane();
        addLayout.setAlignment(Pos.TOP_CENTER);
        addLayout.setHgap(10);
        addLayout.setVgap(10);
        addLayout.setPadding(new Insets(2, 2, 2, 2));

        // Create UI elements for adding product
        Label typeLabel = new Label("Type:");
        ChoiceBox<String> typeChoiceBox = new ChoiceBox<>();
        typeChoiceBox.getItems().addAll("", "Phone", "Laptop", "Car");
        typeChoiceBox.setValue("");
        Label modelLabel = new Label("Model:");
        TextField modelTextField = new TextField();
        Label priceLabel = new Label("Price:");
        TextField priceTextField = new TextField();
        Label countLabel = new Label("Count:");
        Slider countSlider = new Slider(0, 10, 0);
        countSlider.setShowTickLabels(true);
        countSlider.setShowTickMarks(true);
        countSlider.setMajorTickUnit(1);
        countSlider.setBlockIncrement(1);
        Label deliveryDateLabel = new Label("Delivery Date:");
        DatePicker deliveryDatePicker = new DatePicker();
        Button saveButton = new Button("Save");
        Label statusLabel = new Label();

        // Add UI elements to the layout grid
        addLayout.add(menuBar, 0, 0);
        menuBar.setMinWidth(650);
        saveButton.setMaxWidth(150);
        addLayout.add(nameLabel1, 0, 1);
        addLayout.add(studentNumberLabel1, 1, 1);
        addLayout.add(groupNumberLabel1, 2, 1);
        addLayout.add(nameLabel2, 0, 2);
        addLayout.add(studentNumberLabel2, 1, 2);
        addLayout.add(groupNumberLabel2, 2, 2);
        addLayout.add(typeLabel, 0, 3);
        addLayout.add(typeChoiceBox, 1, 3);
        addLayout.add(modelLabel, 0, 4);
        addLayout.add(modelTextField, 1, 4);
        addLayout.add(priceLabel, 0, 5);
        addLayout.add(priceTextField, 1, 5);
        addLayout.add(countLabel, 0, 6);
        addLayout.add(countSlider, 1, 6);
        addLayout.add(deliveryDateLabel, 0, 7);
        addLayout.add(deliveryDatePicker, 1, 7);
        addLayout.add(saveButton, 1, 8, 2, 1);
        addLayout.add(statusLabel, 0, 9, 2, 1);
        addLayout.setPrefSize(700, 500);

        // Create the add scene
        Scene addScene = new Scene(addLayout);

        // Set action for save button
        saveButton.setOnAction(e -> {
            // Get input values
            selectedType = typeChoiceBox.getValue();
            enteredModel = modelTextField.getText();
            enteredPrice = priceTextField.getText();
            enteredCount = (int) countSlider.getValue();
            LocalDate selectedDeliveryDate = deliveryDatePicker.getValue();

            if (validateInput(selectedType, enteredModel, enteredPrice, enteredCount, selectedDeliveryDate)) {
                // Add product if input is valid
                addProduct(selectedType, enteredModel, Float.parseFloat(enteredPrice), enteredCount, selectedDeliveryDate);

                // Clear input fields
                clearInputFields(typeChoiceBox, modelTextField, priceTextField, countSlider, deliveryDatePicker);

                // Display success message
                statusLabel.setText("Product added successfully!");
                statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                // Display error message for invalid input
                statusLabel.setText("Invalid input. Please enter valid values.");
                statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        });

        // Set the add scene as the primary stage's scene
        primaryStage.setScene(addScene);
    }

    private void showSearchProductScene(Stage primaryStage, MenuBar menuBar) {
        // Create the layout for the search scene
        GridPane searchLayout = new GridPane();
        searchLayout.setAlignment(Pos.CENTER);
        searchLayout.setHgap(10);
        searchLayout.setVgap(8);
        searchLayout.setPadding(new Insets(2, 2, 2, 2));

        // Create UI elements for search functionality
        Label searchLabel = new Label("Search Criteria:");
        TextField searchTextField = new TextField();
        Button searchButton = new Button("Search");
        Button refreshButton = new Button("Refresh");
        TableView<Product> tableView = new TableView<>();
        Label statusLabel = new Label();

        // Create table columns for displaying product information
        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        TableColumn<Product, String> typeColumn = new TableColumn<>("Type");
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
        TableColumn<Product, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
        TableColumn<Product, Float> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        TableColumn<Product, Integer> countColumn = new TableColumn<>("Count");
        countColumn.setCellValueFactory(cellData -> cellData.getValue().countProperty().asObject());
        TableColumn<Product, String> deliveryDateColumn = new TableColumn<>("Delivery Date");
        deliveryDateColumn.setCellValueFactory(cellData -> cellData.getValue().deliveryDateProperty().asString());

        // Add columns to the table view
        tableView.getColumns().addAll(idColumn, typeColumn, modelColumn, priceColumn, countColumn, deliveryDateColumn);

        // Set actions for refresh and search buttons
        refreshButton.setOnAction(e -> {
            tableView.getItems().clear();
            statusLabel.setText("");
            loadProducts(tableView);
        });

        searchButton.setOnAction(e -> {
            String searchCriteria = searchTextField.getText();
            tableView.getItems().clear();
            statusLabel.setText("");
            if (searchCriteria.isEmpty()) {
                loadProducts(tableView);
            } else {
                searchProducts(tableView, searchCriteria);
            }
            if (tableView.getItems().isEmpty()) {
                statusLabel.setText("No records available for this search criteria!");
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        });

        // Add UI elements to the layout grid
        searchLayout.add(menuBar, 0, 0);
        menuBar.setMinWidth(700);

        // Add labels and input fields
        searchLayout.add(nameLabel1, 0, 1);
        searchLayout.add(studentNumberLabel1, 1, 1);
        searchLayout.add(groupNumberLabel1, 2, 1);
        searchLayout.add(nameLabel2, 0, 2);
        searchLayout.add(studentNumberLabel2, 1, 2);
        searchLayout.add(groupNumberLabel2, 2, 2);
        searchLayout.add(searchLabel, 0, 3);
        searchLayout.add(searchTextField, 1, 3);
        searchLayout.add(searchButton, 2, 3);
        searchLayout.add(refreshButton, 2, 4);
        searchLayout.add(tableView, 0, 5, 4, 1);
        searchLayout.add(statusLabel, 0, 6, 4, 1);

        searchLayout.setPrefSize(500, 500);

        // Create the search scene
        Scene searchScene = new Scene(searchLayout);

        // Load products into the table view
        loadProducts(tableView);

        // Set the search scene as the primary stage's scene
        primaryStage.setScene(searchScene);
    }

    private void showDeleteProductScene(Stage primaryStage, MenuBar menuBar) {
        GridPane deleteLayout = new GridPane();
        deleteLayout.setAlignment(Pos.CENTER);
        deleteLayout.setHgap(10);
        deleteLayout.setVgap(10);
        deleteLayout.setPadding(new Insets(2, 2, 2, 2));

        // Create UI components
        Label idLabel = new Label("Product ID:");
        TextField idTextField = new TextField();
        Button deleteButton = new Button("Delete");
        Label statusLabel = new Label();

        // Add UI components to the grid pane
        deleteLayout.add(menuBar, 0, 0);
        deleteLayout.add(idLabel, 0, 1);
        deleteLayout.add(idTextField, 1, 1);
        deleteLayout.add(deleteButton, 0, 2, 2, 1);
        deleteLayout.add(statusLabel, 0, 3, 2, 1);

        deleteLayout.setPrefSize(500, 200);

        Scene deleteScene = new Scene(deleteLayout);

        // Delete button action
        deleteButton.setOnAction(e -> {
            String idText = idTextField.getText();

            if (!idText.isEmpty() && idText.matches("\\d+")) {
                int id = Integer.parseInt(idText);
                boolean productExists = isProductExists(id);

                if (productExists) {
                    Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                    confirmDialog.setTitle("Confirmation");
                    confirmDialog.setHeaderText(null);
                    confirmDialog.setContentText("Are you sure you want to delete this product?");

                    Optional<ButtonType> result = confirmDialog.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        if (deleteProduct(id)) {
                            statusLabel.setText("Product with ID " + id + " has been successfully deleted.");
                            statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                            statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                        } else {
                            statusLabel.setText("Error deleting product. Please try again later.");
                            statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                            statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                        }
                    } else {
                        statusLabel.setText("Deletion of product with ID " + id + " has been cancelled.");
                        statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                        statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                    }
                } else {
                    statusLabel.setText("Product with ID " + id + " does not exist.");
                    statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                    statusLabel.setTextFill(javafx.scene.paint.Color.RED);
                }
            } else {
                statusLabel.setText("Invalid product ID. Please enter a valid integer.");
                statusLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            }
        });

        primaryStage.setScene(deleteScene);
    }

    private void loadProducts(TableView<Product> tableView) {
        try {
            establishConnection(); // Establish a connection to the database
            sql = "SELECT * FROM productstbl_abdullah_hassan";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(); // Execute the query and retrieve the result set

            ObservableList<Product> productList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String type = resultSet.getString("Type");
                String model = resultSet.getString("Model");
                float price = resultSet.getFloat("Price");
                int count = resultSet.getInt("Count");
                LocalDate deliveryDate = resultSet.getDate("DeliveryDate").toLocalDate();
                Product product = new Product(id, type, model, price, count, deliveryDate);
                productList.add(product); // Add each product to the product list
            }

            tableView.setItems(productList); // Set the items of the TableView to the product list

            closeConnection(); // Close the database connection
        } catch (SQLException e) {
            System.err.println("Error loading products from the database.");
            e.printStackTrace();
        }
    }

    private void searchProducts(TableView<Product> tableView, String searchCriteria) {
        try {
            establishConnection(); // Establish a connection to the database
            sql = "SELECT * FROM productstbl_abdullah_hassan WHERE Type LIKE ? OR Model LIKE ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, "%" + searchCriteria + "%"); // Set the search criteria for Type column
            preparedStatement.setString(2, "%" + searchCriteria + "%"); // Set the search criteria for Model column
            resultSet = preparedStatement.executeQuery(); // Execute the query and retrieve the result set

            ObservableList<Product> productList = FXCollections.observableArrayList();

            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String type = resultSet.getString("Type");
                String model = resultSet.getString("Model");
                float price = resultSet.getFloat("Price");
                int count = resultSet.getInt("Count");
                LocalDate deliveryDate = resultSet.getDate("DeliveryDate").toLocalDate();
                Product product = new Product(id, type, model, price, count, deliveryDate);
                productList.add(product); // Add each product to the product list
            }

            tableView.setItems(productList); // Set the items of the TableView to the product list

            closeConnection(); // Close the database connection
        } catch (SQLException e) {
            System.err.println("Error searching for products.");
            e.printStackTrace();
        }
    }

    private boolean deleteProduct(int id) {
        try {
            establishConnection(); // Establish a connection to the database
            sql = "DELETE FROM productstbl_abdullah_hassan WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id); // Set the ID parameter in the SQL query
            affectedRows = preparedStatement.executeUpdate(); // Execute the delete query and get the number of affected rows
            closeConnection(); // Close the database connection
            return affectedRows == 1; // Return true if one row was affected (deletion successful), false otherwise
        } catch (SQLException e) {
            System.err.println("Error deleting product.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateInput(String type, String model, String price, int count, LocalDate deliveryDate) {
        // Check if the type, model, and deliveryDate are not empty
        // Check if the price matches the regex pattern for a decimal number
        // Check if the count is greater than 0
        // Check if the deliveryDate is not null
        return !type.isEmpty() && !model.isEmpty() && price.matches("^\\d+(\\.\\d+)?$") && count > 0 && deliveryDate != null;
    }

    private void addProduct(String type, String model, float price, int count, LocalDate deliveryDate) {
        try {
            establishConnection();
            String sql = "INSERT INTO productstbl_abdullah_hassan (Type, Model, Price, DeliveryDate, Count) VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, type); // Set the product type parameter
            preparedStatement.setString(2, model); // Set the product model parameter
            preparedStatement.setFloat(3, price); // Set the product price parameter
            preparedStatement.setDate(4, java.sql.Date.valueOf(deliveryDate)); // Set the delivery date parameter
            preparedStatement.setInt(5, count); // Set the product count parameter
            affectedRows = preparedStatement.executeUpdate(); // Execute the SQL statement and get the number of affected rows
            closeConnection();
        } catch (SQLException e) {
            System.err.println("Error adding product.");
            e.printStackTrace();
        }
    }

    private void clearInputFields(ChoiceBox<String> typeChoiceBox, TextField modelTextField, TextField priceTextField, Slider countSlider, DatePicker deliveryDatePicker) {
        // Clear the input fields by resetting their values
        typeChoiceBox.setValue(""); // Clear the choice box selection
        modelTextField.setText(""); // Clear the model text field
        priceTextField.setText(""); // Clear the price text field
        countSlider.setValue(0); // Reset the count slider to 0
        deliveryDatePicker.setValue(null); // Clear the delivery date picker
    }

    private boolean isProductExists(int id) {
        try {
            establishConnection(); // Establish the database connection
            String sql = "SELECT COUNT(*) FROM productstbl_abdullah_hassan WHERE ID = ?";
            preparedStatement = connection.prepareStatement(sql); // Create a prepared statement
            preparedStatement.setInt(1, id); // Set the ID parameter in the query
            resultSet = preparedStatement.executeQuery(); // Execute the query
            if (resultSet.next()) {
                int count = resultSet.getInt(1); // Retrieve the count from the result set
                return count > 0; // Return true if count is greater than 0, indicating that the product exists
            }
        } catch (SQLException e) {
            System.err.println("Error checking if product exists.");
            e.printStackTrace();
        } finally {
            closeConnection(); // Close the database connection
        }
        return false; // Return false if an exception occurs or no result is found
    }

    private void establishConnection() {
        String user = "root"; // Username for the database
        String password = "marmar07"; // Password for the database
        String url = "jdbc:mysql://localhost:3306/productsdb_alghamdi_almaajeeni"; // URL for the database

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load the MySQL JDBC driver
            connection = DriverManager.getConnection(url, user, password); // Establish the database connection
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Connection not working."); // Handle any exceptions that occur during the connection process
        }
    }

    private void closeConnection() {
        try {
            if (connection != null && preparedStatement != null && resultSet != null) {
                connection.close(); // Close the database connection
                preparedStatement.close(); // Close the prepared statement
                resultSet.close(); // Close the result set
            }
        } catch (SQLException ex) {
            // Handle any SQL exceptions that occur during the closing of resources
            // You might consider logging or displaying an error message here
        }
    }

}
