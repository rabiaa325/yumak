
package yumak;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainFrame extends JFrame  {
     ImageIcon icon;
    public MainFrame(String title) throws IOException {
        super(title);

        setSize(1000, 555);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setFocusable(false);  //odağı frame den aldık  aksiyonlarımız framde değil game panel de olacak 
        setLocationRelativeTo(this);
        setLayout(null); 
        
         
        JPanel ArkaCerceve=new  JPanel();
        ArkaCerceve.setSize(getSize());
        ArkaCerceve.setLayout(null);
        icon = new ImageIcon("Background.jpg");
        JLabel background=new JLabel(icon);
        background.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());
        ArkaCerceve.add(background);
        //backgroundpanel şeklinde göster
        GamePanel OyunPanel = new GamePanel();
        
        OyunPanel.requestFocus();
        OyunPanel.addKeyListener(OyunPanel); 
        OyunPanel.setFocusable(true); //focusu panele veriyoruz
        OyunPanel.setLayout(null); // free design 
        OyunPanel.setOpaque(false); // saydam yapmamızın sebebi background umuzu görebilmek opaklığı true yapsaydık background un üstünde olacaktı
        OyunPanel.setBounds(0, 0, getWidth(), getHeight());        
        
        add(OyunPanel); 
        add(ArkaCerceve);//bu arka resimdeki görüntüyü göstermek için olmazsa boş gözükür
        
        setVisible(true);

    }
}
