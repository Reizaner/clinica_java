/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package datos;

import java.sql.Connection;

/**
 *
 * @author Usuario
 */
public class Conexion {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String SERVIDOR = "localhost";
    static final String DB = "clinica";
    static final String PUERTO = "3307";
    static final String USUARIO = "pablo";
    static final String PWS = "12345678a";
    static final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + DB + "?" + "user=" + USUARIO + "&password=" + PWS;

    protected Connection cn;
}
