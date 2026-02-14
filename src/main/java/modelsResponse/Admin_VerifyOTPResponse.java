package modelsResponse;

import java.util.List;

public class Admin_VerifyOTPResponse {

    private Boolean status;
    private List<Object> data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
