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

    public static final boolean IS_LOG_ALLOWED = false;

    //public static final String URL = "fioridev.ke.com.pk";
    public static final String URL = "fioriqa.ke.com.pk";
    public static final String BASE_URL = "https://" + URL + ":44300/";

    public static String FONT_NAME = "PT_Sans-Web-Regular.ttf";

    public static String Firebase_DB_NAME = "locations_dev";

    public static HttpLoggingInterceptor.Level LOG_LEVEL = HttpLoggingInterceptor.Level.NONE;


    private static X509Certificate getUserCertificate2() throws Exception {
        // this certificate does not include any extensions

        //QA Certificate
//        String sCert = "-----BEGIN CERTIFICATE REQUEST-----\n" +
//                "MIIBwzCCASwCAQAwgYIxCzAJBgNVBAYTAkRFMRwwGgYDV\n" +
//                "QQKExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2ViIEFTMREwDwYDVQQLEwhJSU5JV\n" +
//                "ElBTDEtMCsGA1UEAxMkS0ZRIFNTTCBjbGllbnQgU1NMIENsaWVudCAoU3RhbmRhcmQpMIGfMA0GCSqGS\n" +
//                "Ib3DQEBAQUAA4GNADCBiQKBgQCt1Vzb2d5sBNPLCmQkqG7FwQeVIVQX/8/6GbThPUHp236UEiqqPXOmv\n" +
//                "G3vMUyQellhwb3z0FfTVYAqSU7zSLie/EBTnzh7Vw4/h2uZigAJwztiFOnyCAWrlA/Bi2IfPqdAtbGTx\n" +
//                "ALXoesOOYa46/fzQK3Vvq7l5bSUFuO818H8ewIDAQABoAAwDQYJKoZIhvcNAQEFBQADgYEApGshWnyRu\n" +
//                "xTD4OcJAASCQantqTWWbYn+DyCvlMb0lMhOmaFxkR0FyMkDKfGZqTmzsbPXKCy4FkIxcT5aaVqxFUQJ6\n" +
//                "BIVfZZ7KBOl/YNd+45JOODUHgul3D8PeqDPN7DvwLGAlIaPydn2h7bvC8v8/cxx0lTwAl+Ng69T9wCBn\n" +
//                "pg=-----END CERTIFICATE REQUEST-----";

//        String sCert = "-----BEGIN CERTIFICATE REQUEST-----\n" +
//                "MIIBUzCBvQIBADAUMRIwEAYDVQQDEwlhbm9ueW1vdXMwg\n" +
//                "Z8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAJHQmUE6ejMSIXt9y8kmLEG2PQfIR4nfm9BEZ\n" +
//                "XVXQnOya\n" +
//                "zxjLXg+jxikWkAV1VU/ep/zkIzTTZyYXbYC0I0e23pnXuYlK1CfOvQ2MWumGQTzXXgeOtN//\n" +
//                "wJfRoV4P\n" +
//                "aHNOOUQS1dagAvBIFulgzNJhvN0Fhrg0OlEKSRnwz3ve6KrAgMBAAGgADANBgkqhkiG9w0BA\n" +
//                "QUFAAOBg\n" +
//                "QAtG33gEROqycl0FqqWdvzFnc8vXtx4Z8DmdTg9MvDQeT68YGjgobvFJNg6sZelAryOjs2Pc\n" +
//                "YEWN3GrA\n" +
//                "YB1fLo/t2Ub9FJ/QuHoxQCRIvkqnLkS7lI6jcKLKg+dBJbm2BsV9abDCuhp5aein6RoiM7Am\n" +
//                "zOWZqedN\n" +
//                "p+YNGKxmCC6Uw==-----END CERTIFICATE REQUEST-----";

        String sCert =  "-----BEGIN CERTIFICATE-----\n" +
                        "MIICWjCCAcMCCAogIAMxIUYBMA0GCSqGSIb3DQEBBQUAMHIxCzAJBgNVBAYTAkRF\n" +
                        "MRwwGgYDVQQKExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2Vi\n" +
                        "IEFTMRQwEgYDVQQLEwtJMDAyMDIzNzM1MDEaMBgGA1UEAxMRZmlvcmlxYS5rZS5j\n" +
                        "b20ucGswHhcNMjAwMzMxMjE0NjAxWhcNMzgwMTAxMDAwMDAxWjByMQswCQYDVQQG\n" +
                        "EwJERTEcMBoGA1UEChMTU0FQIFRydXN0IENvbW11bml0eTETMBEGA1UECxMKU0FQ\n" +
                        "IFdlYiBBUzEUMBIGA1UECxMLSTAwMjAyMzczNTAxGjAYBgNVBAMTEWZpb3JpcWEu\n" +
                        "a2UuY29tLnBrMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCViuWgse0ul5Hu\n" +
                        "1Fp8teeUWZSCO1s/43ksUbLn7HTZdUXxsCdi81RRemTcWCKhHVlU9u2xz/3JGI+b\n" +
                        "l4ZJgw5zzW8TpRDcXviB9IyI3BheeoI3m/NLPfywWm10meZVv72kTzRKCvXfg0xI\n" +
                        "Dh7Q5z00PC+TfIZ5DWfW6CQYOFES/wIDAQABMA0GCSqGSIb3DQEBBQUAA4GBAAVO\n" +
                        "06iTQwMwgk1NdLrrVAiu4ZTHRI8605WYaGJxx6Yf5ipfjQj1P97q00uqALyRt9qz\n" +
                        "GhYpmBdQZYLszMzEBZUYOqazJ1PSHKLMTTLFnFwqjZ2bT/SriJsq9zDEByBvezTm\n" +
                        "UP0iSkgSZk5d7R7VFZ5RrkESaRGF97KZHFot2hiq\n" +
                        "-----END CERTIFICATE-----";
        // DEV certificate
//        String sCert ="-----BEGIN CERTIFICATE-----\n" +
//                "MIIGXjCCBUagAwIBAgITWAAAAEie/VKmAilyaQAAAAAASDANBgkqhkiG9w0BAQsF\n" +
//                "ADBVMRIwEAYKCZImiZPyLGQBGRYCcGsxEzARBgoJkiaJk/IsZAEZFgNjb20xFDAS\n" +
//                "BgoJkiaJk/IsZAEZFgRrZXNjMRQwEgYDVQQDEwtLRS1DQUNTLVNSVjAeFw0yMDA2\n" +
//                "MjgxNDAxNDRaFw0yMTA2MjgxNDExNDRaMHMxCzAJBgNVBAYTAkRFMRwwGgYDVQQK\n" +
//                "ExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2ViIEFTMRQwEgYD\n" +
//                "VQQLEwtJMDAyMDIzNzM1MDEbMBkGA1UEAxMSZmlvcmlkZXYua2UuY29tLnBrMIIC\n" +
//                "IjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAv7oGEhGKpW5fRnXbVSsY9XYZ\n" +
//                "Scnu0oILclZ9PhzJ3xLzKJeYaNwmAwv8o0LwgYXWmmBwxRjCgu94DBIcG4ImYolv\n" +
//                "Mg1bs/IKdkeGS0Jb3xUsDz8E+5d7zAdq0dJAnHW+C9fu2s6in00O5yFbLVnEwTRz\n" +
//                "sBDKKlHvvAsciXq6WVxw7UJ7v5640ub8+lCk+/TwsfU9KGpvGn9Pj2UudE61fctu\n" +
//                "kUrYCwWXUmxgAClau0XflRt4bunyGjjj3C0L/YRGl6mqJgWklHSK8jJHSD6bnF3b\n" +
//                "9kFtT4weWsQyYu78UICJzxkJsHvAUFmwGrg0G9syqjk08aNKPu4srraXHfTN4OLn\n" +
//                "e/XKk1ca242UJtKeoglC99xm8exGgWTkWrFCRF9q78by5g36azuTtXh76XSXLuBm\n" +
//                "+DyiUveqZiG4g4emEub6sBz7kAjF8rU1Q6+tYNX6uogFBOE42L0ENyxgThz+9vRO\n" +
//                "q8jERPIQr9Wpsv+FJSkiJ0hg26oxmTE7Kmihf+AG7OlZEam/AQ/cvB2XE/+xWn65\n" +
//                "XWvj4okD+ZHNB+Cwu/5M3G8bifrQTmcYuN1JAOzLXumPPzgVWCdj6n/zgYvS8GIb\n" +
//                "k5S0+vpsyPOJazawScsckOGtlgK5/pVIqUIbzSpmeKYXKpuzjw7aH1E8NIcp7o5w\n" +
//                "ynO21KJ1ZDgzfV8Ml0kCAwEAAaOCAgcwggIDMB0GA1UdEQQWMBSCEmZpb3JpZGV2\n" +
//                "LmtlLmNvbS5wazAdBgNVHQ4EFgQUKiQIs93OvENId6K0zYsTQGBQIRMwHwYDVR0j\n" +
//                "BBgwFoAU8ps17FYGMdX/i6ptESRKwUNV8mcwgdAGA1UdHwSByDCBxTCBwqCBv6CB\n" +
//                "vIaBuWxkYXA6Ly8vQ049S0UtQ0FDUy1TUlYsQ049S0UtQ0FDUy1TUlYsQ049Q0RQ\n" +
//                "LENOPVB1YmxpYyUyMEtleSUyMFNlcnZpY2VzLENOPVNlcnZpY2VzLENOPUNvbmZp\n" +
//                "Z3VyYXRpb24sREM9a2VzYyxEQz1jb20sREM9cGs/Y2VydGlmaWNhdGVSZXZvY2F0\n" +
//                "aW9uTGlzdD9iYXNlP29iamVjdENsYXNzPWNSTERpc3RyaWJ1dGlvblBvaW50MIHA\n" +
//                "BggrBgEFBQcBAQSBszCBsDCBrQYIKwYBBQUHMAKGgaBsZGFwOi8vL0NOPUtFLUNB\n" +
//                "Q1MtU1JWLENOPUFJQSxDTj1QdWJsaWMlMjBLZXklMjBTZXJ2aWNlcyxDTj1TZXJ2\n" +
//                "aWNlcyxDTj1Db25maWd1cmF0aW9uLERDPWtlc2MsREM9Y29tLERDPXBrP2NBQ2Vy\n" +
//                "dGlmaWNhdGU/YmFzZT9vYmplY3RDbGFzcz1jZXJ0aWZpY2F0aW9uQXV0aG9yaXR5\n" +
//                "MAwGA1UdEwEB/wQCMAAwDQYJKoZIhvcNAQELBQADggEBAJZlSiM38O1jZs7zZxFd\n" +
//                "qg+dKn+LlDNFvsEzHz9d+vzDwHA6oDnJvvbH4C7qghpPIFUHHLde8ViqeBQjUMO/\n" +
//                "vRMCFJab2FSawo6GfmxqJ7aqHw9lfBvd8WIwoH5win+tMnvp/LDG3Xz037zLxsrG\n" +
//                "YhMy/gmEWHZkXlIz5JTBw0x9hL2hZMzSfHWyrvETlVidCapWTW4v+xPP56Pxq7Pj\n" +
//                "91fYRtNt3sfEakv4eJrfPiNyOWs362CN4/zri6PPQp4RTwu6YnP08pRHoTW0eXso\n" +
//                "y+t21xL4cxZjxyOo94Q6VvepkHxJoR09uenp6mwFf+4/609H9c5DUVulSJUI7XWG\n" +
//                "xNc=\n" +
//                "-----END CERTIFICATE-----";
//     String sCert ="-----BEGIN CERTIFICATE-----\n" +
//                "MIIFrTCCA5UCCAogIAQoEhcBMA0GCSqGSIb3DQEBCwUAMIGYMQswCQYDVQQGEwJE\n" +
//                "RTEcMBoGA1UEChMTU0FQIFRydXN0IENvbW11bml0eTETMBEGA1UECxMKU0FQIFdl\n" +
//                "YiBBUzEUMBIGA1UECxMLSTAwMjAyMzczNTAxGzAZBgNVBAMTEmZpb3JpZGV2Lmtl\n" +
//                "LmNvbS5wazEjMCEGCysGAQQBhTYCBQEBExJmaW9yaWRldi5rZS5jb20ucGswHhcN\n" +
//                "MjAwNDI4MTIxNzAxWhcNMzgwMTAxMDAwMDAxWjCBmDELMAkGA1UEBhMCREUxHDAa\n" +
//                "BgNVBAoTE1NBUCBUcnVzdCBDb21tdW5pdHkxEzARBgNVBAsTClNBUCBXZWIgQVMx\n" +
//                "FDASBgNVBAsTC0kwMDIwMjM3MzUwMRswGQYDVQQDExJmaW9yaWRldi5rZS5jb20u\n" +
//                "cGsxIzAhBgsrBgEEAYU2AgUBARMSZmlvcmlkZXYua2UuY29tLnBrMIICIjANBgkq\n" +
//                "hkiG9w0BAQEFAAOCAg8AMIICCgKCAgEArYtkAj3NYIIEDnOS9n6Ca1BCECNfgs0L\n" +
//                "Y9GRFLvcCRpQqrJuJdzAYVfIF/WXruHm1pTuPODUTrcUg5misBY0lkbKsR3FmsCB\n" +
//                "LU7gga7Av7TMW0l11xfUVqqCd1TEhggru86b4ug8XgbuTn9eacCMuhkL39qAyi7t\n" +
//                "MN1WjM+X7IyciBNBeFMd1rzj299lWqy08hqLyc+TDmc78dD4/90LnM2I5Wp7dj9C\n" +
//                "nx8vISpEGoHDLWhS2hL72KZwMLfO6ixqlI5SBviyte3x6XqThthgvdKcEwKlMCcu\n" +
//                "tZXApzto2Lq1nvXp2CZ6Ih9e3Pqh7P/e94zbFe1KFbEcHtbILHDg5Iz4/x5RvJKL\n" +
//                "WbBjHQj31+ZvExw1wciYmMpPmL4r0ccwCkBR2NjWg2USFCwoh7X7o2Wapr/YOeU2\n" +
//                "KmpNL5gX9Y+YJ1qcKBW//SJm748+aK8Qw7yxyxccxvJm3IIq+UVf4G1yQYj3ex1a\n" +
//                "tTVqzSEYPHDXWFemBqLtS+5yX5usNNmjMgJBK03Y/R6haMO8mDYWllw19HoL9KM7\n" +
//                "kQ1dyHAQQBz7Q/UaDFY2pyrIFosdYfyiR/T2cfyYMR8xvfTKWqqSN6fiYAcHfO5j\n" +
//                "rpFbSBRuSJ0Ram0t+DSvFbusxGG6BcnC6tVWn4ko3dDpwnJuACQJOrXvoM06q5AV\n" +
//                "ci6gLBk6qi8CAwEAATANBgkqhkiG9w0BAQsFAAOCAgEAKZAp2QbBlFSHzTm/3H3M\n" +
//                "qHXUmMbiB4IVJB0H4FbjEOsNTyYm81y+0ljsVRpP4N9sgjCWsfMy8hgMOaqo8o1d\n" +
//                "ezfJPrbUbSxSGkI2fvUK51CIlqGlReJBi7J3DGt9hROdbqmJ0AqDVVvIJ9iaOioG\n" +
//                "pSVzdHM56s6sKde4zJFDx0fWZ/M2y1ZtTUx5A60CyXt0F824GPY1egZcSQU4JF+t\n" +
//                "scC1LuatFwqcVYSlufKnvuLs7F1VmQ3o1WuBAA6L9lfh4LJ+TDCdtXTyv4hDoREe\n" +
//                "irxwjSYuk1X5IT1AVFUVO+qav/24kS95qxPTo3wv6KmCkvnb59KNSZF8YFQU+O+E\n" +
//                "Us2DDPTNqvI6IqVoqUnt191/C2oNeY4dCxT1YUsP3cAwWKsMNFDRg76I5i043V4k\n" +
//                "UxKOWkVa5zmZrz0ml/Wt+J81ltlEedfAG4JPQlkQ/+/B9MNBObYDEGXb+VpOlV4u\n" +
//                "wT5zI1LF7Id/DbS44ZtYc4lvfbafZbNIcBoDLQ228/nr6X8EAniELFAF4ki8qZPi\n" +
//                "uidt1cjtNqhBAFOuKijf4D0+bnsockj/iA6rgvUNoeuyulXajtKDPubW0FvOUAXV\n" +
//                "Ao3XF8tP+KMyVQUTQtYDrneJb1y14TALt7SQ+jvwOpfCTyw3eDf2uy3mpbUBiB1R\n" +
//                "2hz56ixOb9/DTuR6p4NylHc=\n" +
//                "-----END CERTIFICATE-----";
//     String sCert ="-----BEGIN CERTIFICATE-----\n" +
//                "MIICtTCCAZ0CCAogIAQoEhYQMA0GCSqGSIb3DQEBCwUAMB0xGzAZBgNVBAMTEmZp\n" +
//                "b3JpZGV2LmtlLmNvbS5wazAeFw0yMDA0MjgxMjE2MTBaFw0zODAxMDEwMDAwMDFa\n" +
//                "MB0xGzAZBgNVBAMTEmZpb3JpZGV2LmtlLmNvbS5wazCCASIwDQYJKoZIhvcNAQEB\n" +
//                "BQADggEPADCCAQoCggEBANHgy1hkrej6ZraOFH6iXxSuUO6E0KBdLFmev31c0cmu\n" +
//                "8WUHHJVl3TAg6NO0KKIIDPi+Irn581wPYYFSvZXWij0s9i0l4p8oYLvcUI2+jMfT\n" +
//                "jyzukrc6Iw7vszZ20NbAfSvE2pBHrdEdf0PdyPe3yU/uACURs2FjG/9HhqTGS0TQ\n" +
//                "mxN08WB8xhL8Z8kZxLKMczNwYgMSOcmx9mo1/oc9gejf1QvTRhJgoGvP/bwBRnBW\n" +
//                "AkXevLXoOHSdBkFN1Ly2ycRz5YL2mUiLC1F8Y8cf1ZKMRfE4iauBoZ8ob9wUKjMl\n" +
//                "bQLojK6RRXhnimHTl+Zi1bQPGToY4lIObV5Ft8QLTO0CAwEAATANBgkqhkiG9w0B\n" +
//                "AQsFAAOCAQEASe55yZmYibho8CIZFM1iLohMhVLmT91ik6xraLtkFzDIdJb3RU4z\n" +
//                "DtvhTST5GaEyqgmCh5RxRFmrC2eWWaag1AZE/MeE/0POYz3qc8WXtp9N+MlPRBM4\n" +
//                "bru3BE90KZ4bSOFDZs38W+YyrHbNOqfNj9Gdcug+mXEmPqbG+nLFmaJY2j8DkEPX\n" +
//                "+8pcM0qX6uPteyU5ZsLdGBnbkn0PMYKtRU4O7+C3amCZey315dkFkClsgClNo6dG\n" +
//                "2Gh8Q2NJ4iJG79osE6MDnC9QFKo9c42fV88nJaIgvpBm7p+ciAdKNsXKiE+ZEzJB\n" +
//                "Gm8OElF5VnKdohPUxFqgdG4zz5RSNI1V5g==\n" +
//                "-----END CERTIFICATE-----";
//                "-----BEGIN CERTIFICATE-----\n" +
//                        "MIICXDCCAcUCCAogGAInCSABMA0GCSqGSIb3DQEBBQUAMHMxCzAJBgNVBAYTAkRF\n" +
//                        "MRwwGgYDVQQKExNTQVAgVHJ1c3QgQ29tbXVuaXR5MRMwEQYDVQQLEwpTQVAgV2Vi\n" +
//                        "IEFTMRQwEgYDVQQLEwtJMDAyMDIzNzM1MDEbMBkGA1UEAxMSZmlvcmlkZXYua2Uu\n" +
//                        "Y29tLnBrMB4XDTE4MDIyNzA5MjAwMVoXDTM4MDEwMTAwMDAwMVowczELMAkGA1UE\n" +
//                        "BhMCREUxHDAaBgNVBAoTE1NBUCBUcnVzdCBDb21tdW5pdHkxEzARBgNVBAsTClNB\n" +
//                        "UCBXZWIgQVMxFDASBgNVBAsTC0kwMDIwMjM3MzUwMRswGQYDVQQDExJmaW9yaWRl\n" +
//                        "di5rZS5jb20ucGswgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBANG0IM09s4gG\n" +
//                        "FxrYLwTj9eDrCX+ugy7pWr50iM7MiBd9eQVLR8Uj/UdPzPbeVFOCkiqNJEwyA6Gw\n" +
//                        "XNEODHZFFYY8n2dimefGmCePCqBZ2Yz84WNCd+r1KsX8pviTxNNsxQ4REYkslmAl\n" +
//                        "+/oPd9IYwZ/qq0rQGliek+0J++1SY7D/AgMBAAEwDQYJKoZIhvcNAQEFBQADgYEA\n" +
//                        "irYq/J76tv8pOWREzBdMij/NNd9eAxesU1j8OGqU+G7brfFlYV8guyVpDskuS9my\n" +
//                        "Hys8ac1M5zComMszeFYyJh5LiNNBbchwigjEq7cVXuWUe5MS7wiTr5Ex954zl0xE\n" +
//                        "uP9hr/N91iMZh+fMiq0b6NspFXVKV7EPWVRHVvdoCA4=\n" +
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
