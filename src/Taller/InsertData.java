package Taller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class InsertData {

    public static void main(String[] args) {
        // Establecer conexión con MongoDB
        MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        MongoCursor<Document> cursor = collection.find().iterator();

        // Mostrar los registros
        List<Document> documentos = new ArrayList<>();
        int index = 0;
        while (cursor.hasNext()) {
            Document document = cursor.next();
            documentos.add(document);
            ObjectId objectId = document.getObjectId("_id");
            String idString = (objectId != null) ? objectId.toHexString() : "null"; // Obtener la representación de la ID como String
            System.out.println("[" + index + "] " + document.toJson());
            index++;
        }
        cursor.close();

        Scanner scanner = new Scanner(System.in);
        int selectedIndex;
        System.out.print("Seleccione el número del registro que desea modificar (-1 para cancelar): ");
        String valor = scanner.nextLine();

        if (!"-1".equalsIgnoreCase(valor)) {
            selectedIndex = Integer.parseInt(valor);
            if (selectedIndex >= 0 && selectedIndex < documentos.size()) {
                // Obtener el documento seleccionado
                Document selectedDocument = documentos.get(selectedIndex);

                // Realizar modificaciones en el documento
                System.out.println("Ingrese los nuevos valores para el registro (escriba 'fin' para terminar):");

                String nuevoCampo;
                while (true) {
                    System.out.print("Nombre del nuevo campo (escriba 'fin' para terminar): ");
                    nuevoCampo = scanner.nextLine().trim();
                    if (nuevoCampo.equalsIgnoreCase("fin")) {
                        break;
                    }
                    System.out.print("Valor para el campo '" + nuevoCampo + "': ");
                    String valorCampo = scanner.nextLine().trim();
                    selectedDocument.put(nuevoCampo, valorCampo);
                }

                // Actualizar el documento existente
                collection.replaceOne(new Document("_id", selectedDocument.get("_id")), selectedDocument);
                System.out.println("Se ha modificado el registro seleccionado.");
            } else {
                System.out.println("Operación cancelada o índice fuera de rango. No se modificó ningún registro.");
            }
        }

        System.out.println("Fin del programa");
        // Cerrar recursos
        scanner.close();
        mongoClient.close();
    }
}
