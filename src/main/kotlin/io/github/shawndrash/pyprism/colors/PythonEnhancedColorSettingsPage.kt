package io.github.shawndrash.pyprism.colors

import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.ColorSettingsPage
import com.jetbrains.python.PythonFileType
import com.jetbrains.python.highlighting.PyHighlighter
import com.jetbrains.python.psi.LanguageLevel
import io.github.shawndrash.pyprism.PyPrismBundle
import javax.swing.Icon

/**
 * Surfaces PyPrism's text attribute keys in `Settings → Editor → Color Scheme → Python`,
 * grouped under a "PyPrism" section so users can recolour them without affecting the
 * stock Python categories.
 */
class PythonEnhancedColorSettingsPage : ColorSettingsPage {

    override fun getDisplayName(): String = PyPrismBundle.message("colorSettings.title")

    override fun getIcon(): Icon? = PythonFileType.INSTANCE.icon

    override fun getHighlighter(): SyntaxHighlighter = PyHighlighter(LanguageLevel.getDefault())

    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = arrayOf(
        AttributesDescriptor(PyPrismBundle.message("colorSettings.classReference"), Colors.CLASS_REFERENCE),
    )

    override fun getColorDescriptors(): Array<ColorDescriptor> = ColorDescriptor.EMPTY_ARRAY

    override fun getDemoText(): String = """
        class Greeter:
            DEFAULT_NAME = "world"

            def __init__(self, name: str = DEFAULT_NAME) -> None:
                self.name = name

            def greet(self) -> str:
                return f"Hello, {self.name}"


        greeter = <classRef>Greeter</classRef>("PyPrism")
        another = <classRef>Greeter</classRef>.DEFAULT_NAME
        print(greeter.greet())
    """.trimIndent()

    override fun getAdditionalHighlightingTagToDescriptorMap(): Map<String, TextAttributesKey> = mapOf(
        "classRef" to Colors.CLASS_REFERENCE,
    )
}
