package ru.hendel.fclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
    public native byte[] randomBytes(int no);
    public native byte[] encrypt(byte[] key, byte[] data);
    public native byte[] decrypt(byte[] key, byte[] data);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация генератора случайных чисел
        initRng();

        // Пример использования randomBytes
        byte[] randomBytes = randomBytes(32);
        Log.d("RandomBytes", Arrays.toString(randomBytes));

        // Пример использования шифрования и дешифрования
        byte[] key = "1234567890abcdef".getBytes(); // Пример ключа
        byte[] data = "Hello, World!".getBytes();  // Пример данных

        byte[] encryptedData = encrypt(key, data);
        Log.d("EncryptedData", Arrays.toString(encryptedData));

        byte[] decryptedData = decrypt(key, encryptedData);
        Log.d("DecryptedData", new String(decryptedData));
    }

    /**
     * A native method that is implemented by the 'fclient' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}