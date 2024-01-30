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
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;

public class DeleteScanner {
    
    public static void main(String[] args) {
        // Establecer conexión con MongoDB
        MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        Bson projectionFields = Projections.excludeId(); // Excluye el campo _id
        MongoCursor<Document> cursor = collection.find()
                .projection(projectionFields).iterator();
        try {
             while(cursor.hasNext()) {
                 System.out.println(cursor.next().toJson());
             }
        } finally {
             cursor.close();
        }
        Scanner scanner = new Scanner(System.in);
        String campo = null;
        String valor;
        
        List<String> campos = new ArrayList<>();
        List<String> valores = new ArrayList<>();
        System.out.println("");
        System.out.println("Ingrese campos y posteriormente sus valores, cuando escribas fin se van a eliminar todos los registros en los que coincidan los datos introducidos");        
        System.out.print("Ingrese un campo o escriba 'fin' para terminar: ");
        campo = scanner.nextLine();
        // Loop para permitir al usuario ingresar múltiples campos y valores
        while (!"fin".equalsIgnoreCase(campo)) {
            if ("fin".equalsIgnoreCase(campo)) {
                if (campos.isEmpty()) {
                    System.out.println("No se ha ingresado ningún campo. Saliendo del programa...");
                    scanner.close();
                    mongoClient.close();
                    return; // Salir del método main si no se ha ingresado ningún campo
                } 
            }
            
            System.out.println("Ingrese el valor para " + campo + ": ");
            valor = scanner.nextLine().trim();
            
            // Agregar campos y valores a las listas
            campos.add(campo);
            valores.add(valor);
            
            
            System.out.print("Ingrese un campo o escriba 'fin' para terminar: ");
            campo = scanner.nextLine();
        }
        
        // Crear un filtro combinando todas las condiciones con un AND
        Document filtro = new Document();
        for (int i = 0; i < campos.size(); i++) {
            campo = campos.get(i);
            valor = valores.get(i);
            filtro.append(campo, valor);
        }
        
        // Eliminar documentos basados en el filtro
        DeleteResult result = collection.deleteMany(filtro);
        
        // Imprimir el número de documentos eliminados
        System.out.println("Se han eliminado todos los registros que coinciden con los criterios proporcionados.");
        System.out.println("Se eliminaron " + result.getDeletedCount() + " registros.");
        
        System.out.println("Fin de la eliminación");
        scanner.close();
        mongoClient.close();
    }
}
