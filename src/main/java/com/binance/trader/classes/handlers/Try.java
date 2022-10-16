package com.binance.trader.classes.handlers;

import ch.qos.logback.classic.Logger;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;
import com.binance.trader.interfaces.FallibleAction;
import com.binance.trader.interfaces.FallibleRunnable;
import com.binance.trader.utils.Logging;

import java.util.Optional;

/**
 * This class is used as a wrapper to calls to the exchange in order to catch
 * exception thrown by the connector and handle them appropriately
 */
public class Try {
    public static final Logger logger = Logging.getInstance();

    /**
     * The method is used to safely execute the action provided in parameter
     * by surrounding it in the appropriate try - catch statements and returning
     * an Optional<T>
     * @param action is a function that fetches information from the exchange
     * @return Optional<T> Optional of the T object returned by the action
     */
    public static <T> Optional<T> toGet(FallibleAction<T> action) {
        try {
            return Optional.ofNullable(action.result());
        } catch (BinanceConnectorException | BinanceServerException e) {
            logger.error("Could not get from Binance: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
            throw e;
        }
        return Optional.empty();
    }

    /**
     * The method is used to safely execute the action provided in parameter
     * by surrounding it in the appropriate try - catch statements
     * @param runnable is a function that sends information to the exchange
     */
    public static void toRun(FallibleRunnable runnable) {
        try {
            runnable.run();
        } catch (BinanceConnectorException | BinanceServerException e) {
            logger.error("Could not send to Binance: {}", e.getMessage(), e);
        } catch (BinanceClientException e) {
            logger.error("fullErrMessage: {} \nerrMessage: {} \nerrCode: {} \nHTTPStatusCode: {}",
                    e.getMessage(), e.getErrMsg(), e.getErrorCode(), e.getHttpStatusCode(), e);
            throw e;
        }
    }
}
