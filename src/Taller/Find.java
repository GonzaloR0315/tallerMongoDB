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

public class Find {
	
	public static void main(String[] args) {
		MongoClient mongoClient = MongoDB.getClient();
		MongoDatabase database = mongoClient.getDatabase("Taller");
		MongoCollection<Document> collection = database.getCollection("Taller");
		Bson projectionFields = Projections.fields(
				Projections.include("Trabajador", "year", "Reparacion"),
				Projections.excludeId());
		MongoCursor<Document> cursor = collection.find()
				.projection(projectionFields)
				.sort(Sorts.descending("year")).iterator();
		try {
			while (cursor.hasNext()) {
			    Document doc = cursor.next();
			    String jsonOutput = "{";

			    // Verifica si el documento tiene al menos un campo
			    if (doc.keySet().isEmpty()) {
			        // Si no hay ningún campo, no imprimas nada
			        continue;
			    }

			    // Verifica si el campo "year" existe en el documento
			    if (doc.containsKey("year")) {
			        // Si existe, imprímelo normalmente
			        jsonOutput += "\"year\": " + doc.get("year") + ", ";
			    }

			    // Añade el resto de los campos al JSON de salida
			    for (String key : doc.keySet()) {
			        if (!key.equals("year")) {
			            jsonOutput += "\"" + key + "\": " + doc.get(key) + ", ";
			        }
			    }

			    // Elimina la última coma y espacio
			    if (jsonOutput.endsWith(", ")) {
			        jsonOutput = jsonOutput.substring(0, jsonOutput.length() - 2);
			    }

			    jsonOutput += "}";

			    System.out.println(jsonOutput);
			}

		} finally {
		     cursor.close();
		}
	}

}
