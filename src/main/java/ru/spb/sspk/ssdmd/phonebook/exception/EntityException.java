package ru.spb.sspk.ssdmd.phonebook.exception;


public class EntityException extends RuntimeException {

    public EntityException(String message) {
        super(message);
    }

    public static class EntityNotCreateException extends RuntimeException {

        public EntityNotCreateException(String message) {
            super(message);
        }
    }

    public static class EntityNotFoundException extends RuntimeException {

        public EntityNotFoundException(String message) {
            super(message);
        }
    }
}