package com.spiritflightapps.newberg;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.spiritflightapps.newberg.restclient.StockQuery;
import com.spiritflightapps.newberg.data.DatabaseHandler;
import com.spiritflightapps.newberg.data.StockRow;

public class LazyListAdapter extends BaseAdapter implements ICallBack{

    private String[] bergTests = {
            "1. SITTING TO STANDING",
            "2. STANDING UNSUPPORTED",
            "3. SIT WITH BACK\n UNSUPPORTED AND\n FEET SUPPORTED",
            "4. STANDING TO SITTING",
            "5. TRANSFERS",
            "6. STAND UNSUPPORTED\n WITH EYES CLOSED",
            "7. STAND UNSUPPORTED\n  WITH FEET TOGETHER",
            "8. STAND-REACH\n FORWARD WITH ARM",
            "9. STAND-PICKUP OBJECT\n FROM FLOOR",
            "10. STAND-LOOK BEHIND\n SHOULDERS",
            "11. TURN 360 DEGREES",
            "12. PLACE ALTERNATING\n FOOT ON STEP WHILE\n UNSUPPORTED",
            "13. STAND UNSUPPORTED\n ONE FOOT IN FRONT OF\n THE OTHER",
            "14. STANDING ON ONE LEG"};

    private Context context;
    private static LayoutInflater inflater=null;
    private ArrayList<Stock> listData;
    private StockDataLoader stockDataLoader;
    DatabaseHandler db;


    public LazyListAdapter(Context context, ListView listView) {
        this.context = context;
        listData = new ArrayList<Stock>();
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        stockDataLoader = new StockDataLoader(context, this);
        db = new DatabaseHandler(context);
    }

    public int getCount() {
        return bergTests.length + 1;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi;


        if(position < bergTests.length ) {

           // if (convertView == null)
                vi = inflater.inflate(R.layout.list_row, null);

            TextView symbol = (TextView) vi.findViewById(R.id.stocksymbol);
            //Stock stock = listData.get(position);

            symbol.setText(bergTests[position]);
            Spinner spinner = (Spinner) vi.findViewById(R.id.score_spinner);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.score_array, R.layout.spinner_layout);
            adapter.setDropDownViewResource(R.layout.spinner_layout);
            spinner.setAdapter(adapter);
        } else {
            vi = inflater.inflate(R.layout.total_row, null);
        }

        return vi;
    }

    public void addNewStock(String symbol) {
        if(!symbolExistAlready(symbol)) {
            new QueryStockTask().execute(symbol);
        } else {
            Toast.makeText(context, "Stock symbol '" + symbol + "' already added.", Toast.LENGTH_LONG).show();
        }
    }

    public void deleteStock(int position) {
        String symbol = listData.get(position).getSymbol();
        listData.remove(position);
        notifyDataSetChanged();
        Toast.makeText(context, "Stock '" + symbol + "' deleted.", Toast.LENGTH_LONG).show();
    }


    public void readData() {
        List<StockRow> stockRowList = db.getAllStockRows();
        listData.clear();
        for(StockRow stockRow : stockRowList) {
            Stock newStock = new Stock();
            newStock.setSymbol(stockRow.getSymbol());
            newStock.setCompanyName(stockRow.getCompanyName());
            listData.add(newStock);
            stockDataLoader.LoadData(newStock);
        }
    }


    public void saveData() {
        db.deleteAllRows();
        for (Stock stock : listData) {
            StockRow stockRow = new StockRow(stock.getSymbol(), stock.getCompanyName(), String.valueOf(stock.getTipResource()), stock.getData());
            db.addStockRow(stockRow);
        }
    }

    public void callBack() {
    }


    private boolean symbolExistAlready(String symbol) {
        for(Stock stock : listData) {
            if(stock.getSymbol().equalsIgnoreCase(symbol)) {
                return true;
            }
        }
        return false;
    }


    private void addStockToList(String symbol, String companyName) {
        if(companyName == null) {
            Toast.makeText(context, "Invalid stock symbol.", Toast.LENGTH_LONG).show();
            return;
        }
        Stock newStock = new Stock();
        newStock.setSymbol(symbol);
        newStock.setCompanyName(companyName);
        stockDataLoader.LoadData(newStock);
        listData.add(0, newStock);
        notifyDataSetChanged();
    }


    private class QueryStockTask extends AsyncTask<String, Void, String> {
        String symbol;

        protected String doInBackground(String... stock) {
            this.symbol = stock[0];
            StockQuery stockQuery = new StockQuery();
            String companyName = stockQuery.getStockCompanyName(symbol);
            return companyName;
        }

        protected void onPostExecute(String companyName) {
            addStockToList(symbol, companyName);
        }
    }

}
