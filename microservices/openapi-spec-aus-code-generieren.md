# OpenAPI-Spec aus Code generieren

Uebersicht der wichtigsten Tools pro Sprache mit minimalen Code-Beispielen.

---

## Go

### swaggo/swag (Kommentar-basiert)

Installation:

```bash
go install github.com/swaggo/swag/cmd/swag@latest
go get github.com/swaggo/gin-swagger
go get github.com/swaggo/files
```

Handler mit Annotations:

```go
// @Summary      Get user by ID
// @Description  Returns a single user
// @Tags         users
// @Param        id   path      int  true  "User ID"
// @Success      200  {object}  User
// @Failure      404  {object}  ErrorResponse
// @Router       /users/{id} [get]
func GetUser(c *gin.Context) {
    // ...
}

type User struct {
    ID    int    `json:"id" example:"1"`
    Name  string `json:"name" example:"Jochen"`
    Email string `json:"email" example:"j@example.com"`
}
```

Generieren:

```bash
swag init -g main.go -o ./docs
# erzeugt docs/swagger.json + docs/swagger.yaml
```

### Huma (native, ohne Annotations)

```go
package main

import (
    "context"
    "github.com/danielgtaylor/huma/v2"
    "github.com/danielgtaylor/huma/v2/adapters/humachi"
    "github.com/go-chi/chi/v5"
)

type GreetingInput struct {
    Name string `path:"name" maxLength:"30" example:"world"`
}

type GreetingOutput struct {
    Body struct {
        Message string `json:"message"`
    }
}

func main() {
    router := chi.NewMux()
    api := huma.NewAPI("My API", "1.0.0", humachi.New(router, huma.DefaultConfig("My API", "1.0.0")))

    huma.Register(api, huma.Operation{
        OperationID: "get-greeting",
        Method:      "GET",
        Path:        "/greeting/{name}",
        Summary:     "Get a greeting",
    }, func(ctx context.Context, input *GreetingInput) (*GreetingOutput, error) {
        resp := &GreetingOutput{}
        resp.Body.Message = "Hello, " + input.Name
        return resp, nil
    })
}
// OpenAPI verfuegbar unter /openapi.json
```

---

## Python

### FastAPI (nativ, Gold-Standard)

```bash
pip install fastapi uvicorn
```

```python
from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI(title="My API", version="1.0.0")

class User(BaseModel):
    id: int
    name: str
    email: str

class UserCreate(BaseModel):
    name: str
    email: str

@app.get("/users/{user_id}", response_model=User, tags=["users"])
def get_user(user_id: int):
    """Returns a single user by ID."""
    return {"id": user_id, "name": "Jochen", "email": "j@example.com"}

@app.post("/users", response_model=User, status_code=201, tags=["users"])
def create_user(user: UserCreate):
    return {"id": 1, **user.dict()}
```

Spec abrufen:

```bash
uvicorn main:app
# Swagger UI:  http://localhost:8000/docs
# ReDoc:       http://localhost:8000/redoc
# JSON-Spec:   http://localhost:8000/openapi.json
```

Spec als Datei exportieren:

```python
import json
from main import app

with open("openapi.json", "w") as f:
    json.dump(app.openapi(), f, indent=2)
```

### Django REST Framework

```bash
pip install drf-spectacular
```

`settings.py`:

```python
INSTALLED_APPS = [..., "drf_spectacular"]
REST_FRAMEWORK = {
    "DEFAULT_SCHEMA_CLASS": "drf_spectacular.openapi.AutoSchema",
}
SPECTACULAR_SETTINGS = {"TITLE": "My API", "VERSION": "1.0.0"}
```

```bash
python manage.py spectacular --file schema.yaml
```

---

## TypeScript / Node.js

### NestJS

```bash
npm install @nestjs/swagger
```

```typescript
// main.ts
import { NestFactory } from '@nestjs/core';
import { SwaggerModule, DocumentBuilder } from '@nestjs/swagger';
import { AppModule } from './app.module';

async function bootstrap() {
  const app = await NestFactory.create(AppModule);
  const config = new DocumentBuilder()
    .setTitle('My API').setVersion('1.0').build();
  const document = SwaggerModule.createDocument(app, config);
  SwaggerModule.setup('docs', app, document);
  await app.listen(3000);
}
bootstrap();
```

```typescript
// user.controller.ts
import { Controller, Get, Param } from '@nestjs/common';
import { ApiTags, ApiOperation, ApiResponse } from '@nestjs/swagger';
import { ApiProperty } from '@nestjs/swagger';

export class UserDto {
  @ApiProperty({ example: 1 }) id: number;
  @ApiProperty({ example: 'Jochen' }) name: string;
}

@ApiTags('users')
@Controller('users')
export class UserController {
  @Get(':id')
  @ApiOperation({ summary: 'Get user by ID' })
  @ApiResponse({ status: 200, type: UserDto })
  findOne(@Param('id') id: string): UserDto {
    return { id: +id, name: 'Jochen' };
  }
}
```

UI unter `http://localhost:3000/docs`, JSON unter `/docs-json`.

### Zod + Hono (modern, typsicher)

```bash
npm install hono @hono/zod-openapi zod
```

```typescript
import { OpenAPIHono, createRoute, z } from '@hono/zod-openapi';

const UserSchema = z.object({
  id: z.number().openapi({ example: 1 }),
  name: z.string().openapi({ example: 'Jochen' }),
}).openapi('User');

const route = createRoute({
  method: 'get',
  path: '/users/{id}',
  request: { params: z.object({ id: z.string() }) },
  responses: {
    200: { content: { 'application/json': { schema: UserSchema } }, description: 'OK' },
  },
});

const app = new OpenAPIHono();
app.openapi(route, (c) => c.json({ id: 1, name: 'Jochen' }));
app.doc('/openapi.json', { openapi: '3.0.0', info: { title: 'API', version: '1.0' } });
```

---

## Java / Spring Boot

### springdoc-openapi

`pom.xml`:

```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.5.0</version>
</dependency>
```

```java
@RestController
@RequestMapping("/users")
@Tag(name = "users")
public class UserController {

    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "Found")
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return new User(id, "Jochen", "j@example.com");
    }
}

@Schema(description = "User entity")
public record User(
    @Schema(example = "1") Long id,
    @Schema(example = "Jochen") String name,
    @Schema(example = "j@example.com") String email
) {}
```

Endpunkte:
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- JSON: `http://localhost:8080/v3/api-docs`
- YAML: `http://localhost:8080/v3/api-docs.yaml`

---

## Rust

### utoipa (mit Axum)

`Cargo.toml`:

```toml
[dependencies]
utoipa = { version = "4", features = ["axum_extras"] }
utoipa-swagger-ui = { version = "7", features = ["axum"] }
axum = "0.7"
serde = { version = "1", features = ["derive"] }
```

```rust
use axum::{routing::get, Json, Router};
use serde::Serialize;
use utoipa::{OpenApi, ToSchema};
use utoipa_swagger_ui::SwaggerUi;

#[derive(Serialize, ToSchema)]
struct User {
    #[schema(example = 1)]
    id: u64,
    #[schema(example = "Jochen")]
    name: String,
}

#[utoipa::path(
    get,
    path = "/users/{id}",
    params(("id" = u64, Path, description = "User ID")),
    responses((status = 200, body = User))
)]
async fn get_user() -> Json<User> {
    Json(User { id: 1, name: "Jochen".into() })
}

#[derive(OpenApi)]
#[openapi(paths(get_user), components(schemas(User)))]
struct ApiDoc;

#[tokio::main]
async fn main() {
    let app = Router::new()
        .route("/users/:id", get(get_user))
        .merge(SwaggerUi::new("/swagger-ui").url("/openapi.json", ApiDoc::openapi()));
    // ...
}
```

---

## C# / .NET

### Swashbuckle (ASP.NET Core)

```bash
dotnet add package Swashbuckle.AspNetCore
```

```csharp
// Program.cs
var builder = WebApplication.CreateBuilder(args);
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
    c.SwaggerDoc("v1", new() { Title = "My API", Version = "v1" }));

var app = builder.Build();
app.UseSwagger();
app.UseSwaggerUI();

app.MapGet("/users/{id}", (int id) => new User(id, "Jochen"))
   .WithTags("users")
   .WithOpenApi();

app.Run();

public record User(int Id, string Name);
```

UI: `/swagger`, JSON: `/swagger/v1/swagger.json`

---

## PHP

### swagger-php (Annotations / Attributes)

```bash
composer require zircote/swagger-php
```

```php
use OpenApi\Attributes as OA;

#[OA\Info(version: '1.0.0', title: 'My API')]
class OpenApiSpec {}

class UserController
{
    #[OA\Get(
        path: '/users/{id}',
        tags: ['users'],
        parameters: [new OA\Parameter(name: 'id', in: 'path', required: true)],
        responses: [new OA\Response(response: 200, description: 'OK',
            content: new OA\JsonContent(ref: '#/components/schemas/User'))]
    )]
    public function getUser(int $id) { /* ... */ }
}

#[OA\Schema(schema: 'User')]
class User {
    #[OA\Property(example: 1)] public int $id;
    #[OA\Property(example: 'Jochen')] public string $name;
}
```

Generieren:

```bash
./vendor/bin/openapi src -o openapi.yaml
```

---

## Workflow-Tipps

**Spec versionieren:** Generierte `openapi.json` / `openapi.yaml` in Git checken — Diffs zeigen Breaking Changes sofort.

**CI-Check gegen Breaking Changes:**

```bash
# oasdiff vergleicht zwei Specs
docker run --rm -v $PWD:/specs tufin/oasdiff breaking \
  /specs/openapi-main.yaml /specs/openapi-pr.yaml
```

**Client-SDKs aus der Spec generieren:**

```bash
# OpenAPI Generator (Java-basiert, viele Sprachen)
docker run --rm -v $PWD:/local openapitools/openapi-generator-cli generate \
  -i /local/openapi.yaml -g typescript-axios -o /local/client-ts
```

**Lint:**

```bash
npx @stoplight/spectral-cli lint openapi.yaml
```

**Faustregel:** Tools, die aus Typen/Schemas generieren (FastAPI, NestJS, utoipa, Huma), liefern bessere Specs als kommentar-basierte (swaggo, swagger-php), weil sie nicht aus der Synchronitaet laufen koennen.
