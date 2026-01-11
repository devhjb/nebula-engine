# Architecture Constraints: Nebula-Engine (星云引擎)

Version: 1.0

Status: Frozen

Author: Jabbey

Date: 2026年1月11日

## 引言

本文档旨在明确 Nebula-Engine V1.0 版本的核心架构约束，确保项目在后续的详细设计与实现过程中，能够严格遵循其“轻量级、嵌入式、业务规则自动化引擎”的核心定位。这些约束是基于Architecture Brief文档中的项目定位、概念模型、运行时模型及排除范围而制定，旨在为开发团队提供明确的指导原则和“红线”。



## 约束声明

本文件定义 Nebula-Engine V1.0 的架构级硬约束，用于：

* 防止架构设计在实现与演进过程中发生语义漂移。
* 保护“轻量、嵌入式、决策导向”的核心定位。
* 为未来演进提供清晰的边界与裁决依据。

任何实现、扩展、优化、重构，均不得违反本文件中的约束。若存在冲突，应以本文件为最终裁决依据。



## 核心定位与设计哲学 (Core Positioning)

这些约束定义了引擎的“身份”，是拒绝过度设计的终极裁决标准。

- **[C-CORE-001] 嵌入式库定位 (Library Only)：** 引擎必须以纯粹的 SDK/Library 形式存在，可无缝集成到现有应用中。其生命周期（初始化、加载、执行、销毁）完全由宿主应用（Host Application）管理。
- **[C-CORE-002] 逻辑与流程分离 (Decision, not BPM)：** 严格专注于“业务决策（判定）”，禁止引入长事务、人工审批、延迟触发或分布式流程管理能力。
- **[C-CORE-003] 极简抽象 (Minimal Mental Burden)：** 核心概念模型仅限于 `Engine`, `Context`, `Rule`, `Condition`, `Action`, `Decision`, `Strategy`。任何新增概念必须通过架构委员会特批。
- **[C-CORE-004] 声明式与命令式解耦 (Declarative Logic)：** 业务逻辑必须通过结构化文本（JSON/YAML）描述。底层代码（Action 实现）应通过注册机制接入，严禁在核心引擎中硬编码业务逻辑。
- **[C-CORE-005] 禁止独立部署 (MUST NOT)：** 不得成为需要独立部署的中间件服务、不提供可视化编排界面（UI/GUI）、不提供内置高可用机制或分布式集群协调能力。
- **[C-CORE-006] 解决中间地带问题 (MUST)：** 必须解决“硬编码 vs 重型引擎（如 Drools）”之间的中间地带问题，主打极简抽象与低学习成本。



## 运行时执行约束 (Runtime Execution)

这些约束确保了决策过程的高性能、可预测性和线程安全性。

- **[C-RUNTIME-001] 同步阻塞模型 (Sync & Blocking)：** `execute()` 必须是同步且瞬时的。内部禁止创建线程池或启动异步任务。
- **[C-RUNTIME-002] 单向无环执行 (No Agenda Cycle)：** 执行流必须单向。禁止 Action 执行后反向触发新一轮匹配，严禁递归逻辑。
- **[C-RUNTIME-003] 无状态引擎 (Stateless Engine)：** 引擎实例在加载规则后不保留任何业务执行状态。所有状态必须承载于 `Context` 并在调用结束后销毁。
- **[C-RUNTIME-004] 线程安全 (Thread Safety)：** `Engine` 实例及其规则快照必须支持多线程并发无锁读取。`Context` 视为单线程对象，不由引擎负责跨线程同步。
- **[C-RUNTIME-005] 确定性结果 (Determinism)：** 相同环境下，相同输入必须产生相同输出。冲突裁决必须显式使用 `Execution Strategy`。



## 数据管理与副作用约束 (Data & Side-Effect)

明确“数据从哪来”和“副作用去哪儿”，确保逻辑纯净。

- **[C-DATA-001] Context 唯一数据源 (Single Source of Truth)：** 评估过程严禁绕过 `Context` 访问外部数据库、环境变量或全局缓存。
- **[C-DATA-002] 只读评估原则 (Read-only Evaluation)：** 在 `Condition` 判定阶段，严禁修改 `Context` 状态。数据变更只能发生在 `Action` 执行阶段。
- **[C-DATA-003] 副作用解耦 (Side-Effect Recording)：** 引擎禁止直接执行 IO（如 HTTP 请求）。Action 仅能做两件事：
    1. **Mutation:** 修改 `Context` 状态。
    2. **Intent:** 在 `Decision` 中记录副作用意图（如“发送短信”），由宿主系统最终执行。
- **[C-DATA-004] 内存安全边界 (Memory Guard)：** 必须设定单次决策加载的规则数量上限（默认 1000 条），防止规则膨胀导致的内存溢出。



## API、集成与扩展约束 (API & Extensibility)

定义了 SDK 如何与外部世界“握手”。

- **[C-EXT-001] 零外部运行时依赖 (Zero Dependencies)：** 核心 SDK 严禁依赖 Spring, Jackson, Guava 等框架。JSON 解析及日志通过接口委托给宿主。
- **[C-EXT-002] SPI 扩展机制 (Pluggable Architecture)：** `RuleLoader`, `ConditionEvaluator`, `ActionExecutor` 必须通过标准接口扩展，禁止使用反射扫描或魔法注解。
- **[C-EXT-003] 表达式语言受限 (DSL Constraints)：** V1.0 仅支持基础逻辑运算（AND/OR/NOT）与算术运算。严禁在 Condition 中调用外部 API。
- **[C-EXT-004] API 稳定性 (Backwards Compatibility)：** 核心接口一旦发布，在 V1.x 系列中严禁破坏性变更。
- **[C-EXT-005] 语言原生性 (MUST)：** 核心逻辑必须使用目标语言的标准库实现，确保极高的可移植性和最小的运行时足迹。
- **[C-EXT-006] 自定义操作符 (MUST)：** 必须支持自定义操作符（Operator）的注册机制。
- **[C-EXT-017] 禁止硬编码业务逻辑 (MUST NOT)：** 不得在核心模块中硬编码任何业务特定逻辑。



## 质量与性能约束 (Non-functional Requirements)

具体的工程硬指标。

- **[C-NFR-001] 极致延迟 (Low Latency)：** 100 条规则场景下，单次决策 P95 延迟必须 `< 5ms`。
- **[C-NFR-002] 快速启动 (Cold Start)：** 规则加载与引擎初始化时间必须 `< 2s`。
- **[C-NFR-003] 可追溯性 (Traceability)：** `Decision` 对象必须包含完整的决策轨迹：哪些规则命中了、执行了哪些 Action、执行前后事实的变化。
- **[C-NFR-004] 沙箱化安全 (Sandboxing)：** 规则解析与求值过程必须防范注入攻击，严禁执行系统级指令。
- **[C-NFR-005] 内存占用 (MUST)：** 内存占用必须 < 50MB（在默认配置，1000 条规则场景下）。
- **[C-NFR-006] 规则评估复杂度 (SHOULD)：** 规则评估复杂度应为 O(n)，n 为规则数量（V1.0 接受线性匹配）。
- **[C-NFR-007] 确定性时耗 (MUST)：** 单条规则的评估耗时必须在毫秒级以内。对于耗时较长的业务操作，应通过 Side-Effect 异步处理，而不应阻塞引擎的决策序列。



## 总结

**约束不是限制，而是为了保障自由。**

上述架构约束是 Nebula-Engine V1.0 成功的基石。它们旨在引导开发团队在实现过程中做出正确的权衡和决策，确保最终产品能够满足其核心定位和价值主张。













