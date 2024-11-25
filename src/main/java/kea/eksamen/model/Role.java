package kea.eksamen.model;

public enum Role {
    PROJECT_MANAGER("PM", 1),
    EMPLOYEE("Employee", 2),
    CLIENT("Client", 3);

    private final String displayName;
    private final int id;

    Role(String displayName, int id) {
        this.displayName = displayName;
        this.id = id;
    }

    public static Role getEnumFromId(int id){
        for(Role role : Role.values()){
            if(role.getId() == id){
                return role;
            }
        }
        return null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }
}
