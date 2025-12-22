package org.example.helper;

public class ErrorLogger implements ErrorHandler {
    @Override
    public void handle(String providerName, Throwable error) {
        System.out.println(providerName + " encountered error with error message " + error.getMessage());
    }
}
