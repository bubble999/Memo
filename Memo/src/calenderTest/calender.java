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

		Calendar calendar = Calendar.getInstance();
		calendar.set(setYear, setMonth - 1, 1); // 日付のセット(月初めの日付)

		int year = calendar.get(Calendar.YEAR);// ↑でセットされた年を読み込む
		int dayofweek = calendar.get(Calendar.DAY_OF_WEEK); // 曜日の数字。
		// このフィールドの値は1から7で、SUNDAY(1)、MONDAY(2)、TUESDAY(3)、WEDNESDAY(4)、THURSDAY(5)、FRIDAY(6)、およびSATURDAY(7)になります。
		// calendar.setで月初めの日付をゲットしているので、この曜日は必ず月初めの曜日となる。

		
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

}
