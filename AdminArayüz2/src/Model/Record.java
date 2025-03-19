package Model;

import java.sql.Connection;

public class Record extends User{
	


	public Record(int id, String ePosta, String sifre, String müşteri_isim, String müşteri_cinsiyet) {
		super(id, ePosta, sifre, müşteri_isim, müşteri_cinsiyet);
		
	}
	
    public void displayUserType() {
        System.out.println("Bu kullanıcı Kayıt Oldu!");
    }
	
	public boolean Record(String eposta, String şifre, String isim) {
		
		String query = "INSERT INTO user" + "(eposta,şifre,isim,müşteri_cinsiyet) VALUES" + "(?,?,?,?)";
		
		return true;
		
	}

}
