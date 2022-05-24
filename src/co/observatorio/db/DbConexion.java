package co.observatorio.db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Properties;

/**
 * Clase base para la conexión a una base de datos MySQL
 * @author Feisar Moreno
 * @date 26/02/2016
 */
public abstract class DbConexion {
    protected Statement stm = null;
    protected ResultSet rst = null;
    protected PreparedStatement pstm = null;
    protected Connection conn = null;
    
    private String host = "";
    private String port = "";
    private String database = "";
    private String user  = "";
    private String password = "";
    
    /**
     * Método que crea una conexión a una base de datos MySQL
     * @param isAutoCommit Indica si la conexion tiene autocommit
     */
    public void crearConexion(boolean isAutoCommit) {
        LinkedHashMap<String, String> mapaAtributos = DbConexion.getMapaAtributosConexion();
        
        this.host = mapaAtributos.get("host");
        this.port = mapaAtributos.get("port");
        this.database = mapaAtributos.get("database");
        this.user = mapaAtributos.get("user");
        this.password = mapaAtributos.get("password");
        
        this.crearConexion(this.host, this.port, this.user, this.password, this.database, isAutoCommit);
    }
    
    /**
     * Método que crea una conexión con autocommit a una base de datos MySQL
     */
    public void crearConexion() {
        this.crearConexion(true);
    }
    
    private void crearConexion(String host, String port, String user, String password, String database, boolean isAutoCommit) {
        try {
            //Se realiza la conexión a la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            if (database != null && !database.equals("")) {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
            } else {
                conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, user, password);
            }
            conn.setAutoCommit(isAutoCommit);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection exception: [" + e.toString() + "]");
        }
    }
    
    private void crearConexion(String host, String port, String user, String password, boolean isAutoCommit) {
        this.crearConexion(host, port, user, password, null, isAutoCommit);
    }
    
    /**
     * Método que prueba una conexión a un servidor de bases de datos MySQL
     * @param host Nombre de red o dirección IP del servidor de bases de datos
     * @param port Puerto
     * @param user Nombre de usuario
     * @param password Contraseña
     * @param database Nombre de la base de datos
     * @return Cadena de texto "OK" si se pudo establecer conexión con el servidor de bases de datos, de lo contrario cadena de texto con la excepción generada al tratar de conectar.
     */
    public String verificarConexion(String host, String port, String user, String password, String database) {
        try {
            this.crearConexion(host, port, user, password, true);
            //Se realiza la conexión a la base de datos
            Class.forName("com.mysql.jdbc.Driver");
            if (database != null) {
                this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, user, password);
            } else {
                this.conn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, user, password);
            }
            
            return (this.conn != null) ? "OK" : "Unable to create conection, no error.";
        } catch (ClassNotFoundException | SQLException e) {
            return e.toString();
        }
    }
    
    /**
     * Método que prueba una conexión a un servidor de bases de datos MySQL
     * @param host Nombre de red o dirección IP del servidor de bases de datos
     * @param port Puerto
     * @param user Nombre de usuario
     * @param password Contraseña
     * @return Cadena de texto "OK" si se pudo establecer conexión con el servidor de bases de datos, de lo contrario cadena de texto con la excepción generada al tratar de conectar.
     */
    public String verificarConexion(String host, String port, String user, String password) {
        return this.verificarConexion(host, port, user, password, null);
    }
    
    /**
     * Método que crea una base de datos con los valores dados
     * @param host Nombre de red o dirección IP del servidor de bases de datos
     * @param port Puerto
     * @param user Nombre de usuario
     * @param password Contraseña
     * @param database Nombre de la base de datos
     * @return Cadena de texto "OK" si se creó la base de datos, de lo contrario cadena de texto con la excepción generada.
     */
    public String crearBaseDatos(String host, String port, String user, String password, String database) {
        try {
            this.crearConexion(host, port, user, password, true);
            
            try (CallableStatement cstmt = conn.prepareCall("CREATE DATABASE " + database)) {
                cstmt.execute();
            }
        } catch (SQLException e) {
            return e.toString();
        } finally {
            this.cerrarConexion();
        }
        return "OK";
    }
    
    /**
     * Método que retorna los atributos de conexión a la base de datos MySQL asociada.
     * @return <code>HashMap</code> con los atributos de conexión.
     */
    public static LinkedHashMap<String, String> getMapaAtributosConexion() {
        LinkedHashMap<String, String> mapaAtributos = new LinkedHashMap<>();
        Properties propiedades = new Properties();
        
        FileInputStream is = null;
        try {
            is = new FileInputStream("CargaPacientesNEPS.properties");
        } catch (FileNotFoundException ex) {}
        
        try {
            propiedades.load(is);
        } catch (IOException ex) {}
        
        for (Enumeration e = propiedades.keys(); e.hasMoreElements() ; ) {
            String key = (String)e.nextElement();
            mapaAtributos.put(key, propiedades.getProperty(key));
        }
        
        return mapaAtributos;
    }
    
    public void cerrarConexion() {
        try {
            if (conn != null && !conn.isClosed()) { // if 1
                if (pstm != null) { // if 2
                    pstm.close();
                }
                if (rst != null) { // if 3
                    rst.close();
                }
                if (conn != null) { // if 4
                    conn.close();
                }
            }
        } catch (SQLException e) {
            System.out.println("Close connection exception: [" + e.toString() + "]");
        }
    }
}
