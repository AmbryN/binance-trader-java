# Binance Trading Bot (Java)

![Build and Test status](https://github.com/AmbryN/binance-trader-java/actions/workflows/java-ci.yml/badge.svg)

**WORK IN PROGRESS**

It allows :
* To select a crypto pair to trade from the available pairs (e.g. "BTCUSDT" or "ETHUSDT")
* To select a trading strategy from the available list (for now only "Moving Average")
* To send actual buy and sell orders on Binance (currently being tested on Binance's Testnet)

Planned features :
* Set the strategy's variable (e.g. observation period, number of periods) used to execute said strategy
* Allow to set logging level
* Log transactions to File and/or Database

## Usage

1) Clone the git repository to your computer 
2) `cd` to the directory
3) Create a file named "PrivateConfig.java" in `/src/main/java/com/binance/trader/` containing:
```
package com.binance.trader;

public class PrivateConfig {
    public static final String TESTNET_URL = "https://testnet.binance.vision";
    public static final String TESTNET_API_KEY = "YOU_TESTNET_API_KEY";
    public static final String TESTNET_SECRET_KEY = "YOUR_TESTNET_SECRET_KEY";

    public static final String BINANCE_URL = "https://api.binance.com";
}
```
4) Run `mvn clean install`
5) Run `mvn exec:java -Dexec.mainClass="com.binance.trader.App`

## Built With

* [Java](https://www.java.com/) - Programming language
* [Binance Connector for Java](https://github.com/binance/binance-connector-java) - Interaction with Binance
* [Gson](https://github.com/google/gson) - Deserialization
* [JUnit](https://junit.org/junit4/) - Test Suite
* [Mockito](https://site.mockito.org/) - Mocking / Stubbing Framework

## Authors

* **AmbryN** - *Initial work* - [AmbryN](https://github.com/AmbryN)