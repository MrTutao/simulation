package org.personal.simulation.model;

/**
 * @author taotaotu
 * Sep 28, 2019
 */
public class ResponseModel {

    private static final String SUCCESS = "success";

    private static final String FAILURE = "failure";

    private String state;

    private String message;

    public static ResponseModel createSuccessMessage(String message) {
        return new ResponseModel(SUCCESS, message);
    }

    public static ResponseModel createFailureMessage(String message) {
        return new ResponseModel(FAILURE, message);
    }

    private ResponseModel(String state, String message) {
        this.state = state;
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
