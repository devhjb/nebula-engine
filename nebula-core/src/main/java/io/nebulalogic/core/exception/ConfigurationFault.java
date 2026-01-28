/*
 * Copyright 2026 jabbey and Nebula-Engine contributors
 * Nebula-Engine: A lightweight, rule-driven automation engine designed for complex SaaS business logic.
 *
 * "Nebulae are silent, but destined to collapse into stars."
 *
 * Licensed under the Apache License, Version 2.0 (http://www.apache.org/licenses/LICENSE-2.0)
 *
 * Project: https://github.com/devhjb/nebula-engine
 */
package io.nebulalogic.core.exception;


import java.util.HashMap;
import java.util.Map;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.exception
 * @ClassName ConfigurationFault.java
 * @Description 类 ConfigurationFault 的实现描述：配置故障 - 新增专门的配置异常类
 * @Date 2026年01月28日 14:27
 * @Version 1.0.0
 */

public class ConfigurationFault extends DescriptiveFault {

    public ConfigurationFault(ErrorCode errorCode, String message, Map<String, Object> attrs) {
        super(errorCode, FaultCategory.CONFIGURATION, message, attrs);
    }

    @Override
    public ConfigurationFault withAttribute(String key, Object value) {
        Map<String, Object> next = new HashMap<>(attributes);
        next.put(key, value);
        return new ConfigurationFault(getErrorCode(), getMessage(), next);
    }

}
