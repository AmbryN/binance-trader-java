package org.crypto.bot.classes.handlers;

import ch.qos.logback.classic.Logger;
import com.binance.connector.client.exceptions.BinanceClientException;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.exceptions.BinanceServerException;
import org.crypto.bot.exceptions.BinanceTraderException;
import org.crypto.bot.interfaces.FallibleAction;
import org.crypto.bot.interfaces.FallibleRunnable;
import org.crypto.bot.utils.Logging;

import java.util.Optional;

/**
 * This class is used as a wrapper to calls to the exchange to catch
 * exception thrown by the connector and handle them appropriately.
 */
public class Try {
    public static final Logger logger = Logging.getInstance();

    /**
     * The method is used to safely execute the action provided in parameter
     * by surrounding it in the appropriate try – catch statements and returning
     * an Optional<T>
     *
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
     *
     * @param runnable is a function that sends data to the exchange
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

    /**
     * The method is used to safely execute the action provided in parameter
     * by surrounding it in the appropriate try – catch statements and allows for
     * retries.
     *
     * @param runnable is a function that sends data to the exchange
     */
    public static void toRunTimes(FallibleRunnable runnable, final int MAX_RECONNECT_TRIES) {
        int tries = 0;
        while (tries < MAX_RECONNECT_TRIES) {
            try {
                runnable.run();
                break;
            } catch (BinanceTraderException e) {
                logger.error(e.getMessage(), e);
                if (tries == MAX_RECONNECT_TRIES) {
                    throw e;
                }

                tries++;
                logger.warn(String.format("Trying to reconnect [%d/%d].", tries, MAX_RECONNECT_TRIES));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
