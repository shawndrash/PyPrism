package io.github.shawndrash.pyprism.colors

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey

/**
 * Text attribute keys exposed by PyPrism. Each key has a stable external ID — once
 * published, the ID must not change or users' customised colour schemes will silently
 * stop applying.
 *
 * Every key falls back to a sensible [DefaultLanguageHighlighterColors] entry so that
 * unconfigured colour schemes (Catppuccin, etc.) keep rendering reasonably without
 * any theme-specific work.
 */
object Colors {
    val CLASS_REFERENCE: TextAttributesKey = TextAttributesKey.createTextAttributesKey(
        "PYTHON_ENHANCED_CLASS_REFERENCE",
        DefaultLanguageHighlighterColors.CLASS_NAME,
    )
}
