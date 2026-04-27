# PyPrism Changelog

## [Unreleased]

### Added

- Initial project scaffolding based on `intellij-platform-plugin-template`.
- Target IDE: PyCharm Community/Professional 2024.3+.
- `PythonEnhancedAnnotator` highlights Python class **references** (e.g. `Foo`
  in `Foo.BAR` or `x = Foo`) using a dedicated `PYTHON_ENHANCED_CLASS_REFERENCE`
  text attribute key, falling back to `DefaultLanguageHighlighterColors.CLASS_NAME`
  when a colour scheme has not customised it.
- Color Settings page exposed under `Settings → Editor → Color Scheme → Python`
  with a live demo snippet.
- Annotator test suite covering bare reference, qualified reference, undefined
  symbol, imported class, and string literal cases.
