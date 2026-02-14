package modelsRequest;

public class Admin_LogoutRequest {

    private Boolean inactive;

    public Admin_LogoutRequest() {}

    public Admin_LogoutRequest(Boolean inactive) {
        this.inactive = inactive;
    }

    public Boolean getInactive() {
        return inactive;
    }

    public void setInactive(Boolean inactive) {
        this.inactive = inactive;
    }
}
