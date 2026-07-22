package dao;

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
            connection = DriverManager.getConnection(URL,USER, PASSWORD);
            System.out.println("Conectado a la base de datos");
        } catch (SQLException e) {
            throw  new RuntimeException("No se pudo establecer la conexión a la base de datos", e);
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
