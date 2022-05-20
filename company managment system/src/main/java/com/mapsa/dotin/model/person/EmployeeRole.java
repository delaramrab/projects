package com.mapsa.dotin.model.person;

import org.springframework.security.core.GrantedAuthority;

public enum EmployeeRole implements GrantedAuthority {
    DEVELOPER,
    TESTER,
    PROGRAMMER,
    MANAGER,
    USER;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
