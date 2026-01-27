# Repository Guidelines

## Project Structure & Module Organization
This is a multi-module Maven (Java 8) backend based on RuoYi. Key modules are:
- `ruoyi-admin`: Spring Boot application entry point and REST controllers.
- `ruoyi-framework`: common configuration, security, and framework glue.
- `ruoyi-system`: core system domain, services, and MyBatis mappers.
- `ruoyi-quartz`: scheduled job support.
- `ruoyi-generator`: code generator module.
- `ruoyi-common`: shared utilities, constants, and base classes.
- `tk_custom`: project-specific extensions.

Common source layout is `src/main/java` and `src/main/resources` (including MyBatis mapper XMLs under `resources/mapper/**`). SQL scripts live in `sql/`, and operational docs live in `doc/`.

## Build, Test, and Development Commands
Use Maven from the repo root:
- `mvn -pl ruoyi-admin -am clean package`: build the runnable JAR and dependencies.
- `mvn -pl ruoyi-admin spring-boot:run`: run the app locally from source.
- `mvn test`: run unit tests (if present).

On Windows, `ry.bat` can start/stop the packaged `ruoyi-admin.jar` after building.

## Coding Style & Naming Conventions
- Java: 4-space indentation, no tabs; keep line length reasonable.
- Packages follow `com.ruoyi...`; classes use `PascalCase`, methods/fields `camelCase`.
- Database and mapper XMLs use `snake_case` column names to match existing schemas.
- Prefer updating existing utilities in `ruoyi-common` rather than duplicating helpers.

## Testing Guidelines
No dedicated test module was found in the repository. If you add tests, use standard Maven/Java conventions:
- Place tests under `src/test/java` in the relevant module.
- Name tests `*Test` and keep them focused on one class or service.
- Run `mvn test` before submitting changes.

## Commit & Pull Request Guidelines
Recent history follows a light Conventional Commits style, e.g., `feat: ...`, `fix: ...`, `build: ...`, and occasional `update ...`. Match the existing pattern and keep messages short.

Pull requests should include:
- A concise description of the change and affected modules.
- Links to related issues/tasks (if any).
- Notes on configuration or SQL changes (especially anything in `sql/` or `resources/mapper/**`).

## Configuration Tips
Runtime configuration is typically in module resources (e.g., `ruoyi-admin/src/main/resources`). Avoid committing secrets; use environment-specific overrides or externalized config.