/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ddsi_seminario1;

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
    public static void main(String[] args) {
        System.out.println("Todo correcto");
        try
        {
            //Se carga el driver JDBC
            DriverManager.registerDriver( new oracle.jdbc.driver.OracleDriver() );
             
            //nombre del servidor
            String nombre_servidor = "oracle0.ugr.es";
            //numero del puerto
            String numero_puerto = "1521";
            //SID
            String sid = "practbd.oracle0.ugr.es";
            //URL "jdbc:oracle:thin:@nombreServidor:numeroPuerto:SID"
            String url = "jdbc:oracle:thin:@" + nombre_servidor + ":" + numero_puerto + ":" + sid;
 
            //Nombre usuario y password
            String usuario = "DBAP1";
            String password = "proyecto1";
 
            //Obtiene la conexion
            Connection conexion = DriverManager.getConnection( url, usuario, password );
             
            //Para realiza una consulta
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery( "SELECT * FROM TIPO_OPERACION" );
             
            //Se recorre el resultado obtenido
            while ( resultado.next() )
            {
                //Se imprime el resultado colocando
                //Para obtener la primer columna se coloca el numero 1 y para la segunda columna 2 el numero 2
                System.out.println ( resultado.getInt( 1 ) + "\t" + resultado.getString( 2 ) );
            }
             
            //Cerramos la sentencia
            sentencia.close();
        }catch( Exception e ){
            e.printStackTrace();
        }
    }
    
}
