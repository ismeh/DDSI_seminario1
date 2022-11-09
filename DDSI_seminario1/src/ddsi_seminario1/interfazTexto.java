package ddsi_seminario1;

import java.util.Scanner;


import static ddsi_seminario1.FuncionesSQL.addPedido;

public class interfazTexto {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    static void eligeOpcion() {
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
                    break;
                case '2':
                    interfazAltaPedido();
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

    static void interfazAltaPedido(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nHa elegido dar de alta un nuevo pedido");

        String fechaPedido;
        int codCliente, codPedido;

        System.out.println("\tIntroduzca el código del cliente");
        codCliente = scanner.nextInt();
        System.out.println("\tIntroduzca la fecha del pedido (Formato: x-x-x)");
        fechaPedido = scanner.nextLine();

        codPedido = addPedido(codCliente,fechaPedido);

        System.out.println("\nOpciones:");
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
}
