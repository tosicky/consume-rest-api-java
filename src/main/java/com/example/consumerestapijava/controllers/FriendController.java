package com.example.consumerestapijava.controllers;

import com.example.consumerestapijava.models.Friend;
import com.example.consumerestapijava.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class FriendController {

    private FriendService friendService;

    @Autowired
    HttpServletRequest request;

    @Autowired
    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/friends")
    public Set<Friend> getFriends() {
        var headers = request.getHeader("Authorization");
        var session = request.getSession().getId();
        return friendService.getFriendList();
    }

    @PostMapping("/friend")
    public Set<Friend> addFriend(@RequestBody Friend friend) {
        return friendService.addFriend(friend);
    }

    @PutMapping("/friend/{id}")
    public Set<Friend> updateFriend(@PathVariable String id, @RequestBody Friend friend) {
        return friendService.updateFriend(id, friend);
    }

    @DeleteMapping("/friend/{id}")
    public ResponseEntity<Void> deleteFriend(@PathVariable String id) {
        friendService.deleteFriend(id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
