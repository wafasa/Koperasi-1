/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import tubes.Admin;

/**
 *
 * @author reza
 */
public class Database {
    private final String my_DB =  "jdbc:mysql://localhost:3306/tugas_akhir";
    private final String Driver = "org.gjt.mm.mysql.Driver";
    private final String password = "";
    private final String user = "root";
    private Connection connection = null;
    private Statement stmt = null;
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
    public void connect() {
        try {
//            Class.forName(Driver);
            connection = DriverManager.getConnection(my_DB,user,password);
            stmt = connection.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void close() throws SQLException{
        stmt.close();
        connection.close();
    }
    
    public Admin get_admin(String tmp_id, String tmp_password) throws SQLException{
        Admin adm = null;
        String date;
        String sql = "SELECT * FROM `User` WHERE Id_Admin = '"+tmp_id+"' and Password = '"+tmp_password+"'";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            date = dateformat.format(rs.getDate("TANGGAL_LAHIR"));
             adm = new Admin(rs.getString("Nama"),date,rs.getString("Email"),rs.getString("Jenis_Kelamin").charAt(0),rs.getString("Password"),rs.getString("Id_Admin"),rs.getString("KODE_DOSEN"));
        }
        return dsn;
    }
}
