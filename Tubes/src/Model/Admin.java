/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.Reader;
import java.util.Date;

/**
 *
 * @author reza
 */
public class Admin extends Orang {
    private String No_admin;

    public Admin(String No_admin, String nama, String ttl, String alamat, String password, String email, char jenisKelamin) {
        super(nama, ttl, alamat, password, email, jenisKelamin);
        this.No_admin = No_admin;
    }

    public Admin(){
    }
    
    public String getnoadm(){
        return this.No_admin;
    }
    
    public Anggota Cari_Anggota(Anggota[] anggota, Anggota agt){
        int i = 0;
        Anggota tmpanggota = new Anggota();
        while(anggota[i].Get_Nama() != agt.Get_Nama()){
            i++;
        }
        tmpanggota = anggota[i];
        return tmpanggota;
    }
    
    public void Hapus_Anggota(Anggota[] anggota,Anggota agt){
        boolean is_found = false;
        int i =0;
        while(is_found == false){
            if(anggota[i].Get_Nama().equals(agt.Get_Nama()) == true){
               is_found = true; 
            }
        }
        
        anggota[i] = null;
        int j = i+1;
        while(anggota[j] != null){
            anggota[j-1] = anggota[j];
        }
    }
    
    public void Register_Anggota(Anggota [] anggota, Anggota agt){
        int i = 0;
        while(anggota[i] != null){
            i++;
        }
        
        anggota[i] = agt;
    }
    
    public void Konfirmasi_Peminjaman(Anggota [] anggota, Anggota agt){
        int i = 0;
        while(anggota[i].Get_Nama() != agt.Get_Nama()){
            i++;
        }
        
        anggota[i].Set_Pinjam_Uang(true);
    }
    
    public void Cek_Pinjaman_Dibayarkan(Anggota [] anggota,String nama){
        Anggota tmp = new Anggota();
        int i = 0;
        while(anggota[i].Get_Nama().equals(nama) == false){
            i++;
        }
        
        tmp = anggota[i];
        if(tmp == null){
            System.out.println("Anggota tidak ditemukan");
        }else{
        }
    }
    
    
    public void Cek_Keuangan(Anggota[] anggota){
        int i = 0;
        int sum = 0;
        while(anggota[i] != null){
            sum += anggota[i].Get_Tabungan().Get_Amount();
        }
    }
}
