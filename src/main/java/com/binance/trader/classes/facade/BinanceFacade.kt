package com.binance.trader.classes.facade

import com.binance.connector.client.impl.SpotClientImpl
import com.binance.trader.classes.data.AccountInfo
import com.binance.trader.classes.data.Kline
import com.binance.trader.enums.Symbol
import com.binance.trader.intefaces.Exchange
import com.binance.trader.services.binance.AccountInfoService
import com.binance.trader.services.binance.KlineService
import com.binance.trader.services.binance.OrderService
import com.binance.trader.services.binance.TickerService

/**
 * Facade class for Binance exchange which handles
 * all the connections and work for client the application
 */
public class BinanceFacade : Exchange {
    private val client: SpotClientImpl

    init {
        var url = TESTNET_URL
        var apiKey = System.getenv("TESTNET_API_KEY")
        var secretKey = System.getenv("TESTNET_SECRET_KEY")
        if (System.getenv("BINANCE_TRADER_ENV") == "PROD") {
            url = BINANCE_URL
            apiKey = System.getenv("BINANCE_API_KEY")
            secretKey = System.getenv("BINANCE_SECRET_KEY")
        }
        client = SpotClientImpl(apiKey, secretKey, url)
    }

    override fun getBalances(symbol: Symbol): HashMap<String, Double> {
        val accountInfo: AccountInfo = AccountInfoService(client).accountInfo
        val baseBalance = accountInfo.getBalance(symbol.base).freeBalance
        val quoteBalance = accountInfo.getBalance(symbol.quote).freeBalance

        val balances = java.util.HashMap<String, Double>()
        balances["base"] = baseBalance
        balances["quote"] = quoteBalance
        return balances
    }

    override fun getTickerPrice(symbol: Symbol): Double {
        return TickerService(client).getTicker(symbol).price
    }

    override fun getClosePrices(symbol: Symbol, period: String, nbOfRecordsToFetch: Int): ArrayList<Double> {
        val klines: ArrayList<Kline> = KlineService(client).fetchKlines(symbol, period, nbOfRecordsToFetch)
        val closePrices: ArrayList<Double> = arrayListOf()
        klines.forEach { kline -> closePrices.add(kline.closePrice) }
        return closePrices
    }

    override fun buy(symbol: Symbol, tickerPrice: Double, quoteBalance: Double) {
        OrderService(client).buy(symbol,tickerPrice, quoteBalance)
    }

    override fun sell(symbol: Symbol, tickerPrice: Double, baseBalance: Double) {
        OrderService(client).sell(symbol,tickerPrice, baseBalance)
    }

    companion object {
        private const val TESTNET_URL = "https://testnet.binance.vision"
        private const val BINANCE_URL = "https://api.binance.com"
    }

}