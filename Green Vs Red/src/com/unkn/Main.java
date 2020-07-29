package com.unkn;


public class Main {


    public static void main(String[] args) {

        Engine engine = new Engine();

        engine.initializeBoard();
        engine.setGenerationZero();
        engine.start();

    }
}


