package model;

public class InvalidTypeException extends Exception{
    public String toString() {
        return "Given type is invalid";
    }
}
