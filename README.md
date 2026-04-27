# PyPrism

![Build](https://github.com/shawndrash/PyPrism/workflows/Build/badge.svg)

<!-- Plugin description -->
PyPrism adds finer-grained semantic highlighting for Python in PyCharm.

PyCharm's stock color scheme cannot distinguish class references, class attribute
references, module references, and other identifiers — they all fall through to the
generic identifier channel. PyPrism resolves each reference via the PSI and assigns
dedicated text attribute keys so you can color them independently in
`Settings | Editor | Color Scheme | Python`.

Planned token categories:

- Class reference (e.g. `MyClass` in `MyClass.CONSTANT`)
- Class attribute reference
- Module reference (e.g. `redis`, `os` at a use site)
- Instance attribute
- Function reference
- Parameter
- Type annotation
- Enum member
- `self` / `cls`
- Built-in classes (distinguished from user classes)

All token colors fall back to sensible defaults via
`DefaultLanguageHighlighterColors`, so unconfigured themes keep working.

Preset color schemes (VSCode Dark+, Catppuccin Mocha) will ship as `.icls`
files alongside the plugin.
<!-- Plugin description end -->

## Status

Pre-release. Phase 0 scaffolding only — no runtime behavior yet.

## Development

Requires **JDK 21** and **IntelliJ IDEA 2024.3+** (Community or Ultimate).

```bash
./gradlew buildPlugin       # builds the plugin distribution
./gradlew runIde            # launches a sandbox IDE with the plugin loaded
./gradlew test              # runs unit tests
./gradlew verifyPlugin      # runs the IntelliJ Plugin Verifier
```

We compile against **PyCharm Community** because Python is built into the
IDE itself, providing the `com.intellij.modules.python` module without any
additional plugin dependency. The published plugin runs on any IDE that
ships that module — PyCharm Professional, PyCharm Community, or IntelliJ
IDEA with the Python plugin.

The first `runIde` invocation downloads a PyCharm Community distribution
matching the `platformVersion` in `gradle.properties`.

## License

[Apache-2.0](LICENSE)
