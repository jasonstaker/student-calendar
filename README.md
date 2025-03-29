# My Calender Project

## Why?
I may have the **worst memory** on the planet. I cannot remember things if my life depended on it, and because of that, I have had to develop a very **complex** way of keeping myself on top of school work and day-to-day tasks. How I remember things is usually unique to me, and I find different ways to use different Chrome extensions or tools I already have *unconventionally*. Ever since I started coding, I've always wanted to make an application that I can tailor to my specific needs as a student who also needs to organize their social and professional life. So that is who this project is for; it's an **advanced calendar** for college students.

## Features
My application will do the following:
- It will be able to categorize events based on user-made categories
- The user-made categories will be fully customizable to whatever the user needs
- In this menu, students will be given a variety of options to choose from, such as whether it is a class, some common links for the class, or when homework is typically due.
- You will be able to filter based on categories, and you will be able to make subcategories as well. For example, under the school category, you can make homework assignments and class schedules to view when you have class and when everything is due for the class on one calendar.
- You can also make recurring events.

## User Stories
- As a user, I want to be able to add multiple categories to my calendar.
- As a user, I want to be able to view all the months in a year.
- As a user, I want to be able to add multiple events to a day.
- As a user, I want to be able to view all the events scheduled for a specific day.
- As a user, I want the option to save my calendar to a file so I can access my events later.
- As a user, I want the option to load my saved calendar from a file so I can resume where I left off.

# Instructions for End User

- You can generate the first required action related to the user story "adding multiple Xs to a Y" by clicking the add Category button and typing in a name.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by going into a day of your choosing, clicking add event, and entering the details there.
- You can locate my visual component by going into any day.
- You can save the state of my application by clicking file at the top left, and clicking save.
- You can reload the state of my application by clicking file at the top left, and clicking load.

# Phase 4: Task 2
Event Log:
  <br />Fri Mar 28 13:38:29 PDT 2025
  <br />Category added to calendar.

  Fri Mar 28 13:38:33 PDT 2025
  <br />Subcategory added to calendar.

  Fri Mar 28 13:38:34 PDT 2025
  <br />Subcategory removed from calendar.

  Fri Mar 28 13:38:51 PDT 2025
  <br />Event added to 5 days in the calendar.

  Fri Mar 28 13:39:06 PDT 2025
  <br />Event added to 1 day in the calendar.

  Fri Mar 28 13:39:28 PDT 2025
  <br />Subcategory added to calendar.

  Fri Mar 28 13:39:30 PDT 2025
  <br />Subcategory removed from calendar.

  Fri Mar 28 13:39:31 PDT 2025
  <br />Category removed from calendar.

  Fri Mar 28 13:39:50 PDT 2025
  <br />Event removed from 5 days in the calendar.

  Fri Mar 28 13:39:53 PDT 2025
  <br />Event removed from 1 day in the calendar.

# Phase 4: Task 3
The UML class diagram revealed to me that I have a lot of redundant references to other classes. For example, the calendar class is the primary one. There are so many references to it I had to combine them all into one arrow in the UI class just so it did not look terrible.

If I had the chance I would completely overhaul how the CalendarController class is used. Since every important panel and the calendar is there, I simply could have used it better so they employ the CalendarController, which would centralize all the information in the program. This would make everything MUCH more concise and likely would have been much easier.

Going forward, I think if I had more time I would take a look over my code to identify where I made a "patch job" and would take note of them. Then, I would use the CalendarController class to fix these issues, creating a more cohesive program overall.