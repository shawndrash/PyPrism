package io.github.shawndrash.pyprism.annotator

import com.intellij.testFramework.fixtures.BasePlatformTestCase
import io.github.shawndrash.pyprism.colors.Colors

class PythonEnhancedAnnotatorTest : BasePlatformTestCase() {

    private fun classReferenceTexts(text: String): List<String> {
        myFixture.configureByText("test.py", text)
        return myFixture.doHighlighting()
            .asSequence()
            .filter { it.forcedTextAttributesKey == Colors.CLASS_REFERENCE }
            .sortedBy { it.startOffset }
            .map { text.substring(it.startOffset, it.endOffset) }
            .toList()
    }

    fun testBareClassReferenceIsHighlighted() {
        val source = """
            class Foo: pass
            x = Foo
        """.trimIndent()

        assertEquals(listOf("Foo"), classReferenceTexts(source))
    }

    fun testQualifiedReferenceOnlyHighlightsClassPart() {
        val source = """
            class Foo:
                BAR = 1
            x = Foo.BAR
        """.trimIndent()

        // Only `Foo` is annotated; `.BAR` resolves to a non-class target so falls through
        // to a future v0.3.0 class-attribute pass.
        assertEquals(listOf("Foo"), classReferenceTexts(source))
    }

    fun testUndefinedSymbolIsNotHighlighted() {
        val source = "x = UndefinedClass"

        assertEquals(emptyList<String>(), classReferenceTexts(source))
    }

    fun testImportedClassIsHighlighted() {
        myFixture.addFileToProject("other.py", "class Foo: pass")
        val source = """
            from other import Foo
            x = Foo
        """.trimIndent()

        // Both the use site and the import statement reference resolve to PyClass,
        // so both get highlighted. Pinning the exact count guards against regressions
        // where qualifiers or imports start being mis-counted.
        val highlights = classReferenceTexts(source)
        assertEquals(listOf("Foo", "Foo"), highlights)
    }

    fun testStringLiteralContainingClassNameIsNotHighlighted() {
        val source = """
            class Foo: pass
            x = "Foo"
        """.trimIndent()

        // The class is defined but never referenced; `"Foo"` is a string literal,
        // not a reference expression.
        assertEquals(emptyList<String>(), classReferenceTexts(source))
    }
}
