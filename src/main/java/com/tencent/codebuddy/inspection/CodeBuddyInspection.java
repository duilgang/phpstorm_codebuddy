package com.tencent.codebuddy.inspection;

import com.intellij.codeInspection.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class CodeBuddyInspection extends AbstractBaseJavaLocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new JavaElementVisitor() {
            @Override
            public void visitMethodCallExpression(PsiMethodCallExpression expression) {
                super.visitMethodCallExpression(expression);
                
                String methodName = expression.getMethodExpression().getText();
                PsiMethod method = expression.resolveMethod();
                
                if (method != null) {
                    // Check for System.out.println
                    if (methodName.equals("println") && 
                        method.getContainingClass() != null &&
                        method.getContainingClass().getQualifiedName() != null &&
                        method.getContainingClass().getQualifiedName().equals("java.lang.System.out")) {
                        
                        holder.registerProblem(expression,
                            "Consider using a logger instead of System.out.println for production code",
                            ProblemHighlightType.WEAK_WARNING,
                            new ReplaceWithLoggerFix());
                    }
                }
            }
            
            @Override
            public void visitCatchSection(PsiCatchSection catchSection) {
                super.visitCatchSection(catchSection);
                
                PsiTypeElement caughtType = catchSection.getCatchType();
                if (caughtType != null && caughtType.getText().equals("Exception")) {
                    holder.registerProblem(catchSection,
                        "Avoid catching generic Exception. Catch specific exceptions instead.",
                        ProblemHighlightType.WEAK_WARNING);
                }
            }
            
            @Override
            public void visitNewExpression(PsiNewExpression expression) {
                super.visitNewExpression(expression);
                
                PsiType type = expression.getType();
                if (type != null && type.getCanonicalText().equals("java.util.ArrayList")) {
                    holder.registerProblem(expression,
                        "Consider specifying initial capacity for ArrayList if possible",
                        ProblemHighlightType.INFORMATION);
                }
            }
        };
    }
    
    private static class ReplaceWithLoggerFix implements LocalQuickFix {
        @NotNull
        @Override
        public String getFamilyName() {
            return "CodeBuddy Fixes";
        }
        
        @NotNull
        @Override
        public String getName() {
            return "Replace with logger";
        }
        
        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiElement element = descriptor.getPsiElement();
            if (element instanceof PsiMethodCallExpression) {
                PsiMethodCallExpression methodCall = (PsiMethodCallExpression) element;
                
                // In a real implementation, we would replace System.out.println with a logger call
                // For now, just add a comment
                PsiElement parent = methodCall.getParent();
                if (parent instanceof PsiExpressionStatement) {
                    PsiComment comment = JavaPsiFacade.getElementFactory(project)
                        .createCommentFromText("// TODO: Replace with logger - CodeBuddy", null);
                    parent.getParent().addBefore(comment, parent);
                }
            }
        }
    }
}