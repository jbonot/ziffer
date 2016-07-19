package de.rwth_aachen.ziffer;

public class GuestListItem {

    private String username;
    private String firstName;
    private String lastName;
    private String germanLevel;
    private String imageFile;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGermanLevel() {
        return germanLevel;
    }

    public void setGermanLevel(String germanLevel) {
        this.germanLevel = germanLevel.substring(0,2);
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
}
