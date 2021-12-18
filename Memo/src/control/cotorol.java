package control;
import TestMemo.Memo;
import Time.nowDate;

public class cotorol {

	public static void main(String[] args) {
		Memo me = new Memo();
		me.Memo();
		
		nowDate nTime = new nowDate();
		System.out.println(nTime.getMonth());
	}

}