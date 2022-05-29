package com.cib.api.vo;

public class ExceptionBack {

    private String serviceId;
    private String exceptionCode;
    private String getExceptionDesc;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }

    public String getGetExceptionDesc() {
        return getExceptionDesc;
    }

    public void setGetExceptionDesc(String getExceptionDesc) {
        this.getExceptionDesc = getExceptionDesc;
    }
}
