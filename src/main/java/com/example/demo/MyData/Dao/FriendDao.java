package com.example.demo.MyData.Dao;


import com.example.demo.MyData.Entity.Friend;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface FriendDao {
     List<Friend> getFriendList(int userId);
     void addFriend(Friend friend);
     void updateTimeStamp(Friend friend);
     Date getLastTalkTime(int userId,int friendId);
     Date getTimeStamp(int userId,int friendId);
     void updateUMSG(int userId,int friendId);
     void clearUMSG(int userId,int friendId);
}
