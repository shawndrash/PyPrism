package io.github.shawndrash.pyprism.annotator

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import com.jetbrains.python.psi.PyClass
import com.jetbrains.python.psi.PyReferenceExpression
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
            is PyClass -> Colors.CLASS_REFERENCE
            else -> return
        }

        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .range(element)
            .textAttributes(attributesKey)
            .create()
    }
}
