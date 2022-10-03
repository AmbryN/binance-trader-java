# Binance Trading Bot (Java)

![Test status](https://github.com/AmbryN/binance-trader-java/actions/workflows/tests.yml/badge.svg)

**WORK IN PROGRESS**

It allows:
* To select a crypto pair to trade from the available pairs
* To select a trading strategy from the available list
* Set the strategy's variable (e.g. observation period, number of periods) used to execute said strategy
* To send actual buy and sell orders on Binance (currently being tested on Binance's Testnet)

Planned features:
* Allow to trade with a fraction of your quote balance in order to be able
to use the same quote for multiple bots trading different base cryptos
* Log transactions to File and/or Database
* Add Web UI for crypto and strategy selection

Bugs to fix:
* The bot will sometimes try to send two orders on after each other before the first one has had time 
to finish

## Available pairs (new ones will be added shortly)
- BTCUSDT
- BTCBUSD
- ETHUSDT
- ETHBUSD

## Available strategies
- Simple Moving Average (SMA)
- Exponential Moving Average (EMA)

## Usage

***!!! Caution : use this bot at your own risk and expenses !!!***
> Be aware that Binance will take commissions on your trades in either 
> the quote crypto your using or in BNBs if you've set up your account 
> to do so.
0) Make sure you have JDK 17 or newer and maven installed
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

## Built With

* [Java](https://www.java.com/) - Programming language
* [Binance Connector for Java](https://github.com/binance/binance-connector-java) - Interaction with Binance
* [Gson](https://github.com/google/gson) - Deserialization
* [JUnit](https://junit.org/junit4/) - Test Suite
* [Mockito](https://site.mockito.org/) - Mocking / Stubbing Framework
* [Logback](https://github.com/qos-ch/logback) - Logging utility

## Authors

* **AmbryN** - *Initial work* - [AmbryN](https://github.com/AmbryN)