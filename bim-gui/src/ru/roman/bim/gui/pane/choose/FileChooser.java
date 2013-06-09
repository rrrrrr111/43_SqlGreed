package ru.roman.bim.gui.pane.choose;


import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Date;

public class FileChooser {

    private final JFileChooser fc = new JFileChooser();
    private static final FastDateFormat FILE_NAME_FORMAT = FastDateFormat.getInstance("yyyy.MM.dd");

    private static int reportCounter = 1;

    public FileChooser(final FileChooserParams params) {
        Validate.notBlank(params.getFilesName(), "Wrong filter params, fileName is blank");
        Validate.notBlank(params.getFilesExtension(), "Wrong filter params, fileExtension is blank");

        if (params.getDialogTitle() != null) {
            fc.setDialogTitle(params.getDialogTitle());
        }

        final FileFilter filter = new FileFilter() {
            @Override
            public boolean accept(File f) {
                return !f.isFile() || (f.getName().toLowerCase().endsWith(
                        "." + params.getFilesExtension().toLowerCase()));
            }
            @Override
            public String getDescription() {
                return params.getFilesName();
            }
        };
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
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


    public void setDialogTitle(String dialogTitle) {
        fc.setDialogTitle(dialogTitle);
    }

    public void setCurrentDirectory(File dir) {
        fc.setCurrentDirectory(dir);
    }
}
