package com.example.demo.MyData.JsonObject;

import com.example.demo.MyData.Entity.TalkRecord;
import lombok.Data;

import java.util.List;
@Data
public class FriendMessage {
    int friendId;
    List<TalkRecord> charRecords;
    List<TalkRecord> newRecords;
}
