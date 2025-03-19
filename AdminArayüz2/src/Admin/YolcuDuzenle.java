package Admin;

import javax.swing.*;

import Helper.DatabaseConnection;


import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class YolcuDuzenle extends JPanel {
	
	private JComboBox<String> duzenleComboBox, koltukComboBox, seferComboBox;
	private MainAdmin mainAdmin;
	private JTextField koltukNoField = new JTextField();
	private JTextField seferIdField = new JTextField();
	private JLabel yolcuBilgi = new JLabel();
    
    ImageIcon foto = new ImageIcon("C://Users//ilhan//Desktop//beyazcıkısSon.png"); // Buton Arka Planı
    ImageIcon icon = new ImageIcon("C://Users//ilhan//Desktop//cıkıs8.png"); // Geri Tuşu Arka Planı 
    ImageIcon arka = new ImageIcon("C://Users//ilhan//Desktop//bgg.jpg");	 // Ana Tablo Arka Planı
    ImageIcon tablo = new ImageIcon("C://Users//ilhan//Desktop//seferDuzenle.png"); // Panel Arka Planı

    public YolcuDuzenle(MainAdmin mainAdmin) {
        this.mainAdmin = mainAdmin;
        setLayout(null);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ana Katman için JLayeredPane
        JLayeredPane mainLayeredPane = new JLayeredPane();
        mainLayeredPane.setBounds(0, 0, 480, 380);

        // Ana Arka Plan Resmi
        JLabel mainBackground = new JLabel(arka); // tablo, ana arka plan resmi
        mainBackground.setBounds(0, 0, 480, 380);
        mainLayeredPane.add(mainBackground, Integer.valueOf(0)); // En alt katman
        

        // Düzenleme Formu Katmanlı Yapı
        JLayeredPane duzenlePanelLayeredPane = new JLayeredPane();
        duzenlePanelLayeredPane.setBounds(10, 20, 460, 320);

        JLabel duzenlePanelBackground = new JLabel(tablo); // panelTablo, form paneli arka plan resmi
        duzenlePanelBackground.setBounds(0, 0, 460, 320);
        duzenlePanelLayeredPane.add(duzenlePanelBackground, Integer.valueOf(0));

        JPanel duzenlePanel = new JPanel(null);
        duzenlePanel.setBounds(0, 0, 460, 320);
        duzenlePanel.setOpaque(false); // Arka planı şeffaf yap

        
        //Ana Bileşenler 
        JLabel duzenleMesaj = new JLabel("Düzenlenecek Yolcuyu Seçiniz:");
        duzenleMesaj.setBounds(30,40,200,25);
        duzenleMesaj.setForeground(Color.WHITE);
        duzenlePanel.add(duzenleMesaj);
        
        duzenleComboBox = new JComboBox<>();
        duzenleComboBox.setBounds(230,40,220,25);
        duzenlePanel.add(duzenleComboBox);
        
        JLabel yolcuBilgiMesaj = new JLabel("Yolcu Bilgileri :");
        yolcuBilgiMesaj.setBounds(30, 83, 150, 25);
        yolcuBilgiMesaj.setForeground(Color.WHITE);
        duzenlePanel.add(yolcuBilgiMesaj);

        yolcuBilgi = new JLabel();
        yolcuBilgi.setBounds(230, 83, 250, 25);
        yolcuBilgi.setForeground(Color.WHITE);
        duzenlePanel.add(yolcuBilgi);
        
        
        JLabel seferMesaj = new JLabel("Sefer ID Seçiniz : ");
        seferMesaj.setBounds(30,127,200,25);
        seferMesaj.setForeground(Color.WHITE);
        duzenlePanel.add(seferMesaj);
        
        seferComboBox = new JComboBox<>();
        seferComboBox.setBounds(230,127,100,25);
        duzenlePanel.add(seferComboBox);
        
        JLabel koltukMesaj = new JLabel("Koltuk Numarasını Seçiniz : ");
        koltukMesaj.setBounds(30,170,200,25);
        koltukMesaj.setForeground(Color.WHITE);
        duzenlePanel.add(koltukMesaj);
        
        koltukComboBox = new JComboBox<>();
        koltukComboBox.setBounds(230,170,100,25);
        duzenlePanel.add(koltukComboBox);
        
        JButton kaydetButton = new JButton("Kaydet",foto);
        kaydetButton.setBounds(230, 250, 150, 35);
        butonDegistir(kaydetButton);
        //kaydetButton.addActionListener(e -> kaydet());
        duzenlePanel.add(kaydetButton);

        JButton geriButton = new JButton(icon);
        geriButton.setBounds(80, 240, 60, 60);
        butonDegistir(geriButton);
        duzenlePanel.add(geriButton);

        
        duzenlePanelLayeredPane.add(duzenlePanel, Integer.valueOf(1)); // Üst katmana ekle
        mainLayeredPane.add(duzenlePanelLayeredPane, Integer.valueOf(1)); // Ana katmana ekle

        // Ana katmanı ekle
        add(mainLayeredPane);
        
        yolcuVeriYukle();
        seferleriYukle();
        
        
        duzenleComboBox.addActionListener(e -> yolcuBilgiYukle());
        seferComboBox.addActionListener(e -> koltuklariYukle());
        geriButton.addActionListener(e -> mainAdmin.PanelGöster("AdminPanel"));
        kaydetButton.addActionListener(e -> kaydet());

    }

    private void yolcuVeriYukle() {
        duzenleComboBox.removeAllItems();
        try {
            Connection connect = DatabaseConnection.getConnection();
            Statement st = connect.createStatement();
            ResultSet rs = st.executeQuery("SELECT tc_kimlik, isim, soyisim FROM yolcular");

            while (rs.next()) {
                String tc = rs.getString("tc_kimlik");
                String isimSoyisim = rs.getString("isim") + " " + rs.getString("soyisim");
                duzenleComboBox.addItem(tc + " - " + isimSoyisim);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void yolcuBilgiYukle() {
        String selectedYolcu = (String) duzenleComboBox.getSelectedItem();
        if (selectedYolcu == null || selectedYolcu.isEmpty())
            return;

        // Seçilen TC Kimlik No'sunu ayırarak almak
        String tcKimlik = selectedYolcu.split(" - ")[0];

        try (Connection connect = DatabaseConnection.getConnection()) {
            String sql = "SELECT koltuk_numara, sefer_id FROM yolcular WHERE tc_kimlik = ?";
            PreparedStatement state = connect.prepareStatement(sql);
            state.setString(1, tcKimlik);
            ResultSet rs = state.executeQuery();

            if (rs.next()) {
                String koltukNo = rs.getString("koltuk_numara");
                String seferID = rs.getString("sefer_id");
                yolcuBilgi.setText("Sefer ID: " + seferID + " - Koltuk No: " + koltukNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void seferleriYukle() {
        seferComboBox.removeAllItems(); // Mevcut öğeleri temizler
        try (Connection connect = DatabaseConnection.getConnection()) {
            String sql = "SELECT DISTINCT id FROM seferler"; // sefer_id değerlerini çeker
            PreparedStatement state = connect.prepareStatement(sql);
            ResultSet rs = state.executeQuery();

            while (rs.next()) {
                seferComboBox.addItem(rs.getString("id")); // Her sefer_id'yi comboBox'a ekler
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void koltuklariYukle() {
        koltukComboBox.removeAllItems(); // Mevcut öğeleri temizler
        String selectedSeferId = (String) seferComboBox.getSelectedItem(); // Seçilen sefer_id'yi al

        if (selectedSeferId == null) {
            return; // Sefer seçilmediyse çık
        }

        try (Connection connect = DatabaseConnection.getConnection()) {
            // Sefer_id'ye göre alınmış koltukları al
            String sql = "SELECT koltuk_numara FROM yolcular WHERE sefer_id = ?";
            PreparedStatement state = connect.prepareStatement(sql);
            state.setString(1, selectedSeferId); // Sefer_id parametresini ayarla
            ResultSet rs = state.executeQuery();

            // Alınan koltukları bir listeye ekle
            boolean[] doluKoltuklar = new boolean[30];
            while (rs.next()) {
                int koltukNumara = rs.getInt("koltuk_numara");
                if (koltukNumara > 0 && koltukNumara <= 30) {
                    doluKoltuklar[koltukNumara - 1] = true; // Koltuk numarasını işaretle
                }
            }

            // Boş koltukları combobox'a ekle
            for (int i = 0; i < 30; i++) {
                if (!doluKoltuklar[i]) {
                    koltukComboBox.addItem(String.valueOf(i + 1)); // Boş koltuk numaralarını ekle
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void kaydet() {
        String selectedYolcu = (String) duzenleComboBox.getSelectedItem(); // Seçilen yolcuyu al
        String selectedSeferId = (String) seferComboBox.getSelectedItem(); // Seçilen sefer_id'yi al
        String selectedKoltukNumara = (String) koltukComboBox.getSelectedItem(); // Seçilen koltuk numarasını al

        if (selectedYolcu == null || selectedSeferId == null || selectedKoltukNumara == null) {
            JOptionPane.showMessageDialog(this, "Lütfen tüm bilgileri seçiniz.", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connect = DatabaseConnection.getConnection()) {
            String tcKimlik = selectedYolcu.split(" - ")[0]; // tc_kimlik bilgisini ayıkla
            
            String sql = "UPDATE yolcular SET sefer_id = ?, koltuk_numara = ? WHERE tc_kimlik = ?";
            PreparedStatement state = connect.prepareStatement(sql);
            state.setString(1, selectedSeferId); // Yeni sefer_id'yi ayarla
            state.setString(2, selectedKoltukNumara); // Yeni koltuk numarasını ayarla
            state.setString(3, tcKimlik); // Seçilen yolcunun tc_kimlik numarasını ayarla
            
            int rowsUpdated = state.executeUpdate();
            
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Yolcu bilgileri başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                yolcuVeriYukle(); // Verileri yeniden yükle
            } else {
                JOptionPane.showMessageDialog(this, "Yolcu bilgileri güncellenemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Bir hata oluştu. Lütfen tekrar deneyin.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    
	private static void butonDegistir(JButton button) {
		
		button.setHorizontalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
		button.setVerticalTextPosition(SwingConstants.CENTER); // Yazıyı merkeze hizala
		button.setForeground(Color.BLACK); // Yazıyı Siyah yap
		button.setFont(new Font("Arial", Font.BOLD, 12)); // Yazı tipini ayarla
		button.setOpaque(false); // Şeffaflık
		button.setContentAreaFilled(false); // Dolgu olmadan fotoğraf kullan
		button.setFocusPainted(false); // Tıklama efekti kaldır
		button.setBorderPainted(false); // Kenarlıkları kaldır
	    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // El imleci
		
	}
}