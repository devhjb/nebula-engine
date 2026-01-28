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


import java.util.Arrays;
import java.util.Map;

/**
 * @author jabbey
 * @BelongProject nebula-engine
 * @BelongPackage io.nebulalogic.core.exception
 * @ClassName EngineErrorCode.java
 * @Description 类 EngineErrorCode 的实现描述：引擎错误码枚举 - Nebula-Engine 核心错误码定义
 * @Date 2026年01月28日 12:25
 * @Version 1.0.0
 */
public enum EngineErrorCode implements ErrorCode {
    /* --- Context 相关 --- */
    CONTEXT_FROZEN("E-C-001", "Context is frozen and cannot be modified"),
    MUTATOR_NOT_ALLOWED("E-C-003", "Write access is not allowed in this phase"),

    /* --- 数据校验相关 --- */
    TYPE_MISMATCH("E-D-001", "Data type mismatch"),
    VALUE_OUT_OF_RANGE("E-D-002", "Value is out of range"),

    /* --- 逻辑控制相关 --- */
    PHASE_ILLEGAL("E-L-001", "Illegal execution phase"),

    /* --- 条件评估相关 --- */
    CONDITION_EVAL_ERROR("E-COND-001", "Condition evaluation failed"),

    /* --- 配置相关 --- */
    CONFIGURATION_ERROR("E-CFG-001", "Configuration error"),

    /* --- 动作执行相关错误 --- */
    ACTION_EXEC_ERROR("E-ACT-001", "Action execution failed");


    private final String code;

    private final String message;

    EngineErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }

    /**
     * 根据错误码字符串查找对应的枚举值
     *
     * @param code 错误码字符串
     * @return 对应的EngineErrorCode枚举值
     * @throws ConfigurationFault 如果找不到对应的错误码
     */
    public static EngineErrorCode fromCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            throw new ConfigurationFault(
                    EngineErrorCode.CONFIGURATION_ERROR,
                    "Error code cannot be null or empty",
                    Map.of("providedCode", code)
            );
        }

        for (EngineErrorCode errorCode : values()) {
            if (errorCode.code.equals(code)) {
                return errorCode;
            }
        }

        throw new ConfigurationFault(
                EngineErrorCode.CONFIGURATION_ERROR,
                "Unknown error code: " + code,
                Map.of("providedCode", code, "availableCodes", Arrays.stream(values()).map(EngineErrorCode::code).toList())
        );
    }
}
