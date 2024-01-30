package Taller;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

public class FindAll {
	public static void main(String[] args) {
		MongoClient mongoClient = MongoDB.getClient();
	    MongoDatabase database = mongoClient.getDatabase("Taller");
	    MongoCollection<Document> collection = database.getCollection("Taller");
	    Bson projectionFields = Projections.excludeId(); // Excluye el campo _id
	    MongoCursor<Document> cursor = collection.find()
	            .projection(projectionFields).iterator();
	    try {
	         while(cursor.hasNext()) {
	             System.out.println(cursor.next().toJson());
	         }
	    } finally {
	         cursor.close();
	    }
	}
}
