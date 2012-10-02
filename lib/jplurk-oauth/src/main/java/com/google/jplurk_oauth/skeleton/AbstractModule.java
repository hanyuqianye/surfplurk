package com.google.jplurk_oauth.skeleton;

public abstract class AbstractModule {
    
    protected static boolean MinimalData = false;
    
    public static void setMinimalData(boolean minimal) {
        MinimalData = minimal;
    }
    protected static HttpMethod httpMethod = HttpMethod.GET;
    
    public static void setHttpMethod(HttpMethod method) {
        httpMethod = method;
    }
    public static final String API_URL_PREFIX = "http://www.plurk.com";
    private PlurkOAuth plurkOAuth;
    
    protected PlurkOAuth getPlurkOAuth() {
        return plurkOAuth;
    }
    
    public void setPlurkOAuth(PlurkOAuth auth) {
        this.plurkOAuth = auth;
    }
    
    protected abstract String getModulePath();
    
    protected RequestBuilder requestBy(String url) {
        return new RequestBuilder(plurkOAuth, API_URL_PREFIX + getModulePath() + "/" + url);
    }
}