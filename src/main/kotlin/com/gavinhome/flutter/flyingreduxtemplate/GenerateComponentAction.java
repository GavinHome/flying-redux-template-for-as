package com.gavinhome.flutter.flyingreduxtemplate;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsSafe;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.InputStreamReader;

//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;

public class GenerateComponentAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        var project = e.getData(PlatformDataKeys.PROJECT);
        var psiPath = e.getData(PlatformDataKeys.PSI_ELEMENT).toString();
        var destPath = psiPath.substring(psiPath.indexOf(":") + 1);
        // show an input dialog for user
        var input = Messages.showInputDialog(project, "Please input component name", "Component",
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
            var config = getConfigJson();
            Utils.GenerateComponent(getClass().getClassLoader(), destPath, "%s".formatted(input), Utils.getJSONArray(config.getAsString("templates")));
            project.getProjectFile().refresh(false, true);
            Messages.showInfoMessage(project, "Enjoy yourself", "Info");
        }
    }

    private JSONObject getConfigJson() {
        try {
            var inputStream = getClass().getClassLoader().getResourceAsStream("config.json");
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream));
            return jsonObject;
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new JSONObject();
    }
}
