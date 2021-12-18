package Time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class nowDate {
	LocalDate now;
	public String getYearMonthDate() {
		
		
		// TODO 自動生成されたメソッド・スタブ
		now = LocalDate.now();
		//今日の日付を取得する
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		//表示形式を変更する
		
		return now.format(fmt);
		//表示形式を変更した日付を戻り値として返す
	}
	
	
	    public int getYear() {
	    	now =LocalDate.now();
	    	int year = now.getYear();
	    	return year;
	    }
	    
	    public int getMonth() {
	    	now =LocalDate.now();
	    	int month = now.getMonthValue();
	    	return month;
	    }
	    
	    public int getDay() {
	    	now =LocalDate.now();
	    	int day = now.getDayOfYear();
	    	return day;
	    			
	    }

}
