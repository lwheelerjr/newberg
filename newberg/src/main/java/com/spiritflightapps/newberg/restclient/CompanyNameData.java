package com.spiritflightapps.newberg.restclient;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class CompanyNameData {

	String symbol = "";
    String name = "";

	public CompanyNameData(String symbol, String name) {
		this.symbol = symbol;
        this.name = name;
	}

	public static CompanyNameData fromJSON(String stockSymbol, String json) {
		try {
			JSONObject obj = (JSONObject) new JSONTokener(json).nextValue();
			JSONObject query = (JSONObject) obj.get("query");

			JSONObject results = (JSONObject) query.get("results");
            JSONObject quotes = (JSONObject) results.get("quote");

            String name = quotes.getString("Name");
            boolean validName = quotes.getString("ErrorIndicationreturnedforsymbolchangedinvalid").equalsIgnoreCase("null");

            if(!validName) {
                return null;
            }
			return new CompanyNameData(stockSymbol, name);

		} catch (JSONException je) {
			je.printStackTrace();
		}

  		return null;
	}

    public String toString() {
		String string = "CompanyNameData:\n" + "   Symbol = " + symbol + "\n" + "   Name = " + name + "\n";
		return string;
	}



}