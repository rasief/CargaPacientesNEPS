package co.observatorio.entidad;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa un registro de la tabla listas_detalle
 * @author Feisar Moreno
 * @date 28/08/2018
 */
public class ListaDetalle {
    private long idDetalle;
    private long idLista;
    private String codigoDetalle;
    private String nombreDetalle;
    private int orden;
    
    public ListaDetalle() {
    }
    
    public ListaDetalle(ResultSet rs) {
        try {
            this.idDetalle = rs.getLong("ID_DETALLE");
        } catch (SQLException e) {
            this.idDetalle = 0L;
        }
        
        try {
            this.idLista = rs.getLong("ID_LISTA");
        } catch (SQLException e) {
            this.idLista = 0L;
        }
        
        try {
            this.codigoDetalle = rs.getString("CODIGO_DETALLE");
        } catch (SQLException e) {
            this.codigoDetalle = "";
        }
        
        try {
            this.nombreDetalle = rs.getString("NOMBRE_DETALLE");
        } catch (SQLException e) {
            this.nombreDetalle = "";
        }
        
        try {
            this.orden = rs.getInt("ORDEN");
        } catch (SQLException e) {
            this.orden = 0;
        }
    }
    
    public long getIdDetalle() {
        return this.idDetalle;
    }
    
    public void setIdDetalle(long idDetalle) {
        this.idDetalle = idDetalle;
    }
    
    public long getIdLista() {
        return this.idLista;
    }
    
    public void setIdLista(long idLista) {
        this.idLista = idLista;
    }
    
    public String getCodigoDetalle() {
        return this.codigoDetalle;
    }
    
    public void setCodigoDetalle(String codigoDetalle) {
        this.codigoDetalle = codigoDetalle;
    }
    
    public String getNombreDetalle() {
        return this.nombreDetalle;
    }
    
    public void setNombreDetalle(String nombreDetalle) {
        this.nombreDetalle = nombreDetalle;
    }
    
    public int getOrden() {
        return this.orden;
    }
    
    public void setOrden(int orden) {
        this.orden = orden;
    }
    
    @Override
    public String toString() {
        return this.nombreDetalle;
    }
    
}
