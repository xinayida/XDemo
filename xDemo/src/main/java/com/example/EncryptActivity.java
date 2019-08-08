package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

/**
 * Created by Stefan on 2019/4/14.
 */
public class EncryptActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

//        char[] data = {0x8b, 0xf5, 0x6f, 0x2d, 0xee, 0xe0, 0x99, 0x5, 0xa, 0xa4, 0xb4, 0xe3, 0x67, 0xf2, 0x45, 0xe3, 0x5d, 0x35, 0x50, 0x97, 0xcc, 0xec, 0xf1, 0x15, 0x35, 0xd9, 0xc9, 0xa8, 0xee, 0xb1, 0xe5, 0xe8, 0xf6, 0xbc, 0x5a, 0x77, 0xd, 0x58, 0x28, 0x9e, 0xfa, 0x7a, 0xbe, 0xfa, 0x38, 0xb2, 0xb8, 0x1a, 0xc7, 0xcf, 0x52, 0x8, 0x1f, 0xa, 0xc2, 0xb3, 0xde, 0x37, 0xe8, 0xc2, 0xce, 0x3f, 0x54, 0x90};
//
////        byte[] data = EncryptUtils.base64Decode("XJIsdnp6tAFxyT9tgrc/zg==".getBytes());
//        try {
////            byte[] decode = EncryptUtils.aesDecrypt(data, "b3a6e13121a1ccf6aa66b44f2c07113b");
////            Log.d("Stefan", "decode: " + decode);
////            Log.d("Stefan", new String(decode, "utf-8"));
//            byte[] bytes = "最佳拍档".getBytes("utf-8");
//            String encodeStr = EncryptUtils.bytesToStrByBase64(EncryptUtils.aesEncrypt(bytes, "b3a6e13121a1ccf6aa66b44f2c07113b"));
//            Log.d("Stefan", "encodeStr: " + encodeStr);
//        } catch (UnsupportedEncodingException e) {
//            Log.d("Stefan", "error ~~~~");
//            e.printStackTrace();
//        }
    }
}