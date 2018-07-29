package com.xh.writer;

import com.intellij.psi.*;
import com.xh.ActionParas;
import com.xh.xml.FieldEntity;

import java.util.List;

public class FindByIdWriter extends ElementWriter {

    public FindByIdWriter(ActionParas paras, PsiFile file, PsiClass cls, List<FieldEntity> entities) {
        super(paras, file, cls, entities);
    }

    @Override
    protected String field(String name, String type) {
        StringBuffer sb = new StringBuffer(type);
        sb.append(" ").append(name).append(";\n");
        return sb.toString();
    }

    @Override
    protected void click(List<FieldEntity> entities) {

    }

    @Override
    protected void bind() {
        StringBuffer sb = new StringBuffer("private void initView").append(mCls.getName()).append("(){\n");
        for (FieldEntity entity : elements) {
            sb.append(entity.fieldName).append("=(").append(entity.fullName).append(")findViewById(R.id.").append(entity.id).append(");\n");
        }
        sb.append("}");
        mCls.add(mParas.mFactory.createMethodFromText(sb.toString(), mCls));
        if (mCls.findMethodsByName("onCreate", false).length > 0) {
            PsiMethod onCreate = mCls.findMethodsByName("onCreate", false)[0];
            if (!containsBindView(onCreate, "initView" + mCls.getName() + "();"))
                for (PsiStatement statement : onCreate.getBody().getStatements()) {
                    if ((statement.getFirstChild() instanceof PsiMethodCallExpression)) {
                        PsiReferenceExpression methodExpression = ((PsiMethodCallExpression) statement
                                .getFirstChild())
                                .getMethodExpression();

                        if (methodExpression.getText().equals("setContentView")) {
                            onCreate.getBody().addAfter(mParas.mFactory.createStatementFromText(new StringBuilder()
                                    .append("initView").append(mCls.getName()).append("();").toString(), mCls), statement);

                            break;
                        }
                    }
                }
        } else {
            StringBuilder method = new StringBuilder();
            method.append("@Override protected void onCreate(android.os.Bundle savedInstanceState) {\n");
            method.append("super.onCreate(savedInstanceState);\n");
            method.append("\t// TODO: add setContentView").append("(R.layout.").append(mParas.text).append(");\n");
            method.append("initView").append(mCls.getName()).append("();\n");
            method.append("}");
        }
    }

    private boolean containsBindView(PsiMethod method, String line) {
        PsiCodeBlock body = method.getBody();
        if (body == null) {
            return false;
        }
        PsiStatement[] statements = body.getStatements();
        for (PsiStatement psiStatement : statements) {
            String statementAsString = psiStatement.getText();
            if (((psiStatement instanceof PsiExpressionStatement)) && (statementAsString.contains(line))) {
                return true;
            }
        }
        return false;
    }
}
