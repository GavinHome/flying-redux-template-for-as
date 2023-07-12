package com.gavinhome.flutter.flyingreduxtemplate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsSafe;

import java.util.Objects;

public class GeneratePageAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        var project = e.getData(PlatformDataKeys.PROJECT);
        var psiPath = Objects.requireNonNull(e.getData(PlatformDataKeys.PSI_ELEMENT)).toString();
        var destPath = psiPath.substring(psiPath.indexOf(":") + 1);
        // show an input dialog for user
        var input = Messages.showInputDialog(project, "Please input page name", "Page",
                null, null,
                new InputValidator() {
                    @Override
                    public boolean checkInput(@SuppressWarnings("UnstableApiUsage") @NlsSafe String inputString) {
                        return Utils.IfNotExists(inputString, destPath);
                    }

                    @Override
                    public boolean canClose(@SuppressWarnings("UnstableApiUsage") @NlsSafe String inputString) {
                        return true;
                    }
                });

        if (input != null && !input.isEmpty()) {
            Utils.GeneratePage(getClass().getClassLoader(), destPath, "%s".formatted(input));
            Messages.showInfoMessage(project, "Enjoy yourself", "Info");
//            project.getProjectFile().refresh(false, true);
        }
    }
}
