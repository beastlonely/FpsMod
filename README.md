# FPS Culler — Fabric 1.21.11

Mod visual e somente do lado do cliente para reduzir itens renderizados.

## Opções (tecla F8)

- **Mob dentro do spawner:** oculta apenas a criatura animada dentro da gaiola. Não afeta mobs reais.
- **Partículas:** impede a criação de novas partículas enquanto estiver ligado.
- **Itens dropados:** oculta os itens no chão.
- **Escala dos drops:** ajusta de `0.05` a `1.00`; `0.05` é minúsculo.

As configurações são salvas em `.minecraft/config/fpsculler.json`.

## Compilar

É necessário JDK 21.

```powershell
.\gradlew.bat build
```

O jar fica em `build/libs/`. Instale-o na pasta `mods` de um perfil Fabric 1.21.11, junto com Fabric API e Fabric Language Kotlin.
