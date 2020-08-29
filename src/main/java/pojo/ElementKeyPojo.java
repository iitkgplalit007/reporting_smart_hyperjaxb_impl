package pojo;

import org.apache.commons.lang3.StringUtils;

public class ElementKeyPojo {
    String _recordNumber;
    int _recordNumberOffSet;
    int _recordNumberLength;
    String _subjectHeader;
    String _Schema;
    String _table;

    public int get_recordNumberOffSet() {
        return _recordNumberOffSet;
    }

    public void set_recordNumberOffSet(int _recordNumberOffSet) {
        this._recordNumberOffSet = _recordNumberOffSet;
    }

    public int get_recordNumberLength() {
        return _recordNumberLength;
    }

    public void set_recordNumberLength(int _recordNumberLength) {
        this._recordNumberLength = _recordNumberLength;
    }

    public String get_table() {
        return _table;
    }

    public void set_table(String _table) {
        this._table = _table;
    }

    public String get_recordNumber() {
        return _recordNumber;
    }

    public void set_recordNumber(String _recordNumber) {
        this._recordNumber = _recordNumber;
    }

    public String get_subjectHeader() {
        return _subjectHeader.trim();
    }

    public void set_subjectHeader(String _subjectHeader) {
        this._subjectHeader = _subjectHeader;
    }

    public String get_Schema() {
        return _Schema;
    }

    public void set_Schema(String _Schema) {
        this._Schema = _Schema;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + StringUtils.trimToEmpty(_recordNumber).hashCode();
        result = prime * result + Integer.toString(_recordNumberLength).hashCode();
        result = prime * result + Integer.toString(_recordNumberOffSet).hashCode();
        result = prime * result + StringUtils.trimToEmpty(_subjectHeader).hashCode();
        result = prime * result + StringUtils.trimToEmpty(_Schema).hashCode();
        result = prime * result + StringUtils.trimToEmpty(_table).hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ElementKeyPojo other = (ElementKeyPojo) obj;
        if (!StringUtils.trimToEmpty(_table).equals(StringUtils.trimToEmpty(other.get_table())))
            return false;
        if (!StringUtils.trimToEmpty(get_Schema()).equals(StringUtils.trimToEmpty(other.get_Schema())))
            return false;
        if (!StringUtils.trimToEmpty(_subjectHeader).equals(StringUtils.trimToEmpty(other.get_subjectHeader())))
            return false;
        if (!StringUtils.trimToEmpty(_recordNumber).equals(StringUtils.trimToEmpty(other.get_recordNumber())))
            return false;
        if (!Integer.toString(_recordNumberOffSet).equals(Integer.toString(other.get_recordNumberOffSet())))
            return false;
        if (!Integer.toString(_recordNumberLength).equals(Integer.toString(other.get_recordNumberLength())))
            return false;
        return true;
    }
}
