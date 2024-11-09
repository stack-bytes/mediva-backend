package com.stackbytes.service;


import com.mongodb.client.MongoClient;
import com.mongodb.client.result.UpdateResult;
import com.stackbytes.model.Group;
import com.stackbytes.model.dto.GroupCreateRequestTdo;
import com.stackbytes.model.dto.GroupCreateResponseTdo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private final MongoTemplate mongoTemplate;

    GroupService(MongoTemplate mongoTemplate, MongoClient mongo) {
        this.mongoTemplate = mongoTemplate;
    }

    public GroupCreateResponseTdo createGroup(GroupCreateRequestTdo groupCreateRequestTdo) {

        Group newGroup = new Group(
                groupCreateRequestTdo.getName(),
                groupCreateRequestTdo.getDescription(),
                groupCreateRequestTdo.getOwnerId(),
                groupCreateRequestTdo.getMembers(),
                groupCreateRequestTdo.getRating(),
                groupCreateRequestTdo.getLocation(),
                Pair.of(groupCreateRequestTdo.getGeolocationX(), groupCreateRequestTdo.getGeolocationY())
        ); //add to rabbitmq


        Group insertedGroup = mongoTemplate.insert(newGroup);

        return new GroupCreateResponseTdo("Group inserted succesfully", insertedGroup.getId());
    }

    public boolean addUserToGroup(String userId, String groupId) {
        Update update = new Update().addToSet("membersId", userId);
        Query query = Query.query(Criteria.where("_id").is(groupId));

        try{
            UpdateResult updatedObject = mongoTemplate.updateFirst(query, update, Group.class);
            return updatedObject.getModifiedCount() > 0;
        } catch (Exception e){
            return false;
        }


    }

    public Group getGroupById(String groupId) {
        Group group = mongoTemplate.findById(groupId, Group.class);
        return group;
    }
}
