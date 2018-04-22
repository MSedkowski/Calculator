package com.example.mateusz.calculator;

class DivideByZeroException extends Exception{

    private String message = "Dzielenie przez zero!";

    @Override
    public String getMessage() {
        return message;
    }
}
