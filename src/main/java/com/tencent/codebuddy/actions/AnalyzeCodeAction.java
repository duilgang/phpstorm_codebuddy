package com.tencent.codebuddy.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class AnalyzeCodeAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            Messages.showInfoMessage("Please open a file to analyze.", "CodeBuddy");
            return;
        }

        Document document = editor.getDocument();
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
        if (virtualFile == null) {
            return;
        }

        PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
        if (psiFile == null) {
            return;
        }

        String fileName = virtualFile.getName();
        String fileContent = document.getText();
        
        // Simulate AI analysis
        String analysisResult = analyzeCode(fileName, fileContent);
        
        // Show analysis result
        Messages.showInfoMessage(project, 
            "CodeBuddy Analysis Result for " + fileName + ":\n\n" + analysisResult, 
            "CodeBuddy Analysis");
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabledAndVisible(e.getProject() != null);
    }

    private String analyzeCode(String fileName, String content) {
        StringBuilder result = new StringBuilder();
        result.append("File: ").append(fileName).append("\n");
        result.append("Total lines: ").append(content.split("\n").length).append("\n\n");
        
        // Simple analysis logic
        result.append("Issues Found:\n");
        
        if (content.contains("System.out.println")) {
            result.append("• Consider using logger instead of System.out.println\n");
        }
        
        if (content.contains("new ArrayList()")) {
            result.append("• Specify initial capacity for ArrayList if possible\n");
        }
        
        if (content.contains("catch (Exception e)")) {
            result.append("• Avoid catching generic Exception, catch specific exceptions\n");
        }
        
        if (content.contains("if (x != null)")) {
            result.append("• Consider using Optional or null-safe methods\n");
        }
        
        result.append("\nSuggestions:\n");
        result.append("• Add proper documentation comments\n");
        result.append("• Consider extracting complex methods\n");
        result.append("• Check for resource leak possibilities\n");
        
        return result.toString();
    }
}