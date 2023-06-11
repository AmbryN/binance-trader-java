# Binance Trading Bot (Java)

![Test status](https://github.com/AmbryN/binance-trader-java/actions/workflows/tests.yml/badge.svg)

**WORK IN PROGRESS**

It allows:
* to select a crypto pair to trade from the available pairs
* to create a custom strategy for your specific needs based on the available rules :
  * `OverIndicatorRule`: returns true when the value the first indicator is above the second indicator.
  * `UnderIndicatorRule`: returns true when the value the first indicator is under the second indicator.
  * `AndRule`: returns true when all the provided rules are true.
  * `OrRule`: returns true when at least one of the provided rules is true.
* to send buy and sell orders on an Exchange:
    * `Binance` is the only exchange supported for now
* to log the trades into a file (orderLog.txt) in the project root directory

Planned features:
* Allow to trade with a fraction of your quote balance to be able to share it between multiple bots
* Add UI for cryptocurrency and strategy selection
* Implementation of threads to allow for spinning up multiple bots and remote management 
through the UI
* Log transactions to Database
* Make the bot more Exchange agnostic

## Available pairs
- BTCUSDT
- BTCBUSD
- ETHUSDT
- ETHBUSD

## Create a custom strategy
A strategy is always defined by an entrance rule (i.e., when to enter a trade), and an exit rule 
(i.e., when to exit a trade).
Each rule takes one or more indicators as parameters, which are used to determine the rule's outcome.
Tailored rules can be created by composing the available rules and indicators using the `AndRule` and `OrRule`.

### Available indicators
- `ConstantIndicator`: represents a constant value
- `PriceIndicator`: the result of this indicator are the closing prices of the prices
- `SMAIndicator`: represents the Simple Moving Average of the prices which were given
- `EMAIndicator`: represents the Exponential Moving Average of the prices which were given
- `SubtractIndicator`: represents the difference between two indicators

### Example use

#### Creating a new Indicator
Your first indicator will be calculated on the closing prices of the last bars.
First the indicator needs to be instantiated as follows:
```java
// SMAIndicator(baseIndicator, numberOfPeriods)
Indicator SMA = new SMAIndicator(new PriceIndicator(), 15); 
```

### Creating a Strategy
Now, lets say you want to buy when the price is above the SMA and sell when the price is below the SMA.
The entrance rule would be:
```java
Rule entranceRule = new OverIndicatorRule(new PriceIndicator(), SMA);
```
The exit rule would be:
```java
Rule exitRule = new UnderIndicatorRule(new PriceIndicator(), SMA);
```
Finally, you can create your strategy:
```java
Strategy strategy = new Strategy(entranceRule, exitRule);
```

#### Building more complex rules
If you wanted to build a more complex rule, say the Moving Average Convergence Divergence
you'd have to combine the rules and indicators as follows:
```java
// MACDIndicator(baseIndicator, shortPeriod, longPeriod)
Indicator MACD = new MACDIndicator(new PriceIndicator(), 12, 26); 
// EMAIndicator(baseIndicator, numberOfPeriods)
Indicator signal = new EMAIndicator(MACD, 9);
// SubtractIndicator(baseIndicator, indicatorToSubtract)
Indicator subtraction = new SubtractIndicator(MACD, signal);

// Buy when MACD is above the signal (i.e., subtraction is positive)
Rule entrance = new OverIndicatorRule(subtraction, new ConstantIndicator(0));

// Sell when MACD is below the signal (i.e., subtraction is negative)
Rule exit = new UnderIndicatorRule(subtraction, new ConstantIndicator(0));
```

### Choosing an Exchange
Simply instantiate an `Exchange` corresponding to the exchange you want to trade on:
```java
Exchange exchange = new BinanceExchange();
```

- Binance: `BinanceExchange`

### Choosing a Symbol
Choose between the available symbols:
```java
Symbol symbol = Symbol.BTCUSDT;
```

- Bitcoin / USD Tether: `BTCUSDT`
- Bitcoin / Binance USD: `BTCBUSD`
- Ethereum / USD Tether: `ETHUSDT`
- Ethereum / Binance USD: `ETHBUSD`

### Set the computation period
```java
Period period = Period.FIFTEEN_MINUTES;
``
```

### Creating the Trader bot
Finally, you can create your bot with all the objects you have created:
```java
Trader trader = new Trader(exchange, period, symbol, strategy);
```

### Running the bot
```java
trader.run();
```

## Startup

***!!! Caution : use this bot at your own risk and expenses !!!***
> Be aware that Binance will take commissions on your trades in either 
> the base crypto your trading or in BNBs if you’ve set up your account 
> to do so.
0) Make sure you have JDK 17 (or newer) installed
1) Clone the git repository to your computer 
2) `cd` to the directory
3) Set following environment variables on your system:
```
BINANCE_TRADER_ENV="DEV" // 'DEV' Testnet - 'PROD' Actual Binance Exchang !!!! USE AT YOUR OWN RISK !!!!
TESTNET_API_KEY="YOUR_TESTNET_API_KEY" // 'PROD' uses BINANCE_API_KEY
TESTNET_SECRET_KEY="YOUR_TESTNET_SECRET_KEY" // 'PROD' uses BINANCE_SECRET_KEY
```




### Run in Console mode
4) Modify the `bot/src/main/java/org/crypto/bot/App.java` file to create your own strategy
5) Run `./gradlew build`
6) Run `java -jar bot/build/libs/bot-1.0-SNAPSHOT.jar`

### Run in Library mode (experimental)
4) You can call the `Trader` API directly from your own code

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