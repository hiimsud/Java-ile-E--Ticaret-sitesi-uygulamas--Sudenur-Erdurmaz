package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dbconnet.DatabaseManager;
import Service.SepetService;



public class UrunListeleme extends JFrame {
    private DefaultListModel<Urun> urunListModel;
    private JList<Urun> urunList;
    private JTextField miktarTextField;
    private JButton sepeteEkleButton;

    public UrunListeleme() {
    
   	 final BackButton backButton = new BackButton();
     final JMenuBar jMenuBar = backButton.menuBar;
     setJMenuBar(jMenuBar);
     backButton.initializeMenubar();
	
	
        initializeUI();
        loadUrunler();
    }

    private void initializeUI() {
        setTitle("Ürün Listesi");
        setBounds(450, 200, 500, 312); //düzenlendi
       // setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        urunListModel = new DefaultListModel<>();
        urunList = new JList<>(urunListModel);
        JScrollPane scrollPane = new JScrollPane(urunList);

        miktarTextField = new JTextField(5);
        sepeteEkleButton = new JButton("Sepete Ekle");

        sepeteEkleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sepeteEkleButtonActionPerformed(e);
            }
        });

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Miktar: "));
        panel.add(miktarTextField);
        panel.add(sepeteEkleButton);

        add(panel, BorderLayout.SOUTH);
    }

    private void loadUrunler() {
        try (Connection connection = dbconnet.DatabaseManager.connect()) {
            String query = "SELECT * FROM tbl_urunler";
            try (PreparedStatement statement = connection.prepareStatement(query);
                 ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("urunID");
                    String tur = resultSet.getString("urunTuru");
                    String ad = resultSet.getString("urunAdi");
                    int miktar = resultSet.getInt("urunMiktar");
                    double fiyat = resultSet.getDouble("urunBirimFiyat");
                    String renk = resultSet.getString("urunRenk");

                    Urun urun = new Urun(id, tur, ad,miktar ,fiyat, renk);
                    urunListModel.addElement(urun);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void sepeteEkleButtonActionPerformed(ActionEvent event) {
        Urun selectedUrun = urunList.getSelectedValue();
        if (selectedUrun != null) {
            try {
                int miktar = Integer.parseInt(miktarTextField.getText());

                // Sepete ekleme işlemleri burada yapılacak
                SepetService.sepeteEkle(selectedUrun, miktar);

                // SepetClient'ta güncelleme yap
                SepetClient sepetClient = new SepetClient();
                sepetClient.updateTable(SepetService.getUrunList());
                sepetClient.setVisible(true);

                JOptionPane.showMessageDialog(this, miktar + " adet " + selectedUrun.getAd() +
                        " sepete eklendi.");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Lütfen miktarı sayı olarak girin.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UrunListeleme().setVisible(true);
            }
        });
    }
}
