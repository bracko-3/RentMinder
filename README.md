# RentMinder
Design Document  
  
Brayden Cummins, Will Blaker, Jose Chacon Ascue, Wilmer Esquivel, and Annamalai Subramanian

## Introduction
Have you ever had to split rent and utilities between 6 different people? Have you ever wanted to send a reminder to your roommates to pay up? RentMinder can help you:
  
* Manage and keep track of utilities and rent.
* Send reminders to the household to pay rent/utilities.
* Look back at previous months totals.
* Automatically split the expenses among roommates.
  
Use your Android device to create and manage your own household. Keep timestamps of when each roommate paid their share. Send reminders to those roommates that tend to forget. Keep track of every month's expenses.
## Storyboard

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
  
Given the water/sewer bill for the current month has been billed.
  
When I input the water/sewer bill data.
  
Then I can save the water/sewer bill data.
  
1.2
  
Given the rent has been billed for the current month.
  
When I input the rent cost.
  
Then I can save the rent data.
  
1.3
  
Given the wifi bill for the current month has been billed.
  
When I input the wifi bill cost.
  
Then I can save the wifi bill cost.

Requirement 2: Send Reminders

#### Scenario
As a user inputting house expenses, each time I save an expense, all household members will receive a reminder to pay their share of that certain bill.
  
#### Dependencies
House expense has been saved to the app. 
  
#### Assumptions
Each household member has an active phone plan.
  
Each household member created an account
  
Examples
2.1
  
Given the water/sewer bill for the current month has been inputted.
  
When I click the save button.
  
Then all household members will get a water/sewer bill reminder sent to their phone.
  
2.2 
  
Given the electric/gas bill for the current month has been inputted.
  
When I click the save button.
  
Then all household members will get an electric/gas bill reminder sent to their phone.
  
2.3 
  
Given the wifi bill for the current month has been inputted.
  
When I click the save button.
  
Then all household members will get a wifi bill reminder sent to their phone.


## Class Diagram
