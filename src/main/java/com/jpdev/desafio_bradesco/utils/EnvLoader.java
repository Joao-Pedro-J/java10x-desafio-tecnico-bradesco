package com.jpdev.desafio_bradesco.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvLoader {
    public static Properties load() {
        Properties env = new Properties();

        try (FileInputStream fis = new FileInputStream(".env")){
            env.load(fis);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return env;
    }
}
