package TestMemo;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.rtf.RTFEditorKit;

import Time.nowDate;

public class Memo extends JFrame 
  implements ActionListener, CaretListener{
	
	/* 	JFrame クラスを継承してフレームを作成し、ボタン等を配置する
		以下二つはインターフェース
		ActionListenerはボタンを押したときの処理
		CaretListenerは今どこの位置を編集しようとしているかを表わしている縦の棒
	 */
	
  protected JTextPane textPane;
  	/*	複数行のテキストを文字加工情報付きで表示する場合にはJTextPaneを使用する。 
  		protectedすることで外部からの書き換えを防ぐ
  		privateは自クラスのみアクセスできる
  		protectedは継承先でも使用できる
  
  		変数の型は設計図のようなもので、そのままでは使用できない
  		後で実体化する処理が必要になる
  		設計図のまま使いたい場合は、staticを使用する
  	 */

  protected DefaultStyledDocument dsDocument;
  protected StyleContext sContext;
  	//文書を管理する場合Documentクラスを使用する
  	//文字情報を変更したい場合はDefaultStyledDocument
  	//なくていいなら、PlainDocument
  	//StyleContextは文字加工情報を保存するためのクラス
  
  	//JTextpane = HTML, StyleContext = CSSのイメージ
  	//JTextpaneで表示し、StyleContextで文字情報を保存し、まとめてDocumentに保存する
  

  protected JToolBar toolBar;
  	//ツールバーを配置する
  
  	//以下が配置する内容
  protected JComboBox font;  
  	// フォントを選択
 
  protected JComboBox size;  
  	// フォントサイズ変更
  
  protected JToggleButton emphasis; 
  	// 強調する 
  
 

  protected String currentFontName = "";
  	//フォントの名前を入れる箱
  
  protected int currentFontSize = 0;
  	//フォントサイズを入れる箱
  
  protected boolean flag = false;
  	//boolean型はtrueかfalseの情報のみを保持している

 

  protected RTFEditorKit rtfEditor;
  /*
   *書式設定付き文書はRTF形式(リッチテキストフォーマット形式)で保存
   *RTFはMicrosoft社が策定した
   *文字の大きさやフォントなどの情報を埋め込むことができる文書形式
   *RTF EditorKitにはreadメソッドとwriteメソッドがある
   */

  public void Memo(){
	nowDate nTime = new nowDate();
    setTitle(nTime.getYearMonthDate());
    //タイトルに本日の日付を設定
    
    
    setBounds( 300, 10, 500, 500);
    //左から、ウインドウ表示する位置、ウインドウの大きさ
    //いずれも横縦の順番

    
    textPane = new JTextPane();
    //textPaneを実体化(インスタンス)
    
    
    JScrollPane scroll = new JScrollPane(textPane, 
      JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
      JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    //画面をスクロールできるようにする
    //まず、JScrollPaneのインスタンス化をおこなう textPaneを引数として渡す
    //VERTICALは縦 HORIZONTALが横
    //ALWAYSで常に表示、NEVERで表示しない

    
    getContentPane().add(scroll, BorderLayout.CENTER);
    //継承してある、JFrameクラスのgetContentPaneメソッド
    //フレームは、Paneと呼ばれる層が重なっている構造になっている
    //ボタンなどは、この中のContentPaneという層に追加する(add)
    //scrollをBorderLayout上の、CENTERに配置する

    
    sContext = new StyleContext();
    dsDocument = new DefaultStyledDocument(sContext);
    //文字加工情報を保存するStyleContext型変数を指定して
    //DefaultStyledDocumentクラスのオブジェクトを作成する 
    
    
    textPane.setDocument(dsDocument);
    //textPaneをテキストドキュメントに関連付ける
    //StyledDocumentでなければならない
    
    //文字データに装飾を足して、ドキュメントに保存する
    
    textPane.addCaretListener(this);
    //texrPaneにCaretListenerを設定する

    
    rtfEditor = new RTFEditorKit();
    //ファイル操作用RTFEditorKit

    JMenuBar menuBar = createMenuBar();
    setJMenuBar(menuBar);
    //MenuBarをインスタンス化し、セットする。

    createToolbar();
    //Toolbarメソッドを呼び出す
    
    getContentPane().add(toolBar, BorderLayout.NORTH);
    //ContentpaneのNorthにtoolBarを追加する

    
    createDocument();
    // 初期文書の読み込み 
    //Documentメソッドを呼び出す

     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //×ボタンを押したときの動作を設定する
        //EXIT_ON_CLOSEでアプリケーションを終了する
        //デフォルトでは、フレームが非表示になるだけでアプリケーションは終了しない
        
     setVisible(true);
        //フレームを表示する
      
  }

  protected void createToolbar(){
	    toolBar = new JToolBar();
	    toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
	    //toolBarを実体化
	    //toolbarにレイアウトを設定
	    //FlowLayoutは左から順番に右に向かってボタンを追加していく

	    
	    GraphicsEnvironment ge = 
	      GraphicsEnvironment.getLocalGraphicsEnvironment();
	    String fName[] = ge.getAvailableFontFamilyNames();
	    //フォント一覧を取得する
	    //フォント名を取得するにはGraphicsEnvironmentクラスを使用する
	    
	    //getAvailableFontFamilyNamesメソッドは、
	    //戻り値としてローカル環境にインストールされている
	    //フォントファミリー名の一覧をString型の配列として取得する

	    
	    font = new JComboBox(fName);
	    font.setMaximumSize(font.getPreferredSize());
	    font.addActionListener(this);
	    font.setActionCommand("font");
	    toolBar.add(font);
	    /* fontにフォント名を渡し実体化する
	     * ボタンの最大サイズをgetMaximumSizeメソッドで設定する
	     * getPreferredSizeは推奨サイズ
	     * 
	     * fontにアクションリスナーを追加する
	     * コマンドを設定する
	     * 後述するactionPerformedでどのコマンドと等しいか検証する
	     * 
	     * ツールバーにfontを追加する
	     * 
	     */

	    
	    size = new JComboBox(new String[] {"8", "9", "10", 
	      "11", "12", "14", "16", "18", "20", "22", "24", "26", 
	      "28", "36", "48", "72"});
	    size.setMaximumSize(size.getPreferredSize());
	    size.addActionListener(this);
	    size.setActionCommand("size");
	    toolBar.add(size);
	    //フォントサイズ選択
	    //sizeに配列に入れた文字サイズを入れて実体化する
	    //そのほかの動作はfontと同様


	    
	    emphasis = new JToggleButton("太字");
	    emphasis.setPreferredSize(emphasis.getPreferredSize());
	    emphasis.addActionListener(this);
	    emphasis.setActionCommand("emphasis");
	    toolBar.add(emphasis);
	    //強調表示をするボタン
	    //その他はfontと同様

	   
	    
	  }

  
  protected void createDocument(){
    StringBuffer sb = new StringBuffer();
    sb.append("テキストサンプルです");
    /*
     * 	StringBufferクラスの特徴
     * 	値を繰り返しセットできるため、追加・挿入・変更することができる
		マルチスレッドに対応している
		
		Stringだと値の追加、変更が難しい
		StringBuilderはマルチスレッドに不向き
		
		appendで追加する
     */

    try{
     
      dsDocument.insertString(0, new String(sb), 
    	sContext.getStyle(StyleContext.DEFAULT_STYLE));
      //文章の初期化
      //DefaultStyledDocumentのinsertStringメソッドを起動
      //insertString(int offs, String str, AttributeSet a)
      //
      //指定した位置(offs)に指定した文字(str)を指定した文字情報(a)で挿入する
      //AttributeSetインターフェースはStyleインターフェースの親にあたる
      
    }catch (BadLocationException ble){
      System.err.println("初期文書の読み込みに失敗しました。");
    }
    	//エラー時の処理
  }


  protected JMenuBar createMenuBar(){

	    JMenuBar menuBar = new JMenuBar();
	    //MenuBarを実体化
	    
	    JMenu fileMenu = new JMenu("File", true);
	    menuBar.add(fileMenu);
	    //menuBarにFileという名前のボタンを追加し、
	    //この追加したfileにさらにボタンを足していく

	    JMenuItem newItem = new JMenuItem("New");
	    fileMenu.add(newItem);
	    newItem.addActionListener(this);
	    newItem.setActionCommand("newItem");
	    //JMenuItemのNewをインスタンス化し、filemenuに追加する
	    //actionCommandを設定する

	    JMenuItem openItem = new JMenuItem("Open");
	    fileMenu.add(openItem);
	    openItem.addActionListener(this);
	    openItem.setActionCommand("openItem");
	    //上と同じ

	    JMenuItem saveItem = new JMenuItem("Save");
	    fileMenu.add(saveItem);
	    saveItem.addActionListener(this);
	    saveItem.setActionCommand("saveItem");
	    //上と同じ

	    fileMenu.addSeparator();
	    //セパレートを配置

	    JMenuItem exitItem = new JMenuItem("Exit");
	    fileMenu.add(exitItem);
	    exitItem.addActionListener(this);
	    exitItem.setActionCommand("exitItem");
	    //上と同じ

	    return menuBar;
	    //完成したmenuBarを戻り値として返す
	  }

  public void actionPerformed(ActionEvent e){
    if (flag){
      return;
    }
    //actionEventを引数として受け取りactionPerformedを定義
    //if (flag){return;}により、キャレットの移動の場合は無視できる
    
    String actionCommand = e.getActionCommand();
    //引数として受け取ったeでgetActionCommandメソッドを起動し、
    //上で設定したコマンドをString型変数に受け取る

    if (actionCommand.equals("exitItem")){
      System.exit(0);
      //システムを終了する
      
    }else if ((actionCommand.equals("newItem")) || 
      (actionCommand.equals("openItem")) ||
      (actionCommand.equals("saveItem"))){
    	//もしファイル操作系のコマンドなら、fileOperationを起動する
    	
      fileOperation(actionCommand);
      
    }else{
    	MutableAttributeSet mAttributeSet = new SimpleAttributeSet();
    	//新しい属性セットを作成する
      
      if (actionCommand.equals("font")){
        //コマンドがfontならフォントの変更を行う
    	  
        String fontName = font.getSelectedItem().toString();
        //fontの,現在選択されている,名前を取得する
        
        StyleConstants.setFontFamily(mAttributeSet, fontName);
        /*
         * javax.swing.text.StyleConstants
         * 
         * setFontFamily(MutableAttributeSet a, String fam)
           フォントの属性を設定する
           
           
         */
        
      }else if (actionCommand.equals("size")){
        //sizeならサイズ変更
    	  
        int fontSize = 0;
        //フォントサイズを入れる
        
        try{
          fontSize = Integer.parseInt(size.
            getSelectedItem().toString());
          //sizeの現在選択されている数字文字列を、数字に変換してfontsizeに格納
          
        }catch (NumberFormatException ex){
          return;
          //例外処理
        }

        StyleConstants.setFontSize(mAttributeSet, fontSize);
        /*
         * setFontSize(MutableAttributeSet a, int s)
           フォントサイズを設定する
         */
        
      }else if (actionCommand.equals("emphasis")){
        //文字を強調する
    	  
        StyleConstants.setBold(mAttributeSet, emphasis.isSelected());
        /*
         * 	setBold(MutableAttributeSet a, boolean b)
          	ボールド属性を設定します。
         */
      }else{
        return;
      }

      setAttributeSet(mAttributeSet);
      //下のsetAttributeSetメソッドを呼び出す
    }

    textPane.requestFocusInWindow();
    //操作できる状態に戻す
  }

  protected void fileOperation(String actionCommand){
	  
    JFileChooser choose = new JFileChooser();
    //JFileChooserは、ユーザーがファイルを選択するための単純なメカニズムを提供する
    
    Filter filter = new Filter();
    //別クラスで作ったfilterを実体化
    
    choose.setFileFilter(filter);
    //fileフィルターをchooseにセット

    if (actionCommand.equals("newItem")){
      //コマンドがNewItemだったら
    	
      dsDocument = new DefaultStyledDocument(sContext);
      //デフォルトスタイルドキュメントを実体化し、sContextの文字加工情報をコピー
      textPane.setDocument(dsDocument);
      //textPaneとドキュメントを紐づける
      
    }else if (actionCommand.equals("openItem")){
      //ファイルを開く処理
    	
      dsDocument = new DefaultStyledDocument(sContext);
    //デフォルトスタイルドキュメントにsContextの文字加工情報をコピー

      if (choose.showOpenDialog(this) == JFileChooser.CANCEL_OPTION){
          return;
          //もし、ファイル選択のダイアログを開いて、取り消ししたら元に戻る
        }

      File gsFile = choose.getSelectedFile();
      //選択したファイルをFile型変数、gsFileにコピーする
      
      try{
        InputStream inStream = new FileInputStream(gsFile);
        //連続するデータを順次に必要な分だけ読み込む
        rtfEditor.read(inStream, dsDocument, 0);
        /*
         * 	read(InputStream in, Document doc, int pos)
         * 	パラメータ:
        	in - 読み込み元のストリーム
        	doc - 挿入先
        	pos - コンテンツを配置するドキュメント内の位置
         */
        
        inStream.close();
        
      }catch(Exception ex){
        ex.printStackTrace();
      //例外、スタックトレースを出力
      }

      textPane.setDocument(dsDocument);
      //textPaneにドキュメントをセットする
      
    }else if (actionCommand.equals("saveItem")){
      //ファイルの保存
    	
      if (choose.showSaveDialog(this) == JFileChooser.CANCEL_OPTION){
        return;
      //もし、ファイル選択のダイアログを開いて、取り消ししたら元に戻る
      }

      File gsFile = choose.getSelectedFile();
    //選択したファイルをFile型変数、gsFileにコピーする
      
      try{
        OutputStream outStream = new FileOutputStream(gsFile);
        // 連続するデータを順次に必要な分だけ書き込む
         
        
        rtfEditor.write(outStream, dsDocument, 0, dsDocument.getLength());
        /*
         * write(OutputStream out, Document doc, int pos, int len)
         *  out - 書き込み先のストリーム
  			doc - 書き込み元
  			pos - コンテンツを取得するドキュメント内の位置
  			len - 書き出す量 
         */
        
        outStream.close();
        
      }catch(Exception ex){
    	  
        ex.printStackTrace();
        //例外、スタックトレースを出力
      }
    }else{
      return;
    }
  }

  protected void setAttributeSet(AttributeSet attr) {
    // 選択している文字のスタイルを変更する
     
    

    int start = textPane.getSelectionStart();
    int end = textPane.getSelectionEnd();
    // getSelectionStart()でスタート位置を数字で取得
    //getSelectionEnd()で終わり位置を設定する

    dsDocument.setCharacterAttributes(start, end - start, attr, false);
    //指定した位置,から長さ分,だけ指定したスタイルに設定する
    //trueにすると、もともとも設定してあったスタイルを全て破棄する
    //falseを指定した場合は選択した部分だけ置き換える
  }

  public void caretUpdate(CaretEvent e){
	  //caretインターフェースで定義する必要があるメソッド
	  //CaretEventクラスの値が引数として渡される
	  
    flag = true;

    int p = textPane.getSelectionStart();
    //スタート位置を数字で取得
    
    AttributeSet atrr = dsDocument.getCharacterElement(p).getAttributes();
    //ドキュメントの選択した位置(数字)の文字加工情報を取得し、格納
    
    String name = StyleConstants.getFontFamily(atrr);
    //文字加工情報をもとに、現在のフォント名を文字列で取得
    
    
    if (!currentFontName.equals(name)){
      currentFontName = name;
      font.setSelectedItem(name);
      //もし今のフォント名と違っていたら、
      //cuttentFontNameを更新し、フォントを変更する
    }
    //変更前と同じなら無視する

    int sizeUp = StyleConstants.getFontSize(atrr);
    //フォントサイズを型をそのままに数字で取得
    
    if (currentFontSize != sizeUp){
      currentFontSize = sizeUp;
      size.setSelectedItem(Integer.toString(sizeUp));
      //もし文字サイズが違うなら、
      //フォントサイズを変更し、sizeUpを数字に変換してsizeを変更する
    }

    /* 強調の状態表示 */
    emphasis.setSelected(StyleConstants.isBold(atrr));
    //現在の強調状態を引数として、setSelectedを起動する
    
    
    flag = false;
  }
}