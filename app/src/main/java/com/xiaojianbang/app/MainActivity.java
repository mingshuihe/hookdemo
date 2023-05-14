package com.xiaojianbang.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.xiaojianbang.app.databinding.ActivityMainBinding;
import com.xiaojianbang.encrypt.AES;
import com.xiaojianbang.encrypt.DES;
import com.xiaojianbang.encrypt.DESede;
import com.xiaojianbang.encrypt.MAC;
import com.xiaojianbang.encrypt.MD5;
import com.xiaojianbang.encrypt.RSA_Base64;
import com.xiaojianbang.encrypt.RSA_Hex;
import com.xiaojianbang.encrypt.SHA;
import com.xiaojianbang.encrypt.Signature_;
import com.xiaojianbang.hook.BankCard;
import com.xiaojianbang.hook.Money;
import com.xiaojianbang.hook.Utils;
import com.xiaojianbang.hook.Wallet;
import com.xiaojianbang.ndk.NativeHelper;
import com.xiaojianbang.reflect.ReflectEncrypt;
import com.xiaojianbang.test.HttpsUtils;
import com.xiaojianbang.test.Okhttp3Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Money money;
    private Wallet wallet;
    private BankCard bankCard;
    private DexClassLoader dynamicDex;
    private ActivityMainBinding binding;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Money.setFlag("QQ：24358757");
        Wallet.setFlag("VX：xiaojianbang8888");
        BankCard.setFlag("公众号：非攻code");

        Utils.verifyStoragePermissions(MainActivity.this);
        NativeHelper.readSomething();

        Button Test = findViewById(R.id.Test);
        Button MD5 = findViewById(R.id.MD5);
        Button SHA = findViewById(R.id.SHA);
        Button MAC = findViewById(R.id.MAC);
        Button DES = findViewById(R.id.DES);
        Button DESede = findViewById(R.id.DESede);
        Button AES = findViewById(R.id.AES);
        Button RSA = findViewById(R.id.RSA);
        Button RSAHex = findViewById(R.id.RSAHex);
        Button RSASign = findViewById(R.id.RSASign);
        Button CMD5 = findViewById(R.id.CMD5);
        Button CADD = findViewById(R.id.CADD);
        Button ENCODE = findViewById(R.id.ENCODE);
        Button VPN = findViewById(R.id.VPN);

        Test.setOnClickListener(this);
        MD5.setOnClickListener(this);
        SHA.setOnClickListener(this);
        MAC.setOnClickListener(this);
        DES.setOnClickListener(this);
        DESede.setOnClickListener(this);
        AES.setOnClickListener(this);
        RSA.setOnClickListener(this);
        RSAHex.setOnClickListener(this);
        RSASign.setOnClickListener(this);
        CMD5.setOnClickListener(this);
        CADD.setOnClickListener(this);
        ENCODE.setOnClickListener(this);
        VPN.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        try {
            switch (v.getId()) {
                case R.id.Test:
                    logOutPut("currentThread Id: " + Thread.currentThread().getId());

                    money = new Money("人民币", 100);
                    wallet = new Wallet("xiaojianbang", "lv", 100);
                    bankCard = new BankCard("xiaojianbang", "123456789",
                            "CBDA", BankCard.CREDIT_CARD, "15900000000");
                    wallet.addBankCard(bankCard);
                    wallet.deposit(money);

                    logOutPut("money.getInfo: " + money.getInfo());
                    logOutPut("wallet.getBalance: " + wallet.getBalance());
                    logOutPut("Utils.getCalc: " + Utils.getCalc(2000, 2000));
                    logOutPut("Utils.getCalc: " + Utils.getCalc(2000, 2000, 2000));
                    logOutPut("Utils.getCalc: " + Utils.getCalc(2000, 2000, 2000, 2000));

                    logOutPut((new Money("欧元", 200) {
                        @Override
                        public String getInfo() {
                            return this.getCurrency() + " " + this.getAmount() + " 这是匿名内部类";
                        }
                    }).getInfo());

                    showMap();
                    dynamic();

                    logOutPut(Utils.myPrint(new String[]{"xiaojianbang", "QQ：24358757", "VX：xiaojianbang8888", "公众号：非攻code"}));
                    logOutPut(Utils.myPrint("xiaojianbang", 30, true, bankCard));

                    ArrayList<Object> object_list = new ArrayList<>();
                    object_list.add("xiaojianbang");
                    object_list.add(30);
                    object_list.add(true);
                    object_list.add(bankCard);
                    logOutPut(Utils.myPrint(object_list));

                    ReflectEncrypt.onCreate();

                    //客户端加密部分 cipherText和cipherKey 需要同时提交给服务器
                    String AESKey = generateAESKey();
                    String cipherText = AES.encryptAES("xiaojianbang", AESKey);
                    String cipherKey = RSA_Base64.encryptByPublicKey(AESKey);
                    //服务器解密部分
                    String plainKey = RSA_Base64.decryptByPrivateKey(cipherKey);
                    String plainText = AES.decryptAES(cipherText, plainKey);
                    Log.d("xiaojianbang", "plainKey:" + plainKey + " plainText:" + plainText);

                    new AbstractDemo().test1();
                    new InterfaceDemo().test1();

                    break;
                case R.id.MD5:
                    String MD5_Result = MD5.getMD5("xiaojianbang小肩膀");
                    logOutPut("MD5: " + MD5_Result);
                    break;
                case R.id.SHA:
                    String SHA_Result = SHA.getSHA("xiaojianbang小肩膀");
                    logOutPut("SHA: " + SHA_Result);
                    break;
                case R.id.MAC:
                    String MAC_Result = MAC.getMAC("xiaojianbang小肩膀");
                    logOutPut("MAC: " + MAC_Result);
                    break;
                case R.id.DES:
                    String DES_encrypt_Result = DES.encryptDES("xiaojianbang小肩膀");
                    String DES_decrypt_Result = DES.decryptDES(DES_encrypt_Result);
                    logOutPut("DES_encrypt: " + DES_encrypt_Result);
                    logOutPut("DES_decrypt: " + DES_decrypt_Result);
                    break;
                case R.id.DESede:
                    String DESede_encrypt_Result = DESede.encryptDESede("xiaojianxiaojia小肩膀");
                    String DESede_decrypt_Result = DESede.decryptDESede(DESede_encrypt_Result);
                    logOutPut("DESede_encrypt: " + DESede_encrypt_Result);
                    logOutPut("DESede_decrypt: " + DESede_decrypt_Result);
                    break;
                case R.id.AES:
                    String AESKey2 = generateAESKey();
                    String AES_encrypt_Result = AES.encryptAES("xiaojianbang小肩膀", AESKey2);
                    String AES_decrypt_Result = AES.decryptAES(AES_encrypt_Result, AESKey2);
                    logOutPut("AES_encrypt: " + AES_encrypt_Result);
                    logOutPut("AES_decrypt: " + AES_decrypt_Result);
                    break;
                case R.id.RSA:
                    String RSA_Base64_encrypt_Result = RSA_Base64.encryptByPublicKey("xiaojianbang小肩膀");
                    String RSA_Base64_decrypt_Result = RSA_Base64.decryptByPrivateKey(RSA_Base64_encrypt_Result);
                    logOutPut("RSA_Base64_encrypt: " + RSA_Base64_encrypt_Result);
                    logOutPut("RSA_Base64_decrypt: " + RSA_Base64_decrypt_Result);
                    break;
                case R.id.RSAHex:
                    String RSA_Hex_encrypt_Result = RSA_Hex.encryptByPublicKey("xiaojianbang小肩膀");
                    String RSA_Hex_decrypt_Result = RSA_Hex.decryptByPrivateKey(RSA_Hex_encrypt_Result);
                    logOutPut("RSA_Hex_encrypt: " + RSA_Hex_encrypt_Result);
                    logOutPut("RSA_Hex_decrypt: " + RSA_Hex_decrypt_Result);
                    break;
                case R.id.RSASign:
                    String signStr = Signature_.getSignature("xiaojianbang小肩膀");
                    logOutPut("sign: " + signStr);
                    boolean verify_result = Signature_.verifySignature("xiaojianbang小肩膀", signStr);
                    logOutPut("verify_result: " + verify_result);
                    break;
                case R.id.CMD5:
                    logOutPut("CMD5 md5Result: " + NativeHelper.md5("xiaojianbang"));
                    break;
                case R.id.CADD:
                    logOutPut("CADD addResult: " + NativeHelper.add(5,6,7));
                    break;
                case R.id.ENCODE:
                    logOutPut("ENCODE: " + NativeHelper.encode());
                    break;
                case R.id.VPN:
//                    getNetworkName();
//                    networkCheck();
//                    HttpsUtils.doRequest();
                    Okhttp3Utils.doRequest();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateAESKey() {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 16; i++) {
            int random_i = random.nextInt(100);
            String temp = "0" + Integer.toHexString(random_i);
            temp = temp.substring(temp.length() - 2);
            stringBuilder.append(temp);
        }
        return stringBuilder.toString();
    }

    public void showMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("user", "xiaojianbang");
        map.put("pass", "12345678");
        map.put("code", "654321");
        logOutPut(Utils.shufferMap(map));
        logOutPut(Utils.shufferMap2(map));
    }

    public static void logOutPut(String message) {
        Log.d("xiaojianbang", message);
    }

    public void loadDex() {
        String DexName = "xiaojianbang.dex";
        String DexPath = getDir("shell", MODE_PRIVATE).getAbsolutePath() + File.separator + DexName;
        Context context = getApplicationContext();
        try {
            InputStream ins = context.getAssets().open(DexName);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int index;
            while ((index = ins.read(bytes)) != -1)
                baos.write(bytes, 0, index);
            byte[] decDex = decrypt(baos.toByteArray());
            ins.close();
            baos.close();

            FileOutputStream fos = new FileOutputStream(new File(DexPath));
            fos.write(decDex);
            fos.close();

            this.dynamicDex = new DexClassLoader(
                    DexPath,
                    context.getCacheDir().getAbsolutePath(),
                    getApplicationInfo().nativeLibraryDir,
                    getClassLoader());
            new File(DexPath).delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dynamic() {
        if (this.dynamicDex == null) {
            loadDex();
        }
        try {
            Class<?> dynamic = this.dynamicDex.loadClass("com.xiaojianbang.app.Dynamic");
            Object obj = dynamic.newInstance();
            Method method = dynamic.getDeclaredMethod("sayHello");
            String retval = (String) method.invoke(obj);
            Toast.makeText(this, retval, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] decrypt(byte[] toByteArray) {
        return toByteArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void getNetworkName() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            int count = 0;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface next = networkInterfaces.nextElement();
                logOutPut("getName获得网络设备名称=" + next.getName());
                logOutPut("getDisplayName获得网络设备显示名称=" + next.getDisplayName());
                logOutPut("getIndex获得网络接口的索引=" + next.getIndex());
                logOutPut("isUp是否已经开启并运行=" + next.isUp());
                logOutPut("isBoopback是否为回调接口=" + next.isLoopback());
                logOutPut("**********************" + count++);
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void networkCheck() {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            Network network = connectivityManager.getActiveNetwork();

            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            Log.i("xiaojianbang", "networkCapabilities -> " + networkCapabilities.toString());
            Log.i("xiaojianbang", "networkCapabilities -> " + networkCapabilities.hasTransport(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
