package com.xh.xml;

import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.xh.ActionParas;
import com.xh.dialog.Dialog;
import com.xh.listen.ClickListen;
import com.xh.utils.LayoutUtil;
import com.xh.utils.MessageUtil;
import com.xh.writer.FindByIdWriter;
import com.xh.xml.view.ContextView;
import org.apache.http.util.TextUtils;

import java.util.List;

public class FindById implements ClickListen {
    private ActionParas paras;
    private List<FieldEntity> entities;
    private Dialog mDialog;

    public FindById(ActionParas actitonParas) {
        paras = actitonParas;
        if (TextUtils.isEmpty(paras.text)) {
            paras.text = MessageUtil.showInputDialog(paras.mProject, "请输入布局文件名：", "布局文件");
        }
        if (TextUtils.isEmpty(paras.text))
            return;
//        String[] splits=paras.text.split("R.layout.");
//        if(splits.length>1)
//            paras.text=splits[1];
        PsiFile[] psiFiles = FilenameIndex.getFilesByName(paras.mProject, paras.text + ".xml", GlobalSearchScope.allScope(paras.mProject));
        if (psiFiles == null || psiFiles.length <= 0) {
            MessageUtil.showDialog("选择的文件不存在", "错误");
            return;
        }
        entities = LayoutUtil.getIDsFromLayout(psiFiles[0]);
        if (entities == null || entities.size() <= 0)
            return;
        ContextView contextView = new ContextView(entities, this);
        mDialog = new Dialog(contextView);
    }

    @Override
    public void cancle() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    @Override
    public void confim() {
        if (mDialog != null)
            mDialog.dismiss();
        new FindByIdWriter(paras, paras.mFile, paras.cls, entities).execute();
    }
}
