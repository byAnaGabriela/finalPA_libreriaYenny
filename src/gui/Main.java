package gui;

import dll.Conexion;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {

            Connection conexion = Conexion.getInstance().getConnection();

    }
}
