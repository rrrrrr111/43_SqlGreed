package ru.roman.bim.gui.pane.choose;


import org.apache.commons.lang3.time.FastDateFormat;

import javax.swing.*;
import java.io.File;
import java.util.Date;

public class FileChooser {

    private JFileChooser fc = new JFileChooser();
    private static final FastDateFormat FILE_NAME_FORMAT = FastDateFormat.getInstance("yyyy.MM.dd");

    private static int reportCounter = 1;

    public FileChooser() {
        fc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isFile() || "(?i)(.*\\.xls)$".matches(f.getName());
            }

            @Override
            public String getDescription() {
                return "Excel files (*.xls)";
            }
        });
    }

    public File showSelectFileDialog() {
        int returnVal = fc.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == returnVal) {
            return fc.getSelectedFile();
        }
        return null;
    }

    public File saveFile() {
        String str = "Report-" + reportCounter + "_" + FILE_NAME_FORMAT.format(new Date()) + ".xls";
        fc.setSelectedFile(new File(str));

        int returnVal = fc.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            reportCounter++;
            return file;
        }
        return null;
    }
}
