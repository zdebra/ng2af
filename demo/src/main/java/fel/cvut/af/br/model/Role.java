package fel.cvut.af.br.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zb on 11.1.16.
 */
public enum Role{
    VISITOR("visitor","Visitor"), EDITOR("editor", "Editor"), MANAGER("manager", "Manager"), ADMIN("admin", "Admin");

    private String roleString;
    private String label;

    Role(String roleString, String label) {
        this.roleString = roleString;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return roleString;
    }

    public static List<Role> getAll() {
        return Arrays.asList(values());
    }

    public static Role fromString(String roleStr) {
        switch(roleStr) {
            case "editor":
                return EDITOR;
            case "manager":
                return MANAGER;
            case "admin":
                return ADMIN;
            default:
                return VISITOR;
        }

    }

}
