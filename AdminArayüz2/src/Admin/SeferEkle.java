package Admin;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Helper.DatabaseConnection;

public class SeferEkle extends JPanel{
 
	private static final long serialVersionUID = 1L;
	private JComboBox<String> plakaComboBox;
	private JComboBox<String> soforComboBox;
	private JComboBox<String> baslangıcComboBox;
	private JComboBox<String> bitisComboBox;
	private JTextField  tarihField, saatField;
	private JButton ekleButon, geriButon;
	private MainAdmin mainApp;
	
    ImageIcon buton = new ImageIcon("C://Users//ilhan//Desktop//beyazcıkısSon.png"); // Buton Arka Planı
    ImageIcon cıkıs = new ImageIcon("C://Users//ilhan//Desktop//cıkıs8.png"); // Geri Tuşu Arka Planı
    ImageIcon arka = new ImageIcon("C://Users//ilhan//Desktop//bgg.jpg");	 // Ana Tablo Arka Planı 
    ImageIcon tablo = new ImageIcon("C://Users//ilhan//Desktop//seferEkle.png"); // Panel Arka Planı

	
	public SeferEkle(MainAdmin mainApp) {
		this.mainApp = mainApp;
		
		setLayout(null);

		// Katmanlı Yapı için JLayeredPane
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 500, 400);
		add(layeredPane);

		// Arka Plan Resmi
		JLabel backgroundLabel = new JLabel(arka); //  Arka Plan Resmi
		backgroundLabel.setBounds(0, 0, 500, 400);
		layeredPane.add(backgroundLabel, Integer.valueOf(0)); // En Alt Katman

		// Bileşenler için Panel Katmanı
		JLayeredPane panelLayeredPane = new JLayeredPane();
		panelLayeredPane.setBounds(10, 10, 430, 335);

		// Panel için Arka Plan Resmi
		JLabel panelBackground = new JLabel(tablo); // Panel Resmi 
		panelBackground.setBounds(20, 10, 430, 335);
		panelLayeredPane.add(panelBackground, Integer.valueOf(0)); // Alt Katman

		// Şeffaf Panel
		JPanel panel = new JPanel(null);
		panel.setBounds(20, 10, 430, 335);
		panel.setOpaque(false); // Arka planı şeffaf yap

		// Bileşenler
		JLabel plakaLabel = new JLabel("Otobüs Plakası :");
		plakaLabel.setBounds(30, 30, 150, 25);
		plakaLabel.setForeground(Color.WHITE);
		panel.add(plakaLabel);

		plakaComboBox = new JComboBox<>();
		plakaComboBox.setBounds(180, 30, 200, 25);
		panel.add(plakaComboBox);

		JLabel soforLabel = new JLabel("Şoför :  ");
		soforLabel.setBounds(30, 70, 150, 25);
		soforLabel.setForeground(Color.WHITE);
		panel.add(soforLabel);

		soforComboBox = new JComboBox();
		soforComboBox.setBounds(180, 70, 200, 25);
		panel.add(soforComboBox);

		JLabel baslangıcLabel = new JLabel("Başlangıç Güzergahı :");
		baslangıcLabel.setBounds(30, 110, 150, 25);
		baslangıcLabel.setForeground(Color.WHITE);
		panel.add(baslangıcLabel);

		baslangıcComboBox = new JComboBox();
		baslangıcComboBox.setBounds(180, 110, 200, 25);
		panel.add(baslangıcComboBox);

		JLabel bitisLabel = new JLabel("Bitiş Güzergahı :");
		bitisLabel.setBounds(30, 150, 150, 25);
		bitisLabel.setForeground(Color.WHITE);
		panel.add(bitisLabel);

		bitisComboBox = new JComboBox();
		bitisComboBox.setBounds(180, 150, 200, 25);
		panel.add(bitisComboBox);

		JLabel tarihLabel = new JLabel("Tarih (YYYY-MM-DD) :");
		tarihLabel.setBounds(30, 190, 150, 25);
		tarihLabel.setForeground(Color.WHITE);
		panel.add(tarihLabel);

		tarihField = new JTextField();
		tarihField.setBounds(180, 190, 200, 25);
		panel.add(tarihField);

		JLabel saatLabel = new JLabel("Saat (HH:MM):");
		saatLabel.setBounds(30, 230, 150, 25);
		saatLabel.setForeground(Color.WHITE);
		panel.add(saatLabel);

		saatField = new JTextField();
		saatField.setBounds(180, 230, 200, 25);
		panel.add(saatField);

		ekleButon = new JButton("Sefer Ekle",buton);
		ekleButon.setBounds(180, 275, 150, 35);
		butonDegistir(ekleButon);
		panel.add(ekleButon);

		geriButon = new JButton(cıkıs);
		geriButon.setBounds(80, 260, 60, 60);
		butonDegistir(geriButon);
		panel.add(geriButon);

		// Paneli Katman Yapısına Ekle
		panelLayeredPane.add(panel, Integer.valueOf(1)); // Üst katmana ekle
		layeredPane.add(panelLayeredPane, Integer.valueOf(1)); // Ana katmana ekle

        
        plakaYukle();
        soforYukle();
        sehirYukle();
        
        ekleButon.addActionListener(e -> seferEkle());
        geriButon.addActionListener(e -> mainApp.PanelGöster("AdminPanel"));
	}
	
	private void plakaYukle() {

	    try (Connection connect = DatabaseConnection.getConnection()) {
	        if (connect == null) {
	            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız.");
	            return;
	        }

	        // SQL sorgusunu güncelle
	        String sql = "SELECT plaka FROM otobusler";

	        PreparedStatement stmt = connect.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        // ComboBox'a plakaları ekle
	        while (rs.next()) {
	            plakaComboBox.addItem(rs.getString("plaka"));
	        }
	        
	       // JOptionPane.showMessageDialog(null, "Plakalar başarıyla yüklendi!");
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Otobüs plakaları yüklenirken hata oluştu: " + e.getMessage());
	    }
	}

	private void soforYukle() {
	    try (Connection connect = DatabaseConnection.getConnection()) {
	        if (connect == null) {
	            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız.");
	            return;
	        }

	        String sql = "SELECT sofor_isim, sofor_soyisim FROM soforler"; // Şoför isimlerini çeken sorgu
	        PreparedStatement state = connect.prepareStatement(sql);
	        ResultSet rs = state.executeQuery();

	        while (rs.next()) {
	        	String soforisim = rs.getString("sofor_isim");
	        	String soforSoyisim = rs.getString("sofor_soyisim");
	        	String soforTamAd = soforisim + " " + soforSoyisim;
	            soforComboBox.addItem(soforTamAd);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Şoför bilgileri yüklenirken hata oluştu: " + e.getMessage());
	    }
	    
	    baslangıcComboBox.addActionListener(e -> {
	        String selectedBaslangic = (String) baslangıcComboBox.getSelectedItem();
	        bitisComboBox.removeAllItems();

	        // Diğer şehri bitiş ComboBox'ına ekle
	        for (int i = 0; i < baslangıcComboBox.getItemCount(); i++) {
	            String sehir = baslangıcComboBox.getItemAt(i);
	            if (!sehir.equals(selectedBaslangic)) {
	                bitisComboBox.addItem(sehir);
	            }
	        }
	    });
	}

	private void sehirYukle() {
	    try (Connection connect = DatabaseConnection.getConnection()) {
	        if (connect == null) {
	            JOptionPane.showMessageDialog(null, "Veritabanı bağlantısı başarısız.");
	            return;
	        }

	        String sql = "SELECT sehir_adi FROM sehirler";
	        PreparedStatement state = connect.prepareStatement(sql);
	        ResultSet rs = state.executeQuery();

	        while (rs.next()) {
	            String sehirAdi = rs.getString("sehir_adi");
	            baslangıcComboBox.addItem(sehirAdi);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Şehir bilgileri yüklenirken hata oluştu: " + e.getMessage());
	    }

	}
	
	private void seferEkle() {
	    String plaka = (String) plakaComboBox.getSelectedItem();
	    String sofor = (String) soforComboBox.getSelectedItem();
	    String baslangic = (String) baslangıcComboBox.getSelectedItem();
	    String bitis = (String) bitisComboBox.getSelectedItem();
	    String tarih = tarihField.getText();
	    String saat = saatField.getText();
	    
	    if (plaka == null || sofor.isEmpty() || baslangic.isEmpty() || bitis.isEmpty() || tarih.isEmpty() || saat.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Lütfen Tüm Alanları Doldurun");
	        return;
	    }
	    
	    try (Connection connect = DatabaseConnection.getConnection()) {
	        String sql = "INSERT INTO seferler(plaka, şoför, güzergah, sefer_tarihi, sefer_saati) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement state = connect.prepareStatement(sql);

	        // Değerlerin doğru sırayla ayarlanması:
	        state.setString(1, plaka);                          // Plaka
	        state.setString(2, sofor);                          // Şoför
	        state.setString(3, baslangic + " - " + bitis);      // Güzergah (Başlangıç - Bitiş)
	        state.setString(4, tarih);                          // Tarih
	        state.setString(5, saat);                           // Saat

	        state.executeUpdate();
	        JOptionPane.showMessageDialog(null, "Sefer Başarıyla Eklendi");
	        mainApp.seferDuzenleGuncelle();
	        mainApp.seferDuzenleGuncelle();
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Sefer Eklenirken Hata Oluştu: " + e.getMessage());
	    }
	}

	private static void butonDegistir(JButton button) {
		
		button.setHorizontalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
		button.setVerticalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
		button.setForeground(Color.BLACK); // Yazıyı beyaz yap
		button.setFont(new Font("Arial", Font.BOLD, 12)); // Yazı tipini ayarla
		button.setOpaque(false); // Şeffaflık
		button.setContentAreaFilled(false); // Dolgu olmadan fotoğraf kullan
		button.setFocusPainted(false); // Tıklama efekti kaldır
		button.setBorderPainted(false); // Kenarlıkları kaldır
	    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // El imleci
		
	}

}
