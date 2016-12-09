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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Model.Admin;
import Model.Anggota;
import Model.Constant;
import Model.Peminjaman;
import Model.Tabungan;

/**
 *
 * @author Reza
 */
public class Database {
    private final String my_DB =  "jdbc:mysql://localhost:3306/Koperasi";
    private final String Driver = "org.gjt.mm.mysql.Driver";
    private String password = "";
    private String user = "root";
    private Connection connection = null;
    private Statement stmt = null;
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

    public void connect() {
        try {
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

        
    //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public String getnamakoperasi() throws SQLException{
        String sql = "SELECT Nama FROM `Koperasi`";
        String nama = "";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            nama = rs.getString("Nama");
        }
        return nama;
    }
    
    
    
    //Mengambil data admin
    
    //untuk login admin
    public Admin getAdmin(String tmp_id, String tmp_password) throws SQLException{
        Admin adm = null;
        String date;
        String sql = "SELECT * FROM `Admin` WHERE Id_Admin = '"+tmp_id+"' and Password = '"+tmp_password+"'";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            date = dateformat.format(rs.getDate("Tanggal_Lahir"));
            adm = new Admin(rs.getString("Id_Admin")
                    ,rs.getString("Nama")
                    ,date
                    ,rs.getString("Alamat")
                    ,rs.getString("Password")
                    ,rs.getString("Email")
                    ,rs.getString("Jenis_Kelamin").charAt(0));
        }
        return adm;
    }
    //mengecek pinjaman total && dibayarkan
    public int cekpinjamantotal() throws SQLException{
        String sql = "SELECT User.Status_PInjam, Pinjaman.Id_Anggota, Pinjaman.Jangka_Waktu,Pinjaman.Amount FROM `User`, `Pinjaman` WHERE User.Id_Anggota = Pinjaman.Id_Anggota AND User.Status_PInjam = '1' AND User.Pinjam_Uang = '1'";
        ResultSet rs = stmt.executeQuery(sql);
        int sum = 0;
        while(rs.next()){
            sum += rs.getInt("Amount");
        }
        return sum;
    }
    
    public int getangsurantotal() throws SQLException{
        String sql = "SELECT Amount FROM `Angsuran`";
        ResultSet rs = stmt.executeQuery(sql);
        int sum = 0;
        while(rs.next()){
            sum += rs.getInt("Amount");
        }
        return sum;
    }
    
    //mengambil data tabungan
    public int gettabungan() throws SQLException{
        String sql = "SELECT Tabungan FROM `Koperasi`";
        ResultSet rs = stmt.executeQuery(sql);
        int sum = 0;
        while(rs.next()){
            sum = rs.getInt("Tabungan");
        }
        return sum;
    }
    
    public void tambahtabungan(int amount) throws SQLException{
        int current = 0;
        current = gettabungan();
        current += amount;
        String sql = "UPDATE Koperasi SET `Tabungan` = '"+current+"'";
        stmt.executeUpdate(sql);
    }
    
    public void kurangitabungan(int amount) throws SQLException{
        int current = 0;
        current = gettabungan();
        current -= amount;
        String sql = "UPDATE Koperasi SET `Tabungan` = '"+current+"'";
        stmt.executeUpdate(sql);
    }
    
    public void hapusanggota(String id) throws SQLException{
        String sql = "DELETE FROM `Angsuran` WHERE `Id_Anggota` = '"+id+"'";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM `Pinjaman` WHERE `Id_Anggota` = '"+id+"'";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM `Tabungan` WHERE `Id_Anggota` = '"+id+"'";
        stmt.executeUpdate(sql);
        sql = "DELETE FROM `User` WHERE `Id_Anggota` = '"+id+"'";
        stmt.executeUpdate(sql);
        JOptionPane.showMessageDialog(null,"Berhasil menghapus User"+id);
    }
    
    //mengambil data keuntungan koperasi
    public int cekkeuntungan() throws SQLException{
        int amount = 0;
        ResultSet rs = null;
        String sql = "SELECT * FROM Koperasi";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            amount = rs.getInt("Keuntungan");
        }
        return amount;
    }
    
    public void tambahkeuntungan(int amount) throws SQLException{
        int current = 0;
        current = cekkeuntungan();
        current += amount;
        String sql = "UPDATE Koperasi SET `Keuntungan` = '"+current+"'";
        stmt.executeUpdate(sql);
    }
    
    //digunakan untuk mencari akun anggota
    public Anggota cariakun(String id) throws SQLException{
        Anggota agt = new Anggota();
        agt = getAnggota(id);
        return agt;
    }
    
    public void bayarsimpanansukarela(String id,int amount) throws SQLException{
        Anggota agt = new Anggota();
        agt = cariakun(id);
        if(agt == null){
            JOptionPane.showMessageDialog(null,"Anggota dengan id"+id+"tidak ditemukan");
        }else{
            agt.getTabungan().Add_Amount(amount);
            UpdateTabungan(agt);
            JOptionPane.showMessageDialog(null,"Berhasil menambahkan amount");
        }
    }
    
    public void bayarsimpananwajib(String id,int amount) throws SQLException{
        Anggota agt = new Anggota();
        int sum = 0;
        agt = cariakun(id);
        if(agt == null){
            JOptionPane.showMessageDialog(null,"Anggota dengan id"+id+"tidak ditemukan");
        }else{
            sum = gettabungan();
            sum += amount;
            String sql = "UPDATE Koperasi SET `Tabungan` = '"+sum+"'";
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"Berhasil anda sudah membayar simpanan wajib");
        }
    }
    
    public String getlastnumber() throws SQLException{
        String last = "";
        String sql = "SELECT Id_Anggota FROM User";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            if(rs.isLast() == true){
                last = rs.getString("Id_Anggota");
            }
        }
        int number = Integer.parseInt(last);
        number += 1;
        last = String.valueOf(number);
        return last;
    }
    public void registeranggota(Anggota agt) throws SQLException{
         String sql = "INSERT into User Values('"+agt.getNo_anggota()+"','"
                 +agt.Get_Nama()+"',"
                 +"'"+agt.getEmail()+"',"
                 + "'"+agt.getPassword()+"',"
                 + "'"+agt.getJenisKelamin()+"',"
                 + "'"+agt.getAlamat()+"',"
                 + "'"+agt.getTtl()+"','0','0')";
         stmt.executeUpdate(sql);
         sql = "INSERT into Tabungan Values('0','"+agt.getNo_anggota()+"','sukarela')";
         stmt.executeUpdate(sql);
         sql = "INSERT into Pinjaman Values('"+agt.getNo_anggota()+"','0','0','0')";
         stmt.executeUpdate(sql);
         sql = "INSERT into Angsuran Values('0','0000-00-00','"+agt.getNo_anggota()+"')";
         stmt.executeUpdate(sql);
         int amount = gettabungan();
         amount += 100000; 
         sql = "UPDATE Koperasi SET `Tabungan` = '"+amount+"'";
         stmt.executeUpdate(sql);
         JOptionPane.showMessageDialog(null,"Anggota baru berhasil diregistrasi dengan id : "+agt.getNo_anggota());
    }
    
    //menu anggota
    //untuk mncari akun anggota
    public Anggota getAnggota(String tmp_id) throws SQLException{
        Peminjaman peminjaman = new Peminjaman();
        Tabungan tbg = new Tabungan(); 
        ResultSet rs = null;
        Anggota agt = null;
        String date;
        String sql;
        sql = "SELECT * FROM `Tabungan` WHERE Id_Anggota = '"+tmp_id+"'";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            tbg = new Tabungan(rs.getInt("Amount"),rs.getString("Jenis_Tabungan"));
        }
        
        sql = "SELECT * FROM `Pinjaman` WHERE Id_Anggota = '"+tmp_id+"'";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            peminjaman = new Peminjaman(rs.getInt("Amount")
                    ,rs.getInt("Jangka_Waktu")
                    ,rs.getInt("angsuran"));
        }
        
        sql = "SELECT * FROM `User` Where Id_Anggota = '"+tmp_id+"'";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            date = dateformat.format(rs.getDate("Tanggal_Lahir"));
            agt = new Anggota(rs.getString("Id_Anggota")
                    ,tbg
                    ,rs.getBoolean("Pinjam_Uang")
                    ,rs.getBoolean("Status_Pinjam")
                    ,peminjaman
                    ,rs.getString("Nama")
                    ,date
                    ,rs.getString("Alamat")
                    ,rs.getString("Password")
                    ,rs.getString("Email")
                    ,rs.getString("Jenis_Kelamin").charAt(0));
        }
        System.out.println(agt.Get_Tabungan().Get_Amount());
        System.out.println("Ok");
        return agt;
    }
    
    //digunakan untuk login anggota
    public Anggota getAnggota(String tmp_id, String Password) throws SQLException{
        Peminjaman peminjaman = null;
        Tabungan tbg = null; 
        ResultSet rs = null;
        Anggota agt = null;
        String date;
        String sql = "";
        
        sql = "SELECT * FROM `Tabungan` WHERE Id_Anggota = '"+tmp_id+"'";
        System.out.println(sql);
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            tbg = new Tabungan(rs.getInt("Amount"),rs.getString("Jenis_Tabungan"));
        }
        
        sql = "SELECT * FROM `Pinjaman` WHERE Id_Anggota = '"+tmp_id+"'";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            peminjaman = new Peminjaman(rs.getInt("Amount")
                    ,rs.getInt("Jangka_Waktu")
                    ,rs.getInt("angsuran"));
        }
        
        sql = "SELECT * FROM `User` Where Id_Anggota = '"+tmp_id+"' AND Password = '"+Password+"'";
        rs = stmt.executeQuery(sql);
        while(rs.next()){
            date = dateformat.format(rs.getDate("Tanggal_Lahir"));
            agt = new Anggota(rs.getString("Id_Anggota")
                    ,tbg
                    ,rs.getBoolean("Pinjam_Uang")
                    ,rs.getBoolean("Status_Pinjam")
                    ,peminjaman
                    ,rs.getString("Nama")
                    ,date
                    ,rs.getString("Alamat")
                    ,rs.getString("Password")
                    ,rs.getString("Email")
                    ,rs.getString("Jenis_Kelamin").charAt(0));
        }
        return agt;
    }
    
    //digunakan untuk mengudate tabungan anggota
    public void UpdateTabungan(Anggota agt) throws SQLException{
      String sql = "UPDATE Tabungan SET `Amount` = '"+agt.getTabungan().Get_Amount()+"' where Id_Anggota = '"+agt.getNo_anggota()+"'";
      stmt.executeUpdate(sql);
    }
    
    //untuk pembayaran simpanan wajib
    public void bayarsimpananwajib(int amount,Anggota agt) throws SQLException{
       if(amount % 50000 != 0 || amount == 0){
            JOptionPane.showMessageDialog(null,"Nominal jumlah uang salah.\nMasukan jumlah uang berkelipatan 50000");
        }else{
            if(amount < agt.Get_Tabungan().Get_Amount()){
                agt.Get_Tabungan().Withdraw_Amount(amount);
                UpdateTabungan(agt);
                int tabungan = gettabungan();
                tabungan += amount;
                String sql = "UPDATE Koperasi SET `Tabungan` = '"+tabungan+"'";
                stmt.executeUpdate(sql);
                JOptionPane.showMessageDialog(null,"Sisa tabungan anda :"+agt.Get_Tabungan().Get_Amount());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf saldo anda tidak mencukupi");
            }
        }
    }
    
    //untuk penarikan uang
    public void penarikanuang(int amount,Anggota agt) throws SQLException{
        if(amount % 50000 != 0 || amount == 0){
            JOptionPane.showMessageDialog(null,"Nominal jumlah uang salah.\nMasukan jumlah uang berkelipatan 50000");
        }else{
            if(amount < agt.Get_Tabungan().Get_Amount()){
                agt.Get_Tabungan().Withdraw_Amount(amount);
                UpdateTabungan(agt);
                JOptionPane.showMessageDialog(null,"Sisa tabungan anda :"+agt.Get_Tabungan().Get_Amount());
            }else{
                JOptionPane.showMessageDialog(null,"Maaf saldo anda tidak mencukupi");
            }
        }
    }
    
    //untuk mendonasikan tabukan anggota
    public void DonasikanTabungan(int amount,Anggota agt) throws ParseException, SQLException{
        if(amount % 50000 != 0 || amount == 0){
            JOptionPane.showMessageDialog(null,"Nominal jumlah uang salah.\nMasukan jumlah uang berkelipatan 50000");
        }else{
            if(amount < agt.Get_Tabungan().Get_Amount()){
                agt.Get_Tabungan().Withdraw_Amount(amount);
                UpdateTabungan(agt);
                String sql = "UPDATE `Koperasi` SET `Keuntungan` = '"+(amount+cekkeuntungan())+"'";
                stmt.executeUpdate(sql);
                System.out.println(sql);
                if(!stmt.execute(sql)){
                    JOptionPane.showMessageDialog(null,"Berhasil mendonasikan");
                }else{
                    JOptionPane.showMessageDialog(null, "Gagal melakukan donasi");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Maaf tabungan anda tidak mencukupi");
            }
        }
    }
    
    //digunakn untuk merequest peminjaman anggota
    public void requestpeminjaman(Anggota agt,int Amount) throws SQLException{
        int jangka_waktu = 0;
        boolean valid = false;
        int angsuran = 0;
        if(agt == null && Amount % 1000000 != 0){
            JOptionPane.showMessageDialog(null,"Uang yang dipinjam harus kelipatan 1000000");
            System.out.println("Uang yang dipinjam harus kelipatan 1000000");
        }else{
            if(Amount == 1000000){
                jangka_waktu = 2;
                angsuran = 600000;
                valid = true;
            }else if(Amount == 3000000){
                jangka_waktu = 6;
                angsuran = 575000;
                valid = true;
            }else if(Amount == 5000000){
                jangka_waktu = 10;
                angsuran = 550000;
                valid = true;
            }else if(Amount == 6000000){
                jangka_waktu = 12;
                angsuran = 525000;
                valid = true;
            }else{
                JOptionPane.showMessageDialog(null,"Jumlah nilai pinjaman tidak valid");
            }
            if(valid == true){
                String sql = "UPDATE User SET `Pinjam_Uang` = '1' where Id_Anggota = '"+agt.getNo_anggota()+"'";
                stmt.executeUpdate(sql);
                sql = "UPDATE `Pinjaman` SET `Amount` = '"+Amount+"', `Jangka_Waktu` = '"+jangka_waktu+"',`Angsuran` = '"+angsuran+"' WHERE `Pinjaman`.`Id_Anggota` = '"+agt.getNo_anggota()+"'";
                if(!stmt.execute(sql)){
                    JOptionPane.showMessageDialog(null,"Berhasil merequest peminjaman");
                }else{
                    JOptionPane.showMessageDialog(null, "Gagal melakukan request peminjaman");
                }
            }
        }
    }
    
    public int getminimumbayarangsuran(Anggota agt) throws SQLException{
        int amount = 0;
        boolean is_pinjam = false;
        String sql = "SELECT Status_Pinjam FROM `User` WHERE Id_Anggota = '"+agt.getNo_anggota()+"'";
        ResultSet rs = stmt.executeQuery(sql);
        while(rs.next()){
            is_pinjam = rs.getBoolean("Status_Pinjam");
        }
        
        if(is_pinjam == true){
            sql = "SELECT Angsuran FROM `Pinjaman` WHERE Id_Anggota = '"+agt.getNo_anggota()+"'";
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                amount = rs.getInt("Angsuran");
            }
        }
        return amount;
    }
    
    public void bayarangsuran(Anggota agt,int amount) throws SQLException{
        if(getminimumbayarangsuran(agt) == 0){
            JOptionPane.showMessageDialog(null, "Anda belum melakukan request tabungan\natau tabungan anda belum dikonfirmasi");
        }else{
            if(amount == getminimumbayarangsuran(agt)){
                if(agt.Get_Tabungan().Get_Amount() < amount){
                    JOptionPane.showMessageDialog(null, "Maaf Tabungan Anda tidak mencukupi");
                }else{
                   int angs = 0;
                   int bulan = 0;
                   agt.getTabungan().Withdraw_Amount(amount);
                   UpdateTabungan(agt);
                   String sql = "SELECT Jangka_Waktu FROM `Pinjaman` WHERE Id_Anggota = '"+agt.getNo_anggota()+"'";
                   ResultSet rs = stmt.executeQuery(sql);
                   while(rs.next()){
                       bulan = rs.getInt("Jangka_Waktu");
                   }
                   sql = "SELECT Amount FROM `Angsuran` WHERE Id_Anggota = '"+agt.getNo_anggota()+"'";
                   rs = stmt.executeQuery(sql);
                   while(rs.next()){
                       angs = rs.getInt("Amount");
                   }
                   tambahkeuntungan(amount - 500000);
                   tambahtabungan(500000);
                   if(bulan > 0){
                       bulan = bulan - 1;
                       sql = "UPDATE Pinjaman SET `Jangka_Waktu` = '"+bulan+"' where Id_Anggota = '"+agt.getNo_anggota()+"'";
                       stmt.executeUpdate(sql);
                       angs += amount; 
                       sql = "UPDATE Angsuran SET `Amount` = '"+angs+"' where Id_Anggota = '"+agt.getNo_anggota()+"'";
                       stmt.executeUpdate(sql);
                       if(bulan == 0){
                           sql = "UPDATE Pinjaman SET `Angsuran` = '0', `Amount` = '0' where Id_Anggota = '"+agt.getNo_anggota()+"'";
                           stmt.executeUpdate(sql);
                           sql = "UPDATE Angsuran SET `Amount` = '0' where Id_Anggota = '"+agt.getNo_anggota()+"'";
                           stmt.executeUpdate(sql);
                           sql = "UPDATE User SET `Pinjam_Uang` = '0', `Status_Pinjam` = '0' where Id_Anggota = '"+agt.getNo_anggota()+"'";
                           stmt.executeUpdate(sql);
                           JOptionPane.showMessageDialog(null, "Selamat Tabungan anda sudah lunas");
                       }else{
                           JOptionPane.showMessageDialog(null, "Berhasil Membayar Angsuran\nPinjaman yang sudah dibayarkan : "+angs);
                       }
                   }
                   
                }
            }else{
                JOptionPane.showMessageDialog(null, "Jumlah angsuran yang dimasukan tidak sesuai dengan yang harus dibayarkan");
            }
        }
    }
    
    public ResultSet getdataanggota() throws SQLException{
        String sql = "SELECT * FROM `User`";
        ResultSet rs = null;
        rs= stmt.executeQuery(sql);
        return rs;
    }
    
    public ResultSet getdatapeminjaman() throws SQLException{
        String sql = "SELECT User.Status_PInjam, Pinjaman.Id_Anggota, Pinjaman.Jangka_Waktu,Pinjaman.Amount FROM `User`, `Pinjaman` WHERE User.Id_Anggota = Pinjaman.Id_Anggota AND User.Status_PInjam = '0' AND User.Pinjam_Uang = '1'";
        ResultSet rs = null;
        rs= stmt.executeQuery(sql);
        return rs;
    }
    
    public ResultSet getdatatabungan() throws SQLException{
        String sql = "SELECT * FROM `Tabungan`";
        ResultSet rs = null;
        rs= stmt.executeQuery(sql);
        return rs;
    }
    
    public void konfirmasipeminjaman(String id, int amount) throws SQLException{
        if((gettabungan() - amount) > 10000000){
            String sql = "UPDATE User SET `Status_PInjam` = '1' where Id_Anggota = '"+id+"'";
            kurangitabungan(amount);
            stmt.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "berhasi mengkonfirmasi peminjmana user dengan id : "+id);
        }else{
           JOptionPane.showMessageDialog(null, "Keuangan Koperasi tidak mencukupi untuk dipinjam "); 
        }
        
    }
}