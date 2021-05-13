package com.selfowner.att_teacher;

public class Teacher_Upload_Helper_Class {
    String TeacherName;
    String TeacherEmail;
    String TeacherCollege;
    String TeacherPassword;
    String TeacherDept;
    public Teacher_Upload_Helper_Class(){

    }

    public Teacher_Upload_Helper_Class(String teacherName, String teacherEmail, String teacherCollege,String teacherDept ,String teacherPassword ) {
        TeacherName = teacherName;
        TeacherEmail = teacherEmail;
        TeacherCollege = teacherCollege;
        TeacherDept = teacherDept;
        TeacherPassword = teacherPassword;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public String getTeacherEmail() {
        return TeacherEmail;
    }

    public String getTeacherCollege() {
        return TeacherCollege;
    }

    public String getTeacherPassword() {
        return TeacherPassword;
    }

    public String getTeacherDept() {
        return TeacherDept;
    }
}
