package org.crypto.bot.classes.data;

import org.crypto.bot.enums.Crypto;

public class Fill {
    double price;
    double qty;
    double commission;
    Crypto commissionAsset;
    Long tradeId;

    @Override
    public String toString() {
        return "Fill{" +
                "price=" + price +
                ", qty=" + qty +
                ", commission=" + commission +
                ", commissionAsset=" + commissionAsset +
                ", tradeId=" + tradeId +
                '}';
    }
}
