package Model;

public class Man extends User{

	public Man(int id, String ePosta, String sifre, String müşteri_isim, String müşteri_cinsiyet) {
		super(id, ePosta, sifre, müşteri_isim, müşteri_cinsiyet);
		
	}
	
    @Override
    public void displayUserType() {
        System.out.println("Bu kullanıcı bir erkektir.");
    }
	
	public Man() {}
		
}
