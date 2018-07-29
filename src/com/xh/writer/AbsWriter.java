package com.xh.writer;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.xh.ActionParas;

public abstract class AbsWriter extends WriteCommandAction.Simple {
    protected ActionParas mParas;
    protected PsiFile mFile;
    protected PsiClass mCls;
    protected AbsWriter(ActionParas paras, PsiFile file, PsiClass cls) {
        super(paras.mProject, new PsiFile[0]);
        mParas=paras;
        mFile=file;
        mCls=cls;
    }

    @Override
    protected final void run() throws Throwable {
        writer();
    }

    protected abstract void writer();
}
