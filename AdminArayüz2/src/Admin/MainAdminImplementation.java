package Admin;

public class MainAdminImplementation extends MainAdmin {

    public MainAdminImplementation() {
        super(); // MainAdmin'in constructor'ını çağırır
        setVisible(true); // Uygulama penceresini görünür yapar
    }

    @Override
    protected void initializePanels() {
        adminPanel = new AdminPanel(this);
        seferEkle = new SeferEkle(this);
        seferDuzenle = new SeferDuzenle(this);
        seferSilPanel = new SeferSil(this);
        yolcuDuzenle = new YolcuDuzenle(this);
        yolcuSil = new YolcuSil(this);

        mainPanel.add(adminPanel, "AdminPanel");
        mainPanel.add(seferEkle, "SeferEkle");
        mainPanel.add(seferSilPanel, "SeferSil");
        mainPanel.add(seferDuzenle, "SeferDuzenle");
        mainPanel.add(yolcuDuzenle, "YolcuDuzenle");
        mainPanel.add(yolcuSil, "YolcuSil");
    }

    public void islemYap(seferIslemleri islemi) {
        islemi.islemYap();  // Polymorphism burada devreye girer, hangi sınıf implement edildiyse o çalışır
    }

    public static void main(String[] args) {
        MainAdminImplementation adminPanel = new MainAdminImplementation();

        //SeferSil seferSil = new SeferSil(adminPanel);
      //  adminPanel.islemYap((seferIslemleri) seferSil);  // SeferSil işlemine ait execute metodu çalıştırılır
        
      //  SeferDuzenle seferDuzenle = new SeferDuzenle(adminPanel);
        //adminPanel.islemYap((seferIslemleri) seferDuzenle);  // SeferDuzenle işlemine ait execute metodu çalıştırılır
    }
}
