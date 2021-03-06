/*
    Copyright 2016-2017 Will Winder

    This file is part of Universal Gcode Sender (UGS).

    UGS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UGS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UGS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.willwinder.universalgcodesender.utils;

import com.willwinder.universalgcodesender.i18n.Localization;
import javax.swing.JOptionPane;

/**
 *
 * @author wwinder
 */
public class GUIHelpers {
    // Set with reflection in unit tests.
    private static boolean unitTestMode = false;

    public static void invokeLater(Runnable r) {
        if (unitTestMode) {
            r.run();
        } else {
            java.awt.EventQueue.invokeLater(r);
        }
    }

    /**
     * Displays an error message to the user which will not block the current thread.
     * @param errorMessage message to display in the dialog.
     */
    public static void displayErrorDialog(final String errorMessage) {
        displayErrorDialog(errorMessage, false);
    }

    /**
     * Displays an error message to the user.
     * @param errorMessage message to display in the dialog.
     * @param modal toggle whether the message should block or fire and forget.
     */
    public static void displayErrorDialog(final String errorMessage, boolean modal) {
        Runnable r = () -> {
              //JOptionPane.showMessageDialog(new JFrame(), errorMessage, 
              //        Localization.getString("error"), JOptionPane.ERROR_MESSAGE);
              NarrowOptionPane.showNarrowDialog(250, errorMessage.replaceAll("\\.\\.", "\\."),
                      Localization.getString("error"),
                      JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
        };

        if (modal) {
            r.run();
        } else {
          java.awt.EventQueue.invokeLater(r);
        }
    }

    public static void displayHelpDialog(final String helpMessage) {
        java.awt.EventQueue.invokeLater(() -> {
            NarrowOptionPane.showNarrowConfirmDialog(250, helpMessage, Localization.getString("help"),
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog(new JFrame(), helpMessage, 
            //        Localization.getString("help"), JOptionPane.INFORMATION_MESSAGE);
        });
    }
}
