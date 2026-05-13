# PyPrism Changelog

## [Unreleased]

## [0.1.2]

### Added

- Out-of-the-box default colour for `PYTHON_ENHANCED_CLASS_REFERENCE`: teal
  `#0E7490` on light schemes (Default / Classic Light / IntelliJ Light) and
  `#5EEAD4` on dark schemes (Darcula / Dark / High contrast). Previously the
  token fell back to `CLASS_NAME`, which on Classic Light produced no visible
  difference from the stock class name token, leaving fresh installs looking
  like the plugin did nothing. Users can still override these in
  `Settings → Editor → Color Scheme → Python`.

## [0.1.1]

### Fixed

- Built-in classes (`int`, `set`, `str`, `list`, `dict`, ...) are no longer
  recoloured. Their references resolve to `PyClass` stubs in `builtins.pyi`,
  which previously caused the annotator to override PyCharm's stock
  `Builtin name` highlighting and made them visually indistinguishable from
  user-defined classes. Detection uses `PyBuiltinCache.isBuiltin`. A
  dedicated `BUILTIN_CLASS_REFERENCE` token is planned for a later release.

## [0.1.0]

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
