package ui;

import java.io.IOException;
import java.util.List;

import core.Account;
import core.Profile;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import json.ProfileInformationManagement;
import javafx.scene.image.ImageView;

public class BankAppController {

    @FXML
    private AnchorPane header;

    @FXML
    private Label profileName;

    @FXML
    private Label totalBalance;

    @FXML
    private AnchorPane body;

    @FXML
    private GridPane accountsTable;

    @FXML
    private Label spendingAccount;

    @FXML
    private Label savingAccount;

    @FXML
    private Label bsu;

    @FXML
    private AnchorPane spendingTab;

    @FXML
    private AnchorPane paymentsTab;

    @FXML
    private AnchorPane homeTab;

    @FXML
    private AnchorPane savingsTab;

    @FXML
    private AnchorPane profileTab;

    @FXML
    private Button button;

    @FXML
    private Button loginButton;

    @FXML
    private TextField emailInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label loginError;

    @FXML
    private Text signUpButton;

    @FXML
    private ImageView backArrow;

    @FXML
    private TextField fullName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNr;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField passwordConfirm;

    @FXML
    private Button registerButton;

    @FXML
    private Label registerError;

    @FXML
    private Label spendingAccountBalance;

    private static Profile profile;
    private static String currentDir = System.getProperty("user.dir");
    private static final String path = currentDir.substring(0, currentDir.length() - 5)
            + "/core/src/main/java/json/ProfileInformation.json";

    /**
     * Initializes fields based on current page 
     * 
     */

    public void initialize() {
        if (accountsTable != null && profile == null) {
            updateAccounts();
        } else if (accountsTable != null) {
            updateAccounts();
        }
        if (profileName != null) {
            profileName.setText(profile.getName() + "'s Profile");
        }
        if (totalBalance != null) {
            totalBalance.setText(String.valueOf(profile.getTotalBalance()));
        }
        if (spendingAccountBalance != null){
            spendingAccountBalance.setText(String.valueOf(profile.getAccounts().get(0).getBalance()));
        }
    }

    /**
     * initializes the switch process between tabs
     * 
     * @param event
     * @throws IOException
     */

    @FXML
    public void initializeTab(MouseEvent event) throws IOException {
        String tab = ((Label) ((AnchorPane) event.getSource()).getChildren().get(0)).getText();
        if (tab.equals("Home")) {
            tab = "Overview";
        }
        switchTab(tab);

    }

    /**
     * Switches the tab to according to the value given by initializeTab
     * 
     * @param tab
     * @throws IOException
     */

    @FXML
    private void switchTab(String tab) throws IOException {
        Stage primaryStage = (Stage) profileTab.getScene().getWindow();

        primaryStage.setTitle("Bankapp - " + tab);

        FXMLLoader tabLoader = new FXMLLoader(getClass().getResource(tab + ".fxml"));
        Parent tabPane = tabLoader.load();
        Scene tabScene = new Scene(tabPane);

        primaryStage.setScene(tabScene);
        primaryStage.show();
    }

    /**
     * Updates the account-table with the values of the accounts belonging to the profile
     * 
     */

    @FXML
    public void updateAccounts() {
        int count = 0;
        for (Account account : profile.getAccounts()) {
            Label accountName = new Label(account.getName());
            Label accountBalance = new Label(String.valueOf(account.getBalance()));
            if (count == 0) {
                accountsTable.add(accountName, 0, count);
                accountsTable.add(accountBalance, 1, count);

            } else {
                accountsTable.addRow(count);
                accountsTable.add(accountName, 0, count);
                accountsTable.add(accountBalance, 1, count);
            }
            count += 1;
        }
    }

    /**
     * Handles the switch from login page to signup page
     * 
     * @param event
     */

    @FXML
    public void handleSignUpClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) signUpButton.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles mouseclick on the back-arrow
     * 
     * @param event
     */

    @FXML
    public void handleBackArrow(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backArrow.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles mouseclick on the login-button
     * 
     * @param event
     */

    @FXML
    public void handleLoginButton(MouseEvent event) {

        try {
            String email = emailInput.getText();
            String password = passwordInput.getText();
            List<Profile> profiles = ProfileInformationManagement.readFromFile(path);
            profile = profiles.stream()
                    .filter(profile -> profile.getPassword().equals(password) && profile.getEmail().equals(email))
                    .findFirst().orElseThrow(() -> new Exception("Invalid email or password"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Overview.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            loginError.setText(e.getMessage());
        }
    }

    /**
     * Handles logic for registering new profile
     * 
     */

    @FXML
    public void register() {
        if (password.getText().equals(passwordConfirm.getText())) {
            try {
                if (alreadyRegistered()){
                    throw new IllegalArgumentException("Account already registered");
                }
                profile = new Profile(fullName.getText(), email.getText(), phoneNr.getText(), password.getText());
                Account account = new Account("Spendings account", profile);
                account.add(100);
                profile.addAccount(account);
                ProfileInformationManagement.writeInformationToFile(profile, path);

                Stage primaryStage = (Stage) registerButton.getScene().getWindow();

                primaryStage.setTitle("Bankapp - Overview");

                FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("Overview.fxml"));
                Parent tabPane = tabLoader.load();
                Scene tabScene = new Scene(tabPane);

                primaryStage.setScene(tabScene);
                primaryStage.show();
            } catch (Exception e) {
                registerError.setText(e.getMessage());
            }
        } else {
            registerError.setText("Passwords do not match");
        }
    }

    /**
     * checks if new registered profile already exists or not
     * 
     * @return if profile already exists
     */

    private boolean alreadyRegistered() {
        try {
            List<Profile> profiles = ProfileInformationManagement.readFromFile(path);
            for (Profile profile : profiles) {
                if (profile.getEmail().equals(email.getText()) || profile.getTlf().equals(phoneNr.getText())) {
                    return true;
                }
            }
            return false;
            
        } catch (Exception e) {
           return false;
        }
    }
}
