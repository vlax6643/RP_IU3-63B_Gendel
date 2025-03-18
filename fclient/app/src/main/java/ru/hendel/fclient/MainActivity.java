package ru.hendel.fclient;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Arrays;

import ru.hendel.fclient.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'fclient' library on application startup.
    static {
        System.loadLibrary("fclient");

        Log.d("LibraryLoad", "Loading mbedcrypto library...");
        System.loadLibrary("mbedcrypto");
        Log.d("LibraryLoad", "mbedcrypto loaded successfully.");

    }

    private ActivityMainBinding binding;
    public native int initRng();
    public static native byte[] randomBytes(int no);
    public native byte[] encrypt(byte[] key, byte[] data);
    public native byte[] decrypt(byte[] key, byte[] data);
    ActivityResultLauncher activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRng();


        byte[] randomBytes = randomBytes(32);
        Log.d("RandomBytes", Arrays.toString(randomBytes));

        byte[] key = "1234567890abcdef".getBytes();
        byte[] data = "Hello, World!".getBytes();

        byte[] encryptedData = encrypt(key, data);
        Log.d("EncryptedData", Arrays.toString(encryptedData));

        byte[] decryptedData = decrypt(key, encryptedData);
        Log.d("DecryptedData", new String(decryptedData));

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            // обработка результата
                            String pin = data.getStringExtra("pin");
                            Toast.makeText(MainActivity.this, pin, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static byte[] stringToHex(String s)
    {
        byte[] hex;
        try
        {
            hex = Hex.decodeHex(s.toCharArray());
        }
        catch (DecoderException ex)
        {
            hex = null;
        }
        return hex;
    }


    public void onButtonClick(View v)
    {
        Intent it = new Intent(this, PinpadActivity.class);
        //startActivity(it);
        activityResultLauncher.launch(it);
    }


    /**
     * A native method that is implemented by the 'fclient' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}