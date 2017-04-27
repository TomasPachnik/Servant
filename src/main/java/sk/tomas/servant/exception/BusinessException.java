package sk.tomas.servant.exception;

/**
 * Created by Tomas Pachnik on 27-Apr-17.
 */
abstract class BusinessException extends Exception {
    BusinessException(String message) {
        super(message);
    }
}
