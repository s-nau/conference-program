package Presenter.Central;

import Event.EventManager;
import Event.RoomManager;
import Message.ChatManager;
import Message.MessageManager;
import Person.PersonManager;
import Person.Person;
import Person.OrganizerManager;
import Request.RequestManager;

import java.util.HashMap;
import java.util.Map;

public class ConventionSaver {
    protected RoomManager rm = new RoomManager();
    private static final FileGateway<RoomManager> roomLoader = new FileGateway<>("rooms.ser");
    protected EventManager em = new EventManager();
    private static final FileGateway<EventManager> eventLoader = new FileGateway<>("events.ser");

    protected MessageManager mm = new MessageManager();
    protected ChatManager cm = new ChatManager();
    private static final FileGateway<MessageManager> messageLoader = new FileGateway<>("messages.ser");
    private static final FileGateway<ChatManager> chatLoader = new FileGateway<>("chats.ser");

    protected RequestManager rqm = new RequestManager();
    private static final FileGateway<RequestManager> requestLoader = new FileGateway<>("requests.ser");

    protected Map<String, Person> personByName = new HashMap<>();
    protected Map<String, Person> personByID = new HashMap<>();
    private static final FileGateway<Map<String, Person>> id2Person =
            new FileGateway<>("pByID.ser");
    private static final FileGateway<Map<String, Person>> n2Person =
            new FileGateway<>("pByName.ser");

    ConventionSaver() {
        load();
    }

    ConventionSaver(RoomManager rm, EventManager em, MessageManager mm, ChatManager cm, RequestManager rqm,
                    PersonManager pm) {
        this.rm = rm;
        this.em = em;
        this.mm = mm;
        this.cm = cm;
        this.rqm = rqm;
        Map[] maps = pm.unpack();
        this.personByName = maps[0];
        this.personByID = maps[1];
    }

    /**
     * Loads saved data from a set filepath
     */
    private void load() {
        if (roomLoader.readFile()!= null) {
            rm = roomLoader.readFile();
        }
        if (eventLoader.readFile()!= null) {
            em = eventLoader.readFile();
        }
        if (messageLoader.readFile() != null) {
            mm = messageLoader.readFile();
        }
        if (chatLoader.readFile() != null) {
            cm = chatLoader.readFile();
        }
        if (requestLoader.readFile() != null) {
            rqm = requestLoader.readFile();
        }
        if (id2Person.readFile() != null) {
            personByID = id2Person.readFile();
        }
        if (n2Person.readFile() != null) {
            personByName = n2Person.readFile();
        } else { // If you're creating an entirely new conference attendee list, add an additional "admin" user
            OrganizerManager firstLogin = new OrganizerManager(personByName, personByID, true);
            Map[] maps = firstLogin.unpack();
            personByName = maps[0];
            personByID = maps[1];
        }
    }

    /**
     * Saves data to a set filepath
     */
    protected void save() {
        roomLoader.writeFile(rm);
        eventLoader.writeFile(em);
        messageLoader.writeFile(mm);
        chatLoader.writeFile(cm);
        id2Person.writeFile(personByID);
        n2Person.writeFile(personByName);
        requestLoader.writeFile(rqm);
    }
}
