package Taller;
import org.bson.Document;

import com.mongodb.MongoException;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Distinct {
	
	public static void main(String[] args) {
		MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        try {
            DistinctIterable<Integer> docs = collection.distinct("Reparacion", Filters.eq("Trabajador", "Daniel"), Integer.class);
            MongoCursor<Integer> results = docs.iterator();
            while(results.hasNext()) {
                System.out.println(results.next());
            }
        } catch (MongoException me) {
            System.err.println("An error occurred: " + me);
        }
	}

}
