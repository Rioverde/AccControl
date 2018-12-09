package sample;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Main extends Application {

    public Label Denied;
    public Label DataInformer;


    public TableView<UsersData> table = new TableView<UsersData>();
    ObservableList<UsersData> UsersDataList = FXCollections.observableArrayList();

    public int UserID;

    static BorderPane sceneHomepage;
    static BorderPane sceneDatapage;

    TextField textField;
    PasswordField passwordField;

    Stage primaryStage;

    Scene homeScene;
    Scene DataScene;


    @Override
    public void start(Stage NewStage) throws Exception
    {

        primaryStage = NewStage;

        //TextFields
        textField = new TextField();
        textField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        //login button
        Button Login = new Button("Login");
        Login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                login();

            }
        });

        Denied = new Label(" ");
        Denied.setTextFill(Color.web("#009900"));
        VBox Fields = new VBox(textField, passwordField, Denied);
        Denied.setPrefWidth(260);
        Denied.setAlignment(Pos.CENTER);

        //New User button

        Button Register = new Button("Add User");
        Register.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {

                String NewUsername = textField.getText().toString();
                String NewPassword = passwordField.getText().toString();

                Denied.setText("User "+NewUsername+" added");



                DataBase DataBaseController = new DataBase();
                try {

                    Statement statement = DataBaseController.DataBaseController();
                    statement.executeUpdate("INSERT INTO `AllUsers` (`Username`, `Password`) VALUE ('" + NewUsername + "','" + NewPassword + "')");
                }


                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                }
        });


        sceneHomepage = new BorderPane();
        primaryStage.setTitle("AccControl");



        Fields.setSpacing(10);
        // v1-height v2-size of object //v3-nothing//v4- width
        Fields.setPadding(new Insets(40, 30, 0, 30));
        BorderPane.setAlignment(Fields, Pos.CENTER);


        //Special Boxes(vBox, hBox) for positions of objects
        HBox LoginRegister = new HBox(Login, Register);
        LoginRegister.setSpacing(30);
        LoginRegister.setPadding(new Insets(8, 5, 0, 67));
        BorderPane.setAlignment(LoginRegister, Pos.BOTTOM_CENTER);


        //BorderPane for our Objects
        sceneHomepage.setTop(Fields);
        sceneHomepage.setCenter(LoginRegister);

        homeScene = new Scene(sceneHomepage, 300, 200);

        primaryStage.setScene(homeScene);
        primaryStage.show();


        table.setItems(UsersDataList);

        TableColumn<UsersData, String> Account = new TableColumn<UsersData, String>("Account");
        TableColumn<UsersData, String> User = new TableColumn<UsersData, String>("Username");
        TableColumn<UsersData, String> Psw = new TableColumn<UsersData, String>("Password");

        Account.setPrefWidth(186);
        User.setPrefWidth(186);
        Psw.setPrefWidth(186);

        Account.setCellValueFactory(new PropertyValueFactory<UsersData, String>("Account"));
        User.setCellValueFactory(new PropertyValueFactory<UsersData, String>("Username"));
        Psw.setCellValueFactory(new PropertyValueFactory<UsersData, String>("Password"));



        table.getColumns().addAll(Account, User, Psw);


        }

        //The 'login' Function
        void login()
        {

            sceneDatapage = new BorderPane();

            String Username = textField.getText().toString();
            String Password = passwordField.getText().toString();

            //Checking the Username and Password from Database

            if (Username != null && !Username.matches("") && Password != null && !Password.matches(""))
            {

                DataBase DataBaseController = new DataBase();
                try
                {

                    Statement statement = DataBaseController.DataBaseController();
                    ResultSet resultSet;
                    resultSet = statement.executeQuery("SELECT *FROM `AllUsers` WHERE `Username` = '"+Username+"' AND `Password` = '"+Password+"'");


                    if (resultSet.next())
                    {
                        //Opens a new Data Page
                        //---------------------------------
                        System.out.println("Access Approved");
                        String Name = resultSet.getString("Username");

                        UserID = resultSet.getInt("ID");

                        primaryStage.setTitle(Name);


                        Button ExitButton = new Button("Exit");
                        ExitButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event)
                            {
                                primaryStage.close();
                            }

                        });



                        Button BackButton = new Button("Back");
                        BackButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event)
                            {
                                textField.clear();
                                passwordField.clear();
                                Denied.setText(" ");
                                UsersDataList.clear();
                                RunScene(homeScene);

                            }

                        });


                        TextField DeleteAccount = new TextField();
                        DeleteAccount.setPromptText("Account Name");


                        Button Delete = new Button("Delete");
                        Delete.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {

                                String DropAccount = DeleteAccount.getText();
                                DataBase DataBaseController = new DataBase();
                                    try
                                    {
                                        Statement statement = DataBaseController.DataBaseController();
                                        statement.executeUpdate("DELETE FROM `UsersData` WHERE  `Accounts` = '" + DropAccount + "' AND `UserID` = '" + UserID + "' ");

                                    }

                                    catch (SQLException e)
                                    {
                                        e.printStackTrace();
                                    }

                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                    }

                                DataInformer.setText("Account Deleted");

                                }

                        });





                        Button Clear = new Button("Clear All Data");
                        Clear.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                DataBase DataBaseController = new DataBase();
                                try
                                {
                                    Statement statement = DataBaseController.DataBaseController();
                                    statement.executeUpdate("DELETE FROM `UsersData` WHERE  `UserID` = '" + UserID + "' ");
                                }

                                catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }

                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }

                                DataInformer.setText("All Data Deleted");
                            }
                        });



                        DataInformer = new Label(" ");
                        DataInformer.setTextFill(Color.web("#009900"));
                        DataInformer.setPrefWidth(260);
                        DataInformer.setAlignment(Pos.CENTER);



                        TextField addAccount = new TextField();
                            addAccount.setPromptText("Account");
                        TextField addUsername = new TextField();
                            addUsername.setPromptText("Username");
                        TextField addPassword = new TextField();
                            addPassword.setPromptText("Password");

                            try
                            {
                                Statement statement_add = DataBaseController.DataBaseController();
                                ResultSet NewResultSet;
                                NewResultSet = statement_add.executeQuery("SELECT *FROM `UsersData` WHERE `UserID` = '" + UserID + "'");
                                while (NewResultSet.next())
                                {
                                    UsersData usersData = new UsersData
                                                    (NewResultSet.getString("Accounts"),
                                                    NewResultSet.getString("Login"),
                                                    NewResultSet.getString("Password"));

                                    UsersDataList.add(usersData);

                                }


                            }

                        catch (SQLException e)
                            {
                                e.printStackTrace();
                            }

                        catch (Exception e)
                            {
                                e.printStackTrace();
                            }



                            Button addButton = new Button("Add");
                            addButton.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent a)
                            {

                                DataBase DataBaseController = new DataBase();
                                try {

                                    String AccountInfo = addAccount.getText();
                                    String UsernameInfo = addUsername.getText();
                                    String PasswordInfo = addPassword.getText();

                                    Statement statement = DataBaseController.DataBaseController();
                                    statement.executeUpdate("INSERT INTO `UsersData` (`UserID`, `Accounts` , `Login` , `Password`) VALUE ('" + UserID + "','" + AccountInfo + "','" + UsernameInfo + "', '" + PasswordInfo + "')");
                                }


                                catch (ClassNotFoundException e)
                                {
                                    e.printStackTrace();
                                }

                                catch (SQLException e)
                                {
                                    e.printStackTrace();
                                }

                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }


                                //---------------------//

                                // adding info in table
                                //===========================
                                String AccountInfo = addAccount.getText();
                                String UsernameInfo = addUsername.getText();
                                String PasswordInfo = addPassword.getText();

                                UsersData usersData = new UsersData(AccountInfo,UsernameInfo,PasswordInfo);
                                UsersDataList.add(usersData);
                                //===========================

                                DataInformer.setText("Data Added");
                                addAccount.clear();
                                addUsername.clear();
                                addPassword.clear();
                            }




                        });

                        HBox BackExit = new HBox(BackButton,DataInformer, ExitButton);
                        BackExit.setSpacing(109);
                        sceneDatapage.setPadding(new Insets(0, 0, 0, 0)); //1.top 2.right 3.bottom 4.left
                        GridPane.setValignment(BackExit, VPos.BOTTOM);

                        HBox DeleteMe = new HBox(DeleteAccount,Delete, Clear);
                        DeleteMe.setSpacing(5);
                        sceneDatapage.setPadding(new Insets(0, 10, 10, 10)); //1.top 2.right 3.bottom 4.left
                        BorderPane.setAlignment(DeleteMe, Pos.CENTER);


                        HBox Control = new HBox(addAccount, addUsername, addPassword, addButton);
                        Control.setSpacing(5);
                        Control.setPadding(new Insets(0, 0, 0, 0));
                        BorderPane.setAlignment(Control, Pos.CENTER);

                        VBox DB = new VBox(Control,BackExit);
                        DB.setSpacing(10);

                        VBox Tables = new VBox(table);
                        Tables.setSpacing(5);
                        Tables.setPadding(new Insets(10, 0, 10, 0));


                        sceneDatapage.setBottom(DeleteMe);
                        sceneDatapage.setTop(Tables);
                        sceneDatapage.setCenter(DB);


                        DataScene = new Scene(sceneDatapage, 590, 550);
                        RunScene(DataScene);

                    }

                    else
                        {
                            Denied.setText("Access Denied");
                            Denied.setTextFill(Color.web("#FF0000"));

                            System.out.println("Access Denied");

                            DataInformer = new Label(" ");
                            DataInformer.setTextFill(Color.web("#009900"));
                            DataInformer.setPrefWidth(260);
                            DataInformer.setAlignment(Pos.CENTER);

                        }

                }

                catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }

                catch (SQLException e)
                {
                    e.printStackTrace();
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }


        void RunScene(Scene scene)               //function to get back to scene that you choose
        {
            primaryStage.setScene(scene);
        }

        public static void main(String[] args)
        {
            launch(args);
        }
    }