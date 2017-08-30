package abc.com.win8;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ly.win10.aidl.Book;
import com.ly.win10.aidl.IBookManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static  final String TAG = "MainActivity";
    /**
     * 远程服务所在包名
     */
    private static final String PKG_NAME = "com.ly.win10";
    /**
     * 远程服务的类名
     */
    private static final String CLASS_NAME = "com.ly.win10.service.BookManagerService";
    private TextView tvInfo;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected");

            IBookManager bookManager = IBookManager.Stub.asInterface(service);
            try{
                List<Book> list = bookManager.getBookList();
                Log.i(TAG, "The size of book list is  "+list.size());
                Log.i(TAG, "Type of book list is "+list.getClass().getName());
                String info = list.toString();
                tvInfo.setText(info);
                Log.i(TAG,"Book list : "+info);

                Book book = new Book(3,"Windows");
                bookManager.addBook(book);
                Log.i(TAG, "Add book: "+book);

                List<Book> newList = bookManager.getBookList();
                Log.i(TAG,"New Book list : "+newList.toString());

            }catch (RemoteException e){
                e.printStackTrace();
            }
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
}
