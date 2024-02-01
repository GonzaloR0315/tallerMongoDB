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
                Projections.include("Reparacion", "Trabajador" , "year", "Horas"),
                Projections.excludeId());
        Document doc = collection.find(eq("Reparacion", "Cambio de Rueda"))
                .projection(projectionFields)
                .sort(Sorts.descending("Trabajador.Daniel"))
                .first();
        if (doc == null) {
            System.out.println("No results found.");
        } else {
            System.out.println(doc.toJson());
        }
	}

}
