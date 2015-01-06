import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by mq on 2014-12-20.
 */
public class GUI extends JFrame{
    private int panelHeight=450;
    private int panelWidth=950;
    public GUI(){
        setTitle("13 rätt är respekt");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        Datapanel panel =new Datapanel();
        add(panel);
        setSize(panelWidth,panelHeight);
        setVisible(true);
        setResizable(false);
        try{
            setIconImage(ImageIO.read(new File("svs.jpg")));
        }catch (Exception e){}

        //pack();
    }

    private class Datapanel extends JPanel{
        Color bc= new Color(30,32,33);
        GameData gamedata;
        Datahandler datahandler;
        Data data;
        private JSlider slider;
        private JTextField halv;
        private JTextField hel;
        private JTextField antr;
        Image bgImage = Toolkit.getDefaultToolkit().createImage("bd.jpg");
        public Datapanel(){
            setPreferredSize(new Dimension(panelWidth,panelHeight));
            setLayout(null);
            setBackground(bc);
            data=new Data();
            datahandler=new Datahandler();
            setEU();
            initMenu();
            initslider();
            initTextbox();
            initlabels();
            initButtons();
            repaint();
        }

        private void initMenu(){
            JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            JMenu fileMenu = new JMenu("Spel");
            menuBar.add(fileMenu);
            JMenuItem s = new JMenuItem("Stryktipset");
            JMenuItem e = new JMenuItem("Europatipset");
            JMenuItem p = new JMenuItem("Powerplay");
            fileMenu.add(s);
            fileMenu.add(e);
            fileMenu.add(p);
            s.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setStryk();
                }
            });
            e.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setEU();
                }
            });
            p.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    setPP();
                }
            });
        }

        private void initslider(){
            slider=new JSlider();
            slider.setBounds((int)((double)panelWidth*0.77),(int)((double)panelHeight * 0.80),215,20);
            slider.setValue(0);
            add(slider);
        }

        private void initTextbox(){
            halv =new JTextField("");
            halv.setBounds((int) ((double) panelWidth * 0.77), (int) ((double) panelHeight * 0.15), 30, 20);
          //  add(halv);
            hel =new JTextField("");
            hel.setBounds((int) ((double) panelWidth * 0.83), (int) ((double) panelHeight * 0.15), 30, 20);
          //  add(hel);
            antr=new JTextField("");
            antr.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.67),60,20);
            add(antr);
        }

        private void initlabels(){
            JLabel halvlabel=new JLabel("halv");
            JLabel hellabel=new JLabel("hel");
            JLabel slabel=new JLabel("vikta sannolikhet");
            JLabel antalraderlabel=new JLabel("antal rader");
            halvlabel.setBounds((int) ((double) panelWidth * 0.77), (int) ((double) panelHeight * 0.11), 30, 20);
            hellabel.setBounds((int) ((double) panelWidth * 0.83), (int) ((double) panelHeight * 0.11), 30, 20);
            slabel.setBounds((int)((double)panelWidth*0.77),(int)((double)panelHeight * 0.21),150,20);
            antalraderlabel.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.63),150,20);
         //   add(halvlabel);
         //   add(hellabel);
         //   add(slabel);
            antalraderlabel.setBackground(Color.white);
          //  add(antalraderlabel);

        }

        private void initButtons(){
            JButton goButton=new JButton("GO");
            goButton.setBounds((int)((double)panelWidth*0.77),(int)((double)panelHeight * 0.32),65,30);
            //add(goButton);
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    datahandler.go(gamedata, halv.getText(), hel.getText(), slider.getValue());
                    repaint();
                }
            });

            JButton beastButton=new JButton("Beast");
            beastButton.setBounds((int)((double)panelWidth*0.90),(int)((double)panelHeight * 0.72),75,30);
            add(beastButton);
            beastButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    datahandler.beast(antr.getText(),gamedata, slider.getValue());
                    repaint();
                }
            });
        }



        private void setStryk(){
            data.collectStrykData();
            gamedata=data.Stryk;
            repaint();
        }

        private void setEU(){
            data.collectEUData();
            gamedata=data.EU;
            repaint();

        }

        private void setPP(){
            data.collectPPData();
            gamedata=data.PP;
            repaint();
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bgImage, 0, -10, null);
            g.setColor(Color.white);
            for(int i=0;i<gamedata.games.length;i++){
                g.drawString(String.valueOf(i+1)+". ",(int)((double)panelWidth*0.01),(int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            //matchsträngar
            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((gamedata.games[i]), (int)((double)panelWidth*0.05), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((String.valueOf(gamedata.wodds[i][0])+"   "+String.valueOf(gamedata.wodds[i][1])+"   "+String.valueOf(gamedata.wodds[i][2])), (int)((double)panelWidth*0.20), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((String.valueOf(gamedata.crossed[i][0])+"   "+String.valueOf(gamedata.crossed[i][1])+"   "+String.valueOf(gamedata.crossed[i][2])), (int)((double)panelWidth*0.35), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.games[i]==null)break;
                g.drawString((String.valueOf(gamedata.value[i][0])+"   "+String.valueOf(gamedata.value[i][1])+"   "+String.valueOf(gamedata.value[i][2])), (int)((double)panelWidth*0.50), (int)(((double)panelHeight*0.065)+(i*(double)panelHeight/18.0)));
            }

            g.drawString(gamedata.spelstopp,(int)((double)panelWidth*0.03),(int)((double)panelHeight*0.81));
          //  g.drawString(gamedata.omsättning,(int)((double)panelWidth*0.03),(int)((double)panelHeight*0.90));
            g.setColor(Color.white);
            for(int i=0;i<gamedata.games.length;i++){
                if(gamedata.rad[i][0])g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect((int) ((double) panelWidth * 0.76), (int) (((double) panelHeight * 0.035) + (i * (double) panelHeight / 18.0)), (int) ((double) panelWidth * 0.03), (int) (((double) panelHeight*0.050)));
                if(gamedata.rad[i][1])g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect((int) ((double) panelWidth * 0.7925), (int) (((double) panelHeight * 0.035) + (i * (double) panelHeight / 18.0)), (int) ((double) panelWidth * 0.03), (int) (((double) panelHeight*0.050)));
                if(gamedata.rad[i][2])g.setColor(Color.black);
                else g.setColor(Color.white);
                g.fillRect((int) ((double) panelWidth * 0.824), (int) (((double) panelHeight * 0.035) + (i * (double) panelHeight / 18.0)), (int) ((double) panelWidth * 0.03), (int) (((double) panelHeight*0.050)));
            }
            g.setColor(Color.red);
            for(int i=0;i<gamedata.games.length;i++){
                g.drawString(String.valueOf(gamedata.dtecken[i][0]),(int) ((double) panelWidth * 0.76), (int) (((double) panelHeight * 0.070) + (i * (double) panelHeight / 18.0)));
                g.drawString(String.valueOf(gamedata.dtecken[i][1]),(int) ((double) panelWidth * 0.7925), (int) (((double) panelHeight * 0.070) + (i * (double) panelHeight / 18.0)));
                g.drawString(String.valueOf(gamedata.dtecken[i][2]),(int) ((double) panelWidth * 0.824), (int) (((double) panelHeight * 0.070) + (i * (double) panelHeight / 18.0)));
            }
            g.setColor(Color.white);
            g.drawString(String.valueOf(gamedata.sannolikhet13),(int)((double)panelWidth*0.87),(int)((double)panelHeight * 0.50));
            g.drawString(String.valueOf(gamedata.sannolikhet12),(int)((double)panelWidth*0.87),(int)((double)panelHeight * 0.55));
            g.drawString(String.valueOf(gamedata.sannolikhet11),(int)((double)panelWidth*0.87),(int)((double)panelHeight * 0.60));
            g.drawString(String.valueOf(gamedata.sannolikhet10),(int)((double)panelWidth*0.87),(int)((double)panelHeight * 0.65));

        }

    }
}
