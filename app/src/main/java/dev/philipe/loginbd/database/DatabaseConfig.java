package dev.philipe.loginbd.database;

public interface DatabaseConfig {
    String getDriver();
    String getURL();
    String getUSer();
    String getPassword();
}
