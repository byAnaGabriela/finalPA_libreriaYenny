package dll;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Conexion {
    private static String URL = "jdbc:mysql://localhost:3306/libreria_yenny";
    private static String USER = "root";
    private static String PASSWORD = "";

    private static Connection connection;
    private static Conexion instance;

    private Conexion() {
        try{
            connection = (Connection) DriverManager.getConnection(URL,USER, PASSWORD);
            System.out.println("Conectado");
        } catch (SQLException e) {
            System.out.println("No se pudo establecer la conexión");
        }
    }

    public static Conexion getInstance(){
        if(instance == null){
            instance = new Conexion();
        }
        return instance;
    }
    public Connection getConnection(){
        return connection;
    }

}
