package db;

/**
 * Created by maqiyue on 2018/5/19
 */

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Insert {
    public static MongoCollection<Document> getCollectIon(String colliectionName) {
        MongoCollection<Document> collection = null;
        try {
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            MongoDatabase mongoDatabase = mongoClient.getDatabase("GraduationDesign");
             collection = mongoDatabase.getCollection(colliectionName);


        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return collection;
    }

    public void insert(Map<String,Object> map,String colliectionName){
        MongoCollection<Document> collection = getCollectIon(colliectionName);
        Document document = new Document(map);
        List<Document> documents = new ArrayList<Document>();
        documents.add(document);
        collection.insertMany(documents);

    }
}

