package Model;

public class Women extends User{
	public Women(int id, String ePosta, String sifre, String müşteri_isim, String müşteri_cinsiyet) {
		super(id, ePosta, sifre, müşteri_isim, müşteri_cinsiyet);
		
	}
    public void displayUserType() {
        System.out.println("Bu kullanıcı bir Kadındır.");
    }
	public Women() {
		
	}
	
}
