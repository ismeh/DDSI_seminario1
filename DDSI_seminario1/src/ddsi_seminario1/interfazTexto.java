package ddsi_seminario1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static ddsi_seminario1.DDSI_seminario1.ANSI_RED;
import static ddsi_seminario1.DDSI_seminario1.ANSI_RESET;
import static ddsi_seminario1.FuncionesSQL.addPedido;
import static ddsi_seminario1.FuncionesSQL.consultaTabla;
import static ddsi_seminario1.FuncionesSQL.borradoYCreacion;

public class interfazTexto {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    static void eligeOpcion() throws SQLException {
        System.out.println("Bienvenido a SQL elija una opción para realizar:");

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
                    borradoYCreacion();
                    break;
                case '2':
                    interfazAltaPedido();
                    break;
                case '3':
                    String tabla = interfazElijaTabla();
                    consultaTabla(conexionBD.getConexion(), tabla);
                    break;
                case '4':
                    conexionBD.cerrarConexion();
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

    static void interfazAltaPedido() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHa elegido dar de alta un nuevo pedido");

        String fechaPedido;
        int codCliente, codPedido;

        System.out.println("\tIntroduzca el código del pedido");
        codPedido = scanner.nextInt();
        System.out.println("\tIntroduzca el código del cliente");
        codCliente = scanner.nextInt();
        System.out.println("\tIntroduzca la fecha del pedido (Formato: DD-MM-YYYY)");
        fechaPedido = scanner.next();

        addPedido(codPedido, codCliente,fechaPedido);

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
    }

    static String interfazElijaTabla(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleccione la tabla que mostrar.");

        System.out.println("Opciones:");
        System.out.println("\t(1) STOCK");
        System.out.println("\t(2) PEDIDOS");
        System.out.println("\t(3) DETALLE-PEDIDOS");
        System.out.println("\t(4) CANCELAR");

        String tabla = "";
        char subOpcion = scanner.next().charAt(0);
        switch (subOpcion){
            case '1':
                tabla = "STOCK";
                break;
            case '2':
                tabla = "PEDIDOS";
                break;
            case '3':
                tabla = "DETALLE-PEDIDOS";
                break;
            case '4':
                break;

            default:
                System.out.println(ANSI_RED + "No ha seleccionado una opción válida. Tenga en cuenta que sólo" +
                        "se valorará el primer dígito que se introduzca.");
                System.out.println(ANSI_RESET);
                break;
        }

        return tabla;
    }
}
