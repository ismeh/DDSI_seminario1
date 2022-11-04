/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddsi_seminario1;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 *
 * @author Ismael
 */
public class DDSI_seminario1 {

    static String url = "jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es";

    //Nombre usuario y password
    static String usuario = "x";
    static String password = "x";
    public static void main(String[] args) {
        // TODO code application logic here

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection  conexion = DriverManager.getConnection(url,usuario,password);
            System.out.println("Ha entrado");
            conexion.close();
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }

    }

}
