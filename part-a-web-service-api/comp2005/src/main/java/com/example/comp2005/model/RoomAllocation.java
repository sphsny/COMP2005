package com.example.comp2005.model;

/*
from OpenAPI documentation
RoomAllocation{
    id	integer($int32)
    admissionID	integer($int32)
    roomID	integer($int32)
    timeIn	string($date-time)
    timeOut	string($date-time)
}
*/

public class RoomAllocation {
    public int id;
    public int admissionID;
    public int roomID;
    public String timeIn;
    public String timeOut;
}
