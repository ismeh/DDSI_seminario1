/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddsi_seminario1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.util.Properties;
import java.util.Scanner;

import static ddsi_seminario1.FuncionesSQL.addPedido;
import static ddsi_seminario1.interfazTexto.eligeOpcion;

public class DDSI_seminario1 {

    static String url = "jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    //Nombre usuario y password
    static String usuario = "x";
    static String password = "y";

    public static void main(String[] args) throws SQLException {
        //
        Properties properties = new Properties();
        try {
            Path path = Paths.get("passwd.properties");
            String util = path.toAbsolutePath().toString();
            properties.load(new FileInputStream(util));
            usuario = properties.get("db.user").toString();
            password = properties.get("db.passwd").toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        conexionBD conexion;

        if( (conexion = new conexionBD(url,usuario,password)) != null ){
            System.out.println("Bienvenido a SQL elija una opción para realizar:");

            eligeOpcion();

            conexionBD.cerrarConexion();
        }
        else
            System.out.println("Ha habido un error a la hora de conectarse a la base de datos. Por favor compruebe su red.");
    }
}


