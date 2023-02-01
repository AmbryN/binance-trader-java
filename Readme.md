# Binance Trading Bot (Java)

![Test status](https://github.com/AmbryN/binance-trader-java/actions/workflows/tests.yml/badge.svg)

**WORK IN PROGRESS**

It allows:
* to select a crypto pair to trade from the available pairs
* to select a trading strategy from the available list
* to set the strategy’s variable (e.g. observation period, number of periods) used to execute said strategy
* to send actual buy and sell orders on Binance (currently being tested on Binance’s Testnet)
* to log the trades into a file (orderLog.txt) in the project root directory

Planned features:
* Rework of the strategy building scheme to provide the user with a set of rules, 
* which can be used to tweak the strategy's behavior
* Add UI for crypto and strategy selection
* Implementation of threads to allow for remote management through the UI
* Allow to trade with a fraction of your quote balance to be able
to use the same quote crypto for multiple bots trading different base cryptos
* Log transactions to Database
* Make the bot more Exchange agnostic

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
> the base crypto your trading or in BNBs if you’ve set up your account 
> to do so.
0) Make sure you have JDK 17 (or newer) installed
1) Clone the git repository to your computer 
2) `cd` to the directory
3) Set following environment variables on your system:
```
BINANCE_TRADER_ENV="DEV"
TESTNET_API_KEY="YOUR_TESTNET_API_KEY"
TESTNET_SECRET_KEY="YOUR_TESTNET_SECRET_KEY"
```
4) Run `./gradlew build`

### Run in Console mode
5) Run `java -jar bot/build/libs/bot-1.0-SNAPSHOT.jar`

### Run in Library mode (experimental)
5) You can create a new instance of a Trader object in the following manner:
```java
class MyConsumerApp {
    public void main(String[] args) {
        Exchange exchange = new BinanceExchange();  // The only exchange for now
        Symbol symbol = Symbol.BTCUSDT;             // From the available symbols Enum
        Strategy strategy = new SMAStrategy();      // From the available strategies

        Trader trader = new Trader(exchange, symbol, strategy);
        trader.run();
    }
}
```

**Warning** : Development on this feature is in its beginning phase, 
thus running the bot as a library is experimental and will produce unexpected behaviors.

### Running the Webapp
The webapp is in a non-working state for the moment even though
it can be run using an application server on which to deploy the packaged war file.

## Errors

The bot consumes `BinanceConnectorException` and `BinanceServerException` thrown by the Binance Connector when getting
or sending data to the exchange: when they occur, the bot tries to reconnect 5 times before throwing a 
`BinanceTraderException`.

`BinanceClientException` thrown by the Connector are logged and re-thrown by the bot because they are 
unsolvable without human interaction.

## Built With

* [Java](https://www.java.com/)
* [Jakarta EE](https://jakarta.ee)
* [MariaDB](https://mariadb.org/) - Database
* [Binance Connector for Java](https://github.com/binance/binance-connector-java) - Interaction with Binance
* [Gson](https://github.com/google/gson) - Deserialization
* [JUnit](https://junit.org/junit4/) - Test Suite
* [Mockito](https://site.mockito.org/) - Mocking / Stubbing Framework
* [Logback](https://github.com/qos-ch/logback) - Logging utility

## Authors

* **AmbryN** - *Initial work* - [AmbryN](https://github.com/AmbryN)