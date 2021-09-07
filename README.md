# CapstoneBlogProject

## Authors

Ross Mitchell, Paul Delaney, Timi Makkonen, Patricia Andrianambinintsoa

## Description

This project is a group project where we designed and built a Blog/Content management Web App using SQL, Java, SpringBoot, Thymeleaf, Bootstrap Css, HTML.

## Groupworking Process

We held regular group calls to discuss and work on the project. Each member contributed with regular commits and we created a trello board to manage our workload.

## Running the WebApp on your local machine

We have included the SQL scripts required to create our database. Dowload our project, run the SQL scripts on your machine to create the database, build and run the project using your IDE, view the app in browser on localhost.

## Website Functionality

### Login details

You have the option to login and there are currently two tiers to our login system

* Admin: email = admin@sg.com, password = admin
* Owner: email = owner@sg.com, password = owner

### Admin funtionality

As an admin you can add and edit posts. These posts will be added to the Owners 'EditPosts' page where the owner can view the posts and decide if they should be published to the blog.

### Owner funtionality

* Add/edit/remove posts.
* Publish posts created by an admin.
* Add/edit/remove static pages which can be viewed using the 'Pages' dropdown at the top of the page.
* Add/remove categories for posts.

## Extra information

If you visit the website and are not logged in, you can view all published posts and pages. You also have the option to filter posts by category, by tag, or by category and tag.

We have included screenshots of the website in the "Screenshots" folder in the repo.

## My Contributions - Paul Delaney

We worked hard as a team on a tight timescale to produce this application.
* Discussed initial planning stages with other team members. 
* I provided continous support to the group through regular communaction, regularly checking in with the group to ensure that everyone was progressing and no-one was stuck for too long. 
* Reached out to team members if I was facing difficulties. 
* I created our initial ERD for our database. 
* I created all the DAO interfaces and implemented the CategoryDaoDB and the PostDaoDB - The classes implement the corresponding interface and utilise JDBC to query the database. 
* I wrote unit tests for all the DAOs and debugged the DAOs where neccessary. 
* I implemented the page.html to view a static page.
* I implemented all the input validation by using spring annotation and creating a Validator object to perform the validation. This is essential to ensure our application does not encounter any erros when writing to the database. e.g If we have declared a field in our database as VARCHAR(48) we have to ensure that the user can't enter more than 48 characters. 
* I implemented the category controller and html page for managing categories. 
* I created the image for our heading using a photo editor.
* I made some small UI changes

