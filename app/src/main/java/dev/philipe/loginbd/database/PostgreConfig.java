package dev.philipe.loginbd.database;

public class PostgreConfig implements DatabaseConfig {
    @Override
    public String getDriver() {
        return "org.postgresql.Driver";
    }

    @Override
    public String getURL() {
        return "jdbc:postgresql://10.0.2.2:5432/teste";
    }

    @Override
    public String getUSer() {
        return "postgres";
    }

    @Override
    public String getPassword() {
        return "postgres";
    }
}
