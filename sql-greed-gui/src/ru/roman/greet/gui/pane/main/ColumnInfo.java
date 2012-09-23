package ru.roman.greet.gui.pane.main;

import java.io.Serializable;

/**
 * @author Roman 08.09.12 13:22
 */
public class ColumnInfo implements Serializable {


    private String columnLabel;
    private int columnType;
    private int columnDisplaySize;
    private String columnFullName;
    private String columnClassName;
    private String columnTypeName;
    private String catalogName;
    private int precision;
    private int scale;
    private boolean autoIncrement;
    private boolean caseSensitive;
    private boolean currency;
    private boolean definitelyWritable;
    private int nullable;
    private boolean readOnly;
    private boolean searchable;
    private boolean signed;
    private boolean writable;

    public String[] asArray() {
        return new String[] {
                columnLabel,
                String.valueOf(columnType),
                String.valueOf(columnDisplaySize),
                columnFullName,
                columnClassName,
                columnTypeName,
                catalogName,
                String.valueOf(precision),
                String.valueOf(scale),
                String.valueOf(autoIncrement),
                String.valueOf(caseSensitive),
                String.valueOf(currency),
                String.valueOf(definitelyWritable),
                String.valueOf(nullable),
                String.valueOf(readOnly),
                String.valueOf(searchable),
                String.valueOf(signed),
                String.valueOf(writable)
        };
    }

    public void setColumnLabel(String columnLabel) {
        this.columnLabel = columnLabel;
    }

    public void setColumnType(int columnType) {
        this.columnType = columnType;
    }

    public void setColumnDisplaySize(int columnDisplaySize) {
        this.columnDisplaySize = columnDisplaySize;
    }

    public void setColumnFullName(String columnFullName) {
        this.columnFullName = columnFullName;
    }

    public void setColumnClassName(String columnClassName) {
        this.columnClassName = columnClassName;
    }

    public void setColumnTypeName(String columnTypeName) {
        this.columnTypeName = columnTypeName;
    }


    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }

    public void setCurrency(boolean currency) {
        this.currency = currency;
    }


    public void setDefinitelyWritable(boolean definitelyWritable) {
        this.definitelyWritable = definitelyWritable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setSearchable(boolean searchable) {
        this.searchable = searchable;
    }

    public void setSigned(boolean signed) {
        this.signed = signed;
    }

    public void setWritable(boolean writable) {
        this.writable = writable;
    }

    public String getColumnLabel() {
        return columnLabel;
    }

    public int getColumnType() {
        return columnType;
    }

    public int getColumnDisplaySize() {
        return columnDisplaySize;
    }

    public String getColumnFullName() {
        return columnFullName;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public boolean isCurrency() {
        return currency;
    }

    public boolean isDefinitelyWritable() {
        return definitelyWritable;
    }

    public int getNullable() {
        return nullable;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public boolean isSearchable() {
        return searchable;
    }

    public boolean isSigned() {
        return signed;
    }

    public boolean isWritable() {
        return writable;
    }
}
