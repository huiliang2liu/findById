package com.xh;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

public class ActitonParas {
    public Project mProject;//当前工程对象
    public Editor mEditor;//当前编辑器对象
    public PsiFile mFile;//当前操作文件
    public PsiClass cls;//当前操作的class文件
    public String text;//当前选中的内容
    public PsiElementFactory mFactory;//文件编辑工厂

    ActitonParas(AnActionEvent event) {
        mProject = event.getData(PlatformDataKeys.PROJECT);
        mEditor = event.getData(PlatformDataKeys.EDITOR);
        mFile = event.getData(PlatformDataKeys.PSI_FILE);
        CaretModel model = mEditor.getCaretModel();
        int offset = model.getOffset();
        PsiElement element = mFile.findElementAt(offset);
        cls = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        SelectionModel selectionModel = mEditor.getSelectionModel();
        text = selectionModel.getSelectedText();
        mFactory = JavaPsiFacade.getElementFactory(mProject);
    }
}
