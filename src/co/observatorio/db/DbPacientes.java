package co.observatorio.db;

import co.observatorio.entidad.Paciente;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase para el manejo de la tabla pacientes
 * @author Feisar Moreno
 * @date 28/08/2018
 */
public class DbPacientes extends DbConexion {
    
    /**
     * Método que crea o actualiza los pacientes de acuerdo al contenido de la tabla tmp_pacientes
     * @author Feisar Moreno
     * @date 04/09/2018
     * @param conectado Indicador de conexión a base de datos existente
     * @return <code>true</code> si se pudieron crear y actualizar los pacientes, de lo contrario <code>false</code>.
     */
    public boolean crearEditarPacientes(boolean conectado) {
        try {
            if (!conectado) {
                crearConexion();
            }
            
            String sql =
                    "INSERT IGNORE INTO pacientes " +
                    "(id_tipo_documento, numero_documento, nombre_1, nombre_2, apellido_1, apellido_2, sexo, fecha_nacimiento, " +
                    "id_pais, cod_dep, cod_mun, direccion, telefono_1, id_convenio_paciente, tipo_coti_paciente, nombre_sede, " +
                    "cod_dep_res, cod_mun_res, direccion_res) " +
                    "SELECT id_tipo_doc, numero_doc, nombre_1, nombre_2, apellido_1, apellido_2, sexo, fecha_nac, " +
                    "id_pais, cod_dep_res, cod_mun_res, direccion, telefono, id_convenio_paciente, tipo_coti_paciente, nombre_sede, " +
                    "cod_dep_res, cod_mun_res, direccion " +
                    "FROM temporal_pacientes";
            
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.execute();
            }
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            if (!conectado) {
                cerrarConexion();
            }
        }
    }
    
    /**
     * Método que crea una lista de pacientes temporales
     * @author Feisar Moreno
     * @date 04/09/2018
     * @param conectado Indicador de conexión a base de datos existente
     * @param listaPacientes <code>ArrayList</code> con los pacientes
     * @return <code>true</code> si se pudieron crear los pacientes, de lo contrario <code>false</code>.
     */
    public boolean crearTemporalPacientes(boolean conectado, ArrayList<Paciente> listaPacientes) {
        try {
            if (!conectado) {
                crearConexion();
            }
            
            String sql =
                    "INSERT IGNORE INTO temporal_pacientes " +
                    "(id_tipo_doc, numero_doc, nombre_1, nombre_2, apellido_1, apellido_2, sexo, fecha_nac, id_pais, " +
                    "cod_dep_res, cod_mun_res, direccion, telefono, id_convenio_paciente, tipo_coti_paciente, nombre_sede) " +
                    "VALUES ";
            
            boolean indSeparador = false;
            for (Paciente pacienteAux : listaPacientes) {
                String nombre2;
                if (!pacienteAux.getNombre2().equals("")) {
                    nombre2 = "'" + pacienteAux.getNombre2() + "'";
                } else {
                    nombre2 = "NULL";
                }
                String apellido2;
                if (!pacienteAux.getApellido2().equals("")) {
                    apellido2 = "'" + pacienteAux.getApellido2() + "'";
                } else {
                    apellido2 = "NULL";
                }
                String fechaNacAux;
                if (!pacienteAux.getFechaNac().equals("")) {
                    fechaNacAux = "STR_TO_DATE('" + pacienteAux.getFechaNac() + "', '%d/%m/%Y')";
                } else {
                    fechaNacAux = "NULL";
                }
                String direccion;
                if (!pacienteAux.getDireccion().equals("")) {
                    direccion = "'" + pacienteAux.getDireccion() + "'";
                } else {
                    direccion = "NULL";
                }
                String telefono;
                if (!pacienteAux.getTelefono().equals("")) {
                    telefono = "'" + pacienteAux.getTelefono() + "'";
                } else {
                    telefono = "NULL";
                }
                sql += (indSeparador ? ", " : "") +
                        "(" + pacienteAux.getIdTipoDoc() + ", '" + pacienteAux.getNumeroDoc() + "', '" + pacienteAux.getNombre1() + "', " +
                        nombre2 + ", '" + pacienteAux.getApellido1() + "', " + apellido2 + ", '" + pacienteAux.getSexo() + "', " +
                        fechaNacAux + ", " + pacienteAux.getIdPais() + ", '" + pacienteAux.getCodDepRes() + "', '" + pacienteAux.getCodMunRes() + "', " +
                        direccion + ", " + telefono + ", " + pacienteAux.getIdConvenio() + ", " +
                        pacienteAux.getTipoCotiPaciente() + ", '" + pacienteAux.getNombreSede() + "')";
                indSeparador = true;
            }
            
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.execute();
            }
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            if (!conectado) {
                cerrarConexion();
            }
        }
    }
    
    /**
     * Método que borra la tabla temporal de pacientes
     * @author Feisar Moreno
     * @date 04/09/2018
     * @param conectado Indicador de conexión a base de datos existente
     * @return <code>true</code> si se pudo realizar el borrado, de lo contrario <code>false</code>.
     */
    public boolean borrarTemporalPacientes(boolean conectado) {
        try {
            if (!conectado) {
                crearConexion();
            }
            
            String sql =
                    "DELETE FROM temporal_pacientes";
            
            try (CallableStatement cstmt = conn.prepareCall(sql)) {
                cstmt.execute();
            }
            
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally {
            if (!conectado) {
                cerrarConexion();
            }
        }
    }
    
}
