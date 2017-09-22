package abc.com.win8;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ly.win10.aidl.Book;
import com.ly.win10.aidl.IBookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static  final String TAG = "MainActivity";

    private TextView tvInfo;
    private Button btnGetInfo, btnClear;

    private StringBuilder builder= new StringBuilder();
    private IBookManager mBookManager;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected");

            mBookManager = IBookManager.Stub.asInterface(service);
            getBookList();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInfo = (TextView) findViewById(R.id.tv_info);

        btnGetInfo = (Button) findViewById(R.id.btn_get_info);
        btnGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBookList();
            }
        });
        btnClear = (Button) findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvInfo.setText("");
            }
        });

        Intent intent = new Intent();
        intent.setPackage("com.ly.win10");
        intent.setAction("com.ly.win10.aidl");
        bindService(intent, mConnection,Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }

    private void getBookList(){
        try{
            List<Book> list = mBookManager.getBookList();
            Log.i(TAG, "The size of book list is  "+list.size());
            Log.i(TAG, "Type of book list is "+list.getClass().getName());

            for(Book book: list){
                builder.append(book+"\r\n");
            }

            tvInfo.setText(builder.toString());

        }catch (RemoteException e){
            e.printStackTrace();
        }
    }
}
