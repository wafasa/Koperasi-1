/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes;

/**
 *
 * @author reza
 */
public class Admin extends Orang {
    private String No_admin;

    public Admin(String string, String date, String string0, char charAt, String string1, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            System.out.println("Nama \t : "+tmp.Cek_Angsuran());
        }
    }
    
    public void Cek_Pinjaman_Total(Anggota [] anggota){
        int sum = 0;
        for (int i = 0 ; i++ ; i < anggota.length){
            if(anggota[i].Get_Pinjam_Uang() == true){
                sum = anggota[i].Get_Peminjaman().Get_Amount() + sum;
            }
        }
        
        System.out.println("Jumlah yang dibayarkan : "+sum);
    }
    
    public void Cek_Keuangan(Anggota[] anggota){
        int i = 0;
        int sum = 0;
        while(anggota[i] != null){
            sum += anggota[i].Get_Tabungan().Get_Amount();
        }
    }
}
