/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Database;
import Model.Anggota;
import Model.Peminjaman;
import Model.Tabungan;
import View.RegisterUser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reza
 */
public class ControllerRegister implements ActionListener, FocusListener{
    Anggota agt = new Anggota();
    RegisterUser registrasi = null;
    private Database db = new Database();
    public ControllerRegister() {
        registrasi = new RegisterUser();
        registrasi.setVisible(true);
        registrasi.addListener(this);
        db.connect();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        System.out.println(source.equals(registrasi.getregisterbutton()));
        if (source.equals(registrasi.getregisterbutton())) {
            System.out.println("pass here");
            if(!registrasi.getnama().equals("") && !registrasi.getalamat().equals("")&&!registrasi.getemail().equals("")&&!registrasi.getpassword().equals("") && registrasi.getjeniskelamin()!=0){
                try {
                    agt = new Anggota(db.getlastnumber()
                            ,new Tabungan()
                            ,false
                            ,false
                            ,new Peminjaman()
                            ,registrasi.getnama()
                            ,registrasi.getttl()
                            ,registrasi.getalamat()
                            ,registrasi.getpassword()
                            ,registrasi.getemail()
                            ,registrasi.getjeniskelamin());
                    db.registeranggota(agt);
                    registrasi.setVisible(false);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerRegister.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("pass here2");
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void focusLost(FocusEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
