package com.tencent.codebuddy.intentions;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;

public class CodeBuddyRefactorIntention implements IntentionAction {

    @NotNull
    @Override
    public String getText() {
        return "CodeBuddy: Refactor selected code";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return "CodeBuddy Refactoring";
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        return editor != null && editor.getSelectionModel().hasSelection();
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        if (!editor.getSelectionModel().hasSelection()) {
            Messages.showInfoMessage(project, "Please select some code to refactor", "CodeBuddy");
            return;
        }

        String selectedText = editor.getSelectionModel().getSelectedText();
        if (selectedText == null || selectedText.trim().isEmpty()) {
            Messages.showInfoMessage(project, "Selected text is empty", "CodeBuddy");
            return;
        }

        // Show refactoring options
        String[] options = {
            "Extract Method",
            "Extract Variable", 
            "Rename",
            "Simplify Expression",
            "Cancel"
        };

        int choice = Messages.showDialog(project,
            "Select refactoring operation:",
            "CodeBuddy Refactoring",
            options,
            0,
            Messages.getInformationIcon());

        if (choice < 0 || choice >= options.length - 1) {
            return;
        }

        String operation = options[choice];
        String result = applyRefactoring(selectedText, operation);
        
        Messages.showInfoMessage(project,
            "CodeBuddy Refactoring Suggestion:\n\n" + result,
            "Refactoring Result");
    }

    @Override
    public boolean startInWriteAction() {
        return false;
    }

    private String applyRefactoring(String code, String operation) {
        StringBuilder result = new StringBuilder();
        result.append("Operation: ").append(operation).append("\n\n");
        result.append("Original code:\n").append(code).append("\n\n");
        
        switch (operation) {
            case "Extract Method":
                result.append("Suggested refactoring:\n");
                result.append("1. Extract the selected code into a new method\n");
                result.append("2. Give the method a descriptive name\n");
                result.append("3. Consider parameters and return type\n");
                break;
                
            case "Extract Variable":
                result.append("Suggested refactoring:\n");
                result.append("1. Extract complex expression to a variable\n");
                result.append("2. Give the variable a meaningful name\n");
                result.append("3. This improves readability\n");
                break;
                
            case "Rename":
                result.append("Suggested refactoring:\n");
                result.append("1. Rename variables/methods to be more descriptive\n");
                result.append("2. Follow naming conventions\n");
                result.append("3. Use IntelliJ's built-in rename refactoring\n");
                break;
                
            case "Simplify Expression":
                result.append("Suggested refactoring:\n");
                result.append("1. Simplify complex boolean expressions\n");
                result.append("2. Remove redundant code\n");
                result.append("3. Use modern Java features if applicable\n");
                break;
        }
        
        return result.toString();
    }
}