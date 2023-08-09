package pk.com.ke.complaint;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by iqbal.nadeem on 14-Mar-18.
 */

public class Config {

    public static final boolean IS_LOG_ALLOWED = true;

    public static final String URL = "fioriprd.ke.com.pk";
    public static final String BASE_URL = "https://" + URL + ":44300/";

    public static String FONT_NAME = "PT_Sans-Web-Regular.ttf";

    public static String Firebase_DB_NAME = "locations";

    public static HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.BODY;

/* commented on 6th March 2023
    public static final String CERTIFICATE ="-----BEGIN CERTIFICATE-----\n" +
            "MIICVjCCAb8CCAogGAMnBgEBMA0GCSqGSIb3DQEBBQUAMHAxCzAJBgNVBAYTAkRF\n" +
            "MRwwGgYDVQQKExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2Vi\n" +
            "IEFTMREwDwYDVQQLEwhJSU5JVElBTDEbMBkGA1UEAxMSZmlvcmlwcmQua2UuY29t\n" +
            "LnBrMB4XDTE4MDMyNzA2MDEwMVoXDTM4MDEwMTAwMDAwMVowcDELMAkGA1UEBhMC\n" +
            "REUxHDAaBgNVBAoTE1NBUCBUcnVzdCBDb21tdW5pdHkxEzARBgNVBAsTClNBUCBX\n" +
            "ZWIgQVMxETAPBgNVBAsTCElJTklUSUFMMRswGQYDVQQDExJmaW9yaXByZC5rZS5j\n" +
            "b20ucGswgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAJ2RAk4yjmby+DUXoHUP\n" +
            "8+LQ+eM4QiB7o2RZupUxH/13Nz2ag7zDwHdlXljhctomkdjqFp9qBbmafRUy7W7H\n" +
            "I5Rwg/YT8K5CD8Ol+SctvlZpfLO7eMzmH4JuY7OGdyldDvPRB7DwUg4R1Uz/si0G\n" +
            "c3WVTdV8otGmJ+ofLED7iH4LAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAeLwqAJOM\n" +
            "GUb1r6X/EQGKrpevbZWybcVLmqnMno4vILFkeZIaQbKiTcjYI/5OFtnKudbh7jWo\n" +
            "wTB0ttmbjS3LZy4Xmxd5+j2/ek1Ku+ipMo3jD+18FrifMy58ueXl3zqwGEF/FEpl\n" +
            "cgnqrGHTHAwK4df5D0fLLalmoM5/5aqkdts=\n" +
            "-----END CERTIFICATE-----";
*/
    // Add START on 6th March 2023

    public static final String CERTIFICATE ="-----BEGIN CERTIFICATE-----\n" +
            "MIIGxTCCBa2gAwIBAgIQaOwbiOXtnWYH7i2ldAFvHjANBgkqhkiG9w0BAQsFADCB\n" +
            "lTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
            "A1UEBxMHU2FsZm9yZDEYMBYGA1UEChMPU2VjdGlnbyBMaW1pdGVkMT0wOwYDVQQD\n" +
            "EzRTZWN0aWdvIFJTQSBPcmdhbml6YXRpb24gVmFsaWRhdGlvbiBTZWN1cmUgU2Vy\n" +
            "dmVyIENBMB4XDTIyMTIyMjAwMDAwMFoXDTI0MDEyMjIzNTk1OVowSDELMAkGA1UE\n" +
            "BhMCUEsxDjAMBgNVBAgTBVNpbmRoMRMwEQYDVQQKEwpLLUVsZWN0cmljMRQwEgYD\n" +
            "VQQDDAsqLmtlLmNvbS5wazCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
            "AN1sx3z21kPCR1z5wNPCIDZP6mQb8tUNTgESVPztF2VpTGxZKWjUdVKsuSHdItYY\n" +
            "56HzGJvxmvJ9sbBA+38eQpIruOg4kg/TiIYG+0tgPuJbldNfgf6XiSRaDYMXKdKR\n" +
            "ChwQp1NPO7LCF7YKQWUzAgnyw2qXmfkLVjCClL4Bt4RxVm7MF/K087aATM1zsIrk\n" +
            "wqT9SgJK1RuWIcr8lxgDnFsAn3JRM018ixFhNnvyek6A5wwmOmTWMCm0J5kXE10p\n" +
            "6NBmNm5zv6eJTs6GcEPP1jMmfGvDODoH/viRs6wN2/VCPQ3c+2lMBTrb6HmhDOZJ\n" +
            "N+fYQeIAzxFeSnCAwLmaCTsCAwEAAaOCA1swggNXMB8GA1UdIwQYMBaAFBfZ1iUn\n" +
            "Z/kxwklD2TA2RIxsqU/rMB0GA1UdDgQWBBTrlW83tqRLQ0L2iw71+HdyvnO4FzAO\n" +
            "BgNVHQ8BAf8EBAMCBaAwDAYDVR0TAQH/BAIwADAdBgNVHSUEFjAUBggrBgEFBQcD\n" +
            "AQYIKwYBBQUHAwIwSgYDVR0gBEMwQTA1BgwrBgEEAbIxAQIBAwQwJTAjBggrBgEF\n" +
            "BQcCARYXaHR0cHM6Ly9zZWN0aWdvLmNvbS9DUFMwCAYGZ4EMAQICMFoGA1UdHwRT\n" +
            "MFEwT6BNoEuGSWh0dHA6Ly9jcmwuc2VjdGlnby5jb20vU2VjdGlnb1JTQU9yZ2Fu\n" +
            "aXphdGlvblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5jcmwwgYoGCCsGAQUFBwEB\n" +
            "BH4wfDBVBggrBgEFBQcwAoZJaHR0cDovL2NydC5zZWN0aWdvLmNvbS9TZWN0aWdv\n" +
            "UlNBT3JnYW5pemF0aW9uVmFsaWRhdGlvblNlY3VyZVNlcnZlckNBLmNydDAjBggr\n" +
            "BgEFBQcwAYYXaHR0cDovL29jc3Auc2VjdGlnby5jb20wIQYDVR0RBBowGIILKi5r\n" +
            "ZS5jb20ucGuCCWtlLmNvbS5wazCCAX4GCisGAQQB1nkCBAIEggFuBIIBagFoAHYA\n" +
            "dv+IPwq2+5VRwmHM9Ye6NLSkzbsp3GhCCp/mZ0xaOnQAAAGFOmhh9wAABAMARzBF\n" +
            "AiAFENMO57klo+T9zV6hyJ6XMvxfdsy1My/5VRy7rhky6wIhAJY53La5BSG2p+Ib\n" +
            "xFVRwOwLRUPJtt3eBmWA37agbikfAHcA2ra/az+1tiKfm8K7XGvocJFxbLtRhIU0\n" +
            "vaQ9MEjX+6sAAAGFOmhhxgAABAMASDBGAiEAxOduta4CtzATxWamZOi7LxSXelGj\n" +
            "iDK7m/1Wc7382/ICIQD+OL4a2h96gjJKTREL+VdVEj9DX34YPUWIWOUZe+K9NAB1\n" +
            "AO7N0GTV2xrOxVy3nbTNE6Iyh0Z8vOzew1FIWUZxH7WbAAABhTpoYZIAAAQDAEYw\n" +
            "RAIgciEOBAyPnevaGZZvhE87WJVgdv8B7/bE0KwYQdRnJpECIBmQbnUEOjIMH7UT\n" +
            "VxnXPNGi0idpyzpKNb/iZDnKSyR2MA0GCSqGSIb3DQEBCwUAA4IBAQAru7tA41Z1\n" +
            "WSuOd1EqHg8exjxmSL1xf96GP/Qip27YwGSzlbBPpU9Lldlv6007h2rYJDTnEVN3\n" +
            "4pdCGCbxDFm65dwIUOUGBCOEXAQy22bIzWdvBSanngFQ0dnfFoQJJAfnziu+/8Wo\n" +
            "cmeR74ytFHhjgpSoBo7oI8rbQc+U4lCKTQsRwuT12WJhTlOQjhBKQ0UgfXhK0TAh\n" +
            "nIz6gbVh2JxuTlD5eq5m0dq5LHtz0fKWX+1t4J9rUEpehKINEwss+iQ0Egz4fPs3\n" +
            "fD+te+/+sSIvSqtDZfhcSqk2+EDn/COp1VUvNhiEAmeNe8zx8Y3LT0TXK5s7tBCR\n" +
            "X+S+Nw3YR11K\n" +
            "-----END CERTIFICATE-----";









    public static X509Certificate getUserCertificate2() throws Exception {
        // this certificate does not include any extensions

        /* commented on 6th March 2023

        String sCert =
                "-----BEGIN CERTIFICATE-----\n" +
                        "MIICVjCCAb8CCAogGAMnBgEBMA0GCSqGSIb3DQEBBQUAMHAxCzAJBgNVBAYTAkRF\n" +
                        "MRwwGgYDVQQKExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2Vi\n" +
                        "IEFTMREwDwYDVQQLEwhJSU5JVElBTDEbMBkGA1UEAxMSZmlvcmlwcmQua2UuY29t\n" +
                        "LnBrMB4XDTE4MDMyNzA2MDEwMVoXDTM4MDEwMTAwMDAwMVowcDELMAkGA1UEBhMC\n" +
                        "REUxHDAaBgNVBAoTE1NBUCBUcnVzdCBDb21tdW5pdHkxEzARBgNVBAsTClNBUCBX\n" +
                        "ZWIgQVMxETAPBgNVBAsTCElJTklUSUFMMRswGQYDVQQDExJmaW9yaXByZC5rZS5j\n" +
                        "b20ucGswgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAJ2RAk4yjmby+DUXoHUP\n" +
                        "8+LQ+eM4QiB7o2RZupUxH/13Nz2ag7zDwHdlXljhctomkdjqFp9qBbmafRUy7W7H\n" +
                        "I5Rwg/YT8K5CD8Ol+SctvlZpfLO7eMzmH4JuY7OGdyldDvPRB7DwUg4R1Uz/si0G\n" +
                        "c3WVTdV8otGmJ+ofLED7iH4LAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAeLwqAJOM\n" +
                        "GUb1r6X/EQGKrpevbZWybcVLmqnMno4vILFkeZIaQbKiTcjYI/5OFtnKudbh7jWo\n" +
                        "wTB0ttmbjS3LZy4Xmxd5+j2/ek1Ku+ipMo3jD+18FrifMy58ueXl3zqwGEF/FEpl\n" +
                        "cgnqrGHTHAwK4df5D0fLLalmoM5/5aqkdts=\n" +
                        "-----END CERTIFICATE-----";

     */


        // Add START on 6th March 2023
        String sCert =
                "-----BEGIN CERTIFICATE-----\n" +
                        "MIIGxTCCBa2gAwIBAgIQaOwbiOXtnWYH7i2ldAFvHjANBgkqhkiG9w0BAQsFADCB\n" +
                        "lTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
                        "A1UEBxMHU2FsZm9yZDEYMBYGA1UEChMPU2VjdGlnbyBMaW1pdGVkMT0wOwYDVQQD\n" +
                        "EzRTZWN0aWdvIFJTQSBPcmdhbml6YXRpb24gVmFsaWRhdGlvbiBTZWN1cmUgU2Vy\n" +
                        "dmVyIENBMB4XDTIyMTIyMjAwMDAwMFoXDTI0MDEyMjIzNTk1OVowSDELMAkGA1UE\n" +
                        "BhMCUEsxDjAMBgNVBAgTBVNpbmRoMRMwEQYDVQQKEwpLLUVsZWN0cmljMRQwEgYD\n" +
                        "VQQDDAsqLmtlLmNvbS5wazCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEB\n" +
                        "AN1sx3z21kPCR1z5wNPCIDZP6mQb8tUNTgESVPztF2VpTGxZKWjUdVKsuSHdItYY\n" +
                        "56HzGJvxmvJ9sbBA+38eQpIruOg4kg/TiIYG+0tgPuJbldNfgf6XiSRaDYMXKdKR\n" +
                        "ChwQp1NPO7LCF7YKQWUzAgnyw2qXmfkLVjCClL4Bt4RxVm7MF/K087aATM1zsIrk\n" +
                        "wqT9SgJK1RuWIcr8lxgDnFsAn3JRM018ixFhNnvyek6A5wwmOmTWMCm0J5kXE10p\n" +
                        "6NBmNm5zv6eJTs6GcEPP1jMmfGvDODoH/viRs6wN2/VCPQ3c+2lMBTrb6HmhDOZJ\n" +
                        "N+fYQeIAzxFeSnCAwLmaCTsCAwEAAaOCA1swggNXMB8GA1UdIwQYMBaAFBfZ1iUn\n" +
                        "Z/kxwklD2TA2RIxsqU/rMB0GA1UdDgQWBBTrlW83tqRLQ0L2iw71+HdyvnO4FzAO\n" +
                        "BgNVHQ8BAf8EBAMCBaAwDAYDVR0TAQH/BAIwADAdBgNVHSUEFjAUBggrBgEFBQcD\n" +
                        "AQYIKwYBBQUHAwIwSgYDVR0gBEMwQTA1BgwrBgEEAbIxAQIBAwQwJTAjBggrBgEF\n" +
                        "BQcCARYXaHR0cHM6Ly9zZWN0aWdvLmNvbS9DUFMwCAYGZ4EMAQICMFoGA1UdHwRT\n" +
                        "MFEwT6BNoEuGSWh0dHA6Ly9jcmwuc2VjdGlnby5jb20vU2VjdGlnb1JTQU9yZ2Fu\n" +
                        "aXphdGlvblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5jcmwwgYoGCCsGAQUFBwEB\n" +
                        "BH4wfDBVBggrBgEFBQcwAoZJaHR0cDovL2NydC5zZWN0aWdvLmNvbS9TZWN0aWdv\n" +
                        "UlNBT3JnYW5pemF0aW9uVmFsaWRhdGlvblNlY3VyZVNlcnZlckNBLmNydDAjBggr\n" +
                        "BgEFBQcwAYYXaHR0cDovL29jc3Auc2VjdGlnby5jb20wIQYDVR0RBBowGIILKi5r\n" +
                        "ZS5jb20ucGuCCWtlLmNvbS5wazCCAX4GCisGAQQB1nkCBAIEggFuBIIBagFoAHYA\n" +
                        "dv+IPwq2+5VRwmHM9Ye6NLSkzbsp3GhCCp/mZ0xaOnQAAAGFOmhh9wAABAMARzBF\n" +
                        "AiAFENMO57klo+T9zV6hyJ6XMvxfdsy1My/5VRy7rhky6wIhAJY53La5BSG2p+Ib\n" +
                        "xFVRwOwLRUPJtt3eBmWA37agbikfAHcA2ra/az+1tiKfm8K7XGvocJFxbLtRhIU0\n" +
                        "vaQ9MEjX+6sAAAGFOmhhxgAABAMASDBGAiEAxOduta4CtzATxWamZOi7LxSXelGj\n" +
                        "iDK7m/1Wc7382/ICIQD+OL4a2h96gjJKTREL+VdVEj9DX34YPUWIWOUZe+K9NAB1\n" +
                        "AO7N0GTV2xrOxVy3nbTNE6Iyh0Z8vOzew1FIWUZxH7WbAAABhTpoYZIAAAQDAEYw\n" +
                        "RAIgciEOBAyPnevaGZZvhE87WJVgdv8B7/bE0KwYQdRnJpECIBmQbnUEOjIMH7UT\n" +
                        "VxnXPNGi0idpyzpKNb/iZDnKSyR2MA0GCSqGSIb3DQEBCwUAA4IBAQAru7tA41Z1\n" +
                        "WSuOd1EqHg8exjxmSL1xf96GP/Qip27YwGSzlbBPpU9Lldlv6007h2rYJDTnEVN3\n" +
                        "4pdCGCbxDFm65dwIUOUGBCOEXAQy22bIzWdvBSanngFQ0dnfFoQJJAfnziu+/8Wo\n" +
                        "cmeR74ytFHhjgpSoBo7oI8rbQc+U4lCKTQsRwuT12WJhTlOQjhBKQ0UgfXhK0TAh\n" +
                        "nIz6gbVh2JxuTlD5eq5m0dq5LHtz0fKWX+1t4J9rUEpehKINEwss+iQ0Egz4fPs3\n" +
                        "fD+te+/+sSIvSqtDZfhcSqk2+EDn/COp1VUvNhiEAmeNe8zx8Y3LT0TXK5s7tBCR\n" +
                        "X+S+Nw3YR11K\n" +
                        "-----END CERTIFICATE-----";


        // Add END on 6th March 2023


        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        ByteArrayInputStream bytes = new ByteArrayInputStream(sCert.getBytes());
        return (X509Certificate) certFactory.generateCertificate(bytes);
    }

    public static OkHttpClient.Builder getHttpClient2() {

        try {
            Certificate certificate = getUserCertificate2();

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", certificate);


            // Create a TrustManager that trusts the CAs in our KeyStore.
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm);
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            X509TrustManager x509TrustManager = (X509TrustManager) trustManagers[0];


            // Create an SSLSocketFactory that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            //create Okhttp client
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, x509TrustManager);

            return builder;
        } catch (CertificateException | NoSuchAlgorithmException | KeyManagementException | KeyStoreException | IOException e) {
            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    public static OkHttpClient getHttpClient() throws Exception {
//        return getUnsafeOkHttpClient();
        OkHttpClient.Builder builder = getHttpClient2();

        if (builder == null) {
            builder = new OkHttpClient.Builder();
        }

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(LOG_LEVEL);
        builder.addInterceptor(interceptor);
        //            builder.certificatePinner(getCertificatePinner());
        builder.connectTimeout(20, TimeUnit.SECONDS);
        builder.readTimeout(30, TimeUnit.SECONDS);

        return builder.build();

    }

    /*
     * Not using this Method
     * 
     * private static OkHttpClient getUnsafeOkHttpClient() {
     * try {
     * // Create a trust manager that does not validate certificate chains
     * final TrustManager[] trustAllCerts = new TrustManager[]{
     * new X509TrustManager() {
     *
     * @Override public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
     * }
     * @Override public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
     * }
     * @Override public java.security.cert.X509Certificate[] getAcceptedIssuers() {
     * return new java.security.cert.X509Certificate[]{};
     * }
     * }
     * };
     * <p>
     * // Install the all-trusting trust manager
     * final SSLContext sslContext = SSLContext.getInstance("SSL");
     * sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
     * // Create an ssl socket factory with our all-trusting manager
     * final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
     * <p>
     * OkHttpClient.Builder builder = new OkHttpClient.Builder();
     * builder.sslSocketFactory(sslSocketFactory);
     * builder.hostnameVerifier(new HostnameVerifier() {
     * @Override public boolean verify(String hostname, SSLSession session) {
     * return true;
     * }
     * });
     * <p>
     * <p>
     * HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
     * interceptor.setLevel(LOG_LEVEL);
     * builder.addInterceptor(interceptor);
     * //            builder.certificatePinner(getCertificatePinner());
     * <p>
     * builder.connectTimeout(20, TimeUnit.SECONDS);
     * builder.readTimeout(30, TimeUnit.SECONDS);
     * return builder.build();
     * } catch (Exception e) {
     * return null;
     * }
     * }
     */

    private static CertificatePinner getCertificatePinner() {
        CertificatePinner certificatePinner = new CertificatePinner.Builder()
                .add(URL, "sha256/")
                .build();

        return certificatePinner;
    }


}
