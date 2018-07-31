package com.brsatalay.projectbase.library.core.util;

import android.content.Context;

import com.brsatalay.projectbase.library.core.helper.HttpClientHelper;
import com.brsatalay.projectbase.library.core.network.interceptor.NetworkInterceptor;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.annotations.Nullable;
import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by barisatalay on 10.04.2018.
 */

public class UtilsRetrofit {
    public static OkHttpClient createClient(Context mContext, Dispatcher mDispatcher,OkHttpClient.Builder builderDef, Interceptor... args){
        OkHttpClient.Builder builder = builderDef;

        if (builder == null) {
            builder = new HttpClientHelper(mContext).newBuilder()
//                    .addInterceptor(new NetworkInterceptor(mContext))//Sanırım gerek yok. Kullanmak isteyen kişi kendisi ekleyebilir.
            ;
        }
        if (args != null)
            for(Interceptor interceptor : args){
                builder.addInterceptor(interceptor);
            }
        OkHttpClient result = builder
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .dispatcher(mDispatcher)
                .build();

        return result;
    }

    public static Retrofit createRetrofitAdapter(Context mContext, Dispatcher mDispatcher, String baseUrl, @Nullable Interceptor... args){
        return new Retrofit.Builder()
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(UtilsGson.createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(UtilsRetrofit.createClient(mContext, mDispatcher, null, args))
                .build();

    }

    public static Retrofit createUnSafeRetrofitAdapter(Context mContext, Dispatcher mDispatcher, String baseUrl, @Nullable Interceptor... args){
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(UtilsGson.createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(UtilsRetrofit.createClient(mContext, mDispatcher, getUnsafeOkHttpClient(), args))
                .build();

    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
