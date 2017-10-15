package com.dahuo.sunflower.links.network.retrofit;

public interface ApiCallback<T> {
  /**
   * Invoked for a received HTTP response.
   */
  void onResponse(T t);

  /**
   * Invoked when a network exception occurred talking to the server or when an unexpected
   * exception occurred creating the request or processing the response.
   */
  void onFailure(ApiError error);

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    void onFinish();
}
