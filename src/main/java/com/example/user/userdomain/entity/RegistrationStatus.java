package com.example.user.userdomain.entity;

public enum RegistrationStatus {
    /* 재학 */
    ATTENDING("재학"),
    /* 휴학 */
    TAKEOFFSCHOOL("휴학"),
    /* 졸업 */
    GRADUATE("졸업");

    private final String label;

    RegistrationStatus(String label) {
        this.label = label;
    }
}
