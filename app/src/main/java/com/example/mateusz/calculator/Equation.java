package com.example.mateusz.calculator;

public class Equation {

    private double firstDigit;
    private double secondDigit;
    private String operationSign;
    private double value;
    private boolean clearScreen = false;
    private boolean equalSignClicked = false;
    private boolean waitForDigit = false;

    public Equation(double firstDigit, double secondDigit, String operationSign) {
        this.firstDigit = firstDigit;
        this.secondDigit = secondDigit;
        this.operationSign = operationSign;
    }

    public Equation(double firstDigit, String operationSign) {
        this.firstDigit = firstDigit;
        this.operationSign = operationSign;
    }

    public Equation() {
        this.firstDigit = Double.NaN;
        this.secondDigit = Double.NaN;
        this.value = Double.NaN;
        this.operationSign = "";
    }

    public double getFirstDigit() {
        return firstDigit;
    }

    public void setFirstDigit(double firstDigit) {
        this.firstDigit = firstDigit;
    }

    public double getSecondDigit() {
        return secondDigit;
    }

    public void setSecondDigit(double secondDigit) {
        this.secondDigit = secondDigit;
    }

    public String getOperationSign() {
        return operationSign;
    }

    public void setOperationSign(String operationSign) {
        this.operationSign = operationSign;
    }

    public double getValue() {
        return value;
    }

    public boolean isClearScreen() {
        return clearScreen;
    }

    public void setClearScreen(boolean clearScreen) {
        this.clearScreen = clearScreen;
    }

    public boolean isEqualSignClicked() {
        return equalSignClicked;
    }

    public void setEqualSignClicked(boolean equalSignClicked) {
        this.equalSignClicked = equalSignClicked;
    }

    public boolean isWaitForDigit() {
        return waitForDigit;
    }

    public void setWaitForDigit(boolean waitForDigit) {
        this.waitForDigit = waitForDigit;
    }

    public void makeEquation(Equation equation) throws DivideByZeroException, NegativeNumberException {
        switch(equation.getOperationSign()) {
            case "+":
                this.value = equation.getFirstDigit() + equation.getSecondDigit();
                break;
            case "-":
                this.value = equation.getFirstDigit() - equation.getSecondDigit();
                break;
            case "*":
                this.value = equation.getFirstDigit() * equation.getSecondDigit();
                break;
            case "/":
                if(this.getSecondDigit() == 0) throw new DivideByZeroException();
                else this.value = equation.getFirstDigit() / equation.getSecondDigit();
                break;
            case "±":
                this.value = equation.getFirstDigit() * (-1);
                break;
            case "sin":
                this.value = Math.sin(equation.getFirstDigit());
                break;
            case "cos":
                this.value = Math.cos(equation.getFirstDigit());
                break;
            case "tan":
                this.value = Math.tan(equation.getFirstDigit());
                break;
            case "ln":
                if(equation.getFirstDigit() < 0) throw new NegativeNumberException();
                else this.value = Math.log(equation.getFirstDigit());
                break;
            case "log":
                if(equation.getFirstDigit() < 0) throw new NegativeNumberException();
                else this.value = Math.log10(equation.getFirstDigit());
                break;
            case "x^2":
                this.value = Math.pow(equation.getFirstDigit(), 2);
                break;
            case "x^y":
                this.value = Math.pow(equation.getFirstDigit(), equation.getSecondDigit());
                break;
            case "√":
                if(equation.getFirstDigit() < 0) throw new NegativeNumberException();
                else this.value = Math.sqrt(equation.getFirstDigit());
                break;
            default:
                this.value = equation.firstDigit;
        }
        this.clearScreen = true;
        firstDigit = value;
    }

    public void clearAll() {
        this.clearScreen = false;
        this.equalSignClicked = false;
        this.firstDigit = Double.NaN;
        this.secondDigit = Double.NaN;
        this.value = Double.NaN;
        this.waitForDigit = false;
        this.operationSign = "";
    }
}
