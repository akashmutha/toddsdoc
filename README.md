# toddsdoc
It is an app which checks how likely the person(patient) will have Todd's Syndrome given some attributes. It stores the patient's data also and can be retrieved in future.

# Details:

App's flow:

First ScreenL: (home page) is NewPatientActivity which has some input.
Once doctor fills the input and clickon Check Todd's Syndrome. It capture the patient details and
insert into the sqlite database. It also calculates how likely the patient can have Todd's Syndrome.
It returns a unique id for each patient which can be used later.

Second Screen: once doctor click's on the old patient button on first screen then another activity
starts (OldPatientActivity). Where doctor can put id of the patient and can view all the details and
the likeliness of the person having Todd's Syndrome.


Implementation:
1. All the basic input checks.
2. Logic is implemented as reusable android library(package toddssyndromelogic)
3. On Check button click an entry is recorded in the sqlite db ( background thread)
4. doctor can clear all the inputs on the first screen.
5. With the unique id doctor can get all the info about a patient
5. Animation(implemented simple basic animation during activity switch)
6. Wrote some tests to check input validation, clear input works or not, logic works correctly or not
 and once we click on the check button whether an entry is recorded in the db or not. (Tests are in
 the AndroidTest Folder and can be run)
7. Also implemented the rest api code ( Rest Adapter and implementation to send the user info to remote
 but didn't use as I don't have the actual end point. But once actual dummy end point is replaced it
 can be used to store the data on remote api)
8. It works on all the android devices (Andorid 4.0 to 5.0 to 6.0 and N, tablet (different screen sizes)

etc)

 Main Libraries or framework used:
 1. RxJava
 2. Retrofit
 3. Google Support Design
 3. gson
 4. Espresso
 5. Followed MVP ( Model View Presenter)