package com.nadezda.diplom;

import com.nadezda.diplom.tables.User;

import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 * Created by Sinky on 03.05.2017.
 */

public class SingletonData {
    private static volatile SingletonData instance;
    private ArrayList<User> userList = null;
    private User currentUser = null;

    SingletonData() {
        userList = new ArrayList<>();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    // USERS
    public void fillUserListFromDB() {
        DataBaseRequestHelper helper = null;
        PreparedStatement statement = null;
        try {
            helper = new DataBaseRequestHelper();
            statement = helper.getPreparedStatement(DataBaseRequestHelper.SQL_GET_USERS_LIST);
            userList.clear();
            userList.addAll(helper.getUserList(statement));
        } finally {
            if (helper != null) {
                helper.closeStatement(statement);
            }
        }
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public User getUserById(int id) {
        if (userList != null && userList.size() > 0) {
            for (User user : userList) {
                if (id == user.getUs_id()) return user;
            }
        }
        return null;
    }

    // INSTANCE
    public static SingletonData getInstance() {
        SingletonData localInstance = instance;
        if (localInstance == null) {
            synchronized (SingletonData.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SingletonData();
                }
            }
        }
        return localInstance;
    }

}
