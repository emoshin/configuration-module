package com.emoshin.automation.parameter;

import com.emoshin.automation.ConfigurationManager;
import com.emoshin.automation.configuration.Configuration;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigurationManagerTest {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationManagerTest.class);

    @Test
    public void testConfigManager() {
        Parameter<String> DB_URL = new SimpleParameter<>("http://www.some.test");
        Parameter<Integer> DB_PORT = new SimpleParameter<>(8888);
        Parameter<List<String>> DB_TABLES = new SimpleParameter<>(new ArrayList<String>() {
            {
                add("users");
            }

            {
                add("products");
            }
        });

        ConfigurationManager manager = ConfigurationManager.getInstance();
        Configuration newConfig = manager.addParameter("param.db.url", DB_URL)
                .addParameter("param.db.port", DB_PORT)
                .addParameter("param.db.tables", DB_TABLES)
                .create();
        logger.info("Current configuration is {}", newConfig);
        Parameter<Integer> port = newConfig.getParameter("param.db.port");
        logger.info("Port is [{}]", port);
    }

    @Test
    public void testConfigManagerMultipleThreads() {
        ExecutorService service = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            service.execute(new ConfigurationThread());
        }
    }

    private class ConfigurationThread implements Runnable {

        private Parameter<String> DB_URL;
        private Parameter<Integer> DB_PORT;

        public ConfigurationThread() {
            int rnd = new Random().ints(1234, 9999).limit(1).sum();
            this.DB_PORT = new SimpleParameter<>(rnd);
            this.DB_URL = new SimpleParameter<>("www.google.ru/" + rnd);
        }

        @Override
        public void run() {
            ConfigurationManager manager = ConfigurationManager.getInstance();
            Configuration newConfig = manager.addParameter("param.db.url", DB_URL)
                    .addParameter("param.db.port", DB_PORT)
                    .create();
            logger.info("Current configuration is {}", newConfig);
        }
    }
}
