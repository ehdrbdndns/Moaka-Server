package com.moaka.common.util;

import java.sql.Statement;

public class Constant {
    /**
     * File URL을 DB에 넣을 때 사용되는 FILE PATH
     */
    public static String LOCAL_FILE_PATH = "http://localhost:8080/images/";

    /**
     * File URL을 DB에 넣을 때 사용되는 FILE PATH(배포용)
     */
    public static String DEPLOY_FILE_PATH = "http://localhost:8080/images/";

    /**
     * File Dummy Path에 사용되는 SYSTEM FILE PATH
     */
    public static String DEPLOY_FILE_PATH_INNER = "/www/gabia_inner_path/www/images";

    /**
     * Image Size 제한
     */
    public static int IMAGE_SIZE = 1024 * 1024 * 5;//5MB

    /**
     * File Size 제한
     */
    public static int FILE_SIZE = 1024 * 1024 * 10; //10MB

    /**
     * Master Auth
     */
    public static String MASTER_ID = "master";
    public static String MASTER_PS = "master";
}