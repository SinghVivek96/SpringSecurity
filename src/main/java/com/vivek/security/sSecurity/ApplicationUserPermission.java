package com.vivek.security.sSecurity;

public enum ApplicationUserPermission {
//        STUDENT_READ("random:read"),
//        STUDENT_WRITE("random:write"),
        COURSE_READ("random:read"),
        COURSE_WRITE("random:write");

        private final String permission;

        ApplicationUserPermission(String permission){
            this.permission = permission;
        }

        public String getPermission(){
            return permission;
        }
}
