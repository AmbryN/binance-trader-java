# Binance Trading Bot (Java)

![Test status](https://github.com/AmbryN/binance-trader-java/actions/workflows/tests.yml/badge.svg)

**WORK IN PROGRESS**

It allows:
* to select a crypto pair to trade from the available pairs
* to select a trading strategy from the available list
* to set the strategy's variable (e.g. observation period, number of periods) used to execute said strategy
* to send actual buy and sell orders on Binance (currently being tested on Binance's Testnet)
* to log the trades into a file (orderLog.txt) in the project root directory

Planned features:
* Schedule the strategy to be run continuously and the actual transaction to be run with a lower
  frequency (using Threads) in order to reduce the transient behaviour of the bot during signal crossing
* Allow to trade with a fraction of your quote balance in order to be able
to use the same quote crypto for multiple bots trading different base cryptos
* Add UI for crypto and strategy selection
* Log transactions to Database

## Available pairs
- BTCUSDT
- BTCBUSD
- ETHUSDT
- ETHBUSD

## Available strategies
- Simple Moving Average (SMA)
- Exponential Moving Average (EMA)
- Moving Average Convergence Divergence (MACD)
- Moving Average Convergence Divergence - Refined 1 (MACDr1): this strategy is based on the MACD but 
with a user definable spread (in %) between the MACD line and the signal line that triggers buy and sell 
`(MACD - Signal) / CurrentPrice * 100 (> or <) spread`
- Moving Average Convergence Divergence - Refined 2 (MACDr2): this strategy is based on the MACD but
it only buys if the crypto is in oversold position and implements a stop-loss if the ticker goes under
the buy price

## Usage

***!!! Caution : use this bot at your own risk and expenses !!!***
> Be aware that Binance will take commissions on your trades in either 
> the base crypto your trading or in BNBs if you've set up your account 
> to do so.
0) Make sure you have JDK 17 (or newer) and maven installed
1) Clone the git repository to your computer 
2) `cd` to the directory
3) Set following environment variables on your system:
```
BINANCE_TRADER_ENV="DEV"
TESTNET_API_KEY="YOUR_TESTNET_API_KEY"
TESTNET_SECRET_KEY="YOUR_TESTNET_SECRET_KEY"
```
4) Run `mvn clean install`
5) Run `mvn exec:java -Dexec.mainClass="com.binance.trader.App"`

## Errors

The bot consumes `BinanceConnectorException` and `BinanceServerException` thrown by the Binance Connector when getting
or sending data to the exchange: when they occur, the bot tries to reconnect 5 times before throwing a 
`BinanceTraderException`.

`BinanceClientException` thrown by the Connector are logged and re-thrown by the bot because they are 
unsolvable without human interaction.

## Built With

* [Java](https://www.java.com/) - Programming language
* [Binance Connector for Java](https://github.com/binance/binance-connector-java) - Interaction with Binance
* [Gson](https://github.com/google/gson) - Deserialization
* [JUnit](https://junit.org/junit4/) - Test Suite
* [Mockito](https://site.mockito.org/) - Mocking / Stubbing Framework
* [Logback](https://github.com/qos-ch/logback) - Logging utility

## Authors

* **AmbryN** - *Initial work* - [AmbryN](https://github.com/AmbryN)