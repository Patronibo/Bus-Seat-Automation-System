package Model;

public abstract class User {
    private int id;
    private String EPosta;
    private String sifre;
    private String müşteri_isim;
    private String müşteri_cinsiyet;
    
    public User(int id, String ePosta, String sifre, String müşteri_isim, String müşteri_cinsiyet) {
        this.id = id;
        this.EPosta = ePosta;
        this.sifre = sifre;
        this.müşteri_isim = müşteri_isim;
        this.müşteri_cinsiyet = müşteri_cinsiyet;
    }
	
    public abstract void displayUserType();
    
	public User() {}


	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEPosta() {
		return EPosta;
	}
	
	public void setEPosta(String ePosta) {
		EPosta = ePosta;
	}
	
	public String getSifre() {
		return sifre;
	}
	
	public void setSifre(String sifre) {
		this.sifre = sifre;
	}
	
	public String getMüşteri_isim() {
		return müşteri_isim;
	}
	
	public void setMüşteri_isim(String müşteri_isim) {
		this.müşteri_isim = müşteri_isim;
	}
	
	public String getMüşteri_cinsiyet() {
		return müşteri_cinsiyet;
	}
	
	public void setMüşteri_cinsiyet(String müşteri_cinsiyet) {
		this.müşteri_cinsiyet = müşteri_cinsiyet;
	}
	
	
}
