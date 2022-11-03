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

    /**
     * @param args the command line arguments
     */

    static String nombre_servidor = "oracle0.ugr.es";
    //numero del puerto
    static String numero_puerto = "1521";
    //SID
    static String sid = "practbd.oracle0.ugr.es"; //practbd.oracle0.ugr.es
    //URL "jdbc:oracle:thin:@nombreServidor:numeroPuerto:SID"
    static String url = "jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es";

    //Nombre usuario y password
    static String usuario = "x8004699";
    static String password = "x8004699";
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
