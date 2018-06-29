package com.fujisoft.update;

import android.app.Application;
import com.lzy.okgo.OkGo;
import okio.Buffer;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo okGo = OkGo.getInstance().init(this);
        // X509証明書信頼マネージャ
        X509TrustManager trustManager = null;
        // SSL安全ソケット工場
        SSLSocketFactory sslSocketFactory = null;
        try {
            // 導入証明書
            trustManager = trustManagerForCertificates(
                    new Buffer().writeUtf8(Constant.cerInNetStr).inputStream(),
                    new Buffer().writeUtf8(Constant.cerOutNetStr).inputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        okGo.getOkHttpClient()
                .newBuilder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String s, SSLSession sslSession) {
                        return true;
                    }
                }).build();


    }

    /**
     * 導入証明書
     *
     * @param certificates
     * @return
     * @throws GeneralSecurityException
     */
    private X509TrustManager trustManagerForCertificates(InputStream... certificates)
            throws GeneralSecurityException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        // 証明書のキーストアを置きする
        char[] password = Constant.CER_PWD.toCharArray();
        KeyStore keyStore = newEmptyKeyStore(password);
        int index = 0;
        for (InputStream certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias,
                    certificateFactory.generateCertificate(certificate));
        }

        // X509証明書信頼マネージャを構築するためのキーストアを使用する
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
                KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
                TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1
                || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                    + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    /**
     * 空キーストアを新規する
     * @param password　パスワード
     * @return
     * @throws GeneralSecurityException
     */
    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            // キーストアを新規する
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in = null;
            // パスワードを設定する
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
