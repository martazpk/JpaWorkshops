package com.ailleron.workshop.entity.jpa;

public abstract class Device {

    public Device() {
    }

    public Device(String deviceName, String localization) {
        this.deviceName = deviceName;
        this.localization = localization;
    }

    private String deviceName;

    private String localization;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }
}
