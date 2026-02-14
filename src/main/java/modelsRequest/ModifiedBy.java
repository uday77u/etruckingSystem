package modelsRequest;

public class ModifiedBy {
    private String userId;
    private String role;

    public ModifiedBy(){
    	
    }
    
    public ModifiedBy(String i, String role) {
        this.userId = i;
        this.role = role;
    }

    // Getters & Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

	@Override
	public String toString() {
		return "ModifiedBy [userId=" + userId + ", role=" + role + "]";
	}
    
    
}
