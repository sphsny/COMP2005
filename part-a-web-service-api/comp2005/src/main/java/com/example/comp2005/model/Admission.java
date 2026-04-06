package com.example.comp2005.model;

/*
from OpenAPI documentation
Admission {
    id	integer($int32)
    admissionDate	string($date-time)
    dischargeDate	string($date-time)
    patientID	integer($int32)
}
 */

public class Admission {
    public int id;
    public int patientID;
    public String admissionDate;
    public String dischargeDate;

    // constructor
    public Admission(int id, int patientID, String admissionDate, String dischargeDate) {
        this.id = id;
        this.patientID = patientID;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }
}
