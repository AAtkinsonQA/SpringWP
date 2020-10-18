# SpringWP

# Workout Planner Timetable

Here I'm just gonna put some hopefully-helpful example files and documents to give you an idea of what your fundamental project should look like. Note that I will not be putting my full project here as I don't wanna encourage anyone to just copy my work, but this'll probably be handy for reference.

Below is the original README.md file for my fundamental project. It's probably a little OTT, I might cut some things down a bit, but you should get an idea of the sections you'll need to include for your own.

### Resources:
* Presentation: https://docs.google.com/presentation/d/1C0J2M6T_B_fquxnGgjRV6K_arbpyLvmv8f2Z-C-wYa0/edit?usp=sharing
* Trello: https://trello.com/b/UfMXjN8h/constellations
* Website: http://35.214.26.193:8001/

## Contents
* [Brief](#brief)
   * [Additional Requirements](#additional-requirements)
   * [My Approach](#my-approach)
* [Architecture](#architecture)
   * [Database Structure](#database-structure)
   * [CI Pipeline](#ci-pipeline)
* [Project Tracking](#project-tracking)
* [Risk Assessment](#risk-assessment)
* [Testing](#testing)
* [Front-End Design](#front-end-design)
* [Known Issues](#known-issues)
* [Future Improvements](#future-improvements)
* [Authors](#authors)

## Brief
Overall objective:
"To create a CRUD application with utilisation of supporting tools, methodologies and technologies that encapsulate all core modules covered during training."

Core Modules: Databases, Java SE, Spring Boot, Front-End Development.

### Additional Requirements
In addition to what has been set out in the brief, I am also required to include the following:
* An agile scrum jira board
* Clear documentation of the design phase, app architecture and risk assessment
* A java-based functional application that follows best practices and design principles
* Test suites for the application, which will include automated tests for validation of the application
* Meet an acceptable level of test coverage in backend and provide consistent reports and evidence.
* A front-end website, created using HTML,CSS,JavaScript in VSC
* Code integrated into a Version Control System and deployed to a cloud-based virtual machine (GCP)

### My Approach
To achieve this, I have decided to produce a simple exercise timetable app that allows the user to do the following:
* Create exercises and assign them to a day of the week on the timetable with the following attributes:
   * *Name* of the exercise
   * *Length* of the exercise in minutes
   * *Number* of sets
* Select exercises which have been completed with a checkbox
* Delete exercises
* Update exercise attributes
* View the updated timetable instantly after any exercises have been created, updated or deleted

Additionally, I would like to allow the user to:
* Refer to a database of exercises and store the attributes of their exercises
* Search for a particular exercise from a database of premade recommendations
* Create several weeks instead of being limited to one week
* Easily move exercises between days so new ones don't have to be created
* Log in to a personal account so several users can have stored timetables

## Architecture
### Database Structure
Pictured below is an entity relationship diagram (ERD) showing the structure of the database. Everything in green has been implemented into the app, while everything in red has not.

![ERD][erd1]

As shown in the ERD, the app models a many-to-many relationship between User entities and Observation entities using an association table. This allows the user to create observation posts and tag multiple users in the database with one observation. Similarly, many observations can therefore be associated with a user.

### CI Pipeline
![ci][ci]

Pictured above is the continuous integration pipeline with the associated frameworks and services related to them. This pipeline allows for rapid and simple development-to-deployment by automating the integration process, i.e. I can produce code on my local machine and push it to GitHub, which will automatically push the new code to Jenkins via a webhook to be automatically installed on the cloud VM. From there, tests are automatically run and reports are produced. A testing environment for the app is also run in debugger mode, allowing for dynamic testing.

This process is handled by a Jenkins 'pipeline' job with distinct build stages. The design of this type of job means that if a previous build stage fails, the job will fail altogether and provide you with detailed information as to where this occurred. The more modular you make this system, the easier it is to pinpoint where your code is failing. As pictured below, the four build stages are:
* 'Checkout SCM' (pull code from Git respository)
* 'Build' (would be more accurately named 'Installation' as Python doesn't require building, in the strictest sense)
* 'Test' (run pytest, produce coverage report) 
* 'Run' (start the flask-app service on the local VM, belonging to systemctl)

![buildstages][buildstages]

Once the app is considered stable, it is then pushed to a separate VM for deployment. This service is run using the Python-based HTTP web server Gunicorn, which is designed around the concept of 'workers' who split the CPU resources of the VM equally. When users connect to the server, a worker is assigned to that connection with their dedicated resources, allowing the server to run faster for each user.

## Project Tracking
Trello was used to track the progress of the project (pictured below). You can find the link to this board here: https://trello.com/b/UfMXjN8h/constellations

![trello][trello]

The board has been designed such that elements of the project move from left to right from their point of conception to being finished and fully implemented. Each card is also colour-coded according to which element of the project it pertains. From left to right, these lists are:
* *Project Requirements*
   A list of requirements set out in the brief in order for this to be a successful project.
* *Project Resources*
   List of relevant resources for quick access.
* *User Stories*
   Any functionality that is implemented into the project first begins as a user story. This keeps the development of every element of the web app focused on the user experience first.
* *Planning*
   The initial stages where a specific element (e.g. a block of code, a server, etc.) is being considered for implementation.
* *In Progress*
   Once the element has had any code written for it/exists in any way, it is placed in the 'in progress' list.
* *Testing*
   Once the element has been created, it moves to the 'testing' list, where its functionality is tested.
* *Finished*
   Any element that is considered to be finished (i.e. works according to its specification) lives in this list.

## Risk Assessment
The risk assessment for this project can be found in full here: https://docs.google.com/spreadsheets/d/1WfKQAjsBfErpQOywRmnZbCe6zw7yFxESFB8WhQd69Es/edit?usp=sharing

Here's a quick screenshot:

![RiskAssessment][riskassessment]

## Testing
pytest is used to run unit tests on the app. These are designed to assert that if a certain function is run, the output should be a known value. Jenkins produces console outputs (pictured below) that will inform the developer how many tests the code passed and which tests they failed.

![pytestconsole][pytestconsole]

pytest also produces a coverage report to show how much of the code in the app has been successfully tested. Jenkins automatically moves this report to the 'templates' folder so that it can be navigated to in a browser, as shown in the picture below.

![coverage][coverage]

## Front-End Design
The front-end of the app is built with HTML, CSS and JavaScript and has one page. It is largely functional and stable.

Home page:

![homepage][createExample.png]

They are then able to log in or register an account:

![signup][signup]

![login][login]

Once they are logged in, they now have access to the 'Enter Observation' page and their account page:

![homeloggedin][homeloggedin]

Navigating to the 'Enter Observation' page allows them to post an observation and optionally tag up to two other observers, which will appear at the top of the home page:

![enterobservation][enterobservation]

![homenewobservation][homenewobservation]

Navigating to the 'Account' page allows them to view their account details, update them and delete the account if they so desire. Deleting an account will also delete any observation they are associated with:

![account][account]

## Known Issues
There are a few bugs with the current build of the app:
* If a user attempts to change their email to another email that already exists in the database, the app will inexplicably delete the account entirely
* Certain errors do not appear on the front end when they should, for example: if the user inputs incorrect login information, they should receive an 'Incorrect Username' or 'Incorrect Password' warning – instead the page merely reloads

## Future Improvements
There are a number of improvements I would like to implement (outside of current bugs):
* Implementation of the stars and constellation database to allow you to tag these celestial objects in observations
* Allow the user to tag an indefinite number of users in an observation, rather than a maximum of two
* Filter observation posts by user, date, location, etc.
* Aesthetic overhaul, to make the front-end both more appealing *and* more functional
   * The aesthetics of an interface are important for the functionality of a web app, insofar as a user can only use functionality that they understand. Confusing aesthetic design will obscure the functionality of the app
   * This would be implemented using CSS, the easiest approach being with Bootstrap
* Implementation of other solar system objects that require realtime updates, e.g. planets whose locations in the sky are always changing
   * This would probably be best achieved by referring to another publicly-available database
* Users can customise their accounts more with profile pictures, add other users as friends, change the colour palette of the website, etc.

## Authors
Harry Volker
