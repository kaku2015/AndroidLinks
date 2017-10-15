package com.dahuo.sunflower.links.network.retrofit;


import com.dahuo.sunflower.links.network.constants.ConstantUrl;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param baseUrl REST baseUrl url
     * @return retrofit service with defined endpoint
     */
    public static <T> T createService(final Class<T> clazz, final String baseUrl) {
        return createService(baseUrl, clazz, GsonConverterFactory.create(), false);
    }

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createService(final Class<T> clazz) {
        return createService(ConstantUrl.BASE_URL, clazz, GsonConverterFactory.create(), false);
    }

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @param isDebug log level
     * @return retrofit service with defined endpoint
     */
    public static <T> T createService(final Class<T> clazz, boolean isDebug) {
        return createService(ConstantUrl.BASE_URL, clazz, GsonConverterFactory.create(), isDebug);
    }
    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createService(final Class<T> clazz, Converter.Factory factory, boolean isDebug) {
        return createService(ConstantUrl.BASE_URL, clazz, factory, isDebug);
    }

    /**
     * Creates a retrofit service from an arbitrary class (clazz)
     * @param clazz Java interface of the retrofit service
     * @return retrofit service with defined endpoint
     */
    public static <T> T createService(final String baseUrl, final Class<T> clazz,
                                      Converter.Factory factory, boolean isDebug) {

        HttpLoggingInterceptor httpLogging = new HttpLoggingInterceptor();
        httpLogging.setLevel(isDebug ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(factory)
                .client(new OkHttpClient.Builder().addInterceptor(httpLogging).build())
                .build();

        return retrofit.create(clazz);
    }
}
