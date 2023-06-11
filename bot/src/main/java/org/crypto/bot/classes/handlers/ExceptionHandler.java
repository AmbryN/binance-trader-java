package org.crypto.bot.classes.handlers;

import ch.qos.logback.classic.Logger;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;
import org.crypto.bot.exceptions.BinanceTraderException;
import org.crypto.bot.utils.Logging;

import java.util.Objects;

public class ExceptionHandler {
    public static final Logger logger = Logging.getInstance();
    public static void handle(Throwable e) {
        if (Objects.requireNonNull(e) instanceof BinanceConnectorException || e instanceof BinanceServerException) {
            logger.error("Could not get from Binance: {}", e.getMessage(), e);
        } else if (e instanceof BinanceClientException bce) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    bce.getMessage(), bce.getErrMsg(), bce.getErrorCode(), bce.getHttpStatusCode(), bce);
        }
        throw new BinanceTraderException("Could not get from the exchange", e);
    }
}
