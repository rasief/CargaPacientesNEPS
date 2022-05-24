package co.observatorio.db;

import co.observatorio.entidad.ListaDetalle;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase para el manejo de la tabla listas y su detalle
 * @author Feisar Moreno
 * @date 28/08/2018
 */
public class DbListas extends DbConexion {
    
    /**
     * Método privado que realiza una consulta sql y retorna un ArrayList de registros de lista_detalle.
     * @author Feisar Moreno
     * @date 28/08/2018
     * @param conectado Indicador de conexión a base de datos existente
     * @param sql Consulta SQL a ejecutar
     * @return <code>ArrayList</code> que cumplen con la consulta SQL.
     * @throws SQLException
     */
    private ArrayList<ListaDetalle> getListaListasDetalle(boolean conectado, String sql) throws SQLException {
        try {
            if (!conectado) {
                crearConexion();
            }
            pstm = conn.prepareStatement(sql);
            rst = pstm.executeQuery();
            
            ArrayList<ListaDetalle> listaListasDetalle = new ArrayList<>();
            while (rst.next()) {
                ListaDetalle listaDetalleAux = new ListaDetalle(rst);
                listaListasDetalle.add(listaDetalleAux);
            }
            rst.close();
            
            return listaListasDetalle;
        } finally {
            if (!conectado) {
                cerrarConexion();
            }
        }
    }
    
    /**
     * Método que retorna un ArrayList de registros de listas_detalle.
     * @author Feisar Moreno
     * @date 28/08/2018
     * @param conectado Indicador de conexión a base de datos existente
     * @param idLista Identificador de la lista
     * @return <code>ArrayList</code> con todos los registros de valores KNet.
     */
    public ArrayList<ListaDetalle> getListaListasDetalle(boolean conectado, long idLista) {
        try {
            String sql =
                    "SELECT * FROM listas_detalle " +
                    "WHERE id_lista=" + idLista + " " +
                    "ORDER BY orden";
            
            return getListaListasDetalle(conectado, sql);
        } catch (SQLException e) {
            System.out.println(e.toString());
            return new ArrayList<>();
        }
    }
    
}
