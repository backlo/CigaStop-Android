package com.example.lenovo.cigastop;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;

import butterknife.ButterKnife;

/**
 * Created by trycatch on 2017. 9. 20..
 */

public class HomeFragment extends Fragment {

    private static String address = "98:D3:32:30:F2:76";
    public static final int REQUEST_ENABLE_BT = 0;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder sb = new StringBuilder();

    Handler h;
    final int RECIEVE_MESSAGE = 1;

    Button onBtn;
    TextView ciga;
    private ConnectedThread mConnectedThread;
    // SPP UUID service
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        onBtn = (Button) v.findViewById(R.id.open);
        ciga = (TextView) v.findViewById(R.id.ciga);

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
            public void onClick(View v) {
                mConnectedThread.write("1");
                Toast.makeText(getActivity(), "OPEN", Toast.LENGTH_SHORT).show();
            }
        });

        h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);
                        sb.append(strIncom);
                        int endOfLineIndex = sb.indexOf("\r\n");
                        if (endOfLineIndex > 0) {
                            String sbprint = sb.substring(0, endOfLineIndex);
                            sb.delete(0, sb.length());
                            ciga.setText(strIncom);

                        }
                        break;
                }
            }

            ;
        };


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

    @Override
    public void onPause() {
        super.onPause();
        try {
            btSocket.close();
        } catch (Exception e2) {
        }
    }

    private boolean checkBTState() {
        if (btAdapter == null) {
            Toast.makeText(getActivity(), "지원안됌", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
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

}
