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
}
