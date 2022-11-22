package com.hbx.ads.domain;

public class SysPermission {

    private String permissionId;
    private String permissionName;
    private String permissionUrl;
    private char available;

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public char getAvailable() {
        return available;
    }

    public void setAvailable(char available) {
        this.available = available;
    }


}
