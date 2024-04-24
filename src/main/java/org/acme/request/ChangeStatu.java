package org.acme.request;

public class ChangeStatu {
    private int id;
    private String statu;

    public ChangeStatu() {
    }

    public ChangeStatu(int email, String statu) {
        this.id = id;
        this.statu = statu;
    }

    public String getStatu() {
        return statu;
    }

    public void setStatu(String statu) {
        this.statu = statu;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
