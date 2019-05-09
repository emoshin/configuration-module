package com.emoshin.automation;

import com.emoshin.automation.configuration.Configuration;
import com.emoshin.automation.parameter.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class ConfigurationManager {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationManager.class);
    private static final Object LOCK = new Object();
    private static volatile ConfigurationManager managerInstance;

    private final Map<Long, Configuration> configurationsMap = new ConcurrentHashMap<>();
    private final ThreadLocal<Configuration> currentThreadConfiguration = ThreadLocal.withInitial(ConfigurationImpl::new);
    private final BiConsumer<String, Parameter<?>> addParamToConfiguration = (name, param) -> currentThreadConfiguration.get().put(name, param);

    private ConfigurationManager() {
    }

    public static ConfigurationManager getInstance() {
        LOG.info("Getting instance of configuration manager");
        ConfigurationManager localInstance = managerInstance;
        if (null == localInstance) {
            synchronized (LOCK) {
                localInstance = managerInstance;
                if (null == localInstance) {
                    LOG.info("Configuration Manager was not initialized! Creating new instance...");
                    localInstance = managerInstance = new ConfigurationManager();
                }
            }
        }
        return localInstance;
    }

    public ConfigurationManager addParameter(String parameterName, Parameter<?> parameter) {
        LOG.info("Adding new parameter '{}' - {} into configuration for thread -> {}",
                parameterName,
                parameter.getValue(),
                Thread.currentThread().getId());
        addParamToConfiguration.accept(parameterName, parameter);
        return this;
    }

    public Configuration create() {
        LOG.info("Creating new configuration object in the configurations map.");
        Long threadId = Thread.currentThread().getId();
        Configuration createdConfig = currentThreadConfiguration.get();
        configurationsMap.put(threadId, createdConfig);
        return createdConfig;
    }

    public Configuration getConfiguration() {
        return getConfigurationForThreadId(Thread.currentThread().getId());
    }

    public Configuration getConfigurationForThreadId(Long threadId) {
        LOG.info("Trying to get configuration for thread -> {}", threadId);
        if (threadId.equals(Thread.currentThread().getId())) {
            return currentThreadConfiguration.get();
        }
        LOG.info("Configuration was not initialized! Creating empty configuration...");
        return configurationsMap.computeIfAbsent(threadId, config -> new ConfigurationImpl());

    }

    private class ConfigurationImpl implements Configuration {

        private final Map<String, Parameter<?>> configurationMap = new HashMap<>();

        @SuppressWarnings("unchecked")
        @Override
        public Parameter<?> getParameter(final String paramName) {
            Parameter<?> value = null;
            for (Map.Entry<String, Parameter<?>> entry : configurationMap.entrySet()) {
                if (entry.getKey().equals(paramName)) {
                    value = entry.getValue();
                }
            }
            if (null == value) {
                throw new ParameterNotFoundException(paramName);
            }
            return value;
        }

        @Override
        public void put(final String paramName, Parameter<?> parameter) {
            if (paramName != null && paramName.length() > 0) {
                configurationMap.put(paramName, parameter);
            }
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("[");
            for (Map.Entry<String, Parameter<?>> entry : configurationMap.entrySet()) {
                stringBuilder.append("\n\t").append(entry.getKey())
                        .append(" = ")
                        .append(entry.getValue().getValue());
            }
            return stringBuilder.append("\n]").toString();
        }

        @Override
        public int size() {
            return configurationMap.size();
        }
    }
}
