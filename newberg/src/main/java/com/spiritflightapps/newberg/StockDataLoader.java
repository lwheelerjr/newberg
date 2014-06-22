package com.spiritflightapps.newberg;

import java.util.Random;

import android.content.Context;

import com.spiritflightapps.newberg.util.CallBackExecutorPool;

public class StockDataLoader {

    Context context;
    CallBackExecutorPool callBackExecutorPool;
    final int stay_id = R.drawable.stay;
    final int buy_id = R.drawable.buy;
    final int sell_id = R.drawable.sell;


    public StockDataLoader(Context context, ICallBack finishedCallBack){
        this.context = context;
        callBackExecutorPool = new CallBackExecutorPool(5, finishedCallBack);
    }

    public void LoadData(Stock stock)
    {
        queueStock(stock);
    }

    private synchronized void setStockTip(Stock stock, Integer id) {
        stock.setTipResource(id);
    }

    private void queueStock(Stock stock)
    {
        callBackExecutorPool.execute(new StocksDataLoader(stock));
    }

    class StocksDataLoader implements Runnable {
        Stock stock;

        StocksDataLoader(Stock stock){
            this.stock = stock;
        }

        @Override
        public void run() {

            //
            // TODO: DO STUFF - FETCH STOCK AND GENERATE TIP
            //
            int x;
            for (long i = 0; i < 50000000L; ++i) {
                x = stay_id * buy_id / sell_id;
            }
            int[] ids = {stay_id, buy_id, sell_id};
            Random generator = new Random();
            int id = ids[generator.nextInt(3)];


            // DETERMINE BUY/SELL/STAY TIP FOR SYMBOL
            setStockTip(stock, id);
        }
    }
}