package Taller;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertDefault {
    
    public static void main(String[] args) {        
        MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        
        // Crear un nuevo documento para insertar en la colección
        Document nuevoDocumento = new Document("Reparacion", "Para golpes delantero")
                .append("Trabajador", "Jon")
                .append("year", 2022)
                .append("Horas", 15);

		// Insertar el nuevo documento en la colección
        collection.insertOne(nuevoDocumento);
        
        System.out.println("Documento insertado correctamente.");
    }

}

