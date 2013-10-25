package com.example.cmd;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView mTextView;
	private EditText mEditText;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTextView = (TextView) findViewById(R.id.log);
		mTextView.setMovementMethod(ScrollingMovementMethod.getInstance()); 
		mEditText = (EditText) findViewById(R.id.commond);
	}

	public void onClickButton(View view){
		mTextView.append(getResult());
	}
	public String getResult(){
		ShellExecute cmdexe = new ShellExecute ( );
		String result="";
		String cmd = mEditText.getText().toString();
		mTextView.append("\n===================================\n");
		mTextView.append(Calendar.getInstance().getTime().toString());
		mTextView.append("\n");
		mTextView.append(cmd);
		mTextView.append("\n\n");

		try {
			result = cmdexe.execute(cmd.split(" "), null);
		} catch (IOException e) {
			Log.e("zhengwenhui", "IOException");
			e.printStackTrace();
		}
		return result;
	}
	private class ShellExecute {
		/* 
		 * args[0] : shell 命令  如"ls" 或"ls -1"; 
		 * args[1] : 命令执行路径  如"/" ;
		 */  
		public String execute ( String[] cmmand,String directory)
				throws IOException {
			String result = "";
			try {  
				ProcessBuilder builder = new ProcessBuilder(cmmand);

				if ( directory != null )
					builder.directory ( new File ( directory ) );
				builder.redirectErrorStream (true);
				Process process = builder.start ( );

				//得到命令执行后的结果  
				InputStream is = process.getInputStream ( );
				byte[] buffer = new byte[1024];
				while ( is.read(buffer) != -1 ) {
					result = result + new String (buffer);
				}
				is.close ( );
			} catch ( Exception e ){
				e.printStackTrace ( );
			}
			return result;
		}  
	}
}
