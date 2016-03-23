package com.willwinder.universalgcodesender.uielements;

import com.willwinder.universalgcodesender.MainWindow;
import com.willwinder.universalgcodesender.i18n.Localization;
import com.willwinder.universalgcodesender.types.Macro;
import com.willwinder.universalgcodesender.utils.Settings;
import org.jdesktop.layout.*;

import javax.swing.*;
import javax.swing.GroupLayout;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MacroPanel extends JPanel {

    private MainWindow mainWindow;
    private Settings settings;
    private java.util.List<JButton> customGcodeButtons = new ArrayList<JButton>();
    private java.util.List<JTextField> customGcodeTextFields = new ArrayList<JTextField>();
    private java.util.List<JTextField> customGcodeNameFields = new ArrayList<JTextField>();
    private java.util.List<JTextField> customGcodeDescriptionFields = new ArrayList<JTextField>();

    public MacroPanel() {

    }

    public MacroPanel(Settings settings, MainWindow mainWindow) {
        this.settings = settings;
        this.mainWindow = mainWindow;
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    @Override
    public void doLayout() {
        initMacroButtons();
        super.doLayout();
    }

    private void initMacroButtons() {
        if (settings == null) {
            //I suppose this should be in a text field.
            System.err.println("settings is null!  Cannot init buttons!");
            return;
        }
        Integer lastMacroIndex = settings.getLastMacroIndex()+1;

        for (int i = customGcodeButtons.size(); i <= lastMacroIndex; i++) {
            JButton button = createMacroButton(i);
            JTextField textField = createMacroTextField(i);
            JTextField nameField = createMacroNameField(i);
            JTextField descriptionField = createMacroDescriptionField(i);

            Macro macro = settings.getMacro(i);
            if (macro != null) {
                textField.setText(macro.getGcode());
                if (macro.getName() != null) {
                    nameField.setText(macro.getName());
                }
                if (macro.getDescription() != null) {
                    descriptionField.setText(macro.getDescription());
                }
            }
        }

        org.jdesktop.layout.GroupLayout macroPanelLayout = new org.jdesktop.layout.GroupLayout(this);

        org.jdesktop.layout.GroupLayout.ParallelGroup parallelGroup = macroPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING);

        org.jdesktop.layout.GroupLayout.SequentialGroup sequentialGroup = macroPanelLayout.createSequentialGroup();
        parallelGroup.add(sequentialGroup);

        sequentialGroup.addContainerGap();
        org.jdesktop.layout.GroupLayout.ParallelGroup parallelGroup1 = macroPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING);
        sequentialGroup.add(parallelGroup1);

        for (int i = 0; i < customGcodeButtons.size(); i++) {
            org.jdesktop.layout.GroupLayout.SequentialGroup group = macroPanelLayout.createSequentialGroup();
            group.add(customGcodeButtons.get(i), org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 49, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                    .add(customGcodeNameFields.get(i))
                    .add(customGcodeTextFields.get(i))
                    .add(customGcodeDescriptionFields.get(i));
            parallelGroup1.add(group);
        }

        macroPanelLayout.setHorizontalGroup( parallelGroup );
        org.jdesktop.layout.GroupLayout.SequentialGroup sequentialGroup1 = macroPanelLayout.createSequentialGroup();
        macroPanelLayout.setVerticalGroup(
                macroPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(sequentialGroup1
                                        .add(8, 8, 8)
                        ));


        for (int i = 0; i < customGcodeButtons.size(); i++) {
            sequentialGroup1.add(macroPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(customGcodeButtons.get(i))
                    .add(customGcodeNameFields.get(i), org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(customGcodeTextFields.get(i), org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(customGcodeDescriptionFields.get(i), org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE));
        }

        setLayout(macroPanelLayout);
    }

    private JTextField createMacroTextField(int index) {
        JTextField textField = new MacroTextField(index, settings);

        customGcodeTextFields.add(textField);
        return textField;
    }

    private JTextField createMacroNameField(int index) {
        JTextField textField = new MacroNameField(index, settings);

        customGcodeNameFields.add(textField);
        return textField;
    }

    private JTextField createMacroDescriptionField(int index) {
        JTextField textField = new MacroDescriptionField(index, settings);

        customGcodeDescriptionFields.add(textField);
        return textField;
    }

    private JButton createMacroButton(int i) {
        JButton button = new JButton(i+"");

        button.setEnabled(false);
        this.setToolTipText(Localization.getString("macroPanel.button"));

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                customGcodeButtonActionPerformed(i);
            }
        });
        customGcodeButtons.add(button);
        return button;
    }

    private void customGcodeButtonActionPerformed(int i) {
        Macro macro = settings.getMacro(i);

        //Poor coupling here.  We should probably pull the executeCustomGcode method out into the backend.
        if (mainWindow == null) {
            System.err.println("MacroPanel not properly initialized.  Cannot execute custom gcode");
        } else {
            mainWindow.executeCustomGcode(macro.getGcode());
        }
    }

    public void updateCustomGcodeControls(boolean enabled) {
        for (JButton button : customGcodeButtons) {
            button.setEnabled(enabled);
        }
    }
}
