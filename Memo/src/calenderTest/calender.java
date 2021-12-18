package calenderTest;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Time.nowDate;

public class calender extends JFrame  implements ActionListener {
	
	JPanel calendarPane, //	カレンダーを記入する
	backPane, 	//背景用
	infoPane, //年、月、曜日などを記入する
	dayBackPane;//日付用
	
	nowDate nTime = new nowDate();

	public void frame() {
		String[] dayweek = { "日曜日", "月曜日", "火曜日", "水曜日", "木曜日", "金曜日", "土曜日" };

		
		calendarPane = new JPanel(new BorderLayout()); // カレンダー用
		backPane = new JPanel(new BorderLayout()); // 背景用

		this.add(calendarPane, BorderLayout.CENTER);//カレンダーをセンターに配置
		calendarPane.add(backPane, BorderLayout.CENTER);//カレンダーパネルに背景用を追加

		infoPane = new JPanel(new BorderLayout()); // 年、月、曜日を記入するようのパネル
		dayBackPane = new JPanel(new GridLayout(0, 7)); // 日付用のバックパネル

		backPane.add(infoPane, BorderLayout.NORTH);
		backPane.add(dayBackPane, BorderLayout.CENTER);
		//背景の北に年,月,曜日 中央に日付が来るように設定
		
		int setYear = nTime.getYear(); // セットする年
		int setMonth = nTime.getMonth(); // セットする月
		int setDay = nTime.getDay(); // セットする日にち

		

		
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
