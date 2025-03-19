package Admin;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import View.GirisGUI;

public abstract class MainAdmin extends JFrame {
    protected CardLayout cardLayout;  // Protected erişim seviyesi
    protected JPanel mainPanel;       // Protected erişim seviyesi

    protected AdminPanel adminPanel;
    protected SeferEkle seferEkle;
    protected SeferSil seferSilPanel;
    protected SeferDuzenle seferDuzenle;
    protected YolcuDuzenle yolcuDuzenle;
    protected YolcuSil yolcuSil;
    
    public MainAdmin() {
        setTitle("Kardeşler Turizm Admin Paneli");
        setResizable(false);
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setContentPane(mainPanel);
        initializePanels();
    }

    protected void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }

    protected void PanelGöster(String name) {
        cardLayout.show(mainPanel, name);
    }
    
	//SeferSil Panelini Güncelleyen Metod
	public void seferSilGüncelle() {
		seferSilPanel.seferYukle();
	}
	
	public void seferDuzenleGuncelle() {
		seferDuzenle.seferleriYukle();
	}
	
	public void cıkısYap() {
        GirisGUI girisGUI = new GirisGUI();
		dispose();
		girisGUI.setVisible(true);

	}

	
	
    protected abstract void initializePanels();
}
