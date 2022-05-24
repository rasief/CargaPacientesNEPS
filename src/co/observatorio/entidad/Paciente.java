package co.observatorio.entidad;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa un registro de la tabla paciente
 * @author Feisar Moreno
 * @date 04/09/2018
 */
public class Paciente {
    private long idPaciente;
    private long idTipoDoc;
    private String numeroDoc;
    private String nombre1;
    private String nombre2;
    private String apellido1;
    private String apellido2;
    private int sexo;
    private String fechaNac;
    private long idPais;
    private String codDepRes;
    private String codMunRes;
    private String direccion;
    private String telefono;
    private long idConvenio;
    private int tipoCotiPaciente;
    private String nombreSede;
    
    public Paciente() {
    }

    public Paciente(long idPaciente, long idTipoDoc, String numeroDoc, String nombre1, String nombre2, String apellido1, String apellido2, int sexo, String fechaNac, long idPais, String codDepRes, String codMunRes, String direccion, String telefono, long idConvenio, int tipoCotiPaciente, String nombreSede) {
        this.idPaciente = idPaciente;
        this.idTipoDoc = idTipoDoc;
        this.numeroDoc = numeroDoc;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.sexo = sexo;
        this.fechaNac = fechaNac;
        this.idPais = idPais;
        this.codDepRes = codDepRes;
        this.codMunRes = codMunRes;
        this.direccion = direccion;
        this.telefono = telefono;
        this.idConvenio = idConvenio;
        this.tipoCotiPaciente = tipoCotiPaciente;
        this.nombreSede = nombreSede;
    }

    public long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public long getIdTipoDoc() {
        return idTipoDoc;
    }

    public void setIdTipoDoc(long idTipoDoc) {
        this.idTipoDoc = idTipoDoc;
    }

    public String getNumeroDoc() {
        return numeroDoc;
    }

    public void setNumeroDoc(String numeroDoc) {
        this.numeroDoc = numeroDoc;
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public long getIdPais() {
        return idPais;
    }

    public void setIdPais(long idPais) {
        this.idPais = idPais;
    }

    public String getCodDepRes() {
        return codDepRes;
    }

    public void setCodDepRes(String codDepRes) {
        this.codDepRes = codDepRes;
    }

    public String getCodMunRes() {
        return codMunRes;
    }

    public void setCodMunRes(String codMunRes) {
        this.codMunRes = codMunRes;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public long getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(long idConvenio) {
        this.idConvenio = idConvenio;
    }

    public int getTipoCotiPaciente() {
        return tipoCotiPaciente;
    }

    public void setTipoCotiPaciente(int tipoCotiPaciente) {
        this.tipoCotiPaciente = tipoCotiPaciente;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }
    
        
}
