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



public class DDSI_seminario1 {

    static String url = "jdbc:oracle:thin:@//oracle0.ugr.es:1521/practbd.oracle0.ugr.es";

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    //Nombre usuario y password
    static String usuario = "x";
    static String password = "y";
    public static void main(String[] args) {
        // TODO code application logic here
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


        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection  conexion = DriverManager.getConnection(url,usuario,password);

            FuncionesSQL funcionesSQL = new FuncionesSQL();

            int numero=1;
            System.out.println("Bienvenido a SQL elija una opción para realizar:");

            eligeOpcion();

            conexion.close();



        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }

    }

    static void eligeOpcion() {
        char numero='0';
        while (numero != '4') {
            System.out.println("\t(1) Borrado y nueva creación de las tablas e inserción de 10 " +
                    "tuplas predefinidas en el código en la tabla Stock.");

            System.out.println("\t(2) Dar de alta nuevo pedido");
            System.out.println("\t(3) Mostrar contenido de las tablas de la BD");
            System.out.println("\t(4) Salir del programa y cerrar conexión a BD");

            Scanner scanner = new Scanner(System.in);
            System.out.println("Elija una opción:");
            numero = scanner.next().charAt(0);

            switch (numero) {
                case '1':
                    break;
                case '2':
                    System.out.println("Ha elegido dar de alta un nuevo pedido");

                    String fechaPedido;
                    int codCliente, codPedido;

                    System.out.println("\tIntroduzca el código del cliente");
                    codCliente = scanner.nextInt();
                    System.out.println("\tIntroduzca la fecha del pedido (Formato: x-x-x)");
                    fechaPedido = scanner.nextLine();

                    codPedido = addPedido(codCliente,fechaPedido);

                    System.out.println("Opciones:");
                    System.out.println("\t(1) Añadir detalles del producto");
                    System.out.println("\t(2) Eliminar detalles del producto");
                    System.out.println("\t(3) Cancelar pedido");
                    System.out.println("\t(4) Finalizar pedido");
                    char subOpcion = scanner.next().charAt(0);
                    switch (subOpcion){
                        case '1':
                            //...
                            //addDetallesPedido(codPedido);
                            break;
                        case '2':
                            //deleteDetallesPedido(codPedido);
                            break;
                        case '3':
                            //deletePedido(codPedido);
                            break;
                        case '4':
                            //Duda, para hacer los cambios permanentes hace falta añadir detalles al pedido?
                            //guardarCambios();
                            break;
                    }

                    break;
                case '3':
                    break;
                case '4':
                    break;
                default:
                    System.out.println(ANSI_RED + "La tecla introducida no corresponde con ninguna opción válida. " +
                            "Por favor, seleccione una de las teclas que se muestran por pantalla. Tenga en cuenta" +
                            " que sólo se seleccionará la primera letra de la cadena que introduzca.");
                    System.out.println(ANSI_RESET);
                    break;
            }

//                if (numero ==1 || numero == 2 || numero == 3)
            //mostrar contenido bd
        }

    }
}


