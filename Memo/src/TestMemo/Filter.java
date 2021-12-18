package TestMemo;
import java.io.File;

import javax.swing.filechooser.FileFilter;

public class Filter extends FileFilter{
	//FileFilterを継承し、フィルタークラスを定義
	//FileFilterは、ユーザーに表示されるファイルにフィルタを適用する
	
  public boolean accept(File f) {
	  //このフィルタが指定されたファイルを受け付けるかどうかを返す
	  
    if (f.isDirectory()) {
      return true;
      //戻り値:この抽象パス名が示すファイルが存在し、
      //さらにそれがディレクトリである場合はtrue、そうでない場合はfalse
    }

    
    String extension = "";
    String fileName = f.getName();
    System.out.println(fileName);
    //拡張子を格納するextension
    //ファイルの名前を全部格納するfileName
    
    int position = fileName.lastIndexOf('.');
    //StringのlastIndexOfメソッドは、
    //対象に指定の文字列が含まれているか文字列の最後から検索する
    //見つかった場合は位置を取得する
    
    if (0 < position &&  position < fileName.length() - 1) {
    	//位置を示す数字が0より大きく、fileNameの長さのマイナス1より小さい場合
    	
    	extension = fileName.substring(position + 1).toLowerCase();
    	//拡張子を.より後ろを小文字にして取得
    	}
    
    //拡張子がrtfだったら、trueそうじゃないならfalseを返す
    if (extension.equals("rtf")){
      return true;
    } else {
      return false;
    }
  }
 
  	//このfilterはrtf onlyを定義するために存在する
  public String getDescription() {
	  
    return "RTF Only";
  }
}