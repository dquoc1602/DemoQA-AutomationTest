package models;

import java.util.Objects;

/**
 * Model class representing the form data for the Textbox page.
 * Follows the Builder pattern for flexible object creation.
 * 
 * @author Automation Team
 */
public class TextboxFormData {
    
    private final String fullName;
    private final String email;
    private final String currentAddress;
    private final String permanentAddress;
    
    /**
     * Private constructor to enforce use of Builder pattern.
     * 
     * @param builder The builder instance containing form data
     */
    private TextboxFormData(Builder builder) {
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.currentAddress = builder.currentAddress;
        this.permanentAddress = builder.permanentAddress;
    }
    
    // Getters
    public String getFullName() {
        return fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getCurrentAddress() {
        return currentAddress;
    }
    
    public String getPermanentAddress() {
        return permanentAddress;
    }
    
    /**
     * Builder class for creating TextboxFormData instances.
     */
    public static class Builder {
        private String fullName;
        private String email;
        private String currentAddress;
        private String permanentAddress;
        
        public Builder withFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }
        
        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }
        
        public Builder withCurrentAddress(String currentAddress) {
            this.currentAddress = currentAddress;
            return this;
        }
        
        public Builder withPermanentAddress(String permanentAddress) {
            this.permanentAddress = permanentAddress;
            return this;
        }
        
        /**
         * Builds and returns a TextboxFormData instance.
         * 
         * @return A new TextboxFormData object
         */
        public TextboxFormData build() {
            return new TextboxFormData(this);
        }
    }
    
    /**
     * Creates a new Builder instance.
     * 
     * @return A new Builder for constructing TextboxFormData
     */
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextboxFormData that = (TextboxFormData) o;
        return Objects.equals(fullName, that.fullName) &&
               Objects.equals(email, that.email) &&
               Objects.equals(currentAddress, that.currentAddress) &&
               Objects.equals(permanentAddress, that.permanentAddress);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(fullName, email, currentAddress, permanentAddress);
    }
    
    @Override
    public String toString() {
        return "TextboxFormData{" +
               "fullName='" + fullName + '\'' +
               ", email='" + email + '\'' +
               ", currentAddress='" + currentAddress + '\'' +
               ", permanentAddress='" + permanentAddress + '\'' +
               '}';
    }
}

