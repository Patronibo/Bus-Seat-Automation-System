package Admin;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Helper.DatabaseConnection;
import View.GirisGUI;

public class AdminPanel extends JPanel implements  AdminPanelInterface {
	private MainAdmin mainAdmin;
	private JComboBox otoSilComboBox;
	private JComboBox soforSilComboBox;
	private JComboBox uyeSilComboBox;
	
    ImageIcon foto = new ImageIcon("C://Users//ilhan//Desktop//beyazcıkısSon.png"); // Buton Arka Planı 
    ImageIcon icon = new ImageIcon("C://Users//ilhan//Desktop//cıkıs8.png"); // Geri Tuşu Arka Planı
    ImageIcon arka = new ImageIcon("C://Users//ilhan//Desktop//bgg.jpg");	 // Ana Tablo Arka Planı
    ImageIcon tablo = new ImageIcon("C://Users//ilhan//Desktop//adminPanel.png"); // Panel Arka Planı

    private static final long serialVersionUID = 1L;

	
    public AdminPanel(MainAdmin mainAdmin) {
        this.mainAdmin = mainAdmin;
        initialize(); // Paneli başlatan metod
    }
		
    public void initialize() {
        setLayout(null);

		JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 485, 365);
        add(layeredPane);

        UIManager.put("TabbedPane.selected", new Color(220,220,220)); // Seçili sekmenin arka plan rengi
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0)); // Kenar boşluklarını sıfırla

        
        // Arka plan resmi ekleme
        JLabel backgroundLabel = new JLabel(arka);
        backgroundLabel.setBounds(0, 0, 500, 400);
        layeredPane.add(backgroundLabel, new Integer(0)); // En arkaya ekle
        


        // Admin mesajı ekleme
        JLabel adminMesaj = new JLabel("Admin Paneline Hoşgeldiniz");
        adminMesaj.setForeground(new Color(255, 255, 255));
        adminMesaj.setBounds(140, 50, 250, 25);
        adminMesaj.setFont(new Font("Arial", Font.BOLD, 15));
        layeredPane.add(adminMesaj, new Integer(1)); // Üst katmana ekle

        // JTabbedPane oluşturma ve ekleme
        JTabbedPane sekme = new JTabbedPane();
        sekme.setForeground(Color.WHITE);
        
        sekme.setBounds(10, 100, 465, 240);
        sekme.setBackground(new Color(0,0,0));
        layeredPane.add(sekme, new Integer(1)); // Üst katmana ekle;
		
		sekme.addTab("Admin İşlemleri", adminİslemleri());
		sekme.addTab("Sefer İşlemleri", seferİslemleri());
		sekme.addTab("Otobüs İşlemleri", otobusİslemleri());
		sekme.addTab("Üye/Yolcu İşlemleri", üyeYolcuİslemleri());
	
	
	}
	
		private JPanel adminİslemleri() {
			
		    JPanel panel = new JPanel(new CardLayout());

		    // Ana ekran paneli
		    JPanel mainPanel = new JPanel(null);

		    // Şifre değiştirme ekranı paneli
		    JPanel sifreDegistirmePanel = new JPanel(null);

		    // JLayeredPane ile ana paneli oluştur
		    JLayeredPane mainLayeredPane = new JLayeredPane();
		    mainLayeredPane.setBounds(0, 0, 465, 220);
		    mainPanel.add(mainLayeredPane);

		    // Arka plan resmi
		    JLabel fotoLabel = new JLabel(tablo);
		    fotoLabel.setBounds(0, 0, 465, 220);
		    mainLayeredPane.add(fotoLabel, Integer.valueOf(0)); // En arka katmana ekle

		    // Ana ekran yazısı
		    JLabel adminMesaj = new JLabel("Şifrenizi Değiştirmek İçin:");
		    adminMesaj.setFont(new Font("Arial", Font.BOLD, 12));
		    adminMesaj.setForeground(Color.WHITE);
		    adminMesaj.setBounds(50, 80, 200, 25);
		    mainLayeredPane.add(adminMesaj, Integer.valueOf(1));

		    // Şifre değiştir butonu
		    JButton sifreButon = new JButton("Şifrenizi Değiştirin", foto);
		    sifreButon.setBounds(220, 75, 150, 35);
		    sifreButon.setHorizontalTextPosition(SwingConstants.CENTER);
		    sifreButon.setVerticalTextPosition(SwingConstants.CENTER);
		    sifreButon.setForeground(Color.WHITE);
		    butonDegistir(sifreButon);
		    mainLayeredPane.add(sifreButon, Integer.valueOf(1));

		    // Çıkış butonu
		    JButton cikisButon = new JButton(icon);
		    cikisButon.setBounds(60, 150, 60, 60);
		    butonDegistir(cikisButon);
		    mainLayeredPane.add(cikisButon, Integer.valueOf(1));

		    // Şifre değiştirme ekranı için JLayeredPane
		    JLayeredPane sifreLayeredPane = new JLayeredPane();
		    sifreLayeredPane.setBounds(0, 0, 465, 220);
		    sifreDegistirmePanel.add(sifreLayeredPane);

		    // Arka plan resmi
		    JLabel sifreBackground = new JLabel(tablo);
		    sifreBackground.setBounds(0, 0, 465, 220);
		    sifreLayeredPane.add(sifreBackground, Integer.valueOf(0));

		    // Şifre değiştirme bileşenleri
		    JLabel eskiSifreMesaj = new JLabel("Eski Şifrenizi Girin:");
		    eskiSifreMesaj.setBounds(40, 30, 200, 25);
		    eskiSifreMesaj.setForeground(Color.WHITE);
		    sifreLayeredPane.add(eskiSifreMesaj, Integer.valueOf(1));
		    
		    JPasswordField eskiSifre = new JPasswordField(20);
		    eskiSifre.setBounds(220, 30, 150, 25);
		    sifreLayeredPane.add(eskiSifre, Integer.valueOf(1));

		    JLabel yeniSifreMesaj = new JLabel("Yeni Şifrenizi Girin:");
		    yeniSifreMesaj.setBounds(40, 80, 200, 25);
		    yeniSifreMesaj.setForeground(Color.WHITE);
		    sifreLayeredPane.add(yeniSifreMesaj, Integer.valueOf(1));

		    JPasswordField yeniSifre = new JPasswordField(20);
		    yeniSifre.setBounds(220, 80, 150, 25);
		    sifreLayeredPane.add(yeniSifre, Integer.valueOf(1));

		    JLabel tekrarSifreMesaj = new JLabel("Yeni Şifrenizi Tekrar Girin:");
		    tekrarSifreMesaj.setBounds(40, 130, 200, 25);
		    tekrarSifreMesaj.setForeground(Color.WHITE);
		    sifreLayeredPane.add(tekrarSifreMesaj, Integer.valueOf(1));

		    JPasswordField tekrarSifre = new JPasswordField(20);
		    tekrarSifre.setBounds(220, 130, 150, 25);
		    sifreLayeredPane.add(tekrarSifre, Integer.valueOf(1));

		    JButton degistirButton = new JButton("Şifreyi Değiştir", foto);
		    degistirButton.setBounds(220, 170, 150, 35);
		    butonDegistir(degistirButton);
		    sifreLayeredPane.add(degistirButton, Integer.valueOf(1));

		    JButton geriButon = new JButton(icon);
		    geriButon.setBounds(60, 150, 60, 60);
		    butonDegistir(geriButon);
		    sifreLayeredPane.add(geriButon, Integer.valueOf(1));
		    
		    degistirButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            String eskiSifreText = String.valueOf(eskiSifre.getPassword());
		            String yeniSifreText = String.valueOf(yeniSifre.getPassword());
		            String tekrarSifreText = String.valueOf(tekrarSifre.getPassword());
	
		            // Alanların boş olup olmadığını kontrol et
		            if (eskiSifreText.isEmpty() || yeniSifreText.isEmpty() || tekrarSifreText.isEmpty()) {
		                // Uyarı mesajı göster
		                JOptionPane.showMessageDialog(panel, "Lütfen Tüm Alanları Doldurun!", "Hata", JOptionPane.ERROR_MESSAGE);
		                return;
		            }
	                
	                if (!yeniSifreText.equals(tekrarSifreText)) {
	                    JOptionPane.showMessageDialog(panel, "Yeni şifreler uyuşmuyor!", "Hata", JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	
	                try (Connection conn = DatabaseConnection.getConnection()) {
	                    String query = "SELECT * FROM admin WHERE admin_sifre = ?";
	                    PreparedStatement stmt = conn.prepareStatement(query);
	                    stmt.setString(1, eskiSifreText);
	                    ResultSet rs = stmt.executeQuery();
	
	                    if (rs.next()) {
	                        String updateQuery = "UPDATE admin SET admin_sifre = ? WHERE admin_sifre = ?";
	                        PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
	                        updateStmt.setString(1, yeniSifreText);
	                        updateStmt.setString(2, eskiSifreText);
	                        int rowsAffected = updateStmt.executeUpdate();
	
	                        if (rowsAffected > 0) {
	                            JOptionPane.showMessageDialog(panel, "Şifre başarıyla değiştirildi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
	                	        CardLayout cl = (CardLayout) panel.getLayout();
	                	        cl.show(panel, "main");
	                        } else {
	                            JOptionPane.showMessageDialog(panel, "Şifre değiştirme başarısız oldu.", "Hata", JOptionPane.ERROR_MESSAGE);
	                        }
	                    } else {
	                        JOptionPane.showMessageDialog(panel, "Eski şifre yanlış!", "Hata", JOptionPane.ERROR_MESSAGE);
	                    }
	                } catch (SQLException ex) {
	                    ex.printStackTrace();
	                    JOptionPane.showMessageDialog(panel, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
	                }
	                eskiSifre.setText("");
	                yeniSifre.setText("");
	                tekrarSifre.setText("");
	            }
	        });
	
		    // Geri dönme işlemleri
		    geriButon.addActionListener(e -> {
		        CardLayout cl = (CardLayout) panel.getLayout();
		        cl.show(panel, "main");
		    });
		    
		    // ActionListener'lar
		    sifreButon.addActionListener(e -> {
		        CardLayout cl = (CardLayout) panel.getLayout();
		        cl.show(panel, "sifreDegistirme");
		    });
	
		    geriButon.addActionListener(e -> {
		        CardLayout cl = (CardLayout) panel.getLayout();
		        cl.show(panel, "main");
		    });
		    
		    cikisButon.addActionListener(e -> mainAdmin.cıkısYap());
	
		    // Panelleri CardLayout'a ekleyin
		    panel.add(mainPanel, "main");
		    panel.add(sifreDegistirmePanel, "sifreDegistirme");
		    
		    return panel;
		}
	
		private JPanel seferİslemleri() {
		    JPanel panel = new JPanel(new CardLayout());

		    // Ana ekran paneli
		    JPanel mainPanel = new JPanel(null);

		    // JLayeredPane oluştur
		    JLayeredPane layeredPane = new JLayeredPane();
		    layeredPane.setBounds(0, 0, 465, 220);
		    mainPanel.add(layeredPane);

		    // Arka plan resmi
		    JLabel backgroundLabel = new JLabel(tablo);
		    backgroundLabel.setBounds(0, 0, 465, 220);
		    layeredPane.add(backgroundLabel, Integer.valueOf(0)); // En arka katman

		    // Sefer ekleme mesajı ve butonu
		    JLabel ekleMesaj = new JLabel("Sefer Eklemek İçin : ");
		    ekleMesaj.setBounds(60, 40, 200, 25);
		    ekleMesaj.setForeground(Color.WHITE);
		    layeredPane.add(ekleMesaj, Integer.valueOf(1));

		    JButton seferEkle = new JButton("Sefer Ekle", foto);
		    seferEkle.setBounds(220, 40, 150, 35);
		    butonDegistir(seferEkle);
		    layeredPane.add(seferEkle, Integer.valueOf(1));

		    // Sefer düzenleme mesajı ve butonu
		    JLabel duzenleMesaj = new JLabel("Sefer Düzenlemek İçin : ");
		    duzenleMesaj.setBounds(60, 100, 200, 25);
		    duzenleMesaj.setForeground(Color.WHITE);
		    layeredPane.add(duzenleMesaj, Integer.valueOf(1));

		    JButton seferDuzenle = new JButton("Sefer Düzenle", foto);
		    seferDuzenle.setBounds(220, 100, 150, 35);
		    butonDegistir(seferDuzenle);
		    layeredPane.add(seferDuzenle, Integer.valueOf(1));

		    // Sefer silme mesajı ve butonu
		    JLabel silMesaj = new JLabel("Sefer Silmek İçin : ");
		    silMesaj.setBounds(60, 160, 200, 25);
		    silMesaj.setForeground(Color.WHITE);
		    layeredPane.add(silMesaj, Integer.valueOf(1));

		    JButton seferSil = new JButton("Sefer Sil", foto);
		    seferSil.setBounds(220, 160, 150, 35);
		    butonDegistir(seferSil);
		    layeredPane.add(seferSil, Integer.valueOf(1));

		    
		    seferEkle.addActionListener(e -> mainAdmin.PanelGöster("SeferEkle"));
		    seferDuzenle.addActionListener(e -> mainAdmin.PanelGöster("SeferDuzenle"));
		    seferSil.addActionListener(e -> mainAdmin.PanelGöster("SeferSil"));

		    
		    panel.add(mainPanel, "main");
		    return panel;
		}

		private JPanel otobusİslemleri() {
		  
		    CardLayout cl = new CardLayout();
		    JPanel panel = new JPanel(cl);

		    // Ana Panel
		    JPanel mainPanel = new JPanel(null);
		    JLayeredPane mainLayeredPane = new JLayeredPane();
		    mainLayeredPane.setBounds(0, 0, 465, 220);
		    mainPanel.add(mainLayeredPane);

		    // Arka plan resmi
		    JLabel backgroundLabel = new JLabel(tablo);
		    backgroundLabel.setBounds(0, 0, 465, 220);
		    mainLayeredPane.add(backgroundLabel, Integer.valueOf(0)); // En arka katman

		    JLabel oPanelMesaj = new JLabel("Otobüs Paneli İçin :");
		    oPanelMesaj.setBounds(60, 50, 150, 25);
		    oPanelMesaj.setForeground(Color.WHITE);
		    mainLayeredPane.add(oPanelMesaj, Integer.valueOf(1));

		    JButton oPanelButon = new JButton("Otobüs İşlemleri", foto);
		    oPanelButon.setBounds(220, 50, 150, 35);
		    oPanelButon.setForeground(Color.WHITE);
		    butonDegistir(oPanelButon);
		    mainLayeredPane.add(oPanelButon, Integer.valueOf(1));

		    JLabel sPanelMesaj = new JLabel("Şoför Paneli İçin :");
		    sPanelMesaj.setBounds(60, 130, 150, 25);
		    sPanelMesaj.setForeground(Color.WHITE);
		    mainLayeredPane.add(sPanelMesaj, Integer.valueOf(1));

		    JButton sPanelButon = new JButton("Şoför İşlemleri", foto);
		    sPanelButon.setBounds(220, 130, 150, 35);
		    butonDegistir(sPanelButon);
		    mainLayeredPane.add(sPanelButon, Integer.valueOf(1));

		    
		    // Otobüs İşlemleri Paneli
		    JPanel otobusPanel = new JPanel(null);
		    JLayeredPane otobusLayeredPane = new JLayeredPane();
		    otobusLayeredPane.setBounds(0, 0, 465, 220);
		    otobusPanel.add(otobusLayeredPane);

		    JLabel otobusBackground = new JLabel(tablo);
		    otobusBackground.setBounds(0, 0, 465, 220);
		    otobusLayeredPane.add(otobusBackground, Integer.valueOf(0));

		    JLabel otoEkleMesajPanel = new JLabel("Otobüs Eklemek İçin :");
		    otoEkleMesajPanel.setBounds(60, 50, 150, 25);
		    otoEkleMesajPanel.setForeground(Color.WHITE);
		    otobusLayeredPane.add(otoEkleMesajPanel, Integer.valueOf(1));

		    JButton otoEklePanelButon = new JButton("Otobüs Ekle", foto);
		    otoEklePanelButon.setBounds(220, 50, 150, 35);
		    butonDegistir(otoEklePanelButon);
		    otobusLayeredPane.add(otoEklePanelButon, Integer.valueOf(1));

		    JLabel otoSilMesajPanel = new JLabel("Otobüs Silmek İçin :");
		    otoSilMesajPanel.setBounds(60, 110, 150, 25);
		    otoSilMesajPanel.setForeground(Color.WHITE);
		    otobusLayeredPane.add(otoSilMesajPanel, Integer.valueOf(1));

		    JButton otoSilPanelButon = new JButton("Otobüs Sil", foto);
		    otoSilPanelButon.setBounds(220, 110, 150, 35);
		    butonDegistir(otoSilPanelButon);
		    otobusLayeredPane.add(otoSilPanelButon, Integer.valueOf(1));

		    JButton otoPanelGeri = new JButton(icon);
		    otoPanelGeri.setBounds(60, 140, 60, 60);
		    butonDegistir(otoPanelGeri);
		    otobusLayeredPane.add(otoPanelGeri, Integer.valueOf(1));
		    
		    
		    // Otobüs Ekleme Paneli
		    JPanel otobusEklePanel = new JPanel(null);
		    JLayeredPane otobusEkleLayeredPane = new JLayeredPane();
		    otobusEkleLayeredPane.setBounds(0, 0, 465, 220);
		    otobusEklePanel.add(otobusEkleLayeredPane);

		    JLabel otobusEkleBackground = new JLabel(tablo);
		    otobusEkleBackground.setBounds(0, 0, 465, 220);
		    otobusEkleLayeredPane.add(otobusEkleBackground, Integer.valueOf(0));

		    JLabel plakaLabel = new JLabel("Otobüs Plakası:");
		    plakaLabel.setBounds(60, 50, 150, 25);
		    plakaLabel.setForeground(Color.WHITE);
		    otobusEkleLayeredPane.add(plakaLabel, Integer.valueOf(1));

		    JTextField plakaField = new JTextField();
		    plakaField.setBounds(220, 50, 150, 25);
		    otobusEkleLayeredPane.add(plakaField, Integer.valueOf(1));

		    JLabel markaLabel = new JLabel("Otobüs Markası:");
		    markaLabel.setBounds(60, 100, 150, 25);
		    markaLabel.setForeground(Color.WHITE);
		    otobusEkleLayeredPane.add(markaLabel, Integer.valueOf(1));

		    JTextField markaField = new JTextField();
		    markaField.setBounds(220, 100, 150, 25);
		    otobusEkleLayeredPane.add(markaField, Integer.valueOf(1));

		    JButton otoEkleButton = new JButton("Ekle", foto);
		    otoEkleButton.setBounds(220, 150, 150, 35);
		    butonDegistir(otoEkleButton);
		    otobusEkleLayeredPane.add(otoEkleButton, Integer.valueOf(1));

		    JButton otoEkleGeriButton = new JButton(icon);
		    otoEkleGeriButton.setBounds(60, 140, 60, 60);
		    butonDegistir(otoEkleGeriButton);
		    otobusEkleLayeredPane.add(otoEkleGeriButton, Integer.valueOf(1));

		    
		    // Otobüs Silme Paneli
		    JPanel otobusSilPanel = new JPanel(null);
		    JLayeredPane otobusSilLayeredPane = new JLayeredPane();
		    otobusSilLayeredPane.setBounds(0, 0, 465, 220);
		    otobusSilPanel.add(otobusSilLayeredPane);
		    
		    JLabel otobusSilBackground = new JLabel(tablo);
		    otobusSilBackground.setBounds(0, 0, 465, 220);
		    otobusSilLayeredPane.add(otobusSilBackground, Integer.valueOf(0));
		    
		    JLabel silPlakaLabel = new JLabel("Silinecek Otobüs Plakası:");
		    silPlakaLabel.setBounds(60, 50, 150, 25);
		    silPlakaLabel.setForeground(Color.WHITE);
		    otobusSilLayeredPane.add(silPlakaLabel, Integer.valueOf(1));

		    otoSilComboBox = new JComboBox<>();
		    otoSilComboBox.setBounds(220, 50, 150, 25);
		    otobusSilLayeredPane.add(otoSilComboBox, Integer.valueOf(1));

		    JButton plakaSilButton = new JButton("Sil", foto);
		    plakaSilButton.setBounds(220, 150, 150, 35);
		    butonDegistir(plakaSilButton);
		    otobusSilLayeredPane.add(plakaSilButton, Integer.valueOf(1));

		    JButton otoSilGeriButton = new JButton(icon);
		    otoSilGeriButton.setBounds(60, 140, 60, 60);
		    butonDegistir(otoSilGeriButton);
		    otobusSilLayeredPane.add(otoSilGeriButton, Integer.valueOf(1));
		    
		    otobusPlakaYukle();
		    
		    
		    
		 // Şoför İşlemleri Paneli
		    JPanel soforPanel = new JPanel(null); // BorderLayout kullanıldı
		    JLayeredPane soforLayeredPane = new JLayeredPane();
		    soforLayeredPane.setBounds(0, 0, 465, 220); // Panelin boyutları

		    JLabel soforBackground = new JLabel(tablo); // Arka plan resmi
		    soforBackground.setBounds(0, 0, 465, 220);
		    soforLayeredPane.add(soforBackground, Integer.valueOf(0)); // En alt katmana ekle

		    // Şoför Paneli Bileşenleri
		    JLabel soforEkleMesaj = new JLabel("Şoför Eklemek İçin :");
		    soforEkleMesaj.setBounds(60, 50, 150, 25);
		    soforEkleMesaj.setForeground(Color.WHITE);
		    soforLayeredPane.add(soforEkleMesaj, Integer.valueOf(1));

		    JButton soforEkleButon = new JButton("Şoför Ekle", foto);
		    soforEkleButon.setBounds(220, 50, 150, 35);
		    butonDegistir(soforEkleButon);
		    soforLayeredPane.add(soforEkleButon, Integer.valueOf(1));

		    JLabel soforSilMesaj = new JLabel("Şoför Silmek İçin :");
		    soforSilMesaj.setBounds(60, 110, 150, 25);
		    soforSilMesaj.setForeground(Color.WHITE);
		    soforLayeredPane.add(soforSilMesaj, Integer.valueOf(1));

		    JButton soforSilButon = new JButton("Şoför Sil", foto);
		    soforSilButon.setBounds(220, 110, 150, 35);
		    butonDegistir(soforSilButon);
		    soforLayeredPane.add(soforSilButon, Integer.valueOf(1));

		    JButton soforPanelGeri = new JButton(icon);
		    soforPanelGeri.setBounds(60, 140, 60, 60);
		    butonDegistir(soforPanelGeri);
		    soforLayeredPane.add(soforPanelGeri, Integer.valueOf(1));

		    // LayeredPane'i panele ekle
		    soforPanel.add(soforLayeredPane);

		    // Şoför Ekleme Paneli
		    JPanel soforEklePanel = new JPanel(new BorderLayout());
		    JLayeredPane soforEkleLayeredPane = new JLayeredPane();
		    soforEkleLayeredPane.setBounds(0, 0, 465, 220);

		    JLabel soforEkleBackground = new JLabel(tablo);
		    soforEkleBackground.setBounds(0, 0, 465, 220);
		    soforEkleLayeredPane.add(soforEkleBackground, Integer.valueOf(0));

		    JLabel soforİsimLabel = new JLabel("Şoför Adı :");
		    soforİsimLabel.setBounds(60, 50, 150, 25);
		    soforİsimLabel.setForeground(Color.WHITE);
		    soforEkleLayeredPane.add(soforİsimLabel, Integer.valueOf(1));

		    JTextField soforİsimYaz = new JTextField();
		    soforİsimYaz.setBounds(220, 50, 150, 25);
		    soforEkleLayeredPane.add(soforİsimYaz, Integer.valueOf(1));

		    JLabel soforSoyadLabel = new JLabel("Şoför Soyadı : ");
		    soforSoyadLabel.setBounds(60, 100, 150, 25);
		    soforSoyadLabel.setForeground(Color.WHITE);
		    soforEkleLayeredPane.add(soforSoyadLabel, Integer.valueOf(1));

		    JTextField soforSoyadYaz = new JTextField();
		    soforSoyadYaz.setBounds(220, 100, 150, 25);
		    soforEkleLayeredPane.add(soforSoyadYaz, Integer.valueOf(1));

		    JButton soforEklePanelButton = new JButton("Ekle", foto);
		    soforEklePanelButton.setBounds(220, 150, 150, 35);
		    butonDegistir(soforEklePanelButton);
		    soforEkleLayeredPane.add(soforEklePanelButton, Integer.valueOf(1));

		    JButton soforEklePanelGeriButton = new JButton(icon);
		    soforEklePanelGeriButton.setBounds(60, 140, 60, 60);
		    butonDegistir(soforEklePanelGeriButton);
		    soforEkleLayeredPane.add(soforEklePanelGeriButton, Integer.valueOf(1));

		    soforEklePanel.add(soforEkleLayeredPane);

		    // Şoför Silme Paneli
		    JPanel soforSilPanel = new JPanel(new BorderLayout());
		    JLayeredPane soforSilLayeredPane = new JLayeredPane();
		    soforSilLayeredPane.setBounds(0, 0, 465, 220);

		    JLabel soforSilBackground = new JLabel(tablo);
		    soforSilBackground.setBounds(0, 0, 465, 220);
		    soforSilLayeredPane.add(soforSilBackground, Integer.valueOf(0));

		    JLabel silSoforPanelLabel = new JLabel("Silinecek Şoför : ");
		    silSoforPanelLabel.setBounds(60, 50, 150, 25);
		    silSoforPanelLabel.setForeground(Color.WHITE);
		    soforSilLayeredPane.add(silSoforPanelLabel, Integer.valueOf(1));

		    soforSilComboBox = new JComboBox<>();
		    soforSilComboBox.setBounds(220, 50, 180, 25);
		    soforSilLayeredPane.add(soforSilComboBox, Integer.valueOf(1));

		    JButton soforSilPanelButton = new JButton("Şoförü Sil", foto);
		    soforSilPanelButton.setBounds(220, 150, 150, 35);
		    butonDegistir(soforSilPanelButton);
		    soforSilLayeredPane.add(soforSilPanelButton, Integer.valueOf(1));

		    JButton soforSilPanelGeriButton = new JButton(icon);
		    soforSilPanelGeriButton.setBounds(60, 140, 60, 60);
		    butonDegistir(soforSilPanelGeriButton);
		    soforSilLayeredPane.add(soforSilPanelGeriButton, Integer.valueOf(1));

		    soforSilPanel.add(soforSilLayeredPane);
		    
		    soforİsimYukle();
		    
		    
		    //Main Panel CardLayout
		    panel.add(mainPanel, "main");
		    
		    //Otobus Panelleri CardLayout
		    panel.add(otobusPanel, "Otobusİslemleri");
		    panel.add(otobusEklePanel, "OtobusEkle");
		    panel.add(otobusSilPanel, "OtobusSil");
		    
		    
		    //Sofor Panelleri CardLayout
		    panel.add(soforPanel, "Soforİslemleri");
		    panel.add(soforEklePanel, "SoforEkle");
		    panel.add(soforSilPanel, "SoforSil");

		    
		    // Otobus Butonları
		    oPanelButon.addActionListener(e -> cl.show(panel, "Otobusİslemleri"));
		    otoPanelGeri.addActionListener(e -> cl.show(panel, "main"));
		    otoEklePanelButon.addActionListener(e -> cl.show(panel, "OtobusEkle"));
		    otoSilPanelButon.addActionListener(e -> cl.show(panel, "OtobusSil"));
		    otoEkleGeriButton.addActionListener(e -> cl.show(panel, "Otobusİslemleri"));
		    otoSilGeriButton.addActionListener(e -> cl.show(panel, "Otobusİslemleri"));
		    plakaSilButton.addActionListener(new ActionListener() {
		    	 @Override
		    	 public void actionPerformed(ActionEvent e) {
		    	     String plakaSec = (String) otoSilComboBox.getSelectedItem();
		    	     if (plakaSec != null) {
		    	         int onay = JOptionPane.showConfirmDialog(null, "Bu Seferi Silmek İstediğinize Emin Misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
		    	         if (onay == JOptionPane.YES_OPTION) {
		    	             boolean silindi = otobusSil(plakaSec);
		    	             if (silindi) {
		    	                 JOptionPane.showMessageDialog(null, "Sefer başarıyla silindi.");
		    	                 otoSilComboBox.removeItem(plakaSec); // Silinen plakayı ComboBox'tan kaldır
		    	                 cl.show(panel, "Otobusİslemleri");
		    	             } else {
		    	                 JOptionPane.showMessageDialog(null, "Sefer silme işlemi başarısız oldu.");
		    	             }
		    	         }
		    	     }
		    	 }
		    	});		
		    otoEkleButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
			        String plaka = plakaField.getText().trim();
			        String marka = markaField.getText().trim();
			
			        if (plaka.isEmpty() || marka.isEmpty()) {
			            JOptionPane.showMessageDialog(panel, "Plaka ve Marka boş bırakılamaz!", "Hata", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
			
			        boolean success = otobusEkle(plaka, marka);
			        if (success) {
			            JOptionPane.showMessageDialog(panel, "Otobüs başarıyla eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
			            plakaField.setText("");
			            markaField.setText("");
			            cl.show(panel, "Otobusİslemleri");
			        }					
				}
			});
		    
		    
		    //Sofor Butonları
		    sPanelButon.addActionListener(e -> cl.show(panel, "Soforİslemleri"));
		    soforPanelGeri.addActionListener(e -> cl.show(panel, "main"));
		    soforEkleButon.addActionListener(e -> cl.show(panel, "SoforEkle"));
		    soforSilButon.addActionListener(e -> cl.show(panel, "SoforSil"));
		    soforEklePanelGeriButton.addActionListener(e -> cl.show(panel, "Soforİslemleri"));
		    soforSilPanelGeriButton.addActionListener(e -> cl.show(panel, "Soforİslemleri"));	    
		    soforEklePanelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
			        String isim = soforİsimYaz.getText().trim();
			        String soyisim = soforSoyadYaz.getText().trim();
			
			        if (isim.isEmpty() || soyisim.isEmpty()) {
			            JOptionPane.showMessageDialog(panel, "İsim Ve Soyisim Boş Bırakılamaz !", "Hata", JOptionPane.ERROR_MESSAGE);
			            return;
			        }
			
			        boolean success = soforEkle(isim, soyisim);
			        if (success) {
			            JOptionPane.showMessageDialog(panel, "Şoför Başarıyla Eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
			            soforİsimYaz.setText("");
			            soforSoyadYaz.setText("");
			            cl.show(panel, "Soforİslemleri");
			        }
			        else {
			        	System.out.println(ERROR);
			        }
				}
			});
		    soforSilPanelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
		    	     String soforSec = (String) soforSilComboBox.getSelectedItem();
		    	     if (soforSec != null) {
		    	         int onay = JOptionPane.showConfirmDialog(null, "Bu Şoförü Silmek İstediğinize Emin Misiniz?", "Onay", JOptionPane.YES_NO_OPTION);
		    	         if (onay == JOptionPane.YES_OPTION) {
		    	             boolean silindi = soforSil(soforSec);
		    	             if (silindi) {
		    	                 JOptionPane.showMessageDialog(null, "Şöfor Başarıyla Silindi." , "Başarılı!", JOptionPane.INFORMATION_MESSAGE);
		    	                 soforSilComboBox.removeItem(soforSec); // Silinen plakayı ComboBox'tan kaldır
		    	                 cl.show(panel, "Soforİslemleri");
		    	             } else {
		    	                 JOptionPane.showMessageDialog(null, "Şoför Silme İşlemi Başarısız Oldu","Hata!", JOptionPane.ERROR_MESSAGE);
		    	             }
		    	         }
		    	     }
		    	 }					
				
			});
		    
		    return panel;
		}
		
		private JPanel üyeYolcuİslemleri() {
			
			// Panel ve katman düzeni
			CardLayout cl = new CardLayout();
			JPanel panel = new JPanel(cl);

			// Ana Panel
			JPanel mainPanel = new JPanel(null);
			JLayeredPane mainLayeredPane = new JLayeredPane();
			mainLayeredPane.setBounds(0, 0, 465, 220);
			mainPanel.add(mainLayeredPane);

			// Arka plan resmi
			JLabel backgroundLabel = new JLabel(tablo);
			backgroundLabel.setBounds(0, 0, 465, 220);
			mainLayeredPane.add(backgroundLabel, Integer.valueOf(0)); // En arka katman

			
			// Ana Panel içerikleri
			JLabel yPanelMesaj = new JLabel("Yolcu Paneli İçin :");
			yPanelMesaj.setBounds(60, 50, 150, 25);
			yPanelMesaj.setForeground(Color.WHITE);
			mainLayeredPane.add(yPanelMesaj, Integer.valueOf(1)); // Üst katman

			JButton yPanelButon = new JButton("Yolcu İşlemleri", foto);
			yPanelButon.setBounds(220, 50, 150, 35);
			butonDegistir(yPanelButon);
			mainLayeredPane.add(yPanelButon, Integer.valueOf(1)); // Üst katman

			JLabel uPanelMesaj = new JLabel("Üye Paneli İçin :");
			uPanelMesaj.setBounds(60, 130, 150, 25);
			uPanelMesaj.setForeground(Color.WHITE);
			mainLayeredPane.add(uPanelMesaj, Integer.valueOf(1)); // Üst katman

			JButton uPanelButon = new JButton("Üye İşlemleri", foto);
			uPanelButon.setBounds(220, 130, 150, 35);
			butonDegistir(uPanelButon);
			mainLayeredPane.add(uPanelButon, Integer.valueOf(1)); // Üst katman

			
			// Yolcu İşlemleri Paneli
			JPanel yolcuPanel = new JPanel(null);
			JLayeredPane yolcuLayeredPane = new JLayeredPane();
			yolcuLayeredPane.setBounds(0, 0, 465, 220);
			yolcuPanel.add(yolcuLayeredPane);

			JLabel yolcuBackground = new JLabel(tablo);
			yolcuBackground.setBounds(0, 0, 465, 220);
			yolcuLayeredPane.add(yolcuBackground, Integer.valueOf(0));

			JLabel yolcuDuzenlePanelMesaj = new JLabel("Yolcu Düzenlemek İçin :");
			yolcuDuzenlePanelMesaj.setBounds(60, 50, 150, 25);
			yolcuDuzenlePanelMesaj.setForeground(Color.WHITE);
			yolcuLayeredPane.add(yolcuDuzenlePanelMesaj, Integer.valueOf(1));

			JButton yolcuDuzenlePanelButon = new JButton("Yolcu Düzenle", foto);
			yolcuDuzenlePanelButon.setBounds(220, 50, 150, 35);
			butonDegistir(yolcuDuzenlePanelButon);
			yolcuLayeredPane.add(yolcuDuzenlePanelButon, Integer.valueOf(1));

			JLabel yolcuSilPanelMesaj = new JLabel("Yolcu Silmek İçin :");
			yolcuSilPanelMesaj.setBounds(60, 110, 150, 25);
			yolcuSilPanelMesaj.setForeground(Color.WHITE);
			yolcuLayeredPane.add(yolcuSilPanelMesaj, Integer.valueOf(1));

			JButton yolcuSilPanelButon = new JButton("Yolcu Sil", foto);
			yolcuSilPanelButon.setBounds(220, 110, 150, 35);
			butonDegistir(yolcuSilPanelButon);
			yolcuLayeredPane.add(yolcuSilPanelButon, Integer.valueOf(1));

			JButton yolcuPanelGeri = new JButton(icon);
			yolcuPanelGeri.setBounds(60, 140, 60, 60);
			butonDegistir(yolcuPanelGeri);
			yolcuLayeredPane.add(yolcuPanelGeri, Integer.valueOf(1));

			
			//Üye İşlemleri Paneli
			JPanel uyePanel = new JPanel(null);
			JLayeredPane uyeLayeredPane = new JLayeredPane();
			uyeLayeredPane.setBounds(0, 0, 465, 220);
			uyePanel.add(uyeLayeredPane);

			JLabel uyeBackground = new JLabel(tablo);
			uyeBackground.setBounds(0, 0, 465, 220);
			uyeLayeredPane.add(uyeBackground, Integer.valueOf(0));

			JLabel uyeSilPanelMesaj = new JLabel("Üye Silmek İçin :");
			uyeSilPanelMesaj.setBounds(60, 70, 150, 25);
			uyeSilPanelMesaj.setForeground(Color.WHITE);
			uyeLayeredPane.add(uyeSilPanelMesaj, Integer.valueOf(1));

			JButton uyeSilPanelButon = new JButton("Üye Sil", foto);
			uyeSilPanelButon.setBounds(220, 70, 150, 35);
			butonDegistir(uyeSilPanelButon);
			uyeLayeredPane.add(uyeSilPanelButon, Integer.valueOf(1));
			
			JButton uyePanelGeri = new JButton(icon);
			uyePanelGeri.setBounds(60, 140, 60, 60);
			butonDegistir(uyePanelGeri);
			uyeLayeredPane.add(uyePanelGeri, Integer.valueOf(1));
			
			
			//Üye Silme Paneli
			JPanel uyeSilPanel = new JPanel(null);
			JLayeredPane uyeSilLayeredPane = new JLayeredPane();
			uyeSilLayeredPane.setBounds(0, 0, 465, 220);
			uyeSilPanel.add(uyeSilLayeredPane);

			JLabel uyeSilBackground = new JLabel(tablo);
			uyeSilBackground.setBounds(0, 0, 465, 220);
			uyeSilLayeredPane.add(uyeSilBackground, Integer.valueOf(0));
			
			JLabel uyeSilMesaj = new JLabel("Silinecek Üyeyi Seçiniz :");
			uyeSilMesaj.setBounds(40,70,150,25);
			uyeSilMesaj.setForeground(Color.WHITE);
			uyeSilLayeredPane.add(uyeSilMesaj, Integer.valueOf(1));
			
			uyeSilComboBox = new JComboBox<>();
			uyeSilComboBox.setBounds(200,70,240,25);
			uyeSilLayeredPane.add(uyeSilComboBox, Integer.valueOf(1));
			
		    JButton uyeSilButon = new JButton("Üye Sil", foto);
		    uyeSilButon.setBounds(220, 150, 150, 35);
		    butonDegistir(uyeSilButon);
		    uyeSilLayeredPane.add(uyeSilButon, Integer.valueOf(1));

		    JButton uyeSilGeriButon = new JButton(icon);
		    uyeSilGeriButon.setBounds(60, 140, 60, 60);
		    butonDegistir(uyeSilGeriButon);
		    uyeSilLayeredPane.add(uyeSilGeriButon, Integer.valueOf(1));
			
			// Main Panel CardLayout
			panel.add(mainPanel, "main");
			
			
			//Yolcu Panelleri CardLayout
			panel.add(yolcuPanel, "Yolcuİşlemleri");
			
			
			//Üye Panelleri CardLayout
			panel.add(uyePanel, "Üyeİşlemleri");
			panel.add(uyeSilPanel, "ÜyeSilPanel");
			
			
			//Yolcu Butonları
			yPanelButon.addActionListener(e -> cl.show(panel, "Yolcuİşlemleri"));
			yolcuDuzenlePanelButon.addActionListener(e -> mainAdmin.PanelGöster("YolcuDuzenle"));
			yolcuSilPanelButon.addActionListener(e -> mainAdmin.PanelGöster("YolcuSil"));
			yolcuPanelGeri.addActionListener(e -> cl.show(panel,"main"));
			
			
			//Üye Butonları
			uPanelButon.addActionListener(e -> cl.show(panel, "Üyeİşlemleri"));
			uyePanelGeri.addActionListener(e -> cl.show(panel, "main"));
			uyeSilPanelButon.addActionListener(e -> cl.show(panel, "ÜyeSilPanel"));
			uyeSilGeriButon.addActionListener(e -> cl.show(panel, "Üyeİşlemleri"));
			uyeSilButon.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String uyeSec = (String) uyeSilComboBox.getSelectedItem();
					if(uyeSec != null) {
						int onay = JOptionPane.showConfirmDialog(null, "Üyeyi Silmek İstediğinize Emin Misiniz ?", "Onay", JOptionPane.YES_NO_OPTION);
						if(onay == JOptionPane.YES_OPTION) {
							uyeSil(uyeSec);
							uyeYukle();
						}
					}
				}
			});
			
			uyeYukle();
			
			return panel;

		}
		
		
		
		private boolean otobusEkle(String plaka, String marka) {
		    try (Connection connect = DatabaseConnection.getConnection()) {
		        if (connect == null) {
		            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
		            return false;
		        }

		        String sql = "INSERT INTO otobusler (plaka, marka) VALUES (?, ?)";
		        PreparedStatement statement = connect.prepareStatement(sql);
		        statement.setString(1, plaka);
		        statement.setString(2, marka);
		        int rowsInserted = statement.executeUpdate();

		        if (rowsInserted > 0) {
		        	otobusPlakaYukle();
		        }
		        	return true;
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
		        return false;
		    }
		}		

		private boolean otobusSil(String plaka) {
			 try (Connection connect = DatabaseConnection.getConnection()) {
			     if (connect == null) {
			         JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
			         return false;
			     }
	             int id = Integer.parseInt(plaka.split(" - ")[0]);
			     String sql = "DELETE FROM otobusler WHERE id = ?";
			     PreparedStatement statement = connect.prepareStatement(sql);
			     statement.setString(1, plaka);
			     int rowsDeleted = statement.executeUpdate();

			     return rowsDeleted > 0;
			 } catch (SQLException ex) {
			     ex.printStackTrace();
			     JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
			     return false;
			 }
		}

		private boolean otobusPlakaYukle() {
			otoSilComboBox.removeAllItems();
        	try(Connection connect = DatabaseConnection.getConnection()){
        		String sql = "SELECT id, plaka FROM otobusler";
        		PreparedStatement state = connect.prepareStatement(sql);
        		ResultSet rs = state.executeQuery();
        		
        		while(rs.next()) {
        			int id = rs.getInt("id");
        			String plaka = rs.getString("plaka");

        			
        			String plakaBilgi = id + " - " + plaka;
        			otoSilComboBox.addItem(plakaBilgi);
        		}
        		return true;
        	}catch(Exception e) {
        		e.printStackTrace();
        		JOptionPane.showMessageDialog(null, "Seferler Yüklenirken Hata Oluştu ! " + e.getMessage());
        		return false;
        	}
        }

		private boolean soforEkle(String isim, String soyisim) {
		    try (Connection connect = DatabaseConnection.getConnection()) {
		        if (connect == null) {
		            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
		            return false;
		        }

		        String sql = "INSERT INTO soforler (sofor_isim, sofor_soyisim) VALUES (?, ?)";
		        PreparedStatement statement = connect.prepareStatement(sql);
		        statement.setString(1, isim);
		        statement.setString(2, soyisim);
		        int rowsInserted = statement.executeUpdate();

		        if (rowsInserted > 0) {
		        	soforİsimYukle();
		        }return true;
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
		        return false;
		    }
		}

		private boolean soforSil(String isim) {
			 try (Connection connect = DatabaseConnection.getConnection()) {
			     if (connect == null) {
			         JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
			         return false;
			     }
	             int id = Integer.parseInt(isim.split(" - ")[0]);
			     String sql = "DELETE FROM soforler WHERE id = ?";
			     PreparedStatement statement = connect.prepareStatement(sql);
			     statement.setString(1, isim);
			     int rowsDeleted = statement.executeUpdate();

			     return rowsDeleted > 0;
			 } catch (SQLException ex) {
			     ex.printStackTrace();
			     JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
			     return false;
			 }
		}

		private boolean soforİsimYukle() {
			soforSilComboBox.removeAllItems();
        	try(Connection connect = DatabaseConnection.getConnection()){
        		String sql = "SELECT id, sofor_isim, sofor_soyisim FROM soforler";
        		PreparedStatement state = connect.prepareStatement(sql);
        		ResultSet rs = state.executeQuery();
        		
        		while(rs.next()) {
        			int id = rs.getInt("id");
        			String isim = rs.getString("sofor_isim");
        			String soyisim = rs.getString("sofor_soyisim");
        			
        			String soforBilgi = id + " - " + isim + " " + soyisim;
        			soforSilComboBox.addItem(soforBilgi);
        		}
        		return true;
        	}catch(Exception e) {
        		e.printStackTrace();
        		JOptionPane.showMessageDialog(null, "Soforler Yüklenirken Hata Oluştu ! " + JOptionPane.ERROR_MESSAGE);
        		return false;
        	}
		}

		private boolean uyeYukle() {
			uyeSilComboBox.removeAllItems();
			try(Connection connect = DatabaseConnection.getConnection()){
				String sql = "SELECT id, email, isim, soyisim FROM musteriler";
				PreparedStatement state = connect.prepareStatement(sql);
				ResultSet rs = state.executeQuery();
				
				while(rs.next()) {
		  			int id = rs.getInt("id");
        			String email = rs.getString("email");
        			String isim = rs.getString("isim");
        			String soyisim = rs.getString("soyisim");
        			
        			String uyeBilgi = id + " - " + email + " - " + isim + " " + soyisim;
        			uyeSilComboBox.addItem(uyeBilgi);
        		}
				return true;
			}catch(Exception e) {
	    		JOptionPane.showMessageDialog(null, "Üyeler Yüklenirken Hata Oluştu ! " + e.getMessage(), "HATA!", JOptionPane.ERROR_MESSAGE);

			}return false;
		}
		
		private boolean uyeSil(String uyeSec) {
        	try(Connection connect = DatabaseConnection.getConnection()){
                int id = Integer.parseInt(uyeSec.split(" - ")[0]);
                String sql = "DELETE FROM musteriler WHERE id = ?";
                PreparedStatement state = connect.prepareStatement(sql);
                state.setInt(1, id);
			    int rowsDeleted = state.executeUpdate();
                JOptionPane.showMessageDialog(null, "Üye Başarıyla Silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                
			    return rowsDeleted > 0;
        	} catch (Exception e) {
                e.printStackTrace();
        		JOptionPane.showMessageDialog(null, "Üye Silinirken Hata Oluştu ! " + e.getMessage(), "HATA!", JOptionPane.ERROR_MESSAGE);
            }
			return false;
		}
		
		
		
		private static void butonDegistir(JButton button) {
			
			button.setHorizontalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
			button.setVerticalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
			button.setForeground(Color.black); // Yazıyı beyaz yap
			button.setFont(new Font("Arial", Font.BOLD, 12)); // Yazı tipini ayarla
			button.setOpaque(false); // Şeffaflık
			button.setContentAreaFilled(false); // Dolgu olmadan fotoğraf kullan
			button.setFocusPainted(false); // Tıklama efekti kaldır
			button.setBorderPainted(false); // Kenarlıkları kaldır
		    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // El imleci
			
		}
		
}