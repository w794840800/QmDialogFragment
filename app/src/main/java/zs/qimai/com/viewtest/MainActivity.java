package zs.qimai.com.viewtest;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        QmDialogFragment qmDialogFragment = new QmDialogFragment.Builder(getSupportFragmentManager())
                .setGravity(Gravity.CENTER)
                 .setHeight(WindowManager.LayoutParams.WRAP_CONTENT)
                .setWidth(WindowManager.LayoutParams.WRAP_CONTENT)
                .setLayoutRes(R.layout.test_dialog)
                .addViewClickId(R.id.tv_test)
                .setText(R.id.tv_test,"hahahaha")
                .setViewClick(new OnViewClickListener() {
                    @Override
                    public void onViewClick(@NotNull View v, @NotNull QmDialogFragment qmDialogFragment) {
                        Toast.makeText(v.getContext(),"我被点击了",Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .create()
                .show();

        //DialogFragment dialogFragment = new DialogFragment();
        //dialogFragment.show(getSupportFragmentManager(),"tag");
    }

}
