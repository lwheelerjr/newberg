package com.spiritflightapps.newberg.restclient;

import org.json.JSONObject;
import org.json.JSONException;


public class Quote {
	
	String date = "";
	Double open;
	Double close;
	Double high;
	Double low; 

	public Quote(String date, Double open, Double close, Double high, Double low) {
		this.date = date;
		this.open = open;
		this.close = close;
		this.high = high;
		this.low = low;
	}


	public static Quote fromJSONObject(JSONObject obj) {

		try {

			return new Quote( (String) obj.get("Date"), Double.parseDouble( (String) obj.get("Open")), Double.parseDouble( (String) obj.get("Close")), Double.parseDouble( (String) obj.get("High")), Double.parseDouble( (String) obj.get("Low")) );

		} catch (JSONException je) {

		}

  		return null;
	}


	public String toString() {
		return "Quote: " + "Date = " + date + " Open = " + open.toString() + " Close = " + close.toString() + " High = " + high.toString() + " Low = " + low.toString();

	}

}