package com.example.mateusz.calculator;

public class NegativeNumberException extends Exception{

    private String message = "Ujemna liczba jest niedozwolona przy tym działaniu!";

    @Override
    public String getMessage() {
        return message;
    }
}
