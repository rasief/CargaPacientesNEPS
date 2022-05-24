package co.observatorio.cargapacientesneps;

import co.observatorio.db.DbListas;
import co.observatorio.db.DbPacientes;
import co.observatorio.entidad.ListaDetalle;
import co.observatorio.entidad.Paciente;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Feisar Moreno
 */
public class CargaPacientesNEPS {
    
    private final int NUM_CAMPOS = 33;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CargaPacientesNEPS neps = new CargaPacientesNEPS();
        neps.cargarArchivo();
    }
    
    public void cargarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Archivos de valores separados por comas (*.csv)", "csv"));

        int retornoExport = fileChooser.showOpenDialog(null);
        if (retornoExport == JFileChooser.APPROVE_OPTION) {
            try {
                File archivoPacientes = fileChooser.getSelectedFile();
                System.out.println("Archivo seleccionado: " + archivoPacientes.getAbsolutePath());

                DbListas dbListas = new DbListas();
                DbPacientes dbPacientes = new DbPacientes();

                //Se obtiene el listado de tipos de documento
                ArrayList<ListaDetalle> listaTiposDoc = dbListas.getListaListasDetalle(false, 2);
                HashMap<String, ListaDetalle> mapaTiposDoc = new HashMap<>();
                listaTiposDoc.forEach((detAux) -> {
                    mapaTiposDoc.put(detAux.getOrden() + "", detAux);
                });
                
                //Se borra la tabla temporal de pacientes
                /*boolean indBorrar = dbPacientes.borrarTemporalPacientes(false);
                if (!indBorrar) {
                    throw new Exception("Error interno al tratar de borrar la tabla temporal de pacientes [1].");
                }*/
                
                //Se recorre el archivo y se carga a la base de datos
                try (FileInputStream fis = new FileInputStream(archivoPacientes);
                        BufferedReader br = new BufferedReader(new InputStreamReader(fis, "ISO-8859-1"))) {
                    String linea;
                    int contLineas = 0;
                    ArrayList<Paciente> listaPacientes = new ArrayList<>();
                    while ((linea = br.readLine()) != null) {
                        if (contLineas % 500 == 0 && listaPacientes.size() > 0) {
                            //Se guardan los pacientes acumulados en la lista
                            boolean indCrear = dbPacientes.crearTemporalPacientes(false, listaPacientes);
                            if (!indCrear) {
                                throw new Exception("Error interno al tratar de insertar los pacientes [" + (contLineas + 1) + "].");
                            }
                            
                            listaPacientes = new ArrayList<>();
                        }

                        //La primera línea es de títulos
                        if (contLineas > 167998) {
                            linea += ";";
                            linea = linea.replace("\"", "");
                            linea = linea.replace("'", "");

                            String[] arrCampos = linea.split(";");
                            if (arrCampos.length == this.NUM_CAMPOS) {
                                long idTipoDoc;
                                if (mapaTiposDoc.containsKey(arrCampos[4])) {
                                    try {
                                        idTipoDoc = mapaTiposDoc.get(arrCampos[4]).getIdDetalle();
                                    } catch (NumberFormatException e) {
                                        idTipoDoc = 0;
                                    }
                                } else {
                                    idTipoDoc = 0;
                                }
                                String numeroDoc = arrCampos[5];
                                String nombre1 = arrCampos[8].trim();
                                String nombre2;
                                int posAux = nombre1.indexOf(" ");
                                if (posAux >= 0) {
                                    nombre2 = nombre1.substring(posAux + 1);
                                    nombre1 = nombre1.substring(0, posAux);
                                } else {
                                    nombre2 = "";
                                }
                                String apellido1 = arrCampos[6];
                                String apellido2 = arrCampos[7];
                                int sexo;
                                switch (arrCampos[13].toLowerCase()) {
                                    case "f":
                                        sexo = 1;
                                        break;
                                    case "m":
                                        sexo = 2;
                                        break;
                                    default:
                                        sexo = 0;
                                        break;
                                }
                                String fechaNac = arrCampos[16];
                                long idPais = 1;
                                String codDepRes;
                                String codMunRes;
                                try {
                                    int codDepAux = Integer.parseInt(arrCampos[21]);
                                    int codMunAux = Integer.parseInt(arrCampos[22]);
                                    
                                    codDepRes = "0" + codDepAux;
                                    codDepRes = codDepRes.substring(codDepRes.length() - 2);
                                    
                                    codMunAux += codDepAux * 1000;
                                    codMunRes = ("0" + codMunAux);
                                    codMunRes = codMunRes.substring(codMunRes.length() - 5);
                                } catch (NumberFormatException e) {
                                    codDepRes = "";
                                    codMunRes = "";
                                }
                                String direccion = arrCampos[14];
                                String telefonos = arrCampos[15];
                                long idConvenio = 2;
                                int tipoCotiPaciente;
                                try {
                                    tipoCotiPaciente = Integer.parseInt(arrCampos[9], 10);
                                } catch (NumberFormatException e) {
                                    tipoCotiPaciente = 0;
                                }
                                String nombreSede = arrCampos[1];

                                Paciente pacienteAux = new Paciente(contLineas, idTipoDoc, numeroDoc, nombre1, nombre2, apellido1, apellido2, sexo, fechaNac, idPais, codDepRes, codMunRes, direccion, telefonos, idConvenio, tipoCotiPaciente, nombreSede);
                                listaPacientes.add(pacienteAux);
                            } else {
                                throw new Exception("Error en el número de campos en la linea " + (contLineas + 1) + ".\nSe esperaban " + this.NUM_CAMPOS + ", y se hallaron " + arrCampos.length + ".");
                            }
                        }
                        contLineas++;
                    }
                    
                    if (!listaPacientes.isEmpty()) {
                        boolean indCrear = dbPacientes.crearTemporalPacientes(false, listaPacientes);
                        if (!indCrear) {
                            throw new Exception("Error interno al tratar de insertar los pacientes [" + (contLineas + 1) + "].");
                        }
                    }
                }
                
                //Se mueven los registros de la tabla temporal a la definitiva
                boolean indCrear = dbPacientes.crearEditarPacientes(false);
                if (!indCrear) {
                    throw new Exception("Error interno al mover los datos de la tabla temporal a la tabla definitiva.");
                }
                
                //Se borra la tabla temporal de pacientes
                /*indBorrar = dbPacientes.borrarTemporalPacientes(false);
                if (!indBorrar) {
                    throw new Exception("Error interno al tratar de borrar la tabla temporal de pacientes [2].");
                }*/
                
                JOptionPane.showMessageDialog(null, "Registros cargados con éxito.", "Carga de archivos", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Se ha presentado un error:\n[" + e.getMessage() + "]", "Error en la carga de archivos", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
}
