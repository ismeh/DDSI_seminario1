package ddsi_seminario1;

import java.sql.*;

public class FuncionesSQL {


    private static int contadorPedidos = 0;


    static void borradoYCreacion() throws SQLException {
        Connection conexion = conexionBD.getConexion();
        borrado(conexion);
        creacion(conexion);
    }
    static void borrado(Connection conexion) throws SQLException {


        DatabaseMetaData metaDatos = conexion.getMetaData();
        String tablaStock = "STOCK";
        String tablaPedido = "PEDIDOS"; // PEDIDOS CON S PQ PEDIDO "EXISTE" SEGUN RSDATOS
        String tablaDetallePedido = "DETALLEPEDIDOS";

        ResultSet rsDetallePedido = metaDatos.getTables(null, null, tablaDetallePedido, null);
        if (rsDetallePedido.next())
            eliminaTabla(conexion, tablaDetallePedido);
        ResultSet rsStock = metaDatos.getTables(null, null, tablaStock, null);
        if (rsStock.next())
            eliminaTabla(conexion, tablaStock);

        ResultSet rsPedido = metaDatos.getTables(null, null, tablaPedido, null);
        if (rsPedido.next())
            eliminaTabla(conexion, tablaPedido);



    }

    static void eliminaTabla(Connection conexion, String nombreTabla) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement("DROP TABLE " + nombreTabla);
        stmt.execute();
        stmt.close();
        stmt =null;
        System.out.println( nombreTabla +" table --> ELIMINADA");
    }


    static void creacion(Connection conexion) throws SQLException {
        String creacionTablaStock= "CREATE TABLE STOCK (Cproducto int PRIMARY KEY NOT NULL,"+
                                    " Cantidad int)";
        String creacionTablaPedidos = "CREATE TABLE PEDIDOS (CPedido int PRIMARY KEY NOT NULL," +
                                    "Ccliente int, FechaPedido DATE)";
        String creacionTablaDetallePedidos = "CREATE TABLE DETALLEPEDIDOS (CPedido int," +
                "Cproducto int, Cantidad int, PRIMARY KEY (Cpedido,CProducto), FOREIGN KEY (CProducto) "+
                "REFERENCES STOCK(Cproducto), FOREIGN KEY (CPedido) REFERENCES PEDIDOS(Cpedido)) ";
        crearTabla(conexion, creacionTablaStock);
        crearTabla(conexion, creacionTablaPedidos);
        crearTabla(conexion, creacionTablaDetallePedidos);

    }
    static void crearTabla(Connection conexion, String creacionTabla) throws SQLException {
        PreparedStatement stmt = null;
        stmt = conexion.prepareStatement(creacionTabla);
        stmt.execute();
        stmt.close();
    }



    public static int addPedido(int codPedido, int codCliente, String fechaPedido) throws SQLException {

        //Insertar datos en la tabla
        PreparedStatement stmt = null;
        stmt =  conexionBD.getConexion().prepareStatement("INSERT INTO PEDIDOS VALUES (?,?,?)");
        stmt.setInt(1,codPedido);
        stmt.setInt(2,codCliente);
        stmt.setString(3,fechaPedido);
        stmt.executeUpdate();

        return codPedido;
    }


}
