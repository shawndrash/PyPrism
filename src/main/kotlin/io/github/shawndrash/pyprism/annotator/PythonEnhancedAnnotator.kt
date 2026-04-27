package io.github.shawndrash.pyprism.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.jetbrains.python.psi.PyClass
import com.jetbrains.python.psi.PyReferenceExpression
import com.jetbrains.python.psi.impl.PyBuiltinCache
import io.github.shawndrash.pyprism.colors.Colors

/**
 * Adds finer-grained text attributes to Python identifier references that PyCharm's
 * stock highlighter leaves on the generic identifier channel.
 *
 * The annotator visits every PSI element. We narrow to [PyReferenceExpression] and
 * resolve the reference: a `Foo` whose target is a class declaration gets the
 * [Colors.CLASS_REFERENCE] attribute key applied to *its own* range, leaving any
 * surrounding qualifier or attribute untouched.
 */
class PythonEnhancedAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element !is PyReferenceExpression) return
        val resolved = element.reference.resolve() ?: return

        val attributesKey = when (resolved) {
            is PyClass -> {
                // int, set, str, list, dict, ... are PyClass instances backed by
                // builtins.pyi stubs. PyCharm's stock highlighter colours them via
                // its own `Builtin name` token; overriding here would erase that
                // distinction. A dedicated BUILTIN_CLASS_REFERENCE token is planned
                // for a later release.
                if (PyBuiltinCache.getInstance(resolved).isBuiltin(resolved)) return
                Colors.CLASS_REFERENCE
            }
            else -> return
        }

        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element)
            .textAttributes(attributesKey)
            .create()
    }
}
