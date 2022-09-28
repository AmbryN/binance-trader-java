# Binance Trading Bot (Java)

**WORK IN PROGRESS**

It allows :
* To select a crypto pair to trade from the available pairs (for now, only "BTCUSDT")
* To select a trading strategy from the available list (for now only "Moving Average")
* To send actual buy and sell orders on Binance (currently being tested on Binance's Testnet)

Planned features :
* Set the strategy's variable (eg. oservation period, number of periods) used to execute said strategy
* Improve User input experience and associated code
* Set the logging level
## Usage

The bot is in early development stage: this part will be updated shortly.

## Built With

* [Java](https://www.java.com/) - Programming language
* [Binance Connector for Java](https://github.com/binance/binance-connector-java) - Interaction with Binance
* [Gson](https://github.com/google/gson) - Deserialization
* [JUnit](https://www.junit.fr/) - Test Suite
* [Mockito](https://site.mockito.org/) - Mocking / Stubbing Framework

## Authors

* **Nicolas AMBRY** - *Initial work* - [AmbryN](https://github.com/AmbryN)