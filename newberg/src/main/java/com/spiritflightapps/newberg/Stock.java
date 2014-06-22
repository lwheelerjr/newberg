package com.spiritflightapps.newberg;

public class Stock {
    static final int INVALID_TIP = R.drawable.ic_launcher;

    String symbol;
    String companyName;
    String data;
    int tipResource = INVALID_TIP;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTipResource() {
        return tipResource;
    }

    public void setTipResource(int tipResource) {
        this.tipResource = tipResource;
    }

    public boolean tipValid() {
        return getTipResource() != INVALID_TIP;
    }
}
