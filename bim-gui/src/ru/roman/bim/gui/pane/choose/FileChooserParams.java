package ru.roman.bim.gui.pane.choose;

/** @author Roman 02.06.13 10:55 */
public class FileChooserParams {

    private String filesName;
    private String filesExtension;
    private String dialogTitle;

    public FileChooserParams(String filesName, String filterRegExp) {
        this.filesName = filesName;
        this.filesExtension = filterRegExp;
    }

    public FileChooserParams(String filesName, String filesExtension, String dialogTitle) {
        this.filesName = filesName;
        this.filesExtension = filesExtension;
        this.dialogTitle = dialogTitle;
    }

    public String getFilesName() {
        return filesName;
    }

    public void setFilesName(String filesName) {
        this.filesName = filesName;
    }

    public String getFilesExtension() {
        return filesExtension;
    }

    public void setFilesExtension(String filesExtension) {
        this.filesExtension = filesExtension;
    }

    public String getDialogTitle() {
        return dialogTitle;
    }

    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle = dialogTitle;
    }
}
