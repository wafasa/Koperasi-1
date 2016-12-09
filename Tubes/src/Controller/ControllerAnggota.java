/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Database;
import Model.Anggota;
import Model.Constant;
import View.MenuAnggota;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author reza
 */
public class ControllerAnggota implements ActionListener, FocusListener {
    private MenuAnggota agt_menu = null;
    private Database db = new Database();
    public ControllerAnggota(Anggota agt) {
        agt_menu = new MenuAnggota ();
        agt_menu.setVisible(true);
        agt_menu.setuser(Constant.userin);
        agt_menu.addListener(this);
        db.connect();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(agt_menu.getconfirmbtn())){
            System.out.println("getconfirm tabungan diklik");
            if(agt_menu.gettabunganmenu().equals("Donasikan Tabungan")){
                try {
                    db.DonasikanTabungan(agt_menu.getamounttabungan(), Constant.userin);
                } catch (ParseException ex) {
                    Logger.getLogger(ControllerAnggota.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAnggota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(agt_menu.gettabunganmenu().equals("Penarikan Uang")){
                try {
                    db.penarikanuang(agt_menu.getamounttabungan(),Constant.userin);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAnggota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(agt_menu.gettabunganmenu().equals("Bayar Simpanan Wajib")){
                try {
                    db.bayarsimpananwajib(agt_menu.getamounttabungan(),Constant.userin);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAnggota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"SIlakan pilih jenis transaksi terlebih dahulu");
            }
        }else if(source.equals(agt_menu.getconfirmpeminjaman())){
            if(agt_menu.getpinjamanmenu().equals("Request Peminjaman")){
                try {
                    db.requestpeminjaman(Constant.userin,agt_menu.getamountpeminjaman());
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAnggota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else if(agt_menu.getpinjamanmenu().equals("Bayar Angsuran")){
                try {
                    db.bayarangsuran(Constant.userin,agt_menu.getamountpeminjaman());
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAnggota.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silakan pilih jenis transaksi terlebih dahulu");
            }
        }else if(source.equals(agt_menu.getlogoutbtn())){
            Constant.userin = null;
            agt_menu.setVisible(false);
            ControllerLogin login = new ControllerLogin();
        }
        agt_menu.setuser(Constant.userin);
        agt_menu.setamounttabungan("");
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
