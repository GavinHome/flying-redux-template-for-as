package com.gavinhome.flutter.flyingreduxtemplate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsSafe;

public class GenerateTodosAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        var project = e.getData(PlatformDataKeys.PROJECT);
        var psiPath = e.getData(PlatformDataKeys.PSI_ELEMENT).toString();
        var destPath = psiPath.substring(psiPath.indexOf(":") + 1);
        // show an input dialog for user
        var input = Messages.showInputDialog(project, "Please input example name", "Example",
                null, null,
                new InputValidator() {
                    @Override
                    public boolean checkInput(@NlsSafe String inputString) {
                        return !Utils.IfExists(inputString, destPath);
                    }

                    @Override
                    public boolean canClose(@NlsSafe String inputString) {
                        return true;
                    }
                });

        if (input != null && !input.isEmpty()) {
            Utils.GenerateTodos(getClass().getClassLoader(), destPath, "%s".formatted(input));
            Messages.showInfoMessage(project, "Enjoy yourself", "Info");
            project.getProjectFile().refresh(false, true);
        }
    }
}