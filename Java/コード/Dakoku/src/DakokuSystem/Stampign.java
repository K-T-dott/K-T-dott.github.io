package DakokuSystem;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Stampign extends JFrame implements ActionListener{

  Timer timer;
  JLabel label;
  int sec = 0;

  public static void main(String[] args){
    Stampign frame = new Stampign();

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setBounds(10, 10, 300, 200);
    frame.setTitle("タイトル");
    frame.setVisible(true);
  }

  Stampign(){
    label = new JLabel();

    JPanel labelPanel = new JPanel();
    labelPanel.add(label);

    timer = new Timer(1000,this);

    getContentPane().add(labelPanel, BorderLayout.CENTER);

    timer.start();
  }

  public void actionPerformed(ActionEvent e){
	  
	  //現在時刻を求め、それをテキスト形式に収める
	  LocalDateTime nowDate = LocalDateTime.now();
	  DateTimeFormatter dtf1 =
				DateTimeFormatter.ofPattern("HH:mm:ss");
	  String dtf2 = dtf1.format(nowDate);
	  
	  //テキストを表示する
	  label.setText(dtf2);

  }
}
