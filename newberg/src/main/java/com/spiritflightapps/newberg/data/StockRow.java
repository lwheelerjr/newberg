package com.spiritflightapps.newberg.data;


public class StockRow {

    long _id;
    String _symbol;
    String _data;
    String _companyname;
    String _tip;
    long _timestamp;

    public StockRow() {

    }

    public StockRow(String symbol, String companyname, String tip, String data){
        this._symbol = symbol;
        this._companyname = companyname;
        this._tip = tip;
        this._data = data;
        this._timestamp = System.currentTimeMillis();
    }

    public long getID(){
        return this._id;
    }


    public void setID(long id){
        this._id = id;
    }


    public String getSymbol(){
        return this._symbol;
    }

    public void setSymbol(String symbol){
        this._symbol = symbol;
    }

    public String getCompanyName(){
        return this._companyname;
    }

    public void setCompanyName(String companyname){
        this._companyname = companyname;
    }


    public String getData(){
        return this._data;
    }

    public void setData(String data){
        this._data = data;
    }

    public long getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(long timestamp) {
        this._timestamp = timestamp;
    }

    public String getTip() {
        return _tip;
    }

    public void setTip(String tip) {
        this._tip = tip;
    }

}
