# Prompt for Spring Boot AI Log Solution Service

Use this prompt when adding the real Spring Boot AI integration for KOH application log analysis.

## Goal

Create a Spring Boot AI service that receives a masked Java Spring Boot error group and returns a structured troubleshooting response. The implementation must not call an LLM if a solution already exists for the same `error_group_id`.

## Required Input

```json
{
  "errorGroupId": 123,
  "fingerprint": "sha256",
  "exceptionClass": "org.example.SomeException",
  "sourceFile": "SomeService.java",
  "lineNumber": 45,
  "firstSeen": "2026-04-28T00:00:44",
  "lastSeen": "2026-04-28T00:10:00",
  "count": 12,
  "message": "Masked message",
  "stackTrace": "Masked stack trace"
}
```

## Safety Rules

Before sending data to the LLM:

- Mask IPv4 addresses as `[IP]`.
- Mask emails as `[EMAIL]`.
- Mask tokens, API keys, passwords, secrets as `[SECRET]`.
- Do not send raw log archive names if they contain user or host identifiers.
- Use `LLM_API_KEY` from environment variables.
- Store the answer in `LLM_SOLUTIONS` linked to `error_group_id`.
- If `LLM_SOLUTIONS` already contains a row for the group, return the stored answer and do not call the LLM.

## Prompt Template

```text
You are a senior Java 17 Spring Boot production support engineer.

Analyze this masked log error group and provide a concise operational answer.

Return the answer in Russian with these sections:
1. Краткая причина
2. Вероятный root cause
3. Что проверить в коде
4. Что проверить в конфигурации/данных
5. Рекомендованное исправление
6. Риск и срочность

Error group:
- Fingerprint: {fingerprint}
- Exception class: {exceptionClass}
- Source: {sourceFile}:{lineNumber}
- First seen: {firstSeen}
- Last seen: {lastSeen}
- Count: {count}

Message:
{message}

Stack trace:
{stackTrace}

Rules:
- Do not invent repository-specific facts.
- Prefer concrete checks over generic advice.
- If stack trace is insufficient, say exactly what data is missing.
- Keep the answer practical for a developer who will open the Java code next.
```

## Suggested Spring Boot AI Shape

```java
@Service
public class LlmSolutionService {

    private final ChatClient chatClient;
    private final LlmSolutionRepository repository;
    private final SensitiveDataMasker masker;

    public LlmSolutionEntity getOrCreateSolution(ErrorGroupEntity group) {
        return repository.findFirstByErrorGroupIdOrderByCreatedAtDesc(group.getId())
                .orElseGet(() -> callLlmAndSave(group));
    }
}
```

Configure `ChatClient` with a provider that reads `LLM_API_KEY` from the environment.
