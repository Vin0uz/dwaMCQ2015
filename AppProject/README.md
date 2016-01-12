#**README FILE**

###**About the _Subject_**

We choosed the subject "MCQ Live". The goal of this project is to build a website which allows user to create **Forms** and to allow other users to answer it. 
When a user create a form, the form is, by default, offline and nobody else can see it.
Then, by a simple click, the user can set a form online/offline.
When somebody answer your form, you will know it, and you will know about the result.


###**Instructions **

Launch the project : Open Eclipse, choose AppProjectApplication.java in AppProject/src/main/java/com.example and launch it as Java Application.
Then go to localhost:8080/ in your browser.

- Use _Register_ to create your own account :
  1. Login and Password need to be at least 6 chars
  2. It ll check if somebody already use this Login

- Use _Connect_ to connect to the website

- Use _CreateForm_ to create a new form

- Use _ManageYourForms_ to access page which allows you to add question to your mcq,
	set them online/offline, access a view of your mcq and result about who fill it

- Use _AccesOnlineForms_ to access page which list Online MCQ, when you click one :
  1. If you have already done it, no chance to do it again!
  2. Otherwise, you ll answer question by question until the end of the mcq (result submit at the end)
	
**_IMPORTANT_** Each questions can contain from two to four answers 
**_IMPORTANT_** Only ONE Answer by question is true.


###**Architecture**

Because of some issues with using AngularJS, Thymeleaf, Rest, Spring together, we decided to 
do not use thymeleaf. 

- RestController : MyRestController.java : 
Here is the requestmapping of REST, allowing to use get/delete/post/put method on 
  - OnlineFormsRepository : this rep store all the forms, use to know which is online or no
  - UserRepository : here are store all the users


- User contains a list of Form contains a list of Question, which contain a list of Answer.
- Every class is an Entity, with a generated ID. Each list is store in Cascade.
- AngularJS : Because of doing it as fast as possible, everything is done in only one Controller. We do not used services and factory, but we used an example of value to show that we understand how it works.
