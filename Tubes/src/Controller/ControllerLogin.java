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
import View.LoginFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Reza
 */
public class ControllerLogin implements ActionListener, FocusListener {

    LoginFrame login_view;
    Database db;

    public ControllerLogin() {
        login_view = new LoginFrame();
        login_view.setVisible(true);
        login_view.addListener(this);
        db = new Database();
        db.connect();
        login_view.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(login_view.get_Login_btn())) {
            String id = login_view.getid();
            String password = login_view.get_Password();
            if (id.matches("1911(.*)") == true) {
                try {
                    Constant.adminin = db.getAdmin(id, password);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(Constant.adminin != null){
                    login_view.setVisible(false);
                    try {
                        new ControllerAdmin(Constant.adminin);
                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(Constant.adminin.Get_Nama());
                }else{
                    JOptionPane.showMessageDialog(null, "Password atau ID salah");
                }
            } else if (id.matches("1301(.*)") == true) {
                try {
                    System.out.println("Anggota");
                    Constant.userin = db.getAnggota(id, password);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (Constant.userin != null) {
                    System.out.println(Constant.userin.Get_Tabungan().Get_Amount());
                    new ControllerAnggota(Constant.userin);
                    login_view.setVisible(false);
                    try {
                        db.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(ControllerLogin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(null, "Password atau ID salah");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Password atau ID salah");
            }
            
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void focusLost(FocusEvent e) {
        Object o = e.getSource();
        if (o.equals(this.login_view.getid())) {
            if (this.login_view.get_Password().equals("")) {
                JOptionPane.showMessageDialog(null, "Password kosong");
            }
        } else if (o.equals(this.login_view.get_Password())) {
            if (this.login_view.get_Password().equals("")) {
                JOptionPane.showMessageDialog(null, "Password kosong");
            }
        }
    }
}
