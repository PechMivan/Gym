### GYM CRM PROJECT

Given the following object descriptions:

![image](https://github.com/PechMivan/Gym/assets/97990963/b2768524-6fd7-4778-a0d8-e173fc4ac8e5)

Services must be implemented for each class as follows:

![image](https://github.com/PechMivan/Gym/assets/97990963/7c42236e-8653-409f-bd89-be59ad02866a)

Service implementations can be found under src/main/java/com/gym/gym/services/implementations

Use of services are shown below:

## Trainee

![image](https://github.com/PechMivan/Gym/assets/97990963/cbbd341d-5101-41b3-b0e1-db8b5ccd19d2)

## Trainer

![image](https://github.com/PechMivan/Gym/assets/97990963/a9df3914-1bc1-491d-a587-9c3a7e9f11d5)

## Training

![image](https://github.com/PechMivan/Gym/assets/97990963/accf8c78-4e6a-4e57-90ed-56ca4ff710c1)

*Notes*:

1. Configure spring application context based on the Spring annotation or on Java based 
approach.

In this case, Spring Annotation Approach is implemented.

2. Implement DAO objects for each of the domain model entities (Trainer, Trainee, 
Training). They should store in and retrieve data from a common in-memory storage - 
java map. Each entity should be stored under a separate namespace, so you could list 
particular entity types.

In this case a common in memory storage (map of maps) was implemented under a class called
DataStorageManager as shown below

![image](https://github.com/PechMivan/Gym/assets/97990963/68178a10-7b48-49ec-840d-d07173d2ce28)


3. Storage should be implemented as a separate spring bean. Implement the ability to 
initialize storage with some prepared data from the file during the application start (use 
spring bean post-processing features). Path to the concrete file should be set using 
property placeholder and external property file. In other words, Every storage 
(java.util.Map) should be implemented as a separate spring bean

DataStorageManager consist of different isolated beans that follows this pattern (Trainee Storage):

![image](https://github.com/PechMivan/Gym/assets/97990963/65c86ca9-3919-4891-9a93-d2de87f74c96)


4. DAO with storage bean should be inserted into services beans using auto wiring. Services 
beans should be injected into the facade using constructor-based injections. The rest of 
the injections should be done in a setter-based way.

For DataStorageManager, beans are injected using a setter-based way:

![image](https://github.com/PechMivan/Gym/assets/97990963/3161d73b-0292-4506-86aa-e7c84a3a5fa8)

For Services, DAOS are autowired to the field:

![image](https://github.com/PechMivan/Gym/assets/97990963/a06dc9ff-6cf6-4be8-8c2c-1df7b46487a7)

For the facade (main class), Services are injected in a constructor-based way:

![image](https://github.com/PechMivan/Gym/assets/97990963/0c9156b3-c0b2-4f85-926b-16a521dddb2c)


5. Cover code with unit tests.

Tests are implemented...

![image](https://github.com/PechMivan/Gym/assets/97990963/ec6803d9-006a-4ef6-bf65-5a4b4a83beaf)

SonarQube and Jacoco report are pending**

9. For Trainee and Trainer create profile functionality implement username and password 
calculation by follow rules:   
• Username going to be calculated from Trainer/Trainee first name and last name 
by concatenation by using dot as a separator (eg. John.Smith)  
• In the case that already exists Trainer or Trainee with the same pair of first and 
last name as a suffix to the username should be added a serial number.  
• Password should be generated as a random 10 chars length string.

Two methods are implemented in Service for Trainer and Trainee on the create method: createUsername
and createPassword. Implementation is shown below

![image](https://github.com/PechMivan/Gym/assets/97990963/e31c3d7a-333b-44fe-a22d-fa9b499554c4)

![image](https://github.com/PechMivan/Gym/assets/97990963/c5b85856-1c2b-4a3d-8c09-61fe1dcbca8f)

Results are shown below:

![image](https://github.com/PechMivan/Gym/assets/97990963/87811ecf-0963-45b6-982d-178b5c6b5e29)




