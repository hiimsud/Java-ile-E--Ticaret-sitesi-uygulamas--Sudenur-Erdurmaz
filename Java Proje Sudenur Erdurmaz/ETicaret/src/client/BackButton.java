package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * @author busrayral
 */




public class BackButton extends JButton implements ActionListener {
    
	JMenuBar menuBar = new JMenuBar();


    public void initializeMenubar() {

        JMenu menu = new JMenu("MENU");

        menuBar.add(menu);

        JMenuItem homePage = new JMenuItem("ANA SAYFA");

        menu.add(homePage);


        homePage.addActionListener(e -> new BackButton().actionPerformed(e));
    }

    public BackButton() {
        super("ANASAYFAYA DÖN");
        setSize(50, 50);
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFrame frame = new DashBoard();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 312);
        frame.setVisible(true);
    }
}
