package com.tencent.codebuddy.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class QuickFixAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            Messages.showInfoMessage("Please open a file to apply quick fix.", "CodeBuddy");
            return;
        }

        Document document = editor.getDocument();
        String content = document.getText();
        
        // Ask user which fix to apply
        String[] options = {"Add TODO comment", "Format code", "Add missing imports", "Cancel"};
        int choice = Messages.showDialog(project,
            "Select quick fix to apply:",
            "CodeBuddy Quick Fix",
            options,
            0,
            Messages.getInformationIcon());
        
        if (choice < 0 || choice >= options.length - 1) {
            return;
        }
        
        switch (choice) {
            case 0:
                applyTodoComment(project, document, editor);
                break;
            case 1:
                formatCode(project, document, editor);
                break;
            case 2:
                addMissingImports(project, document, editor);
                break;
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(e.getProject() != null);
    }

    private void applyTodoComment(Project project, Document document, Editor editor) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            int caretOffset = editor.getCaretModel().getOffset();
            int lineNumber = document.getLineNumber(caretOffset);
            int lineStartOffset = document.getLineStartOffset(lineNumber);
            
            document.insertString(lineStartOffset, "// TODO: CodeBuddy - Add implementation\n");
            Messages.showInfoMessage(project, "TODO comment added at line " + (lineNumber + 1), "CodeBuddy");
        });
    }

    private void formatCode(Project project, Document document, Editor editor) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            String content = document.getText();
            
            // Simple formatting - trim trailing spaces
            String[] lines = content.split("\n");
            StringBuilder formatted = new StringBuilder();
            for (String line : lines) {
                formatted.append(line.trim()).append("\n");
            }
            
            document.setText(formatted.toString());
            Messages.showInfoMessage(project, "Code formatted (trailing spaces removed)", "CodeBuddy");
        });
    }

    private void addMissingImports(Project project, Document document, Editor editor) {
        WriteCommandAction.runWriteCommandAction(project, () -> {
            String content = document.getText();
            
            // Check if we need to add imports
            if (content.contains("List<") || content.contains("ArrayList<")) {
                if (!content.contains("import java.util.List") && !content.contains("import java.util.ArrayList")) {
                    // Find first non-package/import line
                    String[] lines = content.split("\n");
                    int insertLine = 0;
                    for (int i = 0; i < lines.length; i++) {
                        String line = lines[i].trim();
                        if (line.startsWith("package ") || line.startsWith("import ")) {
                            insertLine = i + 1;
                        } else if (!line.isEmpty() && !line.startsWith("//")) {
                            break;
                        }
                    }
                    
                    int insertOffset = document.getLineStartOffset(insertLine);
                    String imports = "import java.util.List;\nimport java.util.ArrayList;\n\n";
                    document.insertString(insertOffset, imports);
                    Messages.showInfoMessage(project, "Added List and ArrayList imports", "CodeBuddy");
                } else {
                    Messages.showInfoMessage(project, "Required imports are already present", "CodeBuddy");
                }
            } else {
                Messages.showInfoMessage(project, "No missing imports detected for List/ArrayList", "CodeBuddy");
            }
        });
    }
}