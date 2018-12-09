package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class DataBase
{

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public Statement DataBaseController () throws ClassNotFoundException, SQLException
    {
        // This will load the MySQL driver, each DB has its own driver
        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://localhost/AccControl?autoReconnect=true&useSSL=false";
        // Setup the connection with the DB
        connect = DriverManager.getConnection(connectionURL, "root", "timelapse1403");
        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();

        return statement;
    }
}
