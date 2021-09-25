package ru.netology.cloudstorage.exception;

public class StorageFileExistException extends StorageException {
    public StorageFileExistException(String message) {
        super(message);
    }

    public StorageFileExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
