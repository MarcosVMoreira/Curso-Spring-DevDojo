package br.com.devdojo.error;

public class ErrorDetail {

    private String title;
    private int status;
    private String details;
    private long timestamp;
    private String developerMessage;

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public int getStatus () {
        return status;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public String getDetails () {
        return details;
    }

    public void setDetails (String details) {
        this.details = details;
    }

    public long getTimestamp () {
        return timestamp;
    }

    public void setTimestamp (long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDeveloperMessage () {
        return developerMessage;
    }

    public void setDeveloperMessage (String developerMessage) {
        this.developerMessage = developerMessage;
    }
}
