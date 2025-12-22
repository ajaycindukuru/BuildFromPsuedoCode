package org.example.helper;

public interface ErrorHandler {
    void handle(String providerName, Throwable error);
}
