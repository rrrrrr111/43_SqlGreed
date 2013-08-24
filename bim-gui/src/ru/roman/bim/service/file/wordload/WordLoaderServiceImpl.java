package ru.roman.bim.service.file.wordload;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import ru.roman.bim.gui.pane.edit.WordUtils;
import ru.roman.bim.gui.pane.main.MainViewModel;
import ru.roman.bim.gui.pane.settings.Settings;
import ru.roman.bim.model.Lang;
import ru.roman.bim.model.WordCategory;
import ru.roman.bim.model.WordType;
import ru.roman.bim.service.ServiceFactory;
import ru.roman.bim.service.gae.GaeConnector;
import ru.roman.bim.util.BimException;
import ru.roman.bim.util.WsUtil;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/** @author Roman 20.01.13 21:39 */
public class WordLoaderServiceImpl implements WordLoaderService {
    private static final Log log = LogFactory.getLog(WordLoaderServiceImpl.class);

    private final GaeConnector gaeConnector = ServiceFactory.getGaeConnector();

    public static final String COLUMN_TEXT_FACED = "TEXT_FACED";
    public static final String COLUMN_TEXT_SHADOWED = "TEXT_SHADOWED";
    public static final String COLUMN_RATING = "RATING";
    public static final String COLUMN_TEXT_FACED_LANG = "TEXT_FACED_LANG";
    public static final String COLUMN_TEXT_SHADOWED_LANG = "TEXT_SHADOWED_LANG";
    public static final String COLUMN_CATEGORY = "CATEGORY";
    public static final String COLUMN_TYPE = "TYPE";

    @Override
    public void loadFile(File fileFroLoading) {

        List<MainViewModel> sheetData = parseExcel(fileFroLoading);
        for (final MainViewModel model : sheetData) {
            WordUtils.checkIdiom(model);
            WordUtils.fillTexts(model, model.getTextFaced(), model.getTextShadowed());

            gaeConnector.save(model, new GaeConnector.GaeCallBack<Long>() {
                @Override
                protected void onSuccess(Long id) {
                    model.setId(id);
                }
            });
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private List<MainViewModel> parseExcel(File fileFroLoading) {
        List<MainViewModel> sheetData = new ArrayList<MainViewModel>();
        FileInputStream fis = null;
        try {

            List<String> columnNames = new ArrayList<String>();
            XMLGregorianCalendar currDate = WsUtil.getCurrGregorian();
            fis = new FileInputStream(fileFroLoading);
            HSSFWorkbook workbook = new HSSFWorkbook(fis);
            HSSFSheet sheet = workbook.getSheetAt(0);
            Iterator rows = sheet.rowIterator();
            HSSFRow row;
            for (int i = 0; rows.hasNext(); i++) {
                row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                MainViewModel model = new MainViewModel();
                HSSFCell cell;
                int columnNum;
                while (cells.hasNext()) {
                    cell = (HSSFCell) cells.next();
                    columnNum = cell.getColumnIndex();
                    final String value = cell.toString();
                    log.trace(String.format("Value of row %s column %s : %s", i, columnNum, value));
                    if (i == 0) {
                        columnNames.add(value);
                    } else {
                        if (columnNum >= columnNames.size()) {
                            continue;
                        }
                        if (columnNum == columnNames.indexOf(COLUMN_TEXT_SHADOWED)) {
                            model.setTextShadowed(value);
                        } else if (StringUtils.isBlank(value)) {
                            throw new BimException("Value of column %s can't be empty", columnNames.get(columnNum));
                        } else if (columnNum == columnNames.indexOf(COLUMN_TEXT_FACED)) {
                            model.setTextFaced(value);
                        } else if (columnNum == columnNames.indexOf(COLUMN_RATING)) {
                            model.setRating(createLong(value));
                        } else if (columnNum == columnNames.indexOf(COLUMN_TEXT_FACED_LANG)) {
                            model.setFacedLangId(Lang.valueOfReduction(value).getOrdinal());
                        } else if (columnNum == columnNames.indexOf(COLUMN_TEXT_SHADOWED_LANG)) {
                            model.setShadowedLangId(Lang.valueOfReduction(value).getOrdinal());
                        } else if (columnNum == columnNames.indexOf(COLUMN_CATEGORY)) {
                            model.setCategory(WordCategory.valueOf(value).getOrdinal());
                        } else if (columnNum == columnNames.indexOf(COLUMN_TYPE)) {
                            model.setType(WordType.valueOf(value).getOrdinal());
                        }
                    }
                }
                if (i == 0) {
                    checkColumn(columnNames, COLUMN_TEXT_FACED);
                    checkColumn(columnNames, COLUMN_TEXT_SHADOWED);
                    checkColumn(columnNames, COLUMN_RATING);
                    checkColumn(columnNames, COLUMN_TEXT_FACED_LANG);
                    checkColumn(columnNames, COLUMN_TEXT_SHADOWED_LANG);
                    checkColumn(columnNames, COLUMN_CATEGORY);
                    checkColumn(columnNames, COLUMN_TYPE);
                } else {
                    model.setEditDate(currDate);
                    model.setOwner(Settings.get().getId());
                    model.setId(null);
                    //model.setModelNum();
                    sheetData.add(model);
                    log.trace("Model formed from Excel row : " + model);
                }


            }
        } catch (Exception e) {
            throw new RuntimeException("Exception while Excel file loading", e);
        } finally {
            IOUtils.closeQuietly(fis);
        }
        return sheetData;
    }

    private Long createLong(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        } else if (StringUtils.containsAny(value, ",.")) {
            return Long.valueOf(value.substring(0, StringUtils.indexOfAny(value, ",.")));
        }
        return Long.valueOf(value);
    }

    private void checkColumn(List<String> columnNames, String name) {
        if (!columnNames.contains(name)) {
            throw new BimException("Incorrect file format, column %s absent", name);
        }
    }

}
