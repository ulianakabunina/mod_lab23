import java.util.ArrayList;
import java.util.List;

interface Observer {
    void update(String groupName, String notification);
}

interface Subject {
    void register(Observer observer, String groupName);
    void remove(Observer observer, String groupName);
    void notify(String groupName, String notification);
}

class Group implements Subject {
    private String name;
    private List<Observer> observers = new ArrayList<>();

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void register(Observer observer, String groupName) {
        if (this.name.equals(groupName)) {
            observers.add(observer);
        }
    }

    public void remove(Observer observer, String groupName) {
        if (this.name.equals(groupName)) {
            observers.remove(observer);
        }
    }

    public void notify(String groupName, String notification) {
        if (this.name.equals(groupName)) {
            for (Observer observer : observers) {
                observer.update(groupName, notification);
            }
        }
    }

    public void publish(String notification) {
        notify(this.name, notification);
    }
}

class User implements Observer {
    private String name;
    private List<String> subscribe = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public void subscribeToGroup(Group group) {
        group.register(this, group.getName());
        subscribe.add(group.getName());
    }

    public void update(String groupName, String notification) {
        System.out.println(name + " получил оповещение от группы '" + groupName + "': " + notification);
    }

    public void print() {
        System.out.println(name + " подписан на группы: " + String.join(", ", subscribe));
    }
}

public class Main {
    public static void main(String[] args) {
        Group group1 = new Group("Группа 1");
        Group group2 = new Group("Группа 2");
        Group group3 = new Group("Группа 3");
        Group group4 = new Group("Группа 4");

        User user1 = new User("Пользователь 1");
        User user2 = new User("Пользователь 2");

        user1.subscribeToGroup(group1);
        user1.subscribeToGroup(group2);
        user1.subscribeToGroup(group3);

        user2.subscribeToGroup(group1);
        user2.subscribeToGroup(group2);
        user2.subscribeToGroup(group3);
        user2.subscribeToGroup(group4);

        user1.print();
        user2.print();

        System.out.println("\nОтправляем оповещения:\n");

        group1.publish("Новое сообщение в группе 1");
        group2.publish("Обновление в группе 2");
        group3.publish("Важное объявление в группе 3");
        group4.publish("Событие в группе 4");
    }
}