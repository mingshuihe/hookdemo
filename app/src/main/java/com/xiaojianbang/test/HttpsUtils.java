package com.xiaojianbang.test;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtils {
    public static String certificate = "-----BEGIN CERTIFICATE-----\n" +
            "MIIKQDCCCSigAwIBAgIMcjncyb61yc15VBX5MA0GCSqGSIb3DQEBCwUAMGYxCzAJ\n" +
            "BgNVBAYTAkJFMRkwFwYDVQQKExBHbG9iYWxTaWduIG52LXNhMTwwOgYDVQQDEzNH\n" +
            "bG9iYWxTaWduIE9yZ2FuaXphdGlvbiBWYWxpZGF0aW9uIENBIC0gU0hBMjU2IC0g\n" +
            "RzIwHhcNMjEwNzAxMDExNjAzWhcNMjIwODAyMDExNjAzWjCBpzELMAkGA1UEBhMC\n" +
            "Q04xEDAOBgNVBAgTB2JlaWppbmcxEDAOBgNVBAcTB2JlaWppbmcxJTAjBgNVBAsT\n" +
            "HHNlcnZpY2Ugb3BlcmF0aW9uIGRlcGFydG1lbnQxOTA3BgNVBAoTMEJlaWppbmcg\n" +
            "QmFpZHUgTmV0Y29tIFNjaWVuY2UgVGVjaG5vbG9neSBDby4sIEx0ZDESMBAGA1UE\n" +
            "AxMJYmFpZHUuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAm1HB\n" +
            "m0ZQIHnU05khvgJXhkUKZn2K4iK1E4Kavx+DGar7z3MELQdMQ7ZbhVg37haeoI+n\n" +
            "bwWDpMhbF3PNgNaTLjiHsGrdl0s3eLVh0zrTkjtH0Q0UBddlilbpPExNPFWq4Wed\n" +
            "22Y5AfKpuo/LUjCzmKc+aEDv2WoTrPjXTENYqyFj8ugGgNL5lHurgVFWdcMssVoO\n" +
            "66Mo/q7+1jLr00+OCUO/gdcYxULEtPaoH5w8d6+Fx2ebBcO/GS5sh/dJ4Xbdl5KV\n" +
            "BmJ4kVW2WeI57eR2ps8WGoDQFxd1Q4b7pOf0MGgGzut6hQQsJC/FZq22H9rQ7gZH\n" +
            "DljQqEm14sQvfaj1YQIDAQABo4IGqjCCBqYwDgYDVR0PAQH/BAQDAgWgMIGgBggr\n" +
            "BgEFBQcBAQSBkzCBkDBNBggrBgEFBQcwAoZBaHR0cDovL3NlY3VyZS5nbG9iYWxz\n" +
            "aWduLmNvbS9jYWNlcnQvZ3Nvcmdhbml6YXRpb252YWxzaGEyZzJyMS5jcnQwPwYI\n" +
            "KwYBBQUHMAGGM2h0dHA6Ly9vY3NwMi5nbG9iYWxzaWduLmNvbS9nc29yZ2FuaXph\n" +
            "dGlvbnZhbHNoYTJnMjBWBgNVHSAETzBNMEEGCSsGAQQBoDIBFDA0MDIGCCsGAQUF\n" +
            "BwIBFiZodHRwczovL3d3dy5nbG9iYWxzaWduLmNvbS9yZXBvc2l0b3J5LzAIBgZn\n" +
            "gQwBAgIwCQYDVR0TBAIwADBJBgNVHR8EQjBAMD6gPKA6hjhodHRwOi8vY3JsLmds\n" +
            "b2JhbHNpZ24uY29tL2dzL2dzb3JnYW5pemF0aW9udmFsc2hhMmcyLmNybDCCA2EG\n" +
            "A1UdEQSCA1gwggNUggliYWlkdS5jb22CDGJhaWZ1YmFvLmNvbYIMd3d3LmJhaWR1\n" +
            "LmNughB3d3cuYmFpZHUuY29tLmNugg9tY3QueS5udW9taS5jb22CC2Fwb2xsby5h\n" +
            "dXRvggZkd3ouY26CCyouYmFpZHUuY29tgg4qLmJhaWZ1YmFvLmNvbYIRKi5iYWlk\n" +
            "dXN0YXRpYy5jb22CDiouYmRzdGF0aWMuY29tggsqLmJkaW1nLmNvbYIMKi5oYW8x\n" +
            "MjMuY29tggsqLm51b21pLmNvbYINKi5jaHVhbmtlLmNvbYINKi50cnVzdGdvLmNv\n" +
            "bYIPKi5iY2UuYmFpZHUuY29tghAqLmV5dW4uYmFpZHUuY29tgg8qLm1hcC5iYWlk\n" +
            "dS5jb22CDyoubWJkLmJhaWR1LmNvbYIRKi5mYW55aS5iYWlkdS5jb22CDiouYmFp\n" +
            "ZHViY2UuY29tggwqLm1pcGNkbi5jb22CECoubmV3cy5iYWlkdS5jb22CDiouYmFp\n" +
            "ZHVwY3MuY29tggwqLmFpcGFnZS5jb22CCyouYWlwYWdlLmNugg0qLmJjZWhvc3Qu\n" +
            "Y29tghAqLnNhZmUuYmFpZHUuY29tgg4qLmltLmJhaWR1LmNvbYISKi5iYWlkdWNv\n" +
            "bnRlbnQuY29tggsqLmRsbmVsLmNvbYILKi5kbG5lbC5vcmeCEiouZHVlcm9zLmJh\n" +
            "aWR1LmNvbYIOKi5zdS5iYWlkdS5jb22CCCouOTEuY29tghIqLmhhbzEyMy5iYWlk\n" +
            "dS5jb22CDSouYXBvbGxvLmF1dG+CEioueHVlc2h1LmJhaWR1LmNvbYIRKi5iai5i\n" +
            "YWlkdWJjZS5jb22CESouZ3ouYmFpZHViY2UuY29tgg4qLnNtYXJ0YXBwcy5jboIN\n" +
            "Ki5iZHRqcmN2LmNvbYIMKi5oYW8yMjIuY29tggwqLmhhb2thbi5jb22CDyoucGFl\n" +
            "LmJhaWR1LmNvbYIRKi52ZC5iZHN0YXRpYy5jb22CESouY2xvdWQuYmFpZHUuY29t\n" +
            "ghJjbGljay5obS5iYWlkdS5jb22CEGxvZy5obS5iYWlkdS5jb22CEGNtLnBvcy5i\n" +
            "YWlkdS5jb22CEHduLnBvcy5iYWlkdS5jb22CFHVwZGF0ZS5wYW4uYmFpZHUuY29t\n" +
            "MB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAfBgNVHSMEGDAWgBSW3mHx\n" +
            "vRwWKVMcwMx9O4MAQOYafDAdBgNVHQ4EFgQUNJKaLwxxYrw92yNtbT6z0R0Rne0w\n" +
            "ggF9BgorBgEEAdZ5AgQCBIIBbQSCAWkBZwB1ACJFRQdZVSRWlj+hL/H3bYbgIyZj\n" +
            "rcBLf13Gg1xu4g8CAAABel+jJjkAAAQDAEYwRAIgIPt5kWXsm47PrqSljzkXx3xD\n" +
            "t0xLC/fIIWbRTrvyJFUCIDxgcy89XYHRxW/WLY/pBDAv1fnK5MpocUYZi7c4uvDl\n" +
            "AHYAKXm+8J45OSHwVnOfY6V35b5XfZxgCvj5TV0mXCVdx4QAAAF6X6MmKgAABAMA\n" +
            "RzBFAiEAzl3C9AQOsbfgoBe61Dnc72Fa+8X3MmImCrsG6kb2f8oCIGeDQqgTEHzx\n" +
            "bjQzGKr4nnjBDPkVpljrV4SUc3n5ysgvAHYAVYHUwhaQNgFK6gubVzxT8MDkOHhw\n" +
            "JQgXL6OqHQcT0wwAAAF6X6MmSwAABAMARzBFAiB5KnN89d/LeQheoojaviS16dad\n" +
            "95CR2Wr8pZWVamxDfgIhAL+3MqWq+E+8mtOIWDyebnH2nS+mm91pmO1mA5CSyiKR\n" +
            "MA0GCSqGSIb3DQEBCwUAA4IBAQA5igBJkkgWWN4+nM6DORuxrJqV+Vb/fC2C06g2\n" +
            "W+bPff1KmHJI8rf2UtQLCSyiXDNH4pqbPpe92LoACcmuHrO83uge4d7ZBfipsD3t\n" +
            "uXqyqTyTTgeM8F7Mi/N1M25VguWZQp+cgVT7rc4oDDhCYJVo4U1fgy2kMnbYURwd\n" +
            "ZrecrR8Z+UDkfHRN2yq76vMkTek4dyFSPP0egR6QAISuyGb844F4kdBDeJkqpIUx\n" +
            "PJ9r70ieHjlNUQe3U03/4hOr48ptfCH24voic/RlcXV32giO9y1b5gHJ95YMXy2o\n" +
            "1z5MXsKSeOQbTpsoNp8Yd/K79WpkcXgP6tVofxFXtP8PsORz\n" +
            "-----END CERTIFICATE-----\n";

    public static HostnameVerifier VERIFY = new HostnameVerifier() {
        @ Override
        public boolean verify(String hostname, SSLSession session) {
            try {
                javax.security.cert.X509Certificate[] peerCertificateChain = session.getPeerCertificateChain();
                javax.security.cert.X509Certificate cf = peerCertificateChain[0];

                RSAPublicKey pubkey = (RSAPublicKey)cf.getPublicKey();
                String encoded = Base64.encodeToString(pubkey.getEncoded(),0);

                CertificateFactory finalcf = CertificateFactory.getInstance("X.509");
                X509Certificate realCertificate = (X509Certificate)finalcf.generateCertificate(new ByteArrayInputStream(certificate.getBytes()));
                String realPubKey = Base64.encodeToString(realCertificate.getPublicKey().getEncoded(),0);

                cf.checkValidity();
                Log.d("xiaojianbang", "HostnameVerifier IssuerDN: " + cf.getIssuerDN().toString());
                Log.d("xiaojianbang", "HostnameVerifier SubjectDN: " + cf.getSubjectDN().toString());
                Log.d("xiaojianbang", "HostnameVerifier 证书版本: "+ cf.getVersion());

                final boolean expected = realPubKey.equalsIgnoreCase(encoded);
                if (!expected) {
                    throw new CertificateException("HostnameVerifier: got error public key: " + encoded);
                }
                Log.d("xiaojianbang","HostnameVerifier 证书公钥验证正确");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    };

    public static void doRequest(){
        new Thread(){
            public void run(){
                String result = HttpsRequest("POST", "https://www.baidu.com/", "user");
                Log.d("xiaojianbang","" + result);
            }
        }.start();
    }

    private static SSLContext getSSLContext() {

        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                if (chain == null) {
                    throw new IllegalArgumentException("checkServerTrusted: X509Certificate array is null");
                }
                if (!(chain.length > 0)) {
                    throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
                }
                if (!(!TextUtils.isEmpty(authType) && authType.toUpperCase().contains("RSA"))) {
                    throw new CertificateException("checkServerTrusted: AuthType is not RSA");
                }
                Log.d("xiaojianbang","authType: " + authType);
                X509Certificate cf = chain[0];

                RSAPublicKey pubkey = (RSAPublicKey)cf.getPublicKey();
                String encoded = Base64.encodeToString(pubkey.getEncoded(),0);

                CertificateFactory finalcf = CertificateFactory.getInstance("X.509");
                X509Certificate realCertificate = (X509Certificate)finalcf.generateCertificate(new ByteArrayInputStream(certificate.getBytes()));
                String realPubKey = Base64.encodeToString(realCertificate.getPublicKey().getEncoded(),0);

                cf.checkValidity();
                Log.d("xiaojianbang", "IssuerDN: " + cf.getIssuerDN().toString());
                Log.d("xiaojianbang", "SubjectDN: " + cf.getSubjectDN().toString());
                Log.d("xiaojianbang", "证书版本: "+ cf.getVersion());

                final boolean expected = realPubKey.equalsIgnoreCase(encoded);
                if (!expected) {
                    throw new CertificateException("checkServerTrusted: got error public key: " + encoded);
                }
                Log.d("xiaojianbang","证书公钥验证正确");

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };

        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext;
    }

    public static String HttpsRequest(String method, String url, String outputStr) {

        try {
            SSLContext sslContext = getSSLContext();
            if (sslContext != null) {
                URL u = new URL(url);
                HttpsURLConnection conn = (HttpsURLConnection) u.openConnection(Proxy.NO_PROXY);

                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setUseCaches(false);
                conn.setConnectTimeout(30000);
                conn.setSSLSocketFactory(sslContext.getSocketFactory());
                conn.setHostnameVerifier(VERIFY);

                if(method.equals("POST")){
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                }

                if (null != outputStr) {
                    OutputStream outputStream = conn.getOutputStream();
                    outputStream.write(outputStr.getBytes("UTF-8"));
                    outputStream.close();
                }

                conn.connect();

                InputStream inputStream = conn.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = null;
                StringBuffer buffer = new StringBuffer();
                while ((str = bufferedReader.readLine()) != null) {
                    buffer.append(str);
                }
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
                conn.disconnect();
                return buffer.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
