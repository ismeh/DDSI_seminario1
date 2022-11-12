package ddsi_seminario1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import static ddsi_seminario1.FuncionesSQL.*;

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
        }
    }

    static void interfazAltaPedido() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHa elegido dar de alta un nuevo pedido");

        String fechaPedido;
        int codCliente, codPedido = 0;
        boolean valido = false;

        do {
            try {
                System.out.println("\tIntroduzca el código del pedido");
                codPedido = scanner.nextInt();
                System.out.println("\tIntroduzca el código del cliente");
                codCliente = scanner.nextInt();
                System.out.println("\tIntroduzca una fecha válida para el pedido (Formato: DD-MM-YYYY)");
                fechaPedido = scanner.next();

                addPedido(codPedido, codCliente, fechaPedido);
                valido = true;
            } catch (SQLException e) {
                int codigoerror = e.getErrorCode();

                if(codigoerror == 1830)
                    System.out.println("\nFecha incorrecta.");
                else if (codigoerror == 1)
                    System.out.println("\nHas introducido un código que ya existe. Por favor, introduce otro código.");
                else
                    System.out.println(e.getCause().toString());

                System.out.println("\nIntroduzca datos válidos para el pedido.");
                valido = false;
            }
        }while(!valido);

        char subOpcion;
        boolean continuar = true;

        do {
            System.out.println("Opciones:");
            System.out.println("\t(1) Añadir detalles del producto");
            System.out.println("\t(2) Eliminar detalles del producto");
            System.out.println("\t(3) Cancelar pedido");
            System.out.println("\t(4) Finalizar pedido");
            subOpcion = scanner.next().charAt(0);
            switch (subOpcion) {
                case '1':
                    int Cproducto, Cantidad;
                    boolean valid = false;

                    do {
                        try {
                            System.out.println("\tIntroduzca el código del producto");
                            Cproducto = scanner.nextInt();
                            System.out.println("\tIntroduzca la cantidad del pedido");
                            Cantidad = scanner.nextInt();
                            addDetallePedido(codPedido, Cproducto, Cantidad);

                            consultaTabla(conexionBD.getConexion(), "DETALLEPEDIDOS");
                            valid = true;
                        } catch (SQLException e) {
                            int cod_error = e.getErrorCode();
                            if (cod_error == 2291)
                                System.out.println("\nEl código del producto no está disponible en el stock. Por favor introduzca otro código: ");
                            else if (cod_error == 2290)
                                System.out.println("\nLa cantidad introducida es ínvalida, debe ser superior a 0. Por favor introduzca otra cantidad correcta: ");
                            else
                                System.out.println(e.getCause().toString());

                            valid = false;
                        }
                    } while (!valid);

                    break;
                case '2':
                    try {
                        deleteDetallesPedido(conexionBD.getConexion(), codPedido);
                        consultaTabla(conexionBD.getConexion(), "DETALLEPEDIDOS");
                    }catch (SQLException e){
                        System.out.println(e.toString());
                    }
                    break;
                case '3':
                    try {
                        deletePedido(conexionBD.getConexion(), codPedido);
                        consultaTabla(conexionBD.getConexion(), "DETALLEPEDIDOS");
                        consultaTabla(conexionBD.getConexion(), "PEDIDOS");
                        continuar = false;
                    }catch (SQLException e){
                        System.out.println(e.toString());
                    }
                    break;
                case '4':
                    guardarCambios(conexionBD.getConexion());
                    continuar = false;
                    break;
            }

        }while(continuar);
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
                tabla = "DETALLEPEDIDOS";
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
