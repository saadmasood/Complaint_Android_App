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
import pk.com.ke.complaint.utils.BasicAuthInterceptor;

/**
 * Created by iqbal.nadeem on 14-Mar-18.
 */

public class Config {

    public static final boolean IS_LOG_ALLOWED = true;

    public static final String URL = "fioriqa.ke.com.pk";
    public static final String BASE_URL = "https://" + URL + ":44300/";

    public static String FONT_NAME = "PT_Sans-Web-Regular.ttf";

    public static String Firebase_DB_NAME = "locations_qa";

    public static HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.NONE;

    public static final String CERTIFICATE =
/* commented by Saad on 28-Oct-2022
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIH0jCCBrqgAwIBAgIQaZ5FILli7R9cUi2zTKip/zANBgkqhkiG9w0BAQsFADCB\n" +
            "lTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
            "A1UEBxMHU2FsZm9yZDEYMBYGA1UEChMPU2VjdGlnbyBMaW1pdGVkMT0wOwYDVQQD\n" +
            "EzRTZWN0aWdvIFJTQSBPcmdhbml6YXRpb24gVmFsaWRhdGlvbiBTZWN1cmUgU2Vy\n" +
            "dmVyIENBMB4XDTIwMDExMDAwMDAwMFoXDTIyMDQxMzAwMDAwMFowgdsxCzAJBgNV\n" +
            "BAYTAlBLMQ4wDAYDVQQREwU3NDQwMDEOMAwGA1UECBMFU2luZGgxEDAOBgNVBAcT\n" +
            "B0thcmFjaGkxLTArBgNVBAkTJFBoYXNlLUlJLCBEZWZlbmNlIEhvdXNpbmcgQXV0\n" +
            "aG9yaXR5LDEgMB4GA1UECRMXMzktQiwgU3Vuc2V0IEJvdWxldmFyZCwxETAPBgNV\n" +
            "BAkTCEtFIEhvdXNlMRMwEQYDVQQKEwpLIEVsZWN0cmljMQswCQYDVQQLEwJJVDEU\n" +
            "MBIGA1UEAwwLKi5rZS5jb20ucGswggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK\n" +
            "AoIBAQDD++iSgn4aYT6G7uYPpuupxD2RZ66wWl7Okaqjmi5wMVBCYqzAMRwBzPN+\n" +
            "9WvtCauWdIz5mls6YZ8AiUcPtnptxldxwhmeXVyG+ZZHGXUFZSi76A9V8u2ci9dd\n" +
            "x0o9Gl8g5a9rPKgFSj7gR3Dr78wBXVc9ZbXfb/e7ssH/UuVaNyRzx6FBjMfH65CI\n" +
            "moYSd8s/zuzqRz8K4SBLmeO7bHxUyC3s8fO3HSatHSCIG/0m1Sge4vFmD7CtgYKI\n" +
            "hF1hKHiagpkSHAvBpVIw28ugUx+tCdDdtBIoq9rc4k525oNAmUZyybeGCf7eACIJ\n" +
            "K5C53UgD3R7guRpMXeFjWG2atGLNAgMBAAGjggPUMIID0DAfBgNVHSMEGDAWgBQX\n" +
            "2dYlJ2f5McJJQ9kwNkSMbKlP6zAdBgNVHQ4EFgQU83k2shGsK6bJy+AH7HubUDm0\n" +
            "V6MwDgYDVR0PAQH/BAQDAgWgMAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYwFAYIKwYB\n" +
            "BQUHAwEGCCsGAQUFBwMCMEoGA1UdIARDMEEwNQYMKwYBBAGyMQECAQMEMCUwIwYI\n" +
            "KwYBBQUHAgEWF2h0dHBzOi8vc2VjdGlnby5jb20vQ1BTMAgGBmeBDAECAjBaBgNV\n" +
            "HR8EUzBRME+gTaBLhklodHRwOi8vY3JsLnNlY3RpZ28uY29tL1NlY3RpZ29SU0FP\n" +
            "cmdhbml6YXRpb25WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0EuY3JsMIGKBggrBgEF\n" +
            "BQcBAQR+MHwwVQYIKwYBBQUHMAKGSWh0dHA6Ly9jcnQuc2VjdGlnby5jb20vU2Vj\n" +
            "dGlnb1JTQU9yZ2FuaXphdGlvblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5jcnQw\n" +
            "IwYIKwYBBQUHMAGGF2h0dHA6Ly9vY3NwLnNlY3RpZ28uY29tMCEGA1UdEQQaMBiC\n" +
            "Cyoua2UuY29tLnBrgglrZS5jb20ucGswggH3BgorBgEEAdZ5AgQCBIIB5wSCAeMB\n" +
            "4QB2AEalVet1+pEgMLWiiWn0830RLEF0vv1JuIWr8vxw/m1HAAABb4+WX7AAAAQD\n" +
            "AEcwRQIgKMfySmjaodHmbyLbZIFAn6iQBXgSIKmlge8ypsr6y7ICIQDDz6UJkclx\n" +
            "mqiX/A3RFNPpYRWEt9ti6zLESVJ6lF9C3wB3AG9Tdqwx8DEZ2JkApFEV/3cVHBHZ\n" +
            "AsEAKQaNsgiaN9kTAAABb4+WX6IAAAQDAEgwRgIhAN/VY5Ghf3SouVkc5lEw7glV\n" +
            "Sbm3bCnY7JUUVmAL33EuAiEAvcb4uZiyLWzyNB/W5kSgN1sramN6vbW2fiHqL9fK\n" +
            "8RkAdgAiRUUHWVUkVpY/oS/x922G4CMmY63AS39dxoNcbuIPAgAAAW+Pll+hAAAE\n" +
            "AwBHMEUCIQCT6cWyF/TLASOJFmDKOoLqUlPrtguYFvYZmDBWEt7UhwIgC327Z4Zn\n" +
            "6MhB9evnB1Q49k+VMKaeKm6TdOeMS/WcpeQAdgBVgdTCFpA2AUrqC5tXPFPwwOQ4\n" +
            "eHAlCBcvo6odBxPTDAAAAW+PlmA8AAAEAwBHMEUCIFF4VsnApBnbuVZwI7BgWzhn\n" +
            "npeMURggKBbD+lEJGqozAiEA9r/PlS85Ms9snqbeomKTtf78RxNYUGIBGMQsZ870\n" +
            "e8IwDQYJKoZIhvcNAQELBQADggEBAFyYVjNc3Lkicw3zyAK+5PXJC95jzjLbGmJW\n" +
            "G58cIIUnB9/mRp1ycqSo05CCwkUBrHJZGRxCEkCMeO10WrZnHHTV6rH6f1kU75QC\n" +
            "ia7iCfTg8Tgp9YurNWTtjOAIqfpqde+FflSw3w1fPx6qWg1JEE6alxU6aY5vqcnw\n" +
            "YUJBxhJogalJS8xJoObfXm2ubOJYtfqayLgV99LCkqeTScf1CbJfydjiVw5J2JJQ\n" +
            "lTIUSX+E7xOM3AOMHG2fs6hSNpEsChMAgrSpl323y8umN4Ru8T1yMca8Q7PdpQ5t\n" +
            "HonDcs/AhDieRbdi6yLFvcI2yd6l26Y8zIgMlzvo4WoNIQAEZpo=\n" +
            "-----END CERTIFICATE-----";
*/

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
                    "-----END CERTIFICATE-----\n";

    public static X509Certificate getUserCertificate2() throws Exception {
        // this certificate does not include any extensions
        String sCert =

// Saad Comment on 2-Nov-2022 START
 /*
                "-----BEGIN CERTIFICATE-----\n" +
                "MIIH0jCCBrqgAwIBAgIQaZ5FILli7R9cUi2zTKip/zANBgkqhkiG9w0BAQsFADCB\n" +
                "lTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
                "A1UEBxMHU2FsZm9yZDEYMBYGA1UEChMPU2VjdGlnbyBMaW1pdGVkMT0wOwYDVQQD\n" +
                "EzRTZWN0aWdvIFJTQSBPcmdhbml6YXRpb24gVmFsaWRhdGlvbiBTZWN1cmUgU2Vy\n" +
                "dmVyIENBMB4XDTIwMDExMDAwMDAwMFoXDTIyMDQxMzAwMDAwMFowgdsxCzAJBgNV\n" +
                "BAYTAlBLMQ4wDAYDVQQREwU3NDQwMDEOMAwGA1UECBMFU2luZGgxEDAOBgNVBAcT\n" +
                "B0thcmFjaGkxLTArBgNVBAkTJFBoYXNlLUlJLCBEZWZlbmNlIEhvdXNpbmcgQXV0\n" +
                "aG9yaXR5LDEgMB4GA1UECRMXMzktQiwgU3Vuc2V0IEJvdWxldmFyZCwxETAPBgNV\n" +
                "BAkTCEtFIEhvdXNlMRMwEQYDVQQKEwpLIEVsZWN0cmljMQswCQYDVQQLEwJJVDEU\n" +
                "MBIGA1UEAwwLKi5rZS5jb20ucGswggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEK\n" +
                "AoIBAQDD++iSgn4aYT6G7uYPpuupxD2RZ66wWl7Okaqjmi5wMVBCYqzAMRwBzPN+\n" +
                "9WvtCauWdIz5mls6YZ8AiUcPtnptxldxwhmeXVyG+ZZHGXUFZSi76A9V8u2ci9dd\n" +
                "x0o9Gl8g5a9rPKgFSj7gR3Dr78wBXVc9ZbXfb/e7ssH/UuVaNyRzx6FBjMfH65CI\n" +
                "moYSd8s/zuzqRz8K4SBLmeO7bHxUyC3s8fO3HSatHSCIG/0m1Sge4vFmD7CtgYKI\n" +
                "hF1hKHiagpkSHAvBpVIw28ugUx+tCdDdtBIoq9rc4k525oNAmUZyybeGCf7eACIJ\n" +
                "K5C53UgD3R7guRpMXeFjWG2atGLNAgMBAAGjggPUMIID0DAfBgNVHSMEGDAWgBQX\n" +
                "2dYlJ2f5McJJQ9kwNkSMbKlP6zAdBgNVHQ4EFgQU83k2shGsK6bJy+AH7HubUDm0\n" +
                "V6MwDgYDVR0PAQH/BAQDAgWgMAwGA1UdEwEB/wQCMAAwHQYDVR0lBBYwFAYIKwYB\n" +
                "BQUHAwEGCCsGAQUFBwMCMEoGA1UdIARDMEEwNQYMKwYBBAGyMQECAQMEMCUwIwYI\n" +
                "KwYBBQUHAgEWF2h0dHBzOi8vc2VjdGlnby5jb20vQ1BTMAgGBmeBDAECAjBaBgNV\n" +
                "HR8EUzBRME+gTaBLhklodHRwOi8vY3JsLnNlY3RpZ28uY29tL1NlY3RpZ29SU0FP\n" +
                "cmdhbml6YXRpb25WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0EuY3JsMIGKBggrBgEF\n" +
                "BQcBAQR+MHwwVQYIKwYBBQUHMAKGSWh0dHA6Ly9jcnQuc2VjdGlnby5jb20vU2Vj\n" +
                "dGlnb1JTQU9yZ2FuaXphdGlvblZhbGlkYXRpb25TZWN1cmVTZXJ2ZXJDQS5jcnQw\n" +
                "IwYIKwYBBQUHMAGGF2h0dHA6Ly9vY3NwLnNlY3RpZ28uY29tMCEGA1UdEQQaMBiC\n" +
                "Cyoua2UuY29tLnBrgglrZS5jb20ucGswggH3BgorBgEEAdZ5AgQCBIIB5wSCAeMB\n" +
                "4QB2AEalVet1+pEgMLWiiWn0830RLEF0vv1JuIWr8vxw/m1HAAABb4+WX7AAAAQD\n" +
                "AEcwRQIgKMfySmjaodHmbyLbZIFAn6iQBXgSIKmlge8ypsr6y7ICIQDDz6UJkclx\n" +
                "mqiX/A3RFNPpYRWEt9ti6zLESVJ6lF9C3wB3AG9Tdqwx8DEZ2JkApFEV/3cVHBHZ\n" +
                "AsEAKQaNsgiaN9kTAAABb4+WX6IAAAQDAEgwRgIhAN/VY5Ghf3SouVkc5lEw7glV\n" +
                "Sbm3bCnY7JUUVmAL33EuAiEAvcb4uZiyLWzyNB/W5kSgN1sramN6vbW2fiHqL9fK\n" +
                "8RkAdgAiRUUHWVUkVpY/oS/x922G4CMmY63AS39dxoNcbuIPAgAAAW+Pll+hAAAE\n" +
                "AwBHMEUCIQCT6cWyF/TLASOJFmDKOoLqUlPrtguYFvYZmDBWEt7UhwIgC327Z4Zn\n" +
                "6MhB9evnB1Q49k+VMKaeKm6TdOeMS/WcpeQAdgBVgdTCFpA2AUrqC5tXPFPwwOQ4\n" +
                "eHAlCBcvo6odBxPTDAAAAW+PlmA8AAAEAwBHMEUCIFF4VsnApBnbuVZwI7BgWzhn\n" +
                "npeMURggKBbD+lEJGqozAiEA9r/PlS85Ms9snqbeomKTtf78RxNYUGIBGMQsZ870\n" +
                "e8IwDQYJKoZIhvcNAQELBQADggEBAFyYVjNc3Lkicw3zyAK+5PXJC95jzjLbGmJW\n" +
                "G58cIIUnB9/mRp1ycqSo05CCwkUBrHJZGRxCEkCMeO10WrZnHHTV6rH6f1kU75QC\n" +
                "ia7iCfTg8Tgp9YurNWTtjOAIqfpqde+FflSw3w1fPx6qWg1JEE6alxU6aY5vqcnw\n" +
                "YUJBxhJogalJS8xJoObfXm2ubOJYtfqayLgV99LCkqeTScf1CbJfydjiVw5J2JJQ\n" +
                "lTIUSX+E7xOM3AOMHG2fs6hSNpEsChMAgrSpl323y8umN4Ru8T1yMca8Q7PdpQ5t\n" +
                "HonDcs/AhDieRbdi6yLFvcI2yd6l26Y8zIgMlzvo4WoNIQAEZpo=\n" +
                "-----END CERTIFICATE-----";
*/
        // Saad Comment on 2-Nov-2022 END

                "-----BEGIN CERTIFICATE-----\n" +
                        "MIIH5DCCBsygAwIBAgIQYq5jzpTVO7h6sOSAEvQI/DANBgkqhkiG9w0BAQsFADCB\n" +
                        "lTELMAkGA1UEBhMCR0IxGzAZBgNVBAgTEkdyZWF0ZXIgTWFuY2hlc3RlcjEQMA4G\n" +
                        "A1UEBxMHU2FsZm9yZDEYMBYGA1UEChMPU2VjdGlnbyBMaW1pdGVkMT0wOwYDVQQD\n" +
                        "EzRTZWN0aWdvIFJTQSBPcmdhbml6YXRpb24gVmFsaWRhdGlvbiBTZWN1cmUgU2Vy\n" +
                        "dmVyIENBMB4XDTIyMDIxODAwMDAwMFoXDTIzMDEwOTIzNTk1OVowZzELMAkGA1UE\n" +
                        "BhMCUEsxDjAMBgNVBAgTBVNpbmRoMRAwDgYDVQQHEwdLYXJhY2hpMRMwEQYDVQQK\n" +
                        "EwpLIEVsZWN0cmljMQswCQYDVQQLEwJJVDEUMBIGA1UEAwwLKi5rZS5jb20ucGsw\n" +
                        "ggIiMA0GCSqGSIb3DQEBAQUAA4ICDwAwggIKAoICAQC/dFWd5IwMGW0NtvhC8cFv\n" +
                        "Gx+XSt1nns+2buJs4gnYt/ZgMRL+77P6GFeNKcSEN8SXWOrzaddvt8sXQ8QgJi7g\n" +
                        "PJKgz5hsE+6XfiZKYF4HD7NxamP00ZLXftrZ9JtGRiD4GViJLZbaejPliVMqnmDn\n" +
                        "ZKXRmJ/bgS51CqnLrZB9kYj1W9ImrCHlLbcy1CQ4KlPMAiEmdxKmC1BehgXlvqoI\n" +
                        "3R8IvrIoUNT8Q68xEJOlC9A1NEcpMRQ2RDMT6ctDZ2y7Y5aJlQswJbIOW2+Jvz7A\n" +
                        "WJx5Jsr2Bdx4eLEF6BgtnEMk7i3ZxrLRq7qTN6JsaTHVsuaQmc4xUT2EBr4oL6hq\n" +
                        "2M0R+OuEJgeLIuuFN47imXtbcr+lbWd5r9wVPWpYx/uFTC4PGPV6qGPNgEWNUCHu\n" +
                        "7R5vSs/8QGqMisxikQOVFur3RKIJQ1Sj8NPRCLQlXYA7n0qTvz6pG7Mnybsjk9Rz\n" +
                        "QNc7BZa1vYARps2O3TxzUvbVXuN2SFL3l6/9kcl4d3yfa5jBDbnk2E5NKrRyXNnc\n" +
                        "ytlAIHvD0ve10ufbpVJeL3ch3ugULPJaC76lWhf2kVCo1g/G4xe5uALupAYaos11\n" +
                        "hUyKubDuP1AXtvNySixzN1UXyqkA3uOm6si/KiQycLEh+VeQ9hJu5a6pGC8lT75g\n" +
                        "0VjBWz9cyGlY7mq6fzCrTwIDAQABo4IDWzCCA1cwHwYDVR0jBBgwFoAUF9nWJSdn\n" +
                        "+THCSUPZMDZEjGypT+swHQYDVR0OBBYEFKpntUpNUxFfoajE3XpMjuizyhmcMA4G\n" +
                        "A1UdDwEB/wQEAwIFoDAMBgNVHRMBAf8EAjAAMB0GA1UdJQQWMBQGCCsGAQUFBwMB\n" +
                        "BggrBgEFBQcDAjBKBgNVHSAEQzBBMDUGDCsGAQQBsjEBAgEDBDAlMCMGCCsGAQUF\n" +
                        "BwIBFhdodHRwczovL3NlY3RpZ28uY29tL0NQUzAIBgZngQwBAgIwWgYDVR0fBFMw\n" +
                        "UTBPoE2gS4ZJaHR0cDovL2NybC5zZWN0aWdvLmNvbS9TZWN0aWdvUlNBT3JnYW5p\n" +
                        "emF0aW9uVmFsaWRhdGlvblNlY3VyZVNlcnZlckNBLmNybDCBigYIKwYBBQUHAQEE\n" +
                        "fjB8MFUGCCsGAQUFBzAChklodHRwOi8vY3J0LnNlY3RpZ28uY29tL1NlY3RpZ29S\n" +
                        "U0FPcmdhbml6YXRpb25WYWxpZGF0aW9uU2VjdXJlU2VydmVyQ0EuY3J0MCMGCCsG\n" +
                        "AQUFBzABhhdodHRwOi8vb2NzcC5zZWN0aWdvLmNvbTAhBgNVHREEGjAYggsqLmtl\n" +
                        "LmNvbS5wa4IJa2UuY29tLnBrMIIBfgYKKwYBBAHWeQIEAgSCAW4EggFqAWgAdgCt\n" +
                        "9776fP8QyIudPZwePhhqtGcpXc+xDCTKhYY069yCigAAAX8NSUnXAAAEAwBHMEUC\n" +
                        "IEctM+tkQFmJ/EBibw6nFUer1l/v8QL0Ybr3EJZYs9ucAiEAmHLiWTQmBrF7eiA1\n" +
                        "SxLHnXN++DOhCdFip5bLmaiq7eoAdwB6MoxU2LcttiDqOOBSHumEFnAyE4VNO9Ir\n" +
                        "wTpXo1LrUgAAAX8NSUmZAAAEAwBIMEYCIQCMJNcYdfhFRVjaWVOMP6fTHIsGHpbo\n" +
                        "wMtweEU3iYkSZQIhANHfGYCtw/dJA9giUZFlLlLkDYLXv0Jq8bZz2h8E+ihlAHUA\n" +
                        "6D7Q2j71BjUy51covIlryQPTy9ERa+zraeF3fW0GvW4AAAF/DUlJdgAABAMARjBE\n" +
                        "AiB1jfqKdPGl4QdE7DlTQqu3zbxtgcjTXtnNjcmvJINVxgIgP/c3vaAAr9G7+D+e\n" +
                        "5gMlvs6Pp1BnXhpnUTnOiX6UNMEwDQYJKoZIhvcNAQELBQADggEBAGCg9ZugRaKZ\n" +
                        "0/xlGELX7s3Fl1UbyQWMm03hkznydqsOUSan8ven/r6H8T3Fx65Tcmk8kxPKQ8iw\n" +
                        "jBkE2tSafdHV+oJuGACt7fKRH/XNfA3+BH9PqwcFhardAwxl4Yvaq66LH21GWWhd\n" +
                        "fEKIBI53x3+gdiJL837qbN8kT0BUadu2c9EGaw07Bh9SI1QCAEag1bkkipWTKu2p\n" +
                        "wqltDW0IoeSKiU2h5dPQoF9SwMh9rGVz0j5xe2DZLYO6vszl9RIQNyQxwjh1QpVo\n" +
                        "RWhetdXioMzslmv2P1kRd/5ED3CFRe4EcVzMVXpfGCaXofD9yOLPl3dsQVm/7CcF\n" +
                        "Qm/vBxDd1+0=\n" +
                        "-----END CERTIFICATE-----\n" ;


//                "-----BEGIN CERTIFICATE-----\n" +
//                "MIIG2zCCBcOgAwIBAgITEwAAI1a3HCKorW7RkgAAAAAjVjANBgkqhkiG9w0BAQsF\n" +
//                "ADBfMRIwEAYKCZImiZPyLGQBGRYCcGsxEzARBgoJkiaJk/IsZAEZFgNjb20xFDAS\n" +
//                "BgoJkiaJk/IsZAEZFgRrZXNjMR4wHAYDVQQDExVrZXNjLUtFLUlTU1VJTkdDQTEt\n" +
//                "Q0EwHhcNMjEwNjA5MDg0NTI1WhcNMjQwNjA4MDg0NTI1WjBhMQswCQYDVQQGEwJE\n" +
//                "RTEcMBoGA1UEChMTU0FQIFRydXN0IENvbW11bml0eTELMAkGA1UECxMCS0UxCzAJ\n" +
//                "BgNVBAsTAklUMRowGAYDVQQDExFmaW9yaXFhLmtlLmNvbS5wazCCAiIwDQYJKoZI\n" +
//                "hvcNAQEBBQADggIPADCCAgoCggIBAK0F8XCMEZk+F7widMWcml5f2Ygu5aJYSu3M\n" +
//                "gRAYvamxgD/9KH8tQPPvKmSav+Vxoj6p/gBqA0R8ViGDp5TN97z7/U4aDQzlgRyS\n" +
//                "f53DeXWYO3FsS513l+BIYOkjwXsMnzFa82R3vdawb/auFhEu4KGKgNz48mB+QxrG\n" +
//                "t151asDQr2x0DN1RQOmpefY/EBv+JCyo392tO+ERLkPg9qZjj1cMY8MEOVTG19E1\n" +
//                "x8w8/cA+wsgk3I9TSqIyQtuzYTKW0BRm/18FMYANF8+wrgYpqkTgbkbyrXxFaMWw\n" +
//                "MQAJwMoOFy7WAGTY07Ga+HJHq0DFBLspXOAHN3BdApaWylRzWCFuCkFz3IurODJk\n" +
//                "qFIM2lrUg4jrzNYsppY3ZP0eSrLZUyXIEP5HK6Q70lnnbfCMaNCt1oCxQLWAv6nJ\n" +
//                "+6eq2z14zpImXZK7hd9R6iP28uGisJMqMXVyBcExCmnZIaKwEAIyWTK6ZUKdC0YE\n" +
//                "giGAuqnB8RMda7B13tWcRyTV6Va32gxO9TYO+YpozKs+uV3hH9cKpnVnRIZB7lvJ\n" +
//                "yDn8+COtUFU+BsG3vX4pMZ/viR7MEuWTB3wHSIaTa1En/xFj8whevXWBCw+Nmzvz\n" +
//                "J6Pj7h0yIy1ZuoiDF2zOmVLJ9p8i68NQpTqTBR9olZO/u85egQHFGswUQsYLOXjk\n" +
//                "FTc7I9wxAgMBAAGjggKMMIICiDAcBgNVHREEFTATghFmaW9yaXFhLmtlLmNvbS5w\n" +
//                "azAdBgNVHQ4EFgQUbkrWHrA5x9rzAJzrWXpirL/wa24wHwYDVR0jBBgwFoAUXyVY\n" +
//                "cNbqMcOED4x+jyiAUgk0QF8wgdwGA1UdHwSB1DCB0TCBzqCBy6CByIaBxWxkYXA6\n" +
//                "Ly8vQ049a2VzYy1LRS1JU1NVSU5HQ0ExLUNBLENOPUtFLUlzc3VpbmdDQTEsQ049\n" +
//                "Q0RQLENOPVB1YmxpYyUyMEtleSUyMFNlcnZpY2VzLENOPVNlcnZpY2VzLENOPUNv\n" +
//                "bmZpZ3VyYXRpb24sREM9a2VzYyxEQz1jb20sREM9cGs/Y2VydGlmaWNhdGVSZXZv\n" +
//                "Y2F0aW9uTGlzdD9iYXNlP29iamVjdENsYXNzPWNSTERpc3RyaWJ1dGlvblBvaW50\n" +
//                "MIHKBggrBgEFBQcBAQSBvTCBujCBtwYIKwYBBQUHMAKGgapsZGFwOi8vL0NOPWtl\n" +
//                "c2MtS0UtSVNTVUlOR0NBMS1DQSxDTj1BSUEsQ049UHVibGljJTIwS2V5JTIwU2Vy\n" +
//                "dmljZXMsQ049U2VydmljZXMsQ049Q29uZmlndXJhdGlvbixEQz1rZXNjLERDPWNv\n" +
//                "bSxEQz1waz9jQUNlcnRpZmljYXRlP2Jhc2U/b2JqZWN0Q2xhc3M9Y2VydGlmaWNh\n" +
//                "dGlvbkF1dGhvcml0eTALBgNVHQ8EBAMCBaAwPQYJKwYBBAGCNxUHBDAwLgYmKwYB\n" +
//                "BAGCNxUIhonTcoKZkkOljxOHsMBmhaKGToEZhNmfdIKLvVcCAWQCAQUwEwYDVR0l\n" +
//                "BAwwCgYIKwYBBQUHAwEwGwYJKwYBBAGCNxUKBA4wDDAKBggrBgEFBQcDATANBgkq\n" +
//                "hkiG9w0BAQsFAAOCAQEANYrCaMJ2v6QLqiAfk7uvDhT69v5y8+SmYEK4G7PwexPy\n" +
//                "jfXgCmaIp6bUhXzU2HF8baPbiVlCDnV+QIF/rItA32Akdr98QNnU16inn4xz9X19\n" +
//                "7ViK7Z+NzOggI9RjbIG0ONFxR+gqbyq+yrlvLeROdme3I90h+4X1MYEDSYqxXV63\n" +
//                "vz0MuZvwawmySAQujQwfWDDVtN+NuiGgGYIIKpV6WMDjmt4We9sDitQDFB72RV5q\n" +
//                "91u0605ieUxz5xuM3xKCC9cAyOyEhur8dQrmqfuskUVH3jPCM9vp1gjh5mgU9ubV\n" +
//                "FFQ0nb3+qvyGmcxuNh6u3nFdfXHyo4UE/FpWpSoq0A==\n" +
//                "-----END CERTIFICATE-----";

//                "-----BEGIN CERTIFICATE-----\n" +
//                "MIICWjCCAcMCCAogIAMxIUYBMA0GCSqGSIb3DQEBBQUAMHIxCzAJBgNVBAYTAkRF\n" +
//                "MRwwGgYDVQQKExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2Vi\n" +
//                "IEFTMRQwEgYDVQQLEwtJMDAyMDIzNzM1MDEaMBgGA1UEAxMRZmlvcmlxYS5rZS5j\n" +
//                "b20ucGswHhcNMjAwMzMxMjE0NjAxWhcNMzgwMTAxMDAwMDAxWjByMQswCQYDVQQG\n" +
//                "EwJERTEcMBoGA1UEChMTU0FQIFRydXN0IENvbW11bml0eTETMBEGA1UECxMKU0FQ\n" +
//                "IFdlYiBBUzEUMBIGA1UECxMLSTAwMjAyMzczNTAxGjAYBgNVBAMTEWZpb3JpcWEu\n" +
//                "a2UuY29tLnBrMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCViuWgse0ul5Hu\n" +
//                "1Fp8teeUWZSCO1s/43ksUbLn7HTZdUXxsCdi81RRemTcWCKhHVlU9u2xz/3JGI+b\n" +
//                "l4ZJgw5zzW8TpRDcXviB9IyI3BheeoI3m/NLPfywWm10meZVv72kTzRKCvXfg0xI\n" +
//                "Dh7Q5z00PC+TfIZ5DWfW6CQYOFES/wIDAQABMA0GCSqGSIb3DQEBBQUAA4GBAAVO\n" +
//                "06iTQwMwgk1NdLrrVAiu4ZTHRI8605WYaGJxx6Yf5ipfjQj1P97q00uqALyRt9qz\n" +
//                "GhYpmBdQZYLszMzEBZUYOqazJ1PSHKLMTTLFnFwqjZ2bT/SriJsq9zDEByBvezTm\n" +
//                "UP0iSkgSZk5d7R7VFZ5RrkESaRGF97KZHFot2hiq\n" +
//                "-----END CERTIFICATE-----";
        //                "-----BEGIN CERTIFICATE-----\n" +
//                        "MIICVDCCAb0CCAogGAMnEUMBMA0GCSqGSIb3DQEBBQUAMG8xCzAJBgNVBAYTAkRF\n" +
//                        "MRwwGgYDVQQKExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2Vi\n" +
//                        "IEFTMREwDwYDVQQLEwhJSU5JVElBTDEaMBgGA1UEAxMRZmlvcmlxYS5rZS5jb20u\n" +
//                        "cGswHhcNMTgwMzI3MTE0MzAxWhcNMzgwMTAxMDAwMDAxWjBvMQswCQYDVQQGEwJE\n" +
//                        "RTEcMBoGA1UEChMTU0FQIFRydXN0IENvbW11bml0eTETMBEGA1UECxMKU0FQIFdl\n" +
//                        "YiBBUzERMA8GA1UECxMISUlOSVRJQUwxGjAYBgNVBAMTEWZpb3JpcWEua2UuY29t\n" +
//                        "LnBrMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD0opzobTJ6HWnfVrwDCZo1\n" +
//                        "I1//Ifq4jY+r4b8QctZbM9nY9xfTisvQrqqFP/y3y0biLblgpIPhdAxfpk9D2MwM\n" +
//                        "VwuiH1tzgbDQj3A1Gf7htOK7/CYh2BTp2tFurxp3zVWaEjJVh5haM4xkZyz/aBRp\n" +
//                        "6iA3RKTeQw6ndzve1+EgCQIDAQABMA0GCSqGSIb3DQEBBQUAA4GBAGZIJcof+B/8\n" +
//                        "0PS7rkC88qCNJS9uTuiE9kNf3yimIX1YMsglH2F7Rmm5MVL/YG+RX7Xe0p5cTZIc\n" +
//                        "QioaVFC5Xylk7d8WGgPZs74V2UdWrP3RvJ3xKJSxyW4coWEB0nVZvDKSSP2PeGG9\n" +
//                        "OvUXASRTvxV62VRPbZhJK3TUL6LkQXAD\n" +
//                        "-----END CERTIFICATE-----";

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
            // Saad Commments added .addInterceptor(new BasicAuthInterceptor(username, password))
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, x509TrustManager);
                    //.addInterceptor(new BasicAuthInterceptor("fioriqa","sapsap2"));

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
