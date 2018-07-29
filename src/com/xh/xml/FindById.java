package com.xh.com.xh.xml;

import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.xh.ActitonParas;
import com.xh.utils.LayoutUtil;
import com.xh.utils.MessageUtil;
import org.apache.http.util.TextUtils;

import java.util.List;

public class FindById {
    private ActitonParas paras;
    private List<FieldEntity> entities;

    public FindById(ActitonParas actitonParas) {
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
        if(psiFiles==null||psiFiles.length<=0){
            MessageUtil.showDialog("选择的文件不存在","错误");
            return;
        }
        entities= LayoutUtil.getIDsFromLayout(psiFiles[0]);
        if(entities==null||entities.size()<=0)
            return;
    }
}
