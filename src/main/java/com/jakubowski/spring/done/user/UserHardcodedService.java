package com.jakubowski.spring.done.user;

import com.jakubowski.spring.done.tasklists.Task;
import com.jakubowski.spring.done.tasklists.TaskList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service
//public class UserHardcodedService {
//
//    private static long userCounter = 0;
//    private static long taskListCounter = 0;
//    private static long taskCounter = 0;
//    private static ArrayList<User> users = new ArrayList();
//    private static ArrayList<TaskList> lists = new ArrayList();
//    private static ArrayList<Task> tasks = new ArrayList();
//
//    static {
//
//        UserProperties userProperties = new UserProperties();
//        UserStatistics userStatistics = new UserStatistics();
//
//        tasks.add(new Task(++taskCounter, "desc1", "priority1", false));
//        tasks.add(new Task(++taskCounter, "desc2", "priority2", false));
//        tasks.add(new Task(++taskCounter, "desc3", "priority3", false));
//
//        lists.add(new TaskList(++taskListCounter, "list1", 0,"blue", tasks));
//        lists.add(new TaskList(++taskListCounter, "list2", 0,"green", tasks));
//
//        users.add(new User(++userCounter, "username", "email", "pass", lists, userProperties, userStatistics));
//    }
//
//    public List<User> findAllUsers() {
//        return users;
//    }
//}
