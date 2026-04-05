package com.example.comp2005.model;

/*
from OpenAPI documentation
Allocation {
    id	integer($int32)
    admissionID	integer($int32)
    employeeID	integer($int32)
    roomID	integer($int32)
    startTime	string($date-time)
    endTime	string($date-time)
}
*/

public class Allocation {
    public int id;
    public int admissionID;
    public int employeeID;
    public int roomID;
    public String startTime;
    public String endTime;
}
