package ru.netology.cloudstorage.service;

import java.io.File;

public class RootDirProvider {
    private static final String APP_DIR = System.getProperty("user.dir");
    private static final String FILE_DIR_PREFIX = "/files";
    private static final String ROOT_DIR = APP_DIR + FILE_DIR_PREFIX;

    public static String get() {
        new File(APP_DIR + FILE_DIR_PREFIX).mkdirs();
        return ROOT_DIR;
    }

}
