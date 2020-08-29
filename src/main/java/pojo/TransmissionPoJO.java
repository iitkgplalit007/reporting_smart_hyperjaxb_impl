package pojo;

import ui.TransmissionUIHelper;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransmissionPoJO {
    static String	label	;
    static String	data_provider_contact_email__0	;
    static String	record_type_code	;
    static String	data_type_code	;
    static String	data_receiver_code	;
    static String	transmission_version_identif_0	;
    static String	submission_type_code	;
    static String	submission_replacement_identifier	;
    static String	data_provider_code	;
    static String	name_of_data_provider_contact	;
    static String	phone_number	;
    static String	phone_number_extension	;
    static String	fax_number	;
    static Date processed_date_item	;
    static String	address_of_contact_street	;
    static String	address_of_contact_city	;
    static String	address_of_contact_state	;
    static String	address_of_contact_zip_code	;
    static String	data_provider_type_code	;
    static String	third_party_entity_federal_e_0	;



    static int      version_number_of_day;
    public static int getVersion_number_of_day() {
        return version_number_of_day;
    }
    public static void setVersion_number_of_day(int version_number_of_day) {
        java.sql.Date today = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        if(getProcessed_date_item().equals(today)){
            version_number_of_day = version_number_of_day + 1;
        }else if(getProcessed_date_item().before(today)){
            version_number_of_day= 0;
        }
        TransmissionPoJO.version_number_of_day = version_number_of_day;
    }

     public static String getLabel() {
        return label;
    }

     static void setLabel(String label) {
        TransmissionPoJO.label = label;
    }

    public static String getData_provider_contact_email__0() {
        return data_provider_contact_email__0;
    }

     public static void setData_provider_contact_email__0(String data_provider_contact_email__0) {
        TransmissionPoJO.data_provider_contact_email__0 = data_provider_contact_email__0;
    }

     public static String getRecord_type_code() {
        return record_type_code;
    }

     static void setRecord_type_code(String record_type_code) {
        TransmissionPoJO.record_type_code = record_type_code;
    }

     public static String getData_type_code() {
        return data_type_code;
    }

     static void setData_type_code(String data_type_code) {
        TransmissionPoJO.data_type_code = data_type_code;
    }

     public static String getData_receiver_code() {
        return data_receiver_code;
    }

     static void setData_receiver_code(String data_receiver_code) {
        TransmissionPoJO.data_receiver_code = data_receiver_code;
    }

     public static String getTransmission_version_identif_0() {
        return transmission_version_identif_0;
    }

     public static void setTransmission_version_identif_0(String transmission_version_identif_0) {
         if(transmission_version_identif_0 == null){
             String transmissionVersionIdentifier = "V";
             Date processedDate =  getProcessed_date_item();
             double julianDate  =  dateToJulian(processedDate);
             int versionNumberOfTheDay = 0;
             if(getTransmission_version_identif_0() == null){
                 versionNumberOfTheDay = 1;
             }else{
                 versionNumberOfTheDay = Integer.parseInt(getTransmission_version_identif_0().substring((getTransmission_version_identif_0().length() -2), getTransmission_version_identif_0().length()));
             }
             String versionNumber = null;
             if(versionNumberOfTheDay <10){
                 versionNumber = "0"+ Integer.toString(versionNumberOfTheDay);
             }else{
                 versionNumber =  Integer.toString(versionNumberOfTheDay);
             }
             String datePart =  new Double(julianDate).toString();
             datePart = datePart.substring(0,5);
             TransmissionPoJO.transmission_version_identif_0 = datePart + transmissionVersionIdentifier + versionNumber;;
         }else{
             TransmissionPoJO.transmission_version_identif_0 = transmission_version_identif_0;
         }
         }

     public static String getSubmission_type_code() {
        return submission_type_code;
    }

     static void setSubmission_type_code(String submission_type_code) {
        TransmissionPoJO.submission_type_code = submission_type_code;
    }

     public static String getSubmission_replacement_identifier() {
        return submission_replacement_identifier;
    }

     static void setSubmission_replacement_identifier(String submission_replacement_identifier) {
        TransmissionPoJO.submission_replacement_identifier = submission_replacement_identifier;
    }

     public static String getData_provider_code() {
        return data_provider_code;
    }

     static void setData_provider_code(String data_provider_code) {
        TransmissionPoJO.data_provider_code = data_provider_code;
    }

     public static String getName_of_data_provider_contact() {
        return name_of_data_provider_contact;
    }

     public static void setName_of_data_provider_contact(String name_of_data_provider_contact) {
        TransmissionPoJO.name_of_data_provider_contact = name_of_data_provider_contact;
    }

     public static String getPhone_number() {
        return phone_number;
    }

     public static void setPhone_number(String phone_number) {
        TransmissionPoJO.phone_number = phone_number;
    }

     public static String getPhone_number_extension() {
        return phone_number_extension;
    }

     public static void setPhone_number_extension(String phone_number_extension) {
        TransmissionPoJO.phone_number_extension = phone_number_extension;
    }

     public static String getFax_number() {
        return fax_number;
    }

     public static void setFax_number(String fax_number) {
        TransmissionPoJO.fax_number = fax_number;
    }

     public static java.sql.Date getProcessed_date_item() {
        return processed_date_item;
    }

     public static void setProcessed_date_item(Date processed_date_item) {
        TransmissionPoJO.processed_date_item = processed_date_item;
    }

     public static String getAddress_of_contact_street() {
        return address_of_contact_street;
    }

     public static void setAddress_of_contact_street(String address_of_contact_street) {
        TransmissionPoJO.address_of_contact_street = address_of_contact_street;
    }

    public static String getAddress_of_contact_city() {
        return address_of_contact_city;
    }

     public static void setAddress_of_contact_city(String address_of_contact_city) {
        TransmissionPoJO.address_of_contact_city = address_of_contact_city;
    }

     public static String getAddress_of_contact_state() {
        return address_of_contact_state;
    }

     public static void setAddress_of_contact_state(String address_of_contact_state) {
        TransmissionPoJO.address_of_contact_state = address_of_contact_state;
    }

    public static String getAddress_of_contact_zip_code() {
        return address_of_contact_zip_code;
    }

     public static void setAddress_of_contact_zip_code(String address_of_contact_zip_code) {
        TransmissionPoJO.address_of_contact_zip_code = address_of_contact_zip_code;
    }

     public static String getData_provider_type_code() {
        return data_provider_type_code;
    }

     static void setData_provider_type_code(String data_provider_type_code) {
        TransmissionPoJO.data_provider_type_code = data_provider_type_code;
    }

     public static String getThird_party_entity_federal_e_0() {
        return third_party_entity_federal_e_0;
    }

     static void setThird_party_entity_federal_e_0(String third_party_entity_federal_e_0) {
        TransmissionPoJO.third_party_entity_federal_e_0 = third_party_entity_federal_e_0;
    }
     public void retrieveTransmissionRecord() throws ParseException {
        java.util.HashMap<String, String> record  =  TransmissionUIHelper.getTransmissionRecord();
        for (java.util.HashMap.Entry<String,String> entry : record.entrySet() ) {
            switch(entry.getKey().toLowerCase()){
                case "label":
                    setLabel(entry.getValue());
                    break;
                case "data_provider_contact_email__0":
                    setData_provider_contact_email__0(entry.getValue());
                    break;
                case "record_type_code":
                    setRecord_type_code(entry.getValue());
                    break;
                case "data_type_code":
                    setData_type_code(entry.getValue());
                    break;
                case "data_receiver_code":
                    setData_receiver_code(entry.getValue());
                    break;
                case "transmission_version_identif_0":
                    setTransmission_version_identif_0(entry.getValue());
                    break;
                case "submission_type_code":
                    setSubmission_type_code(entry.getValue());
                    break;
                case "submission_replacement_identifier":
                    setSubmission_replacement_identifier(entry.getValue());
                    break;
                case "data_provider_code":
                    setData_provider_code(entry.getValue());
                    break;
                case "name_of_data_provider_contact":
                    setName_of_data_provider_contact(entry.getValue());
                    break;
                case "phone_number":
                    setPhone_number(entry.getValue());
                    break;
                case "phone_number_extension":
                    setPhone_number_extension(entry.getValue());
                    break;
                case "fax_number":
                    setFax_number(entry.getValue());
                    break;
                case "processed_date_item":
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                    java.util.Date date =  dateFormat.parse(entry.getValue());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    java.sql.Date sqlDate  = new java.sql.Date(cal.getTime().getTime());
                    setProcessed_date_item(sqlDate);
                    break;
                case "address_of_contact_street":
                    setAddress_of_contact_street(entry.getValue());
                    break;
                case "address_of_contact_city":
                    setAddress_of_contact_city(entry.getValue());
                    break;
                case "address_of_contact_state":
                    setAddress_of_contact_state(entry.getValue());
                    break;
                case "address_of_contact_zip_code":
                    setAddress_of_contact_zip_code(entry.getValue());
                    break;
                case "data_provider_type_code":
                    setData_provider_type_code(entry.getValue());
                    break;
                case "third_party_entity_federal_e_0":
                    setThird_party_entity_federal_e_0(entry.getValue());
                    break;
                default:
                    break;
                    // do nothing for now
            }
        }
    }

    public static TransmissionPoJO clone(TransmissionPoJO instance){
        TransmissionPoJO newinstance = new  TransmissionPoJO();
        newinstance.setLabel(instance.getLabel());
        newinstance.setAddress_of_contact_city(instance.getAddress_of_contact_city());
        newinstance.setAddress_of_contact_state(instance.getAddress_of_contact_state());
        newinstance.setAddress_of_contact_street(instance.getAddress_of_contact_street());
        newinstance.setAddress_of_contact_zip_code(instance.getAddress_of_contact_zip_code());
        newinstance.setData_provider_code(instance.getData_provider_code());
        newinstance.setData_provider_contact_email__0(instance.getData_provider_contact_email__0());
        newinstance.setData_provider_type_code(instance.getData_provider_type_code());
        newinstance.setData_receiver_code(instance.getData_receiver_code());
        newinstance.setData_type_code(instance.getData_type_code());
        newinstance.setFax_number(instance.getFax_number());
        newinstance.setName_of_data_provider_contact(instance.getName_of_data_provider_contact());
        newinstance.setPhone_number(instance.getPhone_number());
        newinstance.setPhone_number_extension(instance.getPhone_number_extension());
        newinstance.setProcessed_date_item(instance.getProcessed_date_item());
        newinstance.setRecord_type_code(instance.getRecord_type_code());
        newinstance.setSubmission_replacement_identifier(instance.getSubmission_replacement_identifier());
        newinstance.setSubmission_type_code(instance.getSubmission_type_code());
        newinstance.setThird_party_entity_federal_e_0(instance.getThird_party_entity_federal_e_0());
        newinstance.setTransmission_version_identif_0(instance.getTransmission_version_identif_0());
        return newinstance;
    }
    private static double dateToJulian(Date date){
        int JGREG= 15 + 31*(10+12*1582);
        Calendar calender = Calendar.getInstance();
        calender.setTime(date);
        int year =  calender.get(Calendar.YEAR);
        int month =  calender.get(Calendar.MONTH);
        int day =  calender.get(Calendar.DAY_OF_MONTH);
        int julianYear = year;
        if (year < 0) julianYear++;
        int julianMonth = month;
        if (month > 2) {
            julianMonth++;
        }
        else {
            julianYear--;
            julianMonth += 13;
        }

        double julian = (java.lang.Math.floor(365.25 * julianYear)
                + java.lang.Math.floor(30.6001*julianMonth) + day + 1720995.0);
        if (day + 31 * (month + 12 * year) >= JGREG) {
            // change over to Gregorian calendar
            int ja = (int)(0.01 * julianYear);
            julian += 2 - ja + (0.25 * ja);
        }
        return java.lang.Math.floor(julian);

    }
    private static Date julianToDate(Double julianDate){
        int JGREG= 15 + 31*(10+12*1582);
        double HALFSECOND = 0.5;
        int jalpha,ja,jb,jc,jd,je,year,month,day;
        double julian = julianDate + HALFSECOND / 86400.0;
        ja = (int) julian;
        if (ja>= JGREG) {
            jalpha = (int) (((ja - 1867216) - 0.25) / 36524.25);
            ja = ja + 1 + jalpha - jalpha / 4;
        }

        jb = ja + 1524;
        jc = (int) (6680.0 + ((jb - 2439870) - 122.1) / 365.25);
        jd = 365 * jc + jc / 4;
        je = (int) ((jb - jd) / 30.6001);
        day = jb - jd - (int) (30.6001 * je);
        month = je - 1;
        if (month > 12) month = month - 12;
        year = jc - 4715;
        if (month > 2) year--;
        if (year <= 0) year--;

        Calendar cal = Calendar.getInstance();

        // set Date portion to January 1, 1970
        cal.set( cal.YEAR, year );
        cal.set( cal.MONTH, month);
        cal.set( cal.DATE, day );

        cal.set( cal.HOUR_OF_DAY, 0 );
        cal.set( cal.MINUTE, 0 );
        cal.set( cal.SECOND, 0 );
        cal.set( cal.MILLISECOND, 0 );
        java.sql.Date sqldate =
                new java.sql.Date( cal.getTime().getTime() );
        return sqldate;

    }
}
