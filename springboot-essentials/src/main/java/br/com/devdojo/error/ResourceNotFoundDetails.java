package br.com.devdojo.error;

public class ResourceNotFoundDetails extends ErrorDetails {

    public static final class Builder {
        private String title;
        private int status;
        private String details;
        private long timestamp;
        private String developerMessage;

        private Builder () {
        }

        public static Builder newBuilder () {
            return new Builder();
        }

        public Builder title (String title) {
            this.title = title;
            return this;
        }

        public Builder status (int status) {
            this.status = status;
            return this;
        }

        public Builder details (String details) {
            this.details = details;
            return this;
        }

        public Builder timestamp (long timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder developerMessage (String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public ResourceNotFoundDetails build () {
            ResourceNotFoundDetails resourceNotFoundDetails = new ResourceNotFoundDetails();
            resourceNotFoundDetails.setDetails(details);
            resourceNotFoundDetails.setTimestamp(timestamp);
            resourceNotFoundDetails.setTitle(title);
            resourceNotFoundDetails.setDeveloperMessage(developerMessage);
            resourceNotFoundDetails.setStatus(status);
            return resourceNotFoundDetails;
        }
    }
}
