package com.hbx.ads.domain.sinoclick;

public class Result {

    private Facebook facebook;
    private Google google;
    private Tiktok tiktok;
    private String lastSyncTime;
    private String syncStatus;
    private Wallet wallet;
    private char finalLevelId;

    public char getFinalLevelId() {
        return finalLevelId;
    }

    public void setFinalLevelId(char finalLevelId) {
        this.finalLevelId = finalLevelId;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public Facebook getFacebook() {
        return facebook;
    }

    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    public Google getGoogle() {
        return google;
    }

    public void setGoogle(Google google) {
        this.google = google;
    }

    public Tiktok getTiktok() {
        return tiktok;
    }

    public void setTiktok(Tiktok tiktok) {
        this.tiktok = tiktok;
    }

    public String getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(String lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    @Override
    public String toString() {
        return "Result{" +
                "facebook=" + facebook +
                ", google=" + google +
                ", tiktok=" + tiktok +
                ", lastSyncTime=" + lastSyncTime +
                ", syncStatus=" + syncStatus +
                '}';
    }
}
