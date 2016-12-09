/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;

/**
 *
 * @author PRAKTIKUM
 */
public abstract class Orang {
    private String nama;
    private String ttl;
    private String alamat;
    private String password;
    public String email;
    public char jenisKelamin;

    public Orang(String nama, String ttl, String alamat, String password, String email, char jenisKelamin) {
        this.nama = nama;
        this.ttl = ttl;
        this.alamat = alamat;
        this.password = password;
        this.email = email;
        this.jenisKelamin = jenisKelamin;
    }
    
    public Orang (){
        
    }
       
    public void setNama(String nama){
        this.nama = nama;
}
    public String Get_Nama (){
        return nama;
}
    public void setTtl (String ttl){
        this.ttl = ttl;
}
    public String getTtl (){
        return ttl;
}
    public void setAlamat (String alamat){
        this.alamat = alamat;
}
    public String getAlamat (){
        return alamat;
}

    public char getJenisKelamin() {
        return jenisKelamin;
}

    public void setJenisKelamin(char jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
 

}
