package com.stackbytes.controllers;

import com.stackbytes.model.Group;
import com.stackbytes.model.dto.GroupCreateRequestTdo;
import com.stackbytes.model.dto.GroupCreateResponseTdo;
import com.stackbytes.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.GroupPrincipal;

@RestController
@RequestMapping("/")
public class GroupController {

    private final GroupService groupService;

    GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @CrossOrigin
    @PostMapping("/")
    public ResponseEntity<GroupCreateResponseTdo> createGroup(@RequestBody GroupCreateRequestTdo groupCreateRequestTdo) {
        GroupCreateResponseTdo groupCreateResponseTdo = groupService.createGroup(groupCreateRequestTdo);
        return ResponseEntity.ok(groupCreateResponseTdo);
    }

    @CrossOrigin
    @PatchMapping("/")
    public ResponseEntity<String> addUserToGroup(@RequestParam String userId, @RequestParam String groupId) {
        return groupService.addUserToGroup(userId, groupId)  ?
                ResponseEntity.ok("User added to group")
                        :
                ResponseEntity.badRequest().body("User not added to group");
    }

    @CrossOrigin
    @GetMapping("/")
    public ResponseEntity<Group> getGroup(@RequestParam String groupId) {
        return ResponseEntity.ok(groupService.getGroupById(groupId));
    }

}
