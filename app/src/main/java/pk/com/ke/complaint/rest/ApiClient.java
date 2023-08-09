package pk.com.ke.complaint.rest;

/**
 * Created by Nadeem Iqbal on 10/24/17.
 */


import android.text.TextUtils;

import com.google.gson.GsonBuilder;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import static pk.com.ke.complaint.Config.BASE_URL;
import static pk.com.ke.complaint.Config.CERTIFICATE;
import static pk.com.ke.complaint.Config.getHttpClient;
import static pk.com.ke.complaint.Config.getUserCertificate2;

public class ApiClient {

    private static Retrofit retrofit_xml = null;
    private static Retrofit retrofit = null;
    private static Retrofit retrofit_JSON = null;
    private static Retrofit retrofit_URL = null;

    private static int TIMEOUT_CONNECT = 20;
    private static int TIMEOUT_READ = 300;

    private static HttpLoggingInterceptor.Level LEVEL = HttpLoggingInterceptor.Level.BODY;


    public static Retrofit getClientXML() {
        OkHttpClient client = null;
        try {
            client = getHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (client != null) {
            if (retrofit_xml == null) {
                retrofit_xml = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(
                                SimpleXmlConverterFactory.createNonStrict(
                                        new Persister(new AnnotationStrategy()
                                        )
                                )
                        )
                        .build();
            }
        } else {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
//                        .client()
                        .build();
            }
        }
        return retrofit_xml;
    }

    public static Retrofit getInstance() {


        if (retrofit_URL == null) {
            //SETTING UP OKHTTP CLIENT
            OkHttpClient okHttpClient;
            OkHttpClient.Builder builder = new OkHttpClient.Builder();

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(LEVEL);

            builder.addInterceptor(loggingInterceptor);
            builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS);
            builder.readTimeout(TIMEOUT_READ, TimeUnit.SECONDS);

            X509TrustManager x509TrustManager = null;
            SSLSocketFactory sslSocketFactory = null;
            try {
                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream bytes = new ByteArrayInputStream(CERTIFICATE.getBytes());
                //ByteArrayInputStream bytesSAML = new ByteArrayInputStream(getSAMLCertificate().getBytes());//SAML

                X509Certificate x509Certificate = (X509Certificate) certFactory.generateCertificate(bytes);
                //X509Certificate x509CertificateSAML = (X509Certificate) certFactory.generateCertificate(bytesSAML);//SAML


                // Create a KeyStore containing our trusted CAs
                String keyStoreType = KeyStore.getDefaultType();
                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(null, null);
                keyStore.setCertificateEntry("ca", x509Certificate);
                //keyStore.setCertificateEntry("ca", x509CertificateSAML);//SAML


                // Create a TrustManager that trusts the CAs in our KeyStore.
                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
                trustManagerFactory.init(keyStore);

                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                x509TrustManager = (X509TrustManager) trustManagers[0];


                // Create an SSLSocketFactory that uses our TrustManager
                SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
                sslSocketFactory = sslContext.getSocketFactory();


                //TODO: hostname verification fix needed

                //create Okhttp client
                builder.sslSocketFactory(sslSocketFactory, x509TrustManager);

                builder.hostnameVerifier((hostname, session) -> true);
            } catch (CertificateException | KeyStoreException | IOException | KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }

//            //For SAML Auth
//            try {
//                CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
//                ByteArrayInputStream bytes = new ByteArrayInputStream(getSAMLCertificate().getBytes());
//                X509Certificate x509Certificate = (X509Certificate) certFactory.generateCertificate(bytes);
//
//
//                // Create a KeyStore containing our trusted CAs
//                String keyStoreType = KeyStore.getDefaultType();
//                KeyStore keyStore = KeyStore.getInstance(keyStoreType);
//                keyStore.load(null, null);
//                keyStore.setCertificateEntry("ca", x509Certificate);
//
//
//                // Create a TrustManager that trusts the CAs in our KeyStore.
//                String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
//                trustManagerFactory.init(keyStore);
//
//                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
//                x509TrustManager = (X509TrustManager) trustManagers[0];
//
//
//                // Create an SSLSocketFactory that uses our TrustManager
//                SSLContext sslContext = SSLContext.getInstance("SSL");
//                sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
//                sslSocketFactory = sslContext.getSocketFactory();
//
//
//                //TODO: hostname verification fix needed
//
//                //create Okhttp client
//                builder.sslSocketFactory(sslSocketFactory, x509TrustManager);
//
//                builder.hostnameVerifier((hostname, session) -> true);
//            } catch (CertificateException | KeyStoreException | IOException | KeyManagementException | NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            }


            okHttpClient = builder.build();


            //CREATING RETROFIT INSTANCE
            retrofit_URL = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .setLenient()
                            .create()))
                    .build();
        }

        return retrofit_URL;
    }


    public static Retrofit getClient() {
        OkHttpClient client = null;
        try {
            client = getHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (client != null) {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .build();
            }
        } else {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
//                        .client()
                        .build();
            }
        }

        return retrofit;
    }


    public static Retrofit getClient_JSON() {
        OkHttpClient client = null;
        try {
            client = getHttpClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (client != null) {
            if (retrofit_JSON == null) {
                retrofit_JSON = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                        .build();
            }
        } else {
            if (retrofit_JSON == null) {
                retrofit_JSON = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
//                        .client(getHttpClient2())
                        .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                        .build();
            }
        }

        return retrofit_JSON;
    }

}