package com.ish.mapmoving;

import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Button moveBtn;
    private View objectView;
    private MyFrameLayout mframeLayout;
    private int prex=0,prey=0,dx,dy;
    private Boolean ifMove = true;
    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            sendRequest();
            mframeLayout.startMove(dx,dy);
            handler.postDelayed(this, 300);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        moveBtn = findViewById(R.id.move_btn);
        objectView = findViewById(R.id.object);
        mframeLayout = findViewById(R.id.frame_ayout);
        moveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifMove){
                    handler.removeCallbacks(runnable);
                    moveBtn.setText("move");
                    ifMove=false;
                }
                else{
                    handler.post(runnable);
                    moveBtn.setText("stop");
                    ifMove=true;
                }

            }
        });

        handler.post(runnable);
    }

    private void sendRequest(){
        String address = "http://www.isssh.cn/test/test.php";
        HttpUtil.sendRequest(address,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                PositionData positionData = gson.fromJson(responseData,new TypeToken<PositionData>(){}.getType());
                dx = prex - positionData.getX();
                dy = prey - positionData.getY();
                prex = positionData.getX();
                prey = positionData.getY();
                Log.d("wedwedw",prex+" "+prey);
            }
        });
    }
}
