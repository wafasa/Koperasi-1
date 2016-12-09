/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Database;
import View.KonfirmasiHapus;
import View.KonfirmasiPinjam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author reza
 */
public class ControllerKonfirmasiPinjam implements ActionListener {
    private KonfirmasiPinjam konfirmasi = new KonfirmasiPinjam();
    private Database db = new Database();
    private String id = "";
    private int amount = 0;
    public ControllerKonfirmasiPinjam(String id,int amount) {
        this.id = id;
        this.amount = amount;
        konfirmasi.setVisible(true);
        konfirmasi.addListener(this);
        db.connect();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(konfirmasi.getconfirmbutton())){
            try {
                db.konfirmasipeminjaman(this.id,this.amount);
                System.out.println(id);
            } catch (SQLException ex) {
                Logger.getLogger(ControllerKonfirmasiPinjam.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        konfirmasi.setVisible(false);
        try {
            db.close();
        } catch (SQLException ex) {
            Logger.getLogger(ControllerKonfirmasiPinjam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
