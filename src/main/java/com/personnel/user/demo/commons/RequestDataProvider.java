package com.personnel.user.demo.commons;

import java.util.UUID;

public class RequestDataProvider {
    private static final ThreadLocal<String> requestHashHolder = new ThreadLocal<>();
    /**
     * Generates and sets a unique hash for the request (if not already set).
     */
    public void initialize() {
        if (requestHashHolder.get() == null) {
            requestHashHolder.set(UUID.randomUUID().toString());
        }
    }

    /**
     * Returns the current request hash (or null if not set).
     */
    public String getRequestHash() {
        return requestHashHolder.get();
    }

    /**
     * Updates the current request hash manually (if needed).
     */
    public void setRequestHash(String hash) {
        requestHashHolder.set(hash);
    }

    /**
     * Clears the request hash to prevent memory leaks.
     */
    public void clear() {
        requestHashHolder.remove();
    }
}
