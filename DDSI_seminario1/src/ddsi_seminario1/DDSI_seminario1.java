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
    public static void main(String[] args) throws SQLException {
        conexionBD.iniciarConexion();
        Connection conexion = conexionBD.getConexion();

        //Tiempo limite de 3 segundos para comprobar si la conexión es valida
        if( conexion.isValid(3) ){
            System.out.println("Bienvenido a SQL elija una opción para realizar:");

            eligeOpcion();

            conexionBD.cerrarConexion();
        }
        else
            System.out.println("Ha habido un error a la hora de conectarse a la base de datos. Por favor compruebe su red.");
    }
}


