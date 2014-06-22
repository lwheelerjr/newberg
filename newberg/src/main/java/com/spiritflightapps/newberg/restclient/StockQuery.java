package com.spiritflightapps.newberg.restclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.commons.httpclient.util.URIUtil;

	public class StockQuery {

	 private static String REST_HOST_URL = "http://query.yahooapis.com/v1/public/yql";

	 private HttpClient getClient() {
		final HttpParams httpParams = new BasicHttpParams();

		HttpConnectionParams.setConnectionTimeout(httpParams, 30000);

		HttpClient client = new DefaultHttpClient(httpParams);

		return client;
	 }

	 private String doRequest(HttpUriRequest request) {

		String responseLines = "";
		try {
			HttpClient client = getClient();

            HttpResponse response = client.execute(request);

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;

            while ((line = rd.readLine()) != null) {
                responseLines = responseLines + line;
            }


		} catch (IOException ioe) {
			System.err.println("Error in doRequest()");
		}

		return responseLines;

	 }

	 private String urlEncode(String url) {
	 	try {
	 		String encodedUrl = URIUtil.encodeQuery(url);
	 		return encodedUrl;
	 	}
	 	catch (IOException ioe) {
	 		System.err.println("Error in URIUtil.encodeQuery()");
	 	}
	 	return url;

	 }


	 private String doGet(String url) {

		HttpGet request = new HttpGet(urlEncode(url));

		System.out.println("GET REST Request = " + urlEncode(url));

		String jsonLines = doRequest(request);

		System.out.println("GET REST Response = " + jsonLines);

		return jsonLines;
	 }

	 /*

	 These are the methods that will be used by the loaders
	 http://query.yahooapis.com/v1/public/yql
     ?q=select * from yahoo.finance.historicaldata where symbol='C' and startDate='2011-04-01' and endDate='2011-05-02'&format=json&env=store://datatables.org/alltableswithkeys
	 */


	 private String createStockQueryString(String stockSymbol, String fromDate, String toDate) {
	 	String stockQueryString = "?q=select * from yahoo.finance.historicaldata where symbol='" + stockSymbol + "' and startDate='"+ fromDate + "' and endDate='" + toDate + "'&format=json&env=store://datatables.org/alltableswithkeys";
	 	return stockQueryString;
	 }

    private String createCompanyNameQueryString(String stockSymbol) {
        String companyNameQueryString = "?q=select Name,ErrorIndicationreturnedforsymbolchangedinvalid from yahoo.finance.quotes where symbol='" + stockSymbol + "'&format=json&env=store://datatables.org/alltableswithkeys";
        return companyNameQueryString;
    }

	 public StockData getStockHistory(String stockSymbol, String fromDate, String toDate) {

	 	String jsonResponse = doGet( REST_HOST_URL + createStockQueryString(stockSymbol, fromDate, toDate));
	 	StockData stockData = StockData.fromJSON(stockSymbol, jsonResponse);

		return stockData;
	 }

     public String getStockCompanyName(String stockSymbol) {

         String jsonResponse = doGet( REST_HOST_URL + createCompanyNameQueryString(stockSymbol));
         CompanyNameData companyNameData = CompanyNameData.fromJSON(stockSymbol, jsonResponse);

         return (companyNameData!=null) ? companyNameData.name : null;
     }


	 public static void main(String[] args) {

	  StockQuery stockQuery = new StockQuery();
	  StockData stockData = stockQuery.getStockHistory("C","2013-04-01","2013-05-02");
	  System.out.println(stockData);

	 }

	}

