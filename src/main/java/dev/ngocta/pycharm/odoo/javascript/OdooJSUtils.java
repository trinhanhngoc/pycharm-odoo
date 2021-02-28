package dev.ngocta.pycharm.odoo.javascript;

import com.intellij.lang.ASTNode;
import com.intellij.lang.javascript.psi.JSCallExpression;
import com.intellij.lang.javascript.psi.JSFile;
import com.intellij.lang.javascript.psi.impl.JSCallExpressionImpl;
import com.intellij.lang.javascript.psi.impl.JSReferenceExpressionImpl;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.indexing.FileBasedIndex;
import dev.ngocta.pycharm.odoo.OdooUtils;
import dev.ngocta.pycharm.odoo.python.module.OdooModuleUtils;
import org.jetbrains.annotations.Nullable;

public class OdooJSUtils {
    private OdooJSUtils() {
    }

    public static boolean isOdooJSFile(@Nullable PsiFile file) {
        return file instanceof JSFile && OdooModuleUtils.isInOdooModule(file);
    }

    public static boolean isOdooJSModuleFile(@Nullable PsiFile file) {
        if (file == null) {
            return false;
        }
        VirtualFile virtualFile = file.getVirtualFile();
        if (virtualFile == null) {
            return false;
        }
        return !FileBasedIndex.getInstance().getFileData(OdooJSModuleIndex.NAME, virtualFile, file.getProject()).isEmpty();
    }

    public static boolean isInOdooJSModule(@Nullable PsiElement element) {
        PsiFile file = OdooUtils.getOriginalContextFile(element);
        return isOdooJSModuleFile(file);
    }

    @Nullable
    public static String getCallFunctionName(@Nullable JSCallExpression callExpression) {
        if (callExpression == null) {
            return null;
        }
        ASTNode methodExpression = JSCallExpressionImpl.getMethodExpression(callExpression.getNode());
        if (methodExpression != null) {
            return JSReferenceExpressionImpl.getReferenceName(methodExpression);
        }
        return null;
    }
}
