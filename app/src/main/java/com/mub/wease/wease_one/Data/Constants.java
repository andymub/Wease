package com.mub.wease.wease_one.Data;

/**
 * Created by Andymub on 08/02/2018.
 */

public class Constants {
    public static String nameOfExam = "uploads/";
    public static String DATABASE_PATH_UPLOADS = "CULTURE";//CULTURE";
    public static String DATABASE_FILE_NAME = "c";//CULTURE";

    public static String getDatabaseFileName() {
        return DATABASE_FILE_NAME;
    }

    public static void setDatabaseFileName(String databaseFileName) {
        DATABASE_FILE_NAME = databaseFileName;
    }

    public static String getDatabasePathUploads() {
        return DATABASE_PATH_UPLOADS;
    }

    public static void setDatabasePathUploads(String databasePathUploads) {
        DATABASE_PATH_UPLOADS = databasePathUploads;
    }

    public static String getNameOfExam() {
        return nameOfExam;
    }

    public static void setNameOfExam(String nameOfExam1) {
        nameOfExam = nameOfExam1;
    }
}
