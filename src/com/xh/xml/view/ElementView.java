package com.xh.xml.view;

import com.xh.listen.CheckListen;
import com.xh.xml.FieldEntity;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class ElementView extends JPanel {
    CheckListen mChechListen;
//    protected ContextView mParent;
    protected FieldEntity mElement;
    protected JCheckBox mCheck;
    protected JLabel mType;
    protected JLabel mID;
    protected JCheckBox mEvent;
    protected JTextField mName;
    protected Color mNameDefaultColor;
    protected Color mNameErrorColor = new Color(8912896);

    public JCheckBox getCheck() {
        return this.mCheck;
    }

    public  void setSelected(boolean selected){
        mCheck.setSelected(selected);
        mElement.use=selected;
    }

    public ElementView( FieldEntity element) {
        this.mElement = element;

        this.mCheck = new JCheckBox();
        this.mCheck.setPreferredSize(new Dimension(40, 26));
            this.mCheck.setSelected(this.mElement.use);
        this.mCheck.addChangeListener(new CheckListener());

        this.mEvent = new JCheckBox();
        this.mEvent.setPreferredSize(new Dimension(100, 26));
        mEvent.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                mElement.click=mEvent.isSelected();
            }
        });

        this.mType = new JLabel(this.mElement.name);
        this.mType.setPreferredSize(new Dimension(100, 26));

        this.mID = new JLabel(this.mElement.id);
        this.mID.setPreferredSize(new Dimension(100, 26));

        this.mName = new JTextField(this.mElement.fieldName, 10);
        this.mNameDefaultColor = this.mName.getBackground();
        this.mName.setPreferredSize(new Dimension(100, 26));
        this.mName.addFocusListener(new FocusListener()
        {
            public void focusGained(FocusEvent e)
            {
            }

            public void focusLost(FocusEvent e)
            {
                syncElement();
            }
        });
        setLayout(new BoxLayout(this, 2));
        setMaximumSize(new Dimension(32767, 54));
        add(this.mCheck);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(this.mType);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(this.mID);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(this.mEvent);
        add(Box.createRigidArea(new Dimension(10, 0)));
        add(this.mName);
        add(Box.createHorizontalGlue());

        checkState();
    }

    public FieldEntity syncElement() {
        mElement.use = mCheck.isSelected();
        mElement.click = mEvent.isSelected();
        mElement.fieldName = mName.getText();

        if ( mElement.use)
            mName.setBackground(mNameDefaultColor);
        else {
            mName.setBackground(mNameErrorColor);
        }

        return this.mElement;
    }

    private void checkState() {
        mElement.use=mCheck.isSelected();
        if (mCheck.isSelected()) {
            mType.setEnabled(true);
            mID.setEnabled(true);
            mName.setEnabled(true);
        } else {
            mType.setEnabled(false);
            mID.setEnabled(false);
            mName.setEnabled(false);
        }

        if (mChechListen != null)
            mChechListen.check(mCheck.isSelected());
    }

    public class CheckListener implements ChangeListener
    {
        public CheckListener()
        {
        }

        public void stateChanged(ChangeEvent event) {
            checkState();
        }
    }
}
