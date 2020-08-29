package pojo;

public class SubjectHeaderPojo {
    static String _subjectHeader ;
    static String _recordNumberCode ;
    static int _recordNumberStartPos ;
    static int _recordNumberLength  ;

    public static String get_subjectHeader() {
        return _subjectHeader;
    }

    public static void set_subjectHeader(String _subjectHeader) {
        SubjectHeaderPojo._subjectHeader = _subjectHeader;
    }

    public static String get_recordNumberCode() {
        return _recordNumberCode;
    }

    public static void set_recordNumberCode(String _recordNumberCode) {
        SubjectHeaderPojo._recordNumberCode = _recordNumberCode;
    }

    public static int get_recordNumberStartPos() {
        return _recordNumberStartPos;
    }

    public static void set_recordNumberStartPos(int _recordNumberStartPos) {
        SubjectHeaderPojo._recordNumberStartPos = _recordNumberStartPos;
    }

    public static int get_recordNumberLength() {
        return _recordNumberLength;
    }

    public static void set_recordNumberLength(int _recordNumberLength) {
        SubjectHeaderPojo._recordNumberLength = _recordNumberLength;
    }

    public static SubjectHeaderPojo getNewInstance(){
        return new SubjectHeaderPojo();
    }

}
