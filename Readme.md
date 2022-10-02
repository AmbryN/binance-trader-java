# Binance Trading Bot (Java)

![Test status](https://github.com/AmbryN/binance-trader-java/actions/workflows/tests.yml/badge.svg)

**WORK IN PROGRESS**

It allows :
* To select a crypto pair to trade from the available pairs (e.g. "BTCUSDT" or "ETHUSDT")
* To select a trading strategy from the available list (for now only "Moving Average")
* Set the strategy's variable (e.g. observation period, number of periods) used to execute said strategy
* To send actual buy and sell orders on Binance (currently being tested on Binance's Testnet)

Planned features :
* Add strategies (e.g. Exponential MovingAvg etc.)
* Log transactions to File and/or Database
* Add Web UI for crypto and strategy selection

## Usage

#### !!! Caution : use this bot at your own risk and expenses !!!
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