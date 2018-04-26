package com.soowin.staremblem.http;

import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.soowin.staremblem.ui.index.bean.BaseBean;
import com.soowin.staremblem.utils.BaseActivity;
import com.soowin.staremblem.utils.PublicApplication;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Call;
import okhttp3.ConnectionSpec;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.platform.Platform;

/**
 * Created by hext on 2016/10/14.
 * 网络请求帮助类
 */
public class HttpTool {
    private static final String TAG = HttpTool.class.getSimpleName();

    //新添加 okHttp部分***********************开始    //Cookie缓存区
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    /**
     * 回掉筛选
     *
     * @param str
     * @return
     * @throws Exception
     */
    private static String getresponse(String str) throws Exception {
        try {
            Logger.e(str);
            Gson gson = new Gson();
            BaseBean data = gson.fromJson(str, BaseBean.class);

            switch (data.get_metadata().getCode()) {
                case 1101://token失效
                    EventBus.getDefault().post(BaseActivity.TOKEN_INVALID);
                    PublicApplication.loginInfo.edit().putString("token", "").apply();
                    break;
               /* case 403://用户无权限访问该资源，请求失败
                    EventBus.getDefault().post(BaseActivity.LACK_JURISDICTION);
                    break;
                case 500://服务器程序错误
                    EventBus.getDefault().post(BaseActivity.SERVICE_ERROR);
                    break;*/
                case 1000://强制升级APP版本
                    EventBus.getDefault().post(BaseActivity.APP_EXPIRED);
                    PublicApplication.loginInfo.edit().putString("token", "").apply();
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "getresponse: ", e);
            return "";
        }
        return str;
    }

    /**
     * okHttp post请求
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String okPost(RequestBody body, String urls) throws Exception {
        try {
            String url = PublicApplication.urlData.hostUrl + urls;
            Log.e(TAG, "okPost:url=" + url);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            //注：这里不能返回null，否则会报NULLException的错误。
                            //原因：当Request 连接到网络的时候，OkHttp会调用loadForRequest()
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            /*OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);
            SSLContext1(okHttpClient);*/// TODO: 2018/3/10 0010 添加证书后
            Log.e(TAG, "okPost: " + PublicApplication.loginInfo.getString("token", ""));
            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Content-Type", "application/json")
                    .addHeader("ft-token", PublicApplication.loginInfo.getString("token", ""))
                    .addHeader("ft-device", "app-ver=" + PublicApplication.loginInfo.getString("app-ver", "") +
                            ";device=android;device-name=" + PublicApplication.loginInfo.getString("device-name", "") +
                            ";device-id=" + PublicApplication.loginInfo.getString("device-id", "") +
                            ";device-channelId" + PublicApplication.loginInfo.getString("channelId", ""))
//                    .addHeader("app-ver",PublicApplication.loginInfo.getString("app-ver", ""))
//                    .addHeader("device","android")
//                    .addHeader("device-id",PublicApplication.loginInfo.getString("device-id", ""))
//                    .addHeader("device-name",PublicApplication.loginInfo.getString("device-name", ""))
                    .post(body)
                    .build();
            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
                Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
//            Call call = SSLContext1(okHttpClient).newCall(request);// TODO: 2018/3/10 0010 添加证书后
            Response response = call.execute();
            return getresponse(response.body().string());
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * okHttp post请求
     *
     * @param body
     * @return
     * @throws Exception
     */
    public static String okPostTest(RequestBody body, String urls) throws Exception {
        try {
            String url = urls;
            Log.e(TAG, "okPost:url=" + url);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
                Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
            return response.body().string();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * okHttp get请求
     *
     * @return
     * @throws Exception
     */
    public static String okGet(String urls) throws Exception {
        try {
            String url = PublicApplication.urlData.hostUrl + urls;
            Log.e(TAG, "okGet: url:" + url);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .cookieJar(new CookieJar() {
                        @Override
                        public void saveFromResponse(okhttp3.HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(okhttp3.HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    })
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
//            Log.e(TAG, "okGet: " + PublicApplication.loginInfo.getString("token", ""));
            Request request = new Request.Builder()
                    .url(url)
//                    .addHeader("Content-Type", "application/json")
                    .addHeader("ft-token", PublicApplication.loginInfo.getString("token", ""))
                    .addHeader("ft-device", "app-ver=" + PublicApplication.loginInfo.getString("app-ver", "") +
                            ";device=android;device-name=" + PublicApplication.loginInfo.getString("device-name", "") +
                            ";device-id=" + PublicApplication.loginInfo.getString("device-id", "") +
                            ";device-channelId" + PublicApplication.loginInfo.getString("channelId", ""))
//                    .addHeader("app-ver",PublicApplication.loginInfo.getString("app-ver", ""))
//                    .addHeader("device","android")
//                    .addHeader("device-id",PublicApplication.loginInfo.getString("device-id", ""))
//                    .addHeader("device-name",PublicApplication.loginInfo.getString("device-name", ""))
                    .get()
                    .build();

            /**
             * 打印日志
             */
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body1 = (FormBody) request.body();
                for (int i = 0; i < body1.size(); i++) {
                    sb.append(URLDecoder.decode(body1.encodedName(i)) + "=" + URLDecoder.decode(body1.encodedValue(i)) + "&");
                }
                if (sb.length() > 0)
                    sb.delete(sb.length() - 1, sb.length());
                Log.e(TAG, "RequestParams:" + sb.toString());
            }

            Call call = okHttpClient.newCall(request);
            Response response = call.execute();

            /*Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                String headerName = responseHeaders.name(i);
                String headerValue = responseHeaders.get(headerName);
                System.out.print("TAG----------->Name:" + headerName + "------------>Value:" + headerValue + "\n");
            }*/
            String s = response.body().string();
            return getresponse(s);
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            return "";
        } catch (InterruptedIOException e) {
            e.printStackTrace();
            return "";
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 图片上传部分
     * 经过测试可用
     */
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final OkHttpClient client = new OkHttpClient();

    public static String uploadImg(MultipartBody.Builder builder, String url) {
        try {
           /* MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            File f = new File(mImgUrls);
            builder.addFormDataPart("json", "user/EditAvatar");// 补充完毕地址
            builder.addFormDataPart("avatar", f.getName(), RequestBody.create(MEDIA_TYPE_PNG, f));
            builder.addFormDataPart("user_id", userId);*/

            MultipartBody requestBody = builder
                    .build();

            //构建请求
            Request request = new Request.Builder()
                    .url(PublicApplication.urlData.hostUrl + url)//地址
                    .post(requestBody)//添加请求体
                    .addHeader("Content-Type", "application/json")
                    .addHeader("ft-token", PublicApplication.loginInfo.getString("token", ""))
                    .addHeader("ft-device", "app-ver=" + PublicApplication.loginInfo.getString("app-ver", "") +
                            ";device=android;device-name=" + PublicApplication.loginInfo.getString("device-name", "") +
                            ";device-id=" + PublicApplication.loginInfo.getString("device-id", ""))
                    .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            return getresponse(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * put请求
     *
     * @return
     */
   /* public static String doPut(MediaType mediaType, String uploadUrl, String localPath) {
        try {
            File file = new File(localPath);
            RequestBody body = RequestBody.create(mediaType, file);
            Request request = new Request.Builder()
                    .url(uploadUrl)
                    .put(body)
                    .build();
            //修改各种 Timeout
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            //如果不需要可以直接写成 OkHttpClient client = new OkHttpClient.Builder().build();

            Response response = client
                    .newCall(request)
                    .execute();
            return response.body().string() + ":" + response.code();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    //上传JPG图片 t odo 待测试
    public String putImg(String uploadUrl, String localPath) throws IOException {
        MediaType imageType = MediaType.parse("image/jpeg; charset=utf-8");
        return doPut(imageType, uploadUrl, localPath);
    }*/

    /**
     * 关联Https请求验证证书
     *
     * @param okHttpClient
     */
    public static OkHttpClient SSLContext1(OkHttpClient.Builder okHttpClient) {
        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .allEnabledTlsVersions()
                .allEnabledCipherSuites()
                .build();
        try {
            //设置证书类型
            CertificateFactory factory = CertificateFactory.getInstance("X.509", "BC");
            //打开放在main文件下的 assets 下的Http证书
            InputStream stream = PublicApplication.getContext().getAssets().open("demo.crt");// TODO: 2018/3/10 0010 获取content方式可能有问题
            Certificate certificate = factory.generateCertificate(stream);
            //证书类型
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //授信证书 , 授信证书密码（应该是服务端证书密码）
            keyStore.load(null, null);
            keyStore.setCertificateEntry("certificate", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //证书密码（应该是客户端证书密码）
            keyManagerFactory.init(keyStore, "555".toCharArray());
/*TLS三次握手协议*/
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            okHttpClient.connectionSpecs(Collections.singletonList(spec))
                    .sslSocketFactory(sslSocketFactory, Platform.get().trustManager(sslSocketFactory))
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    });

            return okHttpClient.build();

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
