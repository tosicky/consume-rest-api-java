package com.example.consumerestapijava.service;

import com.example.consumerestapijava.models.Friend;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FriendService {
    private Set<Friend> friendList;

    public FriendService() {
        this.friendList = new HashSet<>();
    }

    public Set<Friend> getFriendList(){
        return friendList;
    }

    public Set<Friend> addFriend(Friend friend){
        if(friend==null || friend.getId()=="" || friend.getName()==""){
            throw new IllegalArgumentException("Friend ID and Notes cannot be empty.");
        }
       friendList.add(friend);
        return friendList;
    }

    public Set<Friend> updateFriend(String id,Friend friend){
        if(friend==null || friend.getId()==""){
            throw new IllegalArgumentException("Friend ID cannot be empty");
        }
        Optional<Friend> f = friendList.stream().filter(r->r.getId().equals(id)).findFirst();
        if(f.isEmpty()){
            friendList.add(friend);
        }else {
           Friend friendToUpdate= f.get();
            friendList.remove(f.get());
            friendToUpdate.setId(friend.getId());
            friendToUpdate.setName(friend.getName());
            friendToUpdate.setNotes(friend.getNotes());
            friendList.add(friendToUpdate);
        }

        return friendList;
    }

    public void deleteFriend(String id){
        Optional<Friend> optionalFriend= friendList.stream().filter(f->f.getId().equals(id)).findFirst();
        if(optionalFriend.isPresent()){
           friendList.remove(optionalFriend.get());
        }
    }
}
