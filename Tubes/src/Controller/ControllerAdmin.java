/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Database.Database;
import Model.Admin;
import Model.Anggota;
import Model.Constant;
import View.MenuAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author reza
 */
public class ControllerAdmin implements ActionListener, FocusListener {
    private MenuAdmin adm_menu = null;
    private Database db = new Database();
    ResultSet rs = null;
    
    public void clearlist(){
        for(int i = 0; i < 100;i++){
            adm_menu.getJtableanggota().setValueAt(null, i, 0);
            adm_menu.getJtableanggota().setValueAt(null, i, 1);
            adm_menu.getJtableanggota().setValueAt(null, i, 2);
            adm_menu.getJtableanggota().setValueAt(false, i, 3);
        }
    }
    public void viewdata() throws SQLException{
        db.connect();
        adm_menu = new MenuAdmin ();
        rs = db.getdatapeminjaman();
        int i = 0;
        while(rs.next()){
            adm_menu.getJtablepeminjaman().setValueAt(rs.getString("Id_Anggota"), i, 0);
            adm_menu.getJtablepeminjaman().setValueAt(rs.getInt("Amount"), i, 1);
            adm_menu.getJtablepeminjaman().setValueAt(rs.getInt("Jangka_Waktu"),i, 2);
            i++;
        }
        rs = db.getdataanggota();
        i = 0;
        while(rs.next()){
            adm_menu.getJtableanggota().setValueAt(rs.getString("Id_Anggota"), i, 0);
            adm_menu.getJtableanggota().setValueAt(rs.getString("Nama"), i, 1);
            adm_menu.getJtableanggota().setValueAt(rs.getBoolean("Status_Pinjam"), i, 3);
            i++;
        }

        rs = db.getdatatabungan();
        i = 0;
        while(rs.next()){
            adm_menu.getJtableanggota().setValueAt(rs.getInt("Amount"), i, 2);
            i++;
        }
        adm_menu.setkeuntungan(db.cekkeuntungan());
        adm_menu.setkoperasiname(db.getnamakoperasi());
        adm_menu.settabungan(db.gettabungan());
        adm_menu.setpeminjaman(db.cekpinjamantotal() - db.getangsurantotal());
    }
    public ControllerAdmin(Admin dm) throws SQLException {
        viewdata();
        adm_menu.setVisible(true);
        adm_menu.setuser(Constant.adminin);
        adm_menu.addListener(this,new MouseAdapter(){ 
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable)e.getSource();
                int row = target.getSelectedRow();
                if(e.getSource().equals(adm_menu.getJtableanggota())){
                    ControllerKonfirmasiHapus controllerKonfirmasiHapus = new ControllerKonfirmasiHapus((String) adm_menu.getJtableanggota().getValueAt(row,0));
                    System.out.println(adm_menu.getJtableanggota().getValueAt(row,0));
                }else if(e.getSource().equals(adm_menu.getJtablepeminjaman())){
                    ControllerKonfirmasiPinjam controllerKonfirmasiPinjam = new ControllerKonfirmasiPinjam((String) adm_menu.getJtablepeminjaman().getValueAt(row,0),(Integer) adm_menu.getJtablepeminjaman().getValueAt(row,1));
                    System.out.println(adm_menu.getJtableanggota().getValueAt(row,0));
                }
             }
        });
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source.equals(adm_menu.getsearchbtton())){
            if(!adm_menu.getsearchinput().equals("")){
                clearlist();
                try {
                    Anggota x = db.cariakun(adm_menu.getsearchinput());
                    if(x != null){
                        adm_menu.getJtableanggota().setValueAt(x.getNo_anggota(), 0, 0);
                        adm_menu.getJtableanggota().setValueAt(x.Get_Nama(), 0, 1);
                        adm_menu.getJtableanggota().setValueAt(x.Get_Tabungan().Get_Amount(), 0, 2);
                        adm_menu.getJtableanggota().setValueAt(x.Get_Pinjam_Uang(), 0, 3);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Input tidak boleh kosong");
            }
        }else if(source.equals(adm_menu.getregisterbutton())){
            new ControllerRegister();          
        }else if(source.equals(adm_menu.getconfirmbutton())){
            if(!adm_menu.getidinput().equals("") && adm_menu.getidjumlah() != 0){
                if(adm_menu.getjenistabungan().equals("Simpanan Wajib")==true){
                    try {
                        db.bayarsimpananwajib(adm_menu.getidinput(),adm_menu.getidjumlah());
                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else if(adm_menu.getjenistabungan().equals("Simpanan Sukarela") == true){
                    try {
                        db.bayarsimpanansukarela(adm_menu.getidinput(),adm_menu.getidjumlah());
                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silakan pilih jenis menu terlebih dahulu");
                }
                adm_menu.setjenistabungan();
                adm_menu.setidanggota();
                adm_menu.setamount();
            }else{
                JOptionPane.showMessageDialog(null,"Id atau jumlah tabungan tidak boleh kosongx");
            }   
        }else if(source.equals(adm_menu.getlogoutbutton())){
            Constant.adminin = null;
            adm_menu.setVisible(false);
            ControllerLogin login = new ControllerLogin();
        }else if(source.equals(adm_menu.getrefresh())){
            try {
                rs = db.getdatapeminjaman();
                int i = 0;
                while(rs.next()){
                    adm_menu.getJtablepeminjaman().setValueAt(rs.getString("Id_Anggota"), i, 0);
                    adm_menu.getJtablepeminjaman().setValueAt(rs.getInt("Amount"), i, 1);
                    adm_menu.getJtablepeminjaman().setValueAt(rs.getInt("Jangka_Waktu"),i, 2);
                    i++;
                }
                rs = db.getdataanggota();
                i = 0;
                while(rs.next()){
                    adm_menu.getJtableanggota().setValueAt(rs.getString("Id_Anggota"), i, 0);
                    adm_menu.getJtableanggota().setValueAt(rs.getString("Nama"), i, 1);
                    adm_menu.getJtableanggota().setValueAt(rs.getBoolean("Status_Pinjam"), i, 3);
                    i++;
                }

                rs = db.getdatatabungan();
                i = 0;
                while(rs.next()){
                    adm_menu.getJtableanggota().setValueAt(rs.getInt("Amount"), i, 2);
                    i++;
                }
                adm_menu.setkeuntungan(db.cekkeuntungan());
                adm_menu.setkoperasiname(db.getnamakoperasi());
                adm_menu.settabungan(db.gettabungan());
                adm_menu.setpeminjaman(db.cekpinjamantotal() - db.getangsurantotal());
            } catch (SQLException ex) {
                Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        adm_menu.setuser(Constant.adminin);
        try {
            adm_menu.setkeuntungan(db.cekkeuntungan());
        } catch (SQLException ex) {
            Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            adm_menu.setkoperasiname(db.getnamakoperasi());
        } catch (SQLException ex) {
            Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            adm_menu.settabungan(db.gettabungan());
        } catch (SQLException ex) {
            Logger.getLogger(ControllerAdmin.class.getName()).log(Level.SEVERE, null, ex);
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
