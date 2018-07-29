package com.xh.xml.view;

import com.intellij.ui.components.JBScrollPane;
import com.xh.listen.CheckAllListen;
import com.xh.listen.CheckListen;
import com.xh.listen.ClickListen;
import com.xh.xml.FieldEntity;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ContextView extends JPanel implements CheckAllListen, CheckListen {

    private HeaderView headerView;
    private List<ElementView> elementViews;
    private List<FieldEntity> entities;
    private ClickListen mListen;
    protected JCheckBox mPrefixCheck;
    protected JTextField mPrefixValue;
    protected JLabel mPrefixLabel;
    protected JCheckBox mHolderCheck;
    protected JCheckBox msplitOnclickMethodsCheck;
    protected JLabel mHolderLabel;
    protected JButton mConfirm;
    protected JButton mCancel;
    protected boolean mCreateHolder = false;
    private ClickListen listen;
    public ContextView(List<FieldEntity> entities, ClickListen listen){
        mListen=listen;
        this.entities=entities;
        this.listen=listen;
        setLayout(new BoxLayout(this, 3));
        elementViews=new ArrayList<>();
        addInjections();
        addButtons();
    }
    protected void addInjections() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, 3));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        headerView = new HeaderView();
        headerView.mCheckAllListen=this;
        contentPanel.add(this.headerView);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel injectionsPanel = new JPanel();
        injectionsPanel.setLayout(new BoxLayout(injectionsPanel, 3));
        injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        int cnt = 0;
        boolean selectAllCheck = true;
        for (FieldEntity element : entities) {
            ElementView entry = new ElementView(element);
            entry.mChechListen=this;
            if (cnt > 0) {
                injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            injectionsPanel.add(entry);
            cnt++;

            elementViews.add(entry);

            selectAllCheck &= entry.getCheck().isSelected();
        }
        headerView.getAllCheck().setSelected(selectAllCheck);
//        this.mEntryHeader.setAllListener(this.allCheckListener);
        injectionsPanel.add(Box.createVerticalGlue());
        injectionsPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JBScrollPane scrollPane = new JBScrollPane(injectionsPanel);
        contentPanel.add(scrollPane);

        add(contentPanel, "Center");
        refresh();
    }
    protected void addButtons() {
        this.mHolderCheck = new JCheckBox();
        this.mHolderCheck.setPreferredSize(new Dimension(32, 26));
        this.mHolderCheck.setSelected(this.mCreateHolder);
//        this.mHolderCheck.addChangeListener(new CheckHolderListener());

        this.mHolderLabel = new JLabel();
        this.mHolderLabel.setText("Create ViewHolder");

        JPanel holderPanel = new JPanel();
        holderPanel.setLayout(new BoxLayout(holderPanel, 2));
        holderPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        holderPanel.add(this.mHolderCheck);
        holderPanel.add(this.mHolderLabel);
        holderPanel.add(Box.createHorizontalGlue());
        add(holderPanel, "Last");

        this.msplitOnclickMethodsCheck = new JCheckBox();
        this.msplitOnclickMethodsCheck.setPreferredSize(new Dimension(32, 26));
        this.msplitOnclickMethodsCheck.setSelected(false);

        JLabel independentOnclickMethodsLabel = new JLabel();
        independentOnclickMethodsLabel.setText("Split OnClick methods");

        JPanel splitOnclickMethodsPanel = new JPanel();
        splitOnclickMethodsPanel.setLayout(new BoxLayout(splitOnclickMethodsPanel, 2));
        splitOnclickMethodsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        splitOnclickMethodsPanel.add(this.msplitOnclickMethodsCheck);
        splitOnclickMethodsPanel.add(independentOnclickMethodsLabel);
        splitOnclickMethodsPanel.add(Box.createHorizontalGlue());
        add(splitOnclickMethodsPanel, "Last");

        this.mCancel = new JButton();
//        this.mCancel.setAction(new CancelAction());
        this.mCancel.setPreferredSize(new Dimension(120, 26));
        this.mCancel.setText("Cancel");
        this.mCancel.setVisible(true);
        mCancel.addActionListener(new Cancel());
        this.mConfirm = new JButton();
//        this.mConfirm.setAction(new ConfirmAction());
        this.mConfirm.setPreferredSize(new Dimension(120, 26));
        this.mConfirm.setText("Confirm");
        this.mConfirm.setVisible(true);
        mConfirm.addActionListener(new Confim());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, 2));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(this.mCancel);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPanel.add(this.mConfirm);

        add(buttonPanel, "Last");
        refresh();
    }
    protected void refresh() {
        revalidate();

        if (this.mConfirm != null)
            this.mConfirm.setVisible(elementViews.size() > 0);
    }
    private class Confim implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            Util.showDialog("点击了确定", "操作");
            listen.confim();
        }
    }

    private class Cancel implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            Util.showDialog("点击了取消", "操作");
//            checkValidity();
            listen.cancle();

        }
    }
    @Override
    public void checkAll(boolean chekAll) {
        for (ElementView view:elementViews){
            view.mChechListen=null;
            view.setSelected(chekAll);
            view.mChechListen=this;
        }

    }

    @Override
    public void check(boolean check) {
        boolean result = true;
        for (ElementView view : elementViews) {
            result &= view.getCheck().isSelected();
        }

        headerView.mCheckAllListen=null;
        headerView.getAllCheck().setSelected(result);
        headerView.mCheckAllListen=this;
    }
}
