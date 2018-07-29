package com.xh.utils;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

public class MessageUtil {
    private static final Icon ICON =null;

    public static String showInputDialog(Project project, String message, String title) {
        return Messages.showInputDialog(project, message, title, ICON);
    }

    public static void showDialog(String message, String title) {
        Messages.showMessageDialog(message, title, ICON);
    }
}
