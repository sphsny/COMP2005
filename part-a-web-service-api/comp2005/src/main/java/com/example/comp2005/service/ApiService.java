package com.example.comp2005.service;

import com.example.comp2005.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ApiService {
    // get external API
    private static final String BASE_URL = "https://web.socem.plymouth.ac.uk/COMP2005/api";
    // initialise springboot rest template
    // handles HTTP requests and returns JSON body
    // Jackson turns this into Java objects through model classes so that
    // services can use those Java objects for business logic
    private final RestTemplate restTemplate = new RestTemplate();

    // --- GET /Admissions (6 entries) ---
    public List<Admission> getAdmissions() {
        Admission[] result = restTemplate.getForObject(
                BASE_URL + "/Admissions", Admission[].class // tells Jackson to turn response into array of object, where each is mapping to Admission
        );
        return result != null ? Arrays.asList(result) : List.of(); // condition, if, else: if result is not null, return filled list, otherwise, return empty list
    }

    // --- GET /Allocations (4 entries) ---
    public List<Allocation> getAllocations() {
        Allocation[] result = restTemplate.getForObject(
                BASE_URL + "/Allocations", Allocation[].class
        );
        return result != null ? Arrays.asList(result) : List.of();
    }

    // --- GET /Employees (6 entries) ---
    public List<Employee> getEmployees() {
        Employee[] result = restTemplate.getForObject(
                BASE_URL + "/Employees", Employee[].class
        );
        return result != null ? Arrays.asList(result) : List.of();
    }

    // --- GET /Patients (5 entries) ---
    public List<Patient> getPatients() {
        Patient[] result = restTemplate.getForObject(
                BASE_URL + "/Patients", Patient[].class
        );
        return result != null ? Arrays.asList(result) : List.of();
    }

    // --- GET /RoomAllocations (3 entries)
    public List<RoomAllocation> getRoomAllocations() {
        RoomAllocation[] result = restTemplate.getForObject(
                BASE_URL + "/RoomAllocations", RoomAllocation[].class
        );
        return result != null ? Arrays.asList(result) : List.of();
    }
}

/*

GET /Admissions

  {
    "id": 1,
    "admissionDate": "2020-11-28T16:45:00",
    "dischargeDate": "2020-11-28T23:56:00",
    "patientID": 2
  },
  {
    "id": 2,
    "admissionDate": "2020-12-07T22:14:00",
    "dischargeDate": null,
    "patientID": 1
  },
  {
    "id": 3,
    "admissionDate": "2021-09-23T21:50:00",
    "dischargeDate": "2021-09-27T09:56:00",
    "patientID": 2
  },
  {
    "id": 4,
    "admissionDate": "2024-02-23T21:50:00",
    "dischargeDate": "2024-09-27T09:56:00",
    "patientID": 5
  },
  {
    "id": 5,
    "admissionDate": "2024-04-12T22:55:00",
    "dischargeDate": "2024-04-14T11:36:00",
    "patientID": 5
  },
  {
    "id": 6,
    "admissionDate": "2024-04-19T21:50:00",
    "dischargeDate": null,
    "patientID": 5
  }
]

GET /Allocations

[
  {
    "id": 1,
    "admissionID": 1,
    "employeeID": 4,
    "roomID": 0,
    "startTime": "2020-11-28T16:45:00",
    "endTime": "2020-11-28T23:56:00"
  },
  {
    "id": 2,
    "admissionID": 3,
    "employeeID": 4,
    "roomID": 0,
    "startTime": "2021-09-23T21:50:00",
    "endTime": "2021-09-24T09:50:00"
  },
  {
    "id": 3,
    "admissionID": 2,
    "employeeID": 6,
    "roomID": 0,
    "startTime": "2020-12-07T22:14:00",
    "endTime": "2020-12-08T20:00:00"
  },
  {
    "id": 4,
    "admissionID": 2,
    "employeeID": 3,
    "roomID": 0,
    "startTime": "2020-12-08T20:00:00",
    "endTime": "2020-12-09T20:00:00"
  }
]

GET /Employees
[
  {
    "id": 1,
    "surname": "Finley",
    "forename": "Sarah"
  },
  {
    "id": 2,
    "surname": "Jackson",
    "forename": "Robert"
  },
  {
    "id": 3,
    "surname": "Allen",
    "forename": "Alice"
  },
  {
    "id": 4,
    "surname": "Jones",
    "forename": "Sarah"
  },
  {
    "id": 5,
    "surname": "Wicks",
    "forename": "Patrick"
  },
  {
    "id": 6,
    "surname": "Smith",
    "forename": "Alice"
  }
]

GET /Patients
[
  {
    "id": 1,
    "surname": "Robinson",
    "forename": "Viv",
    "nhsNumber": "1113335555"
  },
  {
    "id": 2,
    "surname": "Carter",
    "forename": "Heather",
    "nhsNumber": "2224446666"
  },
  {
    "id": 3,
    "surname": "Barnes",
    "forename": "Nicky",
    "nhsNumber": "6663338888"
  },
  {
    "id": 4,
    "surname": "King",
    "forename": "Jacky",
    "nhsNumber": "7773338888"
  },
  {
    "id": 5,
    "surname": "Sharpe",
    "forename": "Rhi",
    "nhsNumber": "6663339999"
  }
]

GET /RoomAllocations
[
  {
    "id": 1,
    "admissionID": 2,
    "roomID": 3,
    "timeIn": "2020-12-07T22:14:00",
    "timeOut": null
  },
  {
    "id": 2,
    "admissionID": 1,
    "roomID": 2,
    "timeIn": "2020-11-28T16:45:00",
    "timeOut": "2020-11-28T20:45:00"
  },
  {
    "id": 3,
    "admissionID": 1,
    "roomID": 4,
    "timeIn": "2020-11-28T20:45:00",
    "timeOut": "2020-11-28T23:56:00"
  }
]
 */