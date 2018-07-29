package com.xh;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.xh.xml.FindById;

public class Main extends AnAction {
    private ActionParas paras;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        paras=new ActionParas(e);
        new FindById(paras);
    }
}
