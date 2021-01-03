package com.quzehu.learn;

import com.quzehu.learn.invoker.Invoker;


public class ClientApp {

    public static void main( String[] args ) {
        Invoker invoker = new Invoker();
        invoker.callLoop();
    }
}
