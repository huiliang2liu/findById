package com.xh.writer;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.xh.ActionParas;
import com.xh.utils.MessageUtil;
import com.xh.xml.FieldEntity;

import java.util.ArrayList;
import java.util.List;

public abstract class ElementWriter extends AbsWriter {
    protected List<FieldEntity> elements;

    protected ElementWriter(ActionParas paras, PsiFile file, PsiClass cls, List<FieldEntity> entities) {
        super(paras, file, cls);
        elements = entities;
    }

    @Override
    protected void writer() {
        if (elements == null || elements.size() <= 0)
            return;
        List<FieldEntity> clicks = new ArrayList<>();
        boolean bind=false;
        for (FieldEntity entity : elements) {
            if (entity.use){
                mCls.add(mParas.mFactory.createFieldFromText(field(entity.fieldName, entity.fullName), mCls));
                bind=true;
            }
            if (entity.click)
                clicks.add(entity);
        }
        if(bind)
            bind();
        if (clicks.size() > 0)
            click(clicks);

    }

    protected abstract String field(String name, String type);

    protected abstract void click(List<FieldEntity> entities);
    protected abstract void bind();
}
