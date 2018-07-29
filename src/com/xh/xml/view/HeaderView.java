package com.xh.xml.view;

import com.xh.listen.CheckAllListen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class HeaderView extends JPanel {
    CheckAllListen mCheckAllListen;
    protected JCheckBox mAllCheck;
    protected JLabel mType;
    protected JLabel mID;
    protected JLabel mEvent;
    protected JLabel mName;



    public HeaderView() {
        this.mAllCheck = new JCheckBox();
        this.mAllCheck.setPreferredSize(new Dimension(40, 26));
        this.mAllCheck.setSelected(false);
        this.mAllCheck.addItemListener(new AllCheckListener());

        this.mType = new JLabel("Type");
        this.mType.setPreferredSize(new Dimension(100, 26));
        this.mType.setFont(new Font(this.mType.getFont().getFontName(), 1, this.mType.getFont().getSize()));

        this.mID = new JLabel("Id");
        this.mID.setPreferredSize(new Dimension(100, 26));
        this.mID.setFont(new Font(this.mID.getFont().getFontName(), 1, this.mID.getFont().getSize()));

        this.mEvent = new JLabel("OnClick");
        this.mEvent.setPreferredSize(new Dimension(100, 26));
        this.mEvent.setFont(new Font(this.mEvent.getFont().getFontName(), 1, this.mEvent.getFont().getSize()));

        this.mName = new JLabel("Name");
        this.mName.setPreferredSize(new Dimension(100, 26));
        this.mName.setFont(new Font(this.mName.getFont().getFontName(), 1, this.mName.getFont().getSize()));

        setLayout(new BoxLayout(this, 2));
        add(Box.createRigidArea(new Dimension(1, 0)));
        add(this.mAllCheck);
        add(Box.createRigidArea(new Dimension(11, 0)));
        add(this.mType);
        add(Box.createRigidArea(new Dimension(12, 0)));
        add(this.mID);
        add(Box.createRigidArea(new Dimension(12, 0)));
        add(this.mEvent);
        add(Box.createRigidArea(new Dimension(22, 0)));
        add(this.mName);
        add(Box.createHorizontalGlue());
    }

    public JCheckBox getAllCheck() {
        return this.mAllCheck;
    }

    private class AllCheckListener implements ItemListener {
        private AllCheckListener() {
        }

        public void itemStateChanged(ItemEvent itemEvent) {
            mCheckAllListen.checkAll(itemEvent.getStateChange()==1);
        }
    }
}
