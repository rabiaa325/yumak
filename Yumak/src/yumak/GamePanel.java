package yumak;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener {//(KeyListener klavye hareketleri kontrolü)
//,ActionListener olmazsa aşagı yukarı olmuyo

    int delay = 100; // Timer in yenilenme sÄ±klÄ±ÄŸÄ±
    Timer timer = new Timer(delay, this);

    BufferedImage yumak;
    BufferedImage kuruKafa;
    BufferedImage up;
    BufferedImage down;

    private int yumakKatsayısı = 1;//başlangıcta
    private int yumakBoyutCarpanı = 10;
    private int yumakX =150; //baÅŸlangÄ±Ã§
    private int yumakY = 190; //baÅŸlangÄ±Ã§
    private int yumakDegisim = 10;  //hareket pixel deÄŸiÅŸimi   
    private int yumakKalanÖmür = 100;//başlangıc ömrü
     private int kuruKafaX = 0; //baÅŸlangÄ±Ã§
    private int kuruKafaY = 190; //baÅŸlangÄ±Ã§
    private int upX = 220; //baÅŸlangÄ±Ã§
    private int upY = 400;
    private int downX = 160; //baÅŸlangÄ±Ã§
    private int downY = 50;

    private int yumakTotalSüresi = 1;//1 den başlama sebebi
    private int enYüksekSkor = 0;

    private int yakalananSariTopKatsayısı;

    private int saniyeSayaci = 0;
    private int sarıTopUretimSayaci = 0;
    private int kuruKafaUretimSayaci = 0;
    private int asagıOKUretimSayaci = 0;
    private int bonusSayaci = 0;

    JLabel gecenSure = new JLabel();
    JLabel yumakOmru = new JLabel();
    JLabel highScore = new JLabel();
    JLabel bonus = new JLabel();

    ArrayList<RoundedPanel> toplar = new ArrayList();
    ArrayList<kuruKafa> kuruKafalar = new ArrayList();
    ArrayList<up> yukarıOK = new ArrayList();
    ArrayList<down> asagıOK = new ArrayList();

    public GamePanel() {
//throws IOException
//input output ile resimlerimizi okuduk
        try {
            yumak = ImageIO.read(new File("yumak.png"));
            kuruKafa = ImageIO.read(new File("kuruKafa.png"));
            up = ImageIO.read(new File("up.png"));
            down = ImageIO.read(new File("down.png"));
        } catch (IOException ex) {
            JOptionpane.showMessageDialog(this, "Resim Dosyaları Bulunamadi!");
            System.exit(0);
        }
        labelYerlestir();//label kodda düzensizlik yaratmaması için
        sarıTopUret();//başlangıc  için hemen ölmesin diye 1 kereliğine mahsus
        timer.start();

    }
//aşagısı otomatik geldi KeyListener{//(KeyListener klavye hareketleri kontrolü) yapıldığından

    public void labelYerlestir(String yumakTotalSure) throws IOException {
        yumakOmru.setOpaque(true);
        yumakOmru.setBackground(Color.DARK_GRAY);
        yumakOmru.setBounds(0, 501, 150, 25);
        yumakOmru.setForeground(Color.WHITE);
        yumakOmru.setHorizontalAlignment(SwingConstants.CENTER);
        String yumakKalanOmur = null;
        yumakOmru.setText("Kalan Ömür:" + yumakKalanOmur);

        gecenSure.setOpaque(true);
        gecenSure.setBackground(Color.DARK_GRAY);
        gecenSure.setBounds(150, 501, 650, 25);
        gecenSure.setForeground(Color.WHITE);
        gecenSure.setHorizontalAlignment(SwingConstants.CENTER);
        gecenSure.setText("toplam gecen Sure:" + yumakTotalSure);

        readHS();//high scorunu okuduk ve en yüksek  en yüksek skor adlı değişkene attok

        highScore.setOpaque(true);
        highScore.setBackground(Color.DARK_GRAY);
        highScore.setBounds(800, 501, 200, 25);
        highScore.setForeground(Color.WHITE);
        highScore.setHorizontalAlignment(SwingConstants.CENTER);
        highScore.setText("en yüksek skor:" + enYüksekSkor);

        bonus.setBounds(400, 20, 200, 35);
        bonus.setForeground(new Color(024, 102, 255));
        bonus.setFont(new Font("Arial", 0, 25));
        bonus.setHorizontalAlignment(SwingConstants.CENTER);
        bonus.setText("bonus zamanı");
        bonus.setVisible(false);//bonuse zamanını true kayacağız

        add(bonus);
        add(yumakOmru);
        add(gecenSure);
        add(highScore);

    }
      public void readHS() throws FileNotFoundException, IOException {
          try(BufferedReader fileReader=new BufferedReader(new FileReader("Highscore.txt")) ;)
          {
             enYüksekSkor=Integer.valueOf(fileReader.readLine());
          }
          catch (FileNotFoundException ex)
      {
         JOptionPane.showMessageDialog(this,"Dosya Bulunamadı") ;
         ex.printStackTrace();
         System.exit(0);
      }catch ( IOException ex){
          JOptionPane.showMessageDialog(this,"İO exception oluştu") ;
         ex.printStackTrace();
      }
      }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.CYAN);
        g.drawRect(0, 0, 993, 500);

        g.drawImage(yumak, yumakX, yumakY, yumak.getWidth(), yumak.getHeight(), this);
          g.drawImage(kuruKafa, kuruKafaX, kuruKafaY, kuruKafa.getWidth(), kuruKafa.getHeight(), this);
          g.drawImage(up, upX, upY, up.getWidth(), up.getHeight(), this);
          g.drawImage(down, downX, downY, down.getWidth(), down.getHeight(), this);
    }
   
    {
        
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {//e hangi tuşa basıldığını söyler keyPressed butona basma

        int c = e.getKeyCode();

        switch (c) {
            case KeyEvent.VK_UP:
                if (yumakY > 0) {
                    yumakY -= yumakDegisim;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (yumakY < 365) {
                    yumakY += yumakDegisim;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (yumakX < 860) {
                    yumakX += yumakDegisim;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (yumakX > 10) {
                    yumakX -= yumakDegisim;
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {//keyTyped  yazıyazıldığında
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.//işlam istenmiyo
    }

    @Override
    public void keyReleased(KeyEvent e) {//keyReleased butondan parmak kaldırma
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

  
 

    

    private void labelYerlestir() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void sarıTopUret() {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class kuruKafa {

        public kuruKafa() {
        }
    }

    private static class RoundedPanel {

        public RoundedPanel() {
        }
    }

    private static class up {

        public up() {
        }
    }

    private static class down {

        public down() {
        }
    }

    private static class JOptionpane {

        private static void showMessageDialog(GamePanel aThis, String resim_Dosyaları_Bulunamadi) {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        public JOptionpane() {
        }
    }

    private static class fileReader extends Reader {

        public fileReader(String highscoretxt) {
        }

        @Override
        public int read(char[] cbuf, int off, int len) throws IOException {
           throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    

        @Override
        public void close() throws IOException {
           // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private static class FileNotFoundExcepten {

        public FileNotFoundExcepten() {
        }
    }

}
