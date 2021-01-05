package com.example.demo.MyData.Dao;


import com.example.demo.MyData.Entity.Friend;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public interface FriendDao {
     List<Friend> getFriendList(int userId);
     void addFriend(Friend friend);
     void updateLastTalkTime(Friend friend);
     Date getLastTalkTime(int userId,int friendId);
}
