package com.iteaj.util.module.http;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Create Date By 2018-04-08
 *
 * @author iteaj
 * @since 1.7
 */
public class SSLContextManager {

    public SSLContext getSslContext() throws NoSuchAlgorithmException, KeyManagementException {
        //添加信任证书
        TrustManager[] tm={new AllTrustManager()};//AllTrustManager()为信任所有证书

        SSLContext ctx=SSLContext.getInstance("SSL");//创建ssl上下文

        ctx.init(null, tm, new SecureRandom());
        return ctx;
    }

    protected class AllTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}
