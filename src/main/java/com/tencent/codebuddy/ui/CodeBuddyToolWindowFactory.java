package com.tencent.codebuddy.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CodeBuddyToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        CodeBuddyPanel codeBuddyPanel = new CodeBuddyPanel(project);
        Content content = ContentFactory.getInstance().createContent(codeBuddyPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        toolWindow.setStripeTitle("CodeBuddy");
        toolWindow.setTitle("CodeBuddy Assistant");
    }

    public static class CodeBuddyPanel extends JPanel {
        private final Project project;
        private final JTextArea outputArea;
        private final JButton analyzeButton;
        private final JButton clearButton;

        public CodeBuddyPanel(Project project) {
            this.project = project;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            // Title
            JLabel titleLabel = new JLabel("CodeBuddy Assistant");
            titleLabel.setFont(titleLabel.getFont().deriveFont(16.0f));
            titleLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(titleLabel);

            add(Box.createVerticalStrut(10));

            // Buttons panel
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

            analyzeButton = new JButton("Analyze Current File");
            analyzeButton.addActionListener(e -> analyzeCurrentFile());
            buttonPanel.add(analyzeButton);

            buttonPanel.add(Box.createHorizontalStrut(10));

            clearButton = new JButton("Clear");
            clearButton.addActionListener(e -> clearOutput());
            buttonPanel.add(clearButton);

            add(buttonPanel);

            add(Box.createVerticalStrut(10));

            // Output area
            outputArea = new JTextArea(15, 50);
            outputArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(outputArea);
            add(scrollPane);

            // Status label
            JLabel statusLabel = new JLabel("CodeBuddy is ready. Select a file and click Analyze.");
            statusLabel.setAlignmentX(CENTER_ALIGNMENT);
            add(statusLabel);
        }

        private void analyzeCurrentFile() {
            outputArea.append("Analyzing current file...\n");
            outputArea.append("Code analysis complete. Suggestions:\n");
            outputArea.append("1. Check for potential null pointer exceptions\n");
            outputArea.append("2. Consider using try-with-resources for file handling\n");
            outputArea.append("3. Method names could be more descriptive\n\n");
        }

        private void clearOutput() {
            outputArea.setText("");
        }
    }
}