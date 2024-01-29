package Taller;
import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class FindOne {
	
	public static void main(String[] args) {	
		MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        Bson projectionFields = Projections.fields(
                Projections.include("title", "imdb"),
                Projections.excludeId());
        Document doc = collection.find(eq("title", "The Room"))
                .projection(projectionFields)
                .sort(Sorts.descending("imdb.rating"))
                .first();
        if (doc == null) {
            System.out.println("No results found.");
        } else {
            System.out.println(doc.toJson());
        }
	}

}
