package Taller;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

public class DeleteDefault {
    
    public static void main(String[] args) {
        // Establecer conexión con MongoDB
        MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        String campo = "Nuevo empleado";
        String valor = "Juan Pablo";
        // Definir el criterio de eliminación, por ejemplo, eliminar documentos donde el campo "nombre" sea igual a "Juan"
        DeleteResult result = collection.deleteMany(Filters.eq(campo, valor));
        
        // Imprimir el número de documentos eliminados
        System.out.println("Se han eliminado todos los registros en los que " + campo + " era " + valor);
        System.out.println("Se eliminaron " + result.getDeletedCount() + " registros.");
        
        // Cerrar la conexión con MongoDB
        mongoClient.close();
    }

}
