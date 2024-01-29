package Taller;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBHandler {

    private final MongoClient mongoClient;
    private final MongoDatabase database;

    public MongoDBHandler(String connectionString, String databaseName) {
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase(databaseName);
    }

    public void createCollection(String collectionName) {
        database.createCollection(collectionName);
        System.out.println("Colección '" + collectionName + "' creada exitosamente.");
    }

    public void closeConnection() {
        mongoClient.close();
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        MongoDBHandler mongoDBHandler = new MongoDBHandler("mongodb+srv://gonzalorubio:Moral0315@cluster0.w4wdn0h.mongodb.net/", "Taller");
        mongoDBHandler.createCollection("Taller");
        mongoDBHandler.closeConnection();
    }
}

