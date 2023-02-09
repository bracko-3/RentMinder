# RentMinder
Design Document  
  
Brayden Cummins, Will Blaker, Jose Chacon Ascue, Wilmer Esquivel, and Annamalai Subramanian

## Introduction
Have you ever had to split rent and utilities between 6 different people? Have you ever wanted to send a reminder to your roommates to pay up? RentMinder can help you:
  
* Manage and keep track of utilities and rent.
* Send reminders to the household to pay rent/utilities.
* Look back at previous months totals.
* Automatically split the expenses among roommates.
  
Use your Android device to create and manage your own household. Keep timestamps of when each roommate paid their share. Send reminders to those roommates that tend to 
forget. Keep track of every month's expenses.

## Storyboard

![RentMinder](https://user-images.githubusercontent.com/112514952/214733059-0a0e8303-5f76-4973-9c1f-d5545adeb427.png)

## Functional Requirements

### Requirement 1: Record Monthly House Expenses

#### Scenario

As a user living with multiple roommates, I want to be able to record our monthly house expenses.
  
#### Dependencies

Monthly house expenses are fulfilled.
  
#### Assumptions

House expenses are all in United States Dollars (USD).
  
Monthly expenses consist of rent, electric/gas, water/sewer, wifi, and other.
  
#### Examples

1.1
  
**Given** the water/sewer bill for the current month is $75.80.  

**When** I input $75.80 in the water/sewer bill box.  

**Then** I can save the $75.80 water/sewer bill to the app.
  
1.2
  
**Given** rent is $3000 for the current month.  

**When** I input $3000 in the rent box.  

**Then** I can save the $3000 rent bill to the app.  
  
1.3
  
**Given** the wifi bill for the current month is $60.  

**When** I input $60 in the wifi bill box.  

**Then** I can save the $60 wifi bill to the app.  
  
1.4
  
**Given** the electric/gas bill for the current month is $230.  

**When** I input $230 in the electric/gas bill box.  

**Then** I can save the $230 electric/gas bill to the app.
 
1.5

**Given** the other expenses is $100 for the month.

**When** I input adsadfsdsfad in the other bill box.

**Then** i will not be able to save the other expense bill to the app 
and will get a error toast message of other bill box not inputted correctly.

### Requirement 2: Send Reminders

#### Scenario

As a user inputting house expenses, each time I save an expense, all household members will receive a reminder to pay their share of that certain bill.
  
#### Dependencies

House expense has been saved to the app. 
  
#### Assumptions

Each household member has an active phone plan.
  
Each household member created an account
  
#### Examples

2.1
  
**Given** the water/sewer bill , $75.80 , for the current month has been inputted.  

**When** I click the save button.  

**Then** all household members will get a water/sewer bill reminder of $75.80 sent to their phone.
  
2.2 
  
**Given** the electric/gas bill ,  $230 , for the current month has been inputted.

**When** I click the save button.  

**Then** all household members will get an electric/gas bill reminder of $230 sent to their phone.
  
2.3 
  
**Given** the wifi bill , $60 , for the current month has been inputted. 

**When** I click the save button.  

**Then** all household members will get a wifi bill reminder of $60 sent to their phone.
  
## Class Diagram
  
![MicrosoftTeams-image](https://user-images.githubusercontent.com/112514952/214878074-7032179e-9e77-41a6-a2a8-211a16cd1ba9.png)
 
 ## Class Diagram Description
 
 **MainActivity:** The first screen that the user will see when the app loads. This screen will allow the option to enter costs/expenses.
 
 **RetrofitInstance** Bootstrap class required for Retrofit
 
 **HouseHold:** Noun class that represents a household.
 
 **Member:** Noun class that represents a member.
  
 **Payment:** Noun class that represents a payment.
 
**IHouseholdDAO:** Hold household data.

**IMemberDAO:** Hold member data.

**IPaymentDAO:** Holds payment data.

## Scrum Roles

* DevOps/Product Owner/Scrum Master: Brayden Cummins
* Frontend Developer: Jose and Will
* Integration Developer: Wilmer and Annamalai

## Weekly Meeting

Wednesday at 7PM. Microsoft Teams Group

Teams Meeting Link:

https://teams.microsoft.com/_?lm=deeplink&lmsrc=NeutralHomePageWeb&cmpid=WebSignIn&culture=en-us&country=us#/conversations/19:edab28cd362646849e935d5670cf5e45@thread.v2?ctx=chat



