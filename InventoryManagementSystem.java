import javafx.application.Application;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class InventoryManagementSystem extends Application {

    private ObservableList<Product> products = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Management System");

        TableView<Product> table = createProductTable();
        BorderPane borderPane = createBorderPane(table);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private BorderPane createBorderPane(TableView<Product> table) {
        BorderPane borderPane = new BorderPane();

        Button addButton = new Button("Add Product");
        addButton.setOnAction(e -> showAddProductDialog());

        Button sellButton = new Button("Sell Product");
        sellButton.setOnAction(e -> showSellProductDialog(table));

        borderPane.setTop(createMenuBar());
        borderPane.setCenter(table);

        borderPane.setLeft(addButton);
        borderPane.setBottom(sellButton);

        return borderPane;
    }

    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitMenuItem);
        menuBar.getMenus().add(fileMenu);

        return menuBar;
    }

    private TableView<Product> createProductTable() {
        TableView<Product> table = new TableView<>();
        table.setItems(products);

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().addAll(nameColumn, quantityColumn, priceColumn);

        return table;
    }

    private void showAddProductDialog() {
        Dialog<Product> dialog = new Dialog<>();
        dialog.setTitle("Add Product");
        dialog.setHeaderText("Enter Product Details");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        TextField nameField = new TextField();
        TextField quantityField = new TextField();
        TextField priceField = new TextField();

        dialog.getDialogPane().setContent(new VBox(10, new Label("Name:"), nameField, new Label("Quantity:"), quantityField, new Label("Price:"), priceField));

        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                try {
                    String name = nameField.getText();
                    int quantity = Integer.parseInt(quantityField.getText());
                    double price = Double.parseDouble(priceField.getText());
                    return new Product(name, quantity, price);
                } catch (NumberFormatException e) {
                    showAlert("Invalid input. Please enter valid numbers.");
                    return null;
                }
            }
            return null;
        });

        dialog.showAndWait().ifPresent(product -> {
            if (product != null) {
                products.add(product);
            }
        });
    }

    private void showSellProductDialog(TableView<Product> table) {
        Product selectedProduct = table.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            showAlert("Please select a product to sell.");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Sell Product");
        dialog.setHeaderText("Enter quantity to sell:");
        dialog.setContentText("Quantity:");

        dialog.showAndWait().ifPresent(quantityString -> {
            try {
                int quantityToSell = Integer.parseInt(quantityString);

                if (quantityToSell > selectedProduct.getQuantity()) {
                    showAlert("Not enough stock available.");
                    return;
                }

                selectedProduct.setQuantity(selectedProduct.getQuantity() - quantityToSell);
                showAlert("Sold " + quantityToSell + " units of " + selectedProduct.getName());
            } catch (NumberFormatException e) {
                showAlert("Invalid input. Please enter a valid number.");
            }
        });
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class Product {
        private final StringProperty name;
        private final IntegerProperty quantity;
        private final DoubleProperty price;

        public Product(String name, int quantity, double price) {
            this.name = new SimpleStringProperty(name);
            this.quantity = new SimpleIntegerProperty(quantity);
            this.price = new SimpleDoubleProperty(price);
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public int getQuantity() {
            return quantity.get();
        }

        public void setQuantity(int quantity) {
            this.quantity.set(quantity);
        }

        public double getPrice() {
            return price.get();
        }

        public void setPrice(double price) {
            this.price.set(price);
        }
    }
}
