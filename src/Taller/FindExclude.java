package Taller;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class FindExclude {
	
	public static void main(String[] args) {
		MongoClient mongoClient = MongoDB.getClient();
		MongoDatabase database = mongoClient.getDatabase("Taller");
		MongoCollection<Document> collection = database.getCollection("Taller");
		Bson projectionFields = Projections.fields(
				Projections.include("ManoObra", "Precio"),
				Projections.exclude("Ruedas"));
		MongoCursor<Document> cursor = collection.find(lt("runtime", 15))
				.projection(projectionFields)
				.sort(Sorts.descending("Precio")).iterator();
		try {
		     while(cursor.hasNext()) {
		         System.out.println(cursor.next().toJson());
		     }
		} finally {
		     cursor.close();
		}
	}

}
