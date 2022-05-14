# CSE218CCA

This was my CSE218(Data Structures and Algorithms) Project, its a JavaFX application that takes care of the admission process for a school, the school in question is "**Squidward Community College**".

This project was assigned to test my abilities how efficient can I: 
- Write my Code
- Reduce Load Times
- Use Correct, Speedy Data Structures
- Read Files from Disk
- Lose my mind.



## Project Requirements
```
CSE218 Final Project
Write a Java program for College Admissions Management.
It must have the following features:
1. User Account Management
a. A user may create account or login with an existing account.
A user account should consist of the following information:
1) First name
2) Last name
3) Email address which is also used as user name
4) Password. It must be at least 7 characters long with at least 1 uppercase
letter, 1 lowercase letter, and 1 digit.
5) Phone number
6) Address
7) GPA value
8) SAT score (400 – 1600)
9) Application Essay in text format (at least 250 words)
10) Family income in dollar amount
In addition, your system must assign the user with a unique ID number.
2. Your program should store all the user accounts in an efficient data structure for fast queries in
the future.
3. Your program should also set the following criteria to determine the status of the application.
a. Accepted: GPA >= 3.5, essay readability, which is determined by a Fletch Score, is lower
than 60, typos per 100 words in the essay is fewer than 3, family income >= $100,000,
SAT score >= 1200. Google Fletch score to get the formula for its calculation
(https://en.wikipedia.org/wiki/Flesch%E2%80%93Kincaid_readability_tests)
b. Denied: GPA < 2.5, essay readability, which is determined by Fletch Score, is higher than
70, typos per 100 words in the essay is more than 10, family income < $10,000, SAT
score < 800.
c. Waiting Listed: in between the above two categories. The waiting list should rank
applicants by an admissible index named AI, which is calculated using the following
rubrics:
Category Details
GPA From 0.0, every additional 0.1
increases AI by 2;
Fletch Score From 0, every additional 10
points decreases AI by 2
point
Typos per 100 words From 0, every additional typo
per 100 words decreases AI
by 1 point
Family Income From $0, every additional
$10,000 increases AI by 1
point
SAT score Every 100 increases AI by 1
point
Based on these criteria, your program should produce a text document named
lastname.firstname.decision.txt for each applicant. All such files should be saved in the project folder
under a subfolder called decisions. The corresponding file should also be loaded into a TextArea when a
user logs into his/her account. This document contains the decision (whether accepted, denied, or
waiting listed) and a brief explanation of the values of the above rubrics to justify the decision. The
justification should be in a table format listing each factor, its AI contribution, and the total AI score.
The document is generated once all the account information is filled out (submitted). Otherwise, it
should simply state that the application is incomplete.
Finally, in your project folder include a README.txt file. In this file, please explain what data structures
you used for which purposes and why.
```
