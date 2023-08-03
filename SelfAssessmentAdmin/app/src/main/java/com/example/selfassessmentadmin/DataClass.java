package com.example.selfassessmentadmin;

public class DataClass {

    private String dataTitle;
    private String dataDesc;
    private String dataType;
    private String dataImage;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDataTitle() {
        return dataTitle;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public String getDataType() {
        return dataType;
    }

    public String getDataImage() {
        return dataImage;
    }

    public DataClass(String dataTitle, String dataDesc, String dataType, String dataImage) {
        this.dataTitle = dataTitle;
        this.dataDesc = dataDesc;
        this.dataType = dataType;
        this.dataImage = dataImage;
    }

    public DataClass(){



    }

}
