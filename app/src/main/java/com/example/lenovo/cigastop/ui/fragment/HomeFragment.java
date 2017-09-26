package com.example.lenovo.cigastop.ui.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.cigastop.R;
import com.example.lenovo.cigastop.model.UserInfo;
import com.example.lenovo.cigastop.model.UserInfoEvent;
import com.example.lenovo.cigastop.ui.activity.SetCigaActivity;
import com.example.lenovo.cigastop.util.DataBaseManager;
import com.facebook.Profile;
import com.example.lenovo.cigastop.ui.activity.SetCigaActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
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

    //SetCigaActivity setCigaActivity = new SetCigaActivity();

    int todayCiga = 0;
    int allCiga = 0;
    int remindCiga = 20;

    @BindView(R.id.open)
    Button onBtn;

    @BindView(R.id.todayciga)
    TextView todayciga;

    @BindView(R.id.allciga)
    TextView allciga;

    @BindView(R.id.remindciga)
    TextView remindciga;

    private ConnectedThread mConnectedThread;
    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        EventBus.getDefault().register(this);

        DataBaseManager.getInstance().getUserInfo(Profile.getCurrentProfile().getId());

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
                mConnectedThread.write("1");
                Toast.makeText(getActivity(), "OPEN", Toast.LENGTH_SHORT).show();
                todayCiga();
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

          /*  setCigaActivity.setCiga = userInfo.getSettingciga();
            setCigaActivity.settingciga.setText(setCigaActivity.setCiga+"");*/
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }
}
