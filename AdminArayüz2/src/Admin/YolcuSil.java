package Admin;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Helper.DatabaseConnection;

public class YolcuSil extends JPanel{
    private JComboBox<String> yolcuComboBox;
    private JButton silButon;
    private JButton geriButon;

    ImageIcon foto = new ImageIcon("C://Users//ilhan//Desktop//beyazcıkısSon.png"); // Buton Arka Planı
    ImageIcon icon = new ImageIcon("C://Users//ilhan//Desktop//cıkıs8.png"); // Geri Tuşu Arka Planı
    ImageIcon arka = new ImageIcon("C://Users//ilhan//Desktop//bgg.jpg");	 // Ana Tablo Arka Planı
    ImageIcon tablo = new ImageIcon("C://Users//ilhan//Desktop//seferSil.png"); // Panel Arka Planı

   
	
	
	public YolcuSil(MainAdmin mainAdmin) {
		setLayout(null);
		
		
        // Ana Katman için JLayeredPane
        JLayeredPane mainLayeredPane = new JLayeredPane();
        mainLayeredPane.setBounds(0, 0, 485, 365);
        add(mainLayeredPane);

        // Ana Arka Plan Resmi
        JLabel backgroundLabel = new JLabel(arka); // arka, arka plan resmi
        backgroundLabel.setBounds(0, 0, 500, 400);
        mainLayeredPane.add(backgroundLabel, Integer.valueOf(0)); // En alt katman

        // Ana Panel Katmanlı Yapı
        JLayeredPane anaPanelLayeredPane = new JLayeredPane();
        anaPanelLayeredPane.setBounds(10, 30, 460, 280);

        JLabel anaPanelBackground = new JLabel(tablo); // panelTablo, ana panel arka plan resmi
        anaPanelBackground.setBounds(0, 0, 460, 280);
        anaPanelLayeredPane.add(anaPanelBackground, Integer.valueOf(0)); // Alt katman

        JPanel anaPanel = new JPanel(null);
        anaPanel.setBounds(0, 0, 460, 280);
        anaPanel.setOpaque(false); // Arka planı şeffaf yap
        anaPanel.setFont(new Font("Arial", Font.BOLD, 12));

        // Uyarı Mesajı
        JLabel notMesaj = new JLabel("UYARI: Yolcu Silerken Dikkatli Davranın. Yapılan İşlem Geri Alınamaz!");
        notMesaj.setFont(new Font("Arial", Font.BOLD, 12));
        notMesaj.setForeground(Color.WHITE);
        notMesaj.setBounds(40, 20, 400, 50);
        anaPanel.add(notMesaj);

        // Yolcu Seçim Label
        JLabel yolcuSec = new JLabel("Lütfen Silinecek Yolcuyu Seçin");
        yolcuSec.setFont(new Font("Arial", Font.BOLD, 12));
        yolcuSec.setForeground(Color.WHITE);
        yolcuSec.setBounds(140, 60, 200, 50);
        anaPanel.add(yolcuSec);

        // Yolcu Seçim ComboBox
        yolcuComboBox = new JComboBox<>();
        yolcuComboBox.setBounds(30, 130, 420, 30);
        anaPanel.add(yolcuComboBox);

        // Silme Butonu
        silButon = new JButton("Yolcuyu Sil", foto); // foto, buton resmi
        silButon.setBounds(250, 210, 150, 35);
        butonDegistir(silButon);
        anaPanel.add(silButon);

        // Geri Butonu
        geriButon = new JButton(icon); // icon, geri butonu resmi
        geriButon.setBounds(100, 200, 60, 60);
        butonDegistir(geriButon);
        anaPanel.add(geriButon);

        // Ana Paneli Katman Yapısına Ekle
        anaPanelLayeredPane.add(anaPanel, Integer.valueOf(1)); // Üst katmana ekle
        mainLayeredPane.add(anaPanelLayeredPane, Integer.valueOf(1)); // Ana katmana ekle
        
        yolcuYukle();
        
        geriButon.addActionListener(e -> mainAdmin.PanelGöster("AdminPanel"));
        silButon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String yolcuSec = (String) yolcuComboBox.getSelectedItem();
				if(yolcuSec != null) {
					int onay = JOptionPane.showConfirmDialog(null, "Yolcuyu Silmek İstediğinize Emin Misiniz ?", "Onay", JOptionPane.YES_NO_OPTION);
					if(onay == JOptionPane.YES_OPTION) {
						yolcuSil(yolcuSec);
						yolcuYukle();
					}
				}
			}
		});
	}

	private void yolcuYukle() {
		yolcuComboBox.removeAllItems();
		
		try(Connection connect = DatabaseConnection.getConnection()){
			String sql = "SELECT ID, tc_kimlik, isim, soyisim, koltuk_numara, sefer_id FROM yolcular";
			PreparedStatement state = connect.prepareStatement(sql);
			ResultSet rs = state.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("ID");
				String tc = rs.getString("tc_kimlik");
				String isim = rs.getString("isim");
				String soyisim = rs.getString("soyisim");
				int koltuk = rs.getInt("koltuk_numara");
				int sefer = rs.getInt("sefer_id");
				
				String yolcuBilgi = id+"- TC : " +tc + " Yolcu : " + isim + " " + soyisim + " - Sefer ID : " + sefer + " Koltuk : " + koltuk; 
				yolcuComboBox.addItem(yolcuBilgi);
			}
		
		}catch(Exception e) {
			e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Yolcular Yüklenirken Hata Oluştu ! " + e.getMessage(), "HATA!", JOptionPane.ERROR_MESSAGE);

		}
	}
	
	private void yolcuSil(String yolcuSec) {
		try (Connection connect = DatabaseConnection.getConnection()){
			int id = Integer.parseInt(yolcuSec.split("- TC : ")[0]);
			String sql = "DELETE FROM yolcular WHERE ID = ?";
			PreparedStatement state = connect.prepareStatement(sql);
			state.setInt(1, id);
			state.executeUpdate();
            JOptionPane.showMessageDialog(null, "Yolcu Başarıyla Silindi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);

		}catch(Exception e) {
			e.printStackTrace();
    		JOptionPane.showMessageDialog(null, "Yolcu Silinirken Hata Oluştu ! " + e.getMessage(), "HATA!", JOptionPane.ERROR_MESSAGE);

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
