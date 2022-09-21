package com.binance.trader;

import java.util.Scanner;

import com.binance.trader.enums.Symbol;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("What symbol do you want to trader? ");
        
        String userSymbol = scanner.nextLine();
        scanner.close();

        if (Symbol.isValid(userSymbol)) {
            Symbol symbol = Symbol.getSymbol(userSymbol);
            Trader trader = new Trader();
            trader.trade(symbol);
        } else {
            System.out.println("Please specify a valid symbol (eg. BTCUSDT)!");
        }
        
    }
}
