package Taller;

import org.bson.Document;

import java.util.Scanner; // Importar la clase Scanner para leer la entrada del usuario

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class InsertScanner {
    
    public static void main(String[] args) {        
        MongoClient mongoClient = MongoDB.getClient();
        MongoDatabase database = mongoClient.getDatabase("Taller");
        MongoCollection<Document> collection = database.getCollection("Taller");
        
        // Crear un nuevo documento con valores predeterminados
        Document nuevoDocumento = new Document("Reparacion", "Cambio de Rueda")
                .append("Trabajador", "Daniel")
                .append("year", 2012);
        
        // Solicitar al usuario que ingrese los valores adicionales
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese un valor adicional para el campo (o escriba 'fin' para terminar): ");
        String campo = scanner.nextLine();
        
        // Leer valores adicionales del usuario hasta que ingrese "fin"
        while (!campo.equalsIgnoreCase("fin")) {
            System.out.print("Ingrese el valor para " + campo + ": ");
            campo = campo + ":";
            String valor = scanner.nextLine();
            nuevoDocumento.append(campo, valor);
            System.out.print("Ingrese un valor adicional para el campo (o escriba 'fin' para terminar): ");
            campo = scanner.nextLine();
        }
        
        // Insertar el nuevo documento en la colección
        collection.insertOne(nuevoDocumento);
        
        System.out.println("Documento insertado correctamente.");
    }

}

