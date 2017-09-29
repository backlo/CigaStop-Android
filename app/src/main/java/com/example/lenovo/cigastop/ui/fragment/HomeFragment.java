package com.example.lenovo.cigastop.ui.fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.Time;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.model.UserInfoEvent;
import com.example.lenovo.cigastop.util.DataBaseManager;
import com.example.lenovo.cigastop.util.Util;
import com.facebook.AccessToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by trycatch on 2017. 9. 20..
 */

public class HomeFragment extends Fragment {

    private static String address = "98:D3:32:30:F2:76";
    public static final int REQUEST_ENABLE_BT = 0;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private UserInfo userInfo = null;

    int todayCiga = 0;
    int allCiga = 0;
    int remindCiga = 20;
    double calcul = 0;

    @BindView(R.id.open)
    Button onBtn;

    @BindView(R.id.hours)
    TextView hours;

    @BindView(R.id.minutes)
    TextView minutes;

    @BindView(R.id.seconds)
    TextView seconds;

    @BindView(R.id.todayciga)
    TextView todayciga;

    @BindView(R.id.allciga)
    TextView allciga;

    @BindView(R.id.remindciga)
    TextView remindciga;

    @BindView(R.id.settingciga)
    TextView settingciga;

    @BindView(R.id.goaltext)
    TextView goal;

    @BindView(R.id.breath)
    ImageView breath;

    private ConnectedThread mConnectedThread;
    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private Time time;
    private TimerTask timerTask;
    private Timer timer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

//        Bundle extra = getArguments();
//        int setciga = getArguments().getInt("setciga");
//        Log.d("intentset"+setciga,"");

        final NotificationManager nm = (NotificationManager)getContext().getSystemService(getContext().NOTIFICATION_SERVICE);

        EventBus.getDefault().register(this);

        DataBaseManager.getInstance().getUserInfo(AccessToken.getCurrentAccessToken().getUserId());

        btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter

        if (checkBTState()) {
            BluetoothDevice device = btAdapter.getRemoteDevice(address);
            try {
                btSocket = createBluetoothSocket(device);
            } catch (Exception e) {
            }
            btAdapter.cancelDiscovery();

            try {
                btSocket.connect();
            } catch (Exception e) {
                try {
                    btSocket.close();
                } catch (Exception e2) {
                }
            }
            mConnectedThread = new ConnectedThread(btSocket);
            mConnectedThread.start();
        }

        onBtn.setOnClickListener(new View.OnClickListener() {

            private boolean check = false;

            public void onClick(View v) {
                onBtn.setBackgroundResource(R.drawable.lock);
                //mConnectedThread.write("1");
                Toast.makeText(getActivity(), "OPEN", Toast.LENGTH_SHORT).show();
                todayCiga();
                final NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(getContext());
                mCompatBuilder.setSmallIcon(R.mipmap.ciga);
                mCompatBuilder.setContentTitle("설정담배개수" + userInfo.getSettingciga() + "개");
                mCompatBuilder.setContentText("CigaStop");
                mCompatBuilder.setWhen(System.currentTimeMillis());
                mCompatBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
                mCompatBuilder.setAutoCancel(true);
                nm.notify(0,mCompatBuilder.build());
            }
        });

        onBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    onBtn.setBackgroundResource(R.drawable.clicklock);
                }
                return false;
            }
        });

        return v;
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        if (Build.VERSION.SDK_INT >= 10) {
            try {
                final Method m = device.getClass().getMethod("createInsecureRfcommSocketToServiceRecord", new Class[]{UUID.class});
                return (BluetoothSocket) m.invoke(device, MY_UUID);
            } catch (Exception e) {
            }
        }
        return device.createRfcommSocketToServiceRecord(MY_UUID);
    }

    public void onResume() {
        super.onResume();
    }

    // @Override
    public void onPause() {
        super.onPause();
        /*try {
            btSocket.close();
            btAdapter = BluetoothAdapter.getDefaultAdapter();       // get Bluetooth adapter

            if (checkBTState()) {
                BluetoothDevice device = btAdapter.getRemoteDevice(address);
                try {
                    btSocket = createBluetoothSocket(device);
                } catch (Exception e) {
                }
                btAdapter.cancelDiscovery();

                try {
                    btSocket.connect();
                } catch (Exception e) {
                    try {
                        btSocket.close();
                    } catch (Exception e2) {
                    }
                }

                mConnectedThread = new ConnectedThread(btSocket);
                mConnectedThread.start();
            }
            Toast.makeText(getActivity(), "켜짐", Toast.LENGTH_SHORT).show();
        } catch (Exception e2) {
        }*/
    }

    private boolean checkBTState() {
        if (btAdapter == null) {
            Toast.makeText(getActivity(), "지원안됌", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (btAdapter.isEnabled()) {
                Toast.makeText(getActivity(), "블루투스 켜짐", Toast.LENGTH_SHORT).show();
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                Toast.makeText(getActivity(), "블루투스 꺼져있음", Toast.LENGTH_SHORT).show();
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            return true;
        }
    }



    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (Exception e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void write(String message) {
            byte[] msgBuffer = message.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (Exception e) {
            }
        }

    }

    public void todayCiga() {
        ++todayCiga;
        ++allCiga;
        --remindCiga;

        userInfo.setCount(allCiga);
        userInfo.setToday(todayCiga);
        userInfo.setRemind(remindCiga);
        userInfo.setTime(Util.getInstance().getCurTime() + userInfo.getCoolTime());

        DataBaseManager.getInstance().setUserInfo(userInfo);

        todayciga.setText(String.valueOf(todayCiga));
        if (todayCiga == 20) {
            todayCiga = 0;
        }

        allciga.setText(String.valueOf(allCiga));
        if (allCiga >= 1000) {
            allciga.setTextSize(40);
        }

        remindciga.setText(String.valueOf(remindCiga));
        if (remindCiga == 0) {
            remindCiga = 20;
        }

    }

    @Subscribe
    public void UserInfoEvent(UserInfoEvent userInfoEvent) {
        if (userInfoEvent.isResult()) {


            userInfo = userInfoEvent.getUserInfo();
            allCiga = userInfo.getCount();
            allciga.setText(allCiga + "");

            todayCiga = userInfo.getToday();
            todayciga.setText(todayCiga + "");

            remindCiga = userInfo.getRemind();
            remindciga.setText(remindCiga + "");

            settingciga.setText(userInfo.getSettingciga() + "");

            if (userInfo.getToday() != 0 && userInfo.getToday() > userInfo.getSettingciga()) {
                calcul = (double)userInfo.getToday() / (double)userInfo.getSettingciga();
                Log.d("Calcul", calcul + ", " + userInfo.getToday() + ", " + userInfo.getSettingciga());
                goal.setText("오늘은 목표치보다 \n" + calcul + "배 더 피셨네요 ㅡㅡ");
                breath.setBackgroundResource(R.drawable.the_first);

            }
            else if (userInfo.getToday() == userInfo.getSettingciga()){
                goal.setText("오늘은 일정량만 \n 피셨네요!");
                breath.setBackgroundResource(R.drawable.the_second);
            }
            else if(userInfo.getToday() == 0){
                breath.setBackgroundResource(R.drawable.the_fourth);
            }
            else{
                calcul = (double)userInfo.getToday() / (double)userInfo.getSettingciga();
                goal.setText("오늘은 목표치보다 \n" + calcul + "배 덜 피셨네요 ^^");
                breath.setBackgroundResource(R.drawable.the_third);
            }

            Util.getInstance().setUserInfo(userInfo);

            time = Util.getInstance().getRemainTime();
            setTime(time.getHour(), time.getMinute(), time.getSecond());

            if(timerTask != null)
                timerTask.cancel();
            if(!time.isClear()){
                onBtn.setVisibility(View.INVISIBLE);
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        long hour = time.getHour();
                        long minute = time.getMinute();
                        long second = time.getSecond();
                        if(second > 0){
                            time.setSecond(--second);
                        }
                        else if(minute > 0){
                            time.setMinute(--minute);
                            second = 59;
                            time.setSecond(second);
                        }
                        else if(hour > 0){
                            time.setHour(--hour);
                            minute = 59;
                            second = 59;
                            time.setMinute(minute);
                            time.setSecond(second);
                        }
                        else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onBtn.setVisibility(View.VISIBLE);
                                }
                            });
                            timerTask.cancel();
                        }
                        final long finalHour = hour;
                        final long finalMinute = minute;
                        final long finalSecond = second;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setTime(finalHour, finalMinute, finalSecond);
                            }
                        });
                    }
                };
                timer = new Timer();
                timer.schedule(timerTask, 1000, 1000);
            }
            else{
                hours.setText("00");
                minutes.setText("00");
                seconds.setText("00");
                onBtn.setVisibility(View.VISIBLE);
            }
        }
    }

    public void setTime(long hour, long minute, long second){
        if(hour < 10){
            hours.setText("0" + hour);
        }
        else{
            hours.setText("" + hour);
        }
        if(minute < 10){
            minutes.setText("0" + minute);
        }
        else{
            minutes.setText("" + minute);
        }
        if(second < 10){
            seconds.setText("0" + second);
        }
        else{
            seconds.setText("" + second);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if(timerTask != null)
            timerTask.cancel();
    }
}
