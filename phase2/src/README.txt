Welcome to Convention System! This program does not require that you download any additional libraries.


~MANDATORY EXTENSIONS~
------------------------------------------------------------------------------------------------------------------------
-> There are now many types of events: "Talk", "Panel", "Party", and "Workshop".

-> An event can be canceled by an Organizer (Login > View & edit convention event list > Cancel an existing event)

-> Additional User: Employee. Employees are people who are working at the convention who manage the actual
logistics. Their accounts don't allow them to sign up for events and their communication (via chat groups and messages) 
is restricted to organizers and to fellow employees. (You can imagine them as being those people with neutral-looking 
clothing doing all the behind-the-scenes work at the conference, or as caterers/janitors/information booth staffers/etc.) 
Employees work closely with the Request board (see the Optional extension entry for more details about Requests). They 
can handle requests (in which case, their name is recorded along with the request, so any further concerns can be forwarded
to them - this differs from Organizers, who can fulfill requests without their name being recorded). This changes the status 
of the request from pending to fulfilled. 
Employee accounts can only be created and deleted by organizers.

-> Any account can be created by an Organizer (Login > Create and Delete user accounts)

-> The organizer can set maximum number of people that can attend an event when creating an event (Login >
View & edit convention event list > Create a new event) and changed later (Login > View & edit convention event list >
Change the capacity of an existing event)
------------------------------------------------------------------------------------------------------------------------


~OPTIONAL EXTENSIONS~
------------------------------------------------------------------------------------------------------------------------
-> The user's messaging experience has been enhanced by allowing them to delete, archive, or mark messages as "unread"
again after reading them (Login > View your messages > Archive a chat || Archive a message || Leave a Chat || Mark a
message as read || Mark a message as unread)

-> Attendees, Organizers and Employees have the ability to make Requests (Login > View the request board > Make a request). 
Requests are initialized with status "Pending". Organizers and Employees can see the full list of requests
made by all users and fulfill any of them (Login > View the request board > Fulfill a request). After a request has been 
successfully fulfilled, its status is changed to "Fulfilled". This change is visible to the user who made the request 
(Login > View the request board > View yor requests || View a specific request) and to all Organizers and Employees 
(Login > View the request board > View all requests)

-> The text UI has been replaced with a GUI. The GUI was implemented with Model-View-Presenter-style architecture.
Packages "Event", "Message", "Person" and "Request" contain the Model.
Package "Presenter" contains the Presenter. (This package contains both Controller and Presenter classes, according to
traditional program architecture.)
Package "View" contains the View. (This package also contains the Main Method.)

All levels use an outside-inwards dependency: the View instantiates and interacts with presenters and controllers (all
one Presenter layer), the controllers instantiate and interact with various manager classes (Use-Cases) and the manager
classes instantiate and interact with Entity classes.

-> Users have the ability to change their account information (Login > Change account settings)

-> Button descriptors (ToolTipText) were added to various buttons and menus in the program. Hovering over certain
buttons will provide a brief description of what they do.
------------------------------------------------------------------------------------------------------------------------


~DESIGN PATTERNS/PRINCIPLES~
------------------------------------------------------------------------------------------------------------------------
-> DEPENDENCY INVERSION: For the GUI, we considered using inverted dependencies for View-Presenter interactions, but
this idea was eventually scrapped. Our original (Phase 1) design contained a hierarchy of controllers receiving input
from presenters and delegating metods to use-case classes. We attempted to create an interface to let the presenter
layer instantiate view objects, thus allowing us to maintain the same overall structure. However, we found this caused
less flexibility when we tried to design the visual appearance of each menu frame. Instead, we opted to update the
structural hierarchy: the view listens for input it receives from the user and sends it to the presenter layer to be
processed.

-> FACTORY: The "Account" class needed to have the capability to create various types of menus with, potentially, very
different  appearances, depending on what menu the user wanted to view. To allow for that, we created the "AccountView"
abstract class to serve as a basic frame and provide helper methods for any view the Account class would need to
instantiate. In addition, "AccountViewFactory" was created to instantiate an "AccountView" object of a type that
depending on the choice the user submitted. (e.g. If a user wanted to open a "Message" page, their selection would be
sent to the AccountViewFactory, which would create an instance of the subclass of AccountView that displays messaging
options.) This allowed us to use a standard Account user interface for all users, with all differentiation between the
different types of views isolated to the Factory class.

In the original code (Phase 1), the Façade pattern was implemented in each subclass of "PersonController" to handle the
responsibilities of its corresponding user accounts. (e.g if an Attendee wanted to view their contacts, the
AttendeeController would instantiate and run a "ContactController", so that responsibility was isolated to a single
actor.) This concept was adapted to fit the new Factory setup: the AccountViewFactory uses the PersonController it has
been given to instantiate and return its component controllers, based on user input, which it uses to determine what
kind of AccountView it is supposed to create. Thus, each subclass of PersonController has actually become a factory for
its component controllers (all classes that inherit from "SubMenu").

-> OBSERVER: The GUI implements Java's built-in "Action Listener" interface, which is used to relay information between
JPanel components (the elements the user sees onscreen) to the rest of the class.

Originally, we were planning on using the observer pattern for requests. We were planning on making both the person
making the request and an organizer request-tracking entity (stored in "OrganizerManager) Observers for each request.

By using the observer pattern, request information would have been accessed through Use Case classes that observed the
requests - but we decided against this model. The primary reason for this was that we found it difficult to pass around
information about Observers without breaking dependency rules, and we found a different implementation - the one
we're currently using - that ended up being a lot simpler.
------------------------------------------------------------------------------------------------------------------------


~SETUP~
------------------------------------------------------------------------------------------------------------------------
-> When the program runs for the first time, it already contains one instance of an Organizer account. To access it,
login with the following information:

Username: admin
Password: admin

IMPORTANT - after you have logged in with this account, the first thing you should do is go to the
"Change account settings" menu and change its username, password, and email to something  more secure.

This accounts already exists because only Organizers can create non-Attendee accounts.  If you wish to make any more
Speaker, Employee, or Organizer accounts, you can instantiate them through any Organizer's "Create or delete user
accounts" menu.

(Attendee accounts can be created by Organizers or through the welcome menu)

NOTE: when running the program on Mac or Unix software, an "ArrayIndexOutOfBoundsException" may occur when reading the
saved files from the program. This is due to the way these systems store file pathways ("\" vs "/"). These errors do not
interfere with the actual running of the program.
------------------------------------------------------------------------------------------------------------------------


~DIVISION OF WORK~
------------------------------------------------------------------------------------------------------------------------
-> The work was divided between teams, each of which focused on one aspect of the program. Although members mostly
worked on classes within their team's domain, to avoid confusion / interfering with other teams' designs, we also helped
with other teams' workload occasionally when asked for assistance. The basic structure of the program was discussed by
all members in meetings held within a Discord planning server, and detailed designs for various sections of the program
were decided by each of the individual teams.

-> Person team: Responsible for implementing the user accounts and deciding what each user type
(Attendee/Organizer/Speaker/Employee) can do.

    - Allen
    - Paul
    - Shimmy

-> Message team: Responsible for designing and implementing the message system (Message and Chats)

    - Ran
    - Karyn

-> Event team: Responsible for designing and implementing the event system

    Phase 1:
        - Sarah
        - Eytan

    Phase 2:
        - Eytan

-> GUI team: Responsible for implementing graphic user interface (in Phase 1, we simply used presenter classes instead
of View classes) that creates menu windows when the program is run.

    Phase 1:
        - Cara
    Phase 2:
        - Cara
        - Sarah
        - Allen


We decided which optional extensions to implement by discussing them Discord meetings in which all members participated.
------------------------------------------------------------------------------------------------------------------------

