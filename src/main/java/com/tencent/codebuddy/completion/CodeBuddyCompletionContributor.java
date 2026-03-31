package com.tencent.codebuddy.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

public class CodeBuddyCompletionContributor extends CompletionContributor {

    public CodeBuddyCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(PsiElement.class),
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet result) {
                        
                        // Add CodeBuddy-specific suggestions
                        result.addElement(LookupElementBuilder.create("codebuddy_analyze")
                                .withPresentableText("codebuddy_analyze")
                                .withTypeText("CodeBuddy")
                                .withTailText(" - Analyze current code")
                                .withIcon(null));
                        
                        result.addElement(LookupElementBuilder.create("codebuddy_fix")
                                .withPresentableText("codebuddy_fix")
                                .withTypeText("CodeBuddy")
                                .withTailText(" - Get AI suggestions")
                                .withIcon(null));
                        
                        result.addElement(LookupElementBuilder.create("codebuddy_refactor")
                                .withPresentableText("codebuddy_refactor")
                                .withTypeText("CodeBuddy")
                                .withTailText(" - Refactor code")
                                .withIcon(null));
                        
                        result.addElement(LookupElementBuilder.create("codebuddy_explain")
                                .withPresentableText("codebuddy_explain")
                                .withTypeText("CodeBuddy")
                                .withTailText(" - Explain code logic")
                                .withIcon(null));
                    }
                });
    }
}