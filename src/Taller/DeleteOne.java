package Taller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.DeleteResult;

public class DeleteOne {

    public static void main(String[] args) {
        // Establecer conexión con MongoDB
        MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        Bson projectionFields = Projections.excludeId(); // Excluye el campo _id
        MongoCursor<Document> cursor = collection.find().projection(projectionFields).iterator();

        // Mostrar los registros
        List<Document> documentos = new ArrayList<>();
        int index = 0;
        while (cursor.hasNext()) {
            Document document = cursor.next();
            documentos.add(document);
            System.out.println("[" + index + "] " + document.toJson());
            index++;
        }
        cursor.close();

        Scanner scanner = new Scanner(System.in);
        int selectedIndex;
        System.out.print("Seleccione el número del registro que desea eliminar -1 para cancelar: ");
        String valor = scanner.nextLine();

        while (!"-1".equalsIgnoreCase(valor)) {
            selectedIndex = Integer.parseInt(valor);
            if (selectedIndex >= 0 && selectedIndex < documentos.size()) {
                Document selectedDocument = documentos.get(selectedIndex);
                DeleteResult result = collection.deleteOne(selectedDocument);
                System.out.println("Se ha eliminado el registro seleccionado.");
                System.out.println("Registros eliminados: " + result.getDeletedCount());
            } else {
                System.out.println("Operación cancelada o índice fuera de rango. No se eliminó ningún registro.");
            }
            System.out.println("");
            System.out.print("Seleccione el número del registro que desea eliminar -1 para cancelar: ");
            valor = scanner.nextLine();
        }
        
        System.out.println("Fin del programa");
        // Cerrar recursos
        scanner.close();
        mongoClient.close();
    }
}
