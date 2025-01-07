package DakokuSystem;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StampTimer extends JFrame implements ActionListener {

	//必要となるラベルの設定
	JLabel EntryTime = new JLabel("00:00:00");
	LocalDateTime EntryTimes = null;
	JLabel ExitTime = new JLabel("00:00:00");
	LocalDateTime ExitTimes = null;
	JLabel TotalTime = new JLabel("00:00:00");
	JLabel EntryStr = new JLabel("出勤打刻:");
	JLabel ExitStr = new JLabel("退勤打刻:");
	JLabel TotalStr = new JLabel("勤務時間:");
	JButton EntryButton = new JButton("打刻");
	JButton CSVButton = new JButton("csv");

	public static void main(String[] args) {

		//画面の作成
		StampTimer frame = new StampTimer();
		//Timerの設定

		//画面の設定
		//タイトルの設定
		frame.setTitle("勤怠打刻");
		//画面の大きさと表示座標を設定
		frame.setBounds(20, 20, 500, 500);
		//画面の×ボタンを押すとプログラム終了
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//画面の表示
		frame.setVisible(true);
	}

	StampTimer() {

		//パネルの定義
		JPanel panel = new JPanel();

		//パネルをGridBagLayoutにし、配置しやすいようにする
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		panel.setLayout(layout);

		//出勤打刻ラベルの挿入
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(5, 5, 5, 5);
		EntryStr.setFont(new Font("Serif", Font.PLAIN, 35));
		layout.setConstraints(EntryStr, gbc);
		panel.add(EntryStr);

		//退勤打刻ラベルの挿入
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 5, 5);
		ExitStr.setFont(new Font("Serif", Font.PLAIN, 35));
		layout.setConstraints(ExitStr, gbc);
		panel.add(ExitStr);

		//合計勤務ラベルの挿入
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 5, 5);
		TotalStr.setFont(new Font("Serif", Font.PLAIN, 35));
		layout.setConstraints(TotalStr, gbc);
		panel.add(TotalStr);

		//出勤打刻時間ラベルの挿入
		gbc.gridx = 1;
		gbc.gridy = 0;
		EntryTime.setFont(new Font("Serif", Font.PLAIN, 35));
		layout.setConstraints(EntryTime, gbc);
		panel.add(EntryTime);

		//退勤打刻時間ラベルの挿入
		gbc.gridx = 1;
		gbc.gridy = 1;
		ExitTime.setFont(new Font("Serif", Font.PLAIN, 35));
		layout.setConstraints(ExitTime, gbc);
		panel.add(ExitTime);

		//合計勤務時間ラベルの挿入
		gbc.gridx = 1;
		gbc.gridy = 2;
		TotalTime.setFont(new Font("Serif", Font.PLAIN, 35));
		layout.setConstraints(TotalTime, gbc);
		panel.add(TotalTime);

		//打刻ボタンの挿入
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(20, 5, 5, 5);
		EntryButton.addActionListener(this);
		layout.setConstraints(EntryButton, gbc);
		panel.add(EntryButton);

		//CSV記入ボタンの挿入
		gbc.gridx = 0;
		gbc.gridy = 3;
		CSVButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				//今日の日付を取得
				LocalDateTime nowDate = LocalDateTime.now();
				DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				String dtf2 = dtf1.format(nowDate);
				
				IntoCsv(dtf2);

			}

		});
		layout.setConstraints(CSVButton, gbc);
		panel.add(CSVButton);

		//これまでのすべてのラベル・ボタンの挿入
		getContentPane().add(panel, BorderLayout.CENTER);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			//現在時刻を求め、それをテキスト形式に収める
			LocalDateTime nowDate = LocalDateTime.now();
			DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("HH:mm:ss");
			String dtf2 = dtf1.format(nowDate);

			//テキストを表示する
			if (EntryTime.getText().equals("00:00:00")) {
				//出勤時刻を入力
				EntryTime.setText(dtf2);
				EntryTimes = nowDate;
			} else {
				//退勤時刻を入力
				ExitTime.setText(dtf2);
				ExitTimes = nowDate;

				//差分の秒数を取得し、時間を割り出す
				Duration Totaltimes = Duration.between(EntryTimes, ExitTimes);
				double times = Totaltimes.toSeconds();
				int hours = (int) (times / 3600);
				int minutes = (int) ((times - hours * 3600) / 60);
				int seconds = (int) (times - hours * 3600 - minutes * 60);

				//時間をラベルに入力
				String Total = hours + ":" + minutes + ":" + seconds;
				TotalTime.setText(Total);
			}

		} catch (Exception error) {
			if (EntryTime.getText().equals("00:00:00")) {
				EntryTime.setText("エラー！");
			} else {
				ExitTime.setText("エラー！");
				TotalTime.setText("エラー！");
			}
		}

	}
	
	public void IntoCsv(String Today) {
        try {
            // 出力ファイルの作成
            FileWriter filewriter = new FileWriter("DayLog.csv", true);
            // PrintWriterクラスのオブジェクトを生成
            PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));
 
            // データを書き込む
            printwriter.write(Today + ",出勤時間:" + EntryTime.getText() + ",退勤時間:" + ExitTime.getText() + ",勤務時間:" + TotalTime.getText() + "\n");
 
            // ファイルを閉じる
            printwriter.close();
 
        // 出力に失敗したときの処理
        } catch (IOException ex) {
            ex.printStackTrace();
        }
	}

}
