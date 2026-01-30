import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
public class GestionAlumnos {
    public static void main(String[] args) {
        Map<String, List<Double>> alumnos = new HashMap<>();
//lee el fichero
        try (BufferedReader lector = new BufferedReader(new FileReader("Archivo.txt"))) {

            String linea;
            while ((linea = lector.readLine()) != null) {
                //va leyendo el fichero y lo separa por los ";"
                String[] partes = linea.split(";");
                //dice que nombre va a ser la 1º parte y nota la 2ª
                String nombre = partes[0];
                double nota = Double.parseDouble(partes[1]);
                //inserta las notas y nombres
                alumnos.computeIfAbsent(nombre, Notas -> new ArrayList<>()).add(nota);
            }
        }catch (Exception e){
            System.out.println("Error en el archivo");
        }
        System.out.println("Lista de alumnos: ");
        for (String nombre : alumnos.keySet()) {
            System.out.println(nombre + ": " + alumnos.get(nombre));
        }
        System.out.println();
//creamos el mapa de medias
        Map<String, Double> medias = new HashMap<>();
//para cada alumno se pasan todas sus notas a un metodo para calcular las notas
        alumnos.forEach((nombre, listaNotas) -> {
           medias.put(nombre, calcularMedia(listaNotas));
        });

        System.out.println("Alumnos con medias: ");
        for (String nombre : alumnos.keySet()) {
            System.out.println(nombre + " " + medias.get(nombre));
        }
        System.out.println();

        List<String> aprobados = new ArrayList<>();
//si la nota media es mayor a 5, el alumno estará aprobdo y entrara en esta lista
        System.out.println("Alumnos aprobados: ");
        alumnos.forEach((nombre, listaNotas) -> {
            if (medias.get(nombre)>=5){
                aprobados.add(nombre);
                System.out.println(nombre);
            }
        });
        System.out.println();

        List<Map.Entry<String, Double>> ordenados = new ArrayList<>(medias.entrySet());
//pasa el mapa con las medias a una lista y la ordena para que salga primero el valor mas alto
        ordenados.sort(Comparator.comparing(Map.Entry<String, Double>::getValue).reversed());

        System.out.println("Alumnos ordenados por medias:");
        for (Map.Entry<String, Double> entry : ordenados) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        //muestra al alumno con mejor media
        System.out.println("Alumno con mejor media: ");

        System.out.println(ordenados.getFirst());

        try(BufferedWriter escritor = new BufferedWriter(new FileWriter("resultado.txt"))){

            escritor.write("Alumnos aprobados: ");
            escritor.newLine();
            for (String nombre : aprobados) {
                escritor.write(nombre);
                escritor.newLine();
            }
        }catch (Exception e){
            System.out.println("Error en el archivo resultado");
        }
    }

    public static double calcularMedia(List<Double> notas) {
        //va sumando las notas de cada alumno y al final las divide por el número de notas de cada alumno
        Iterator<Double> iterator = notas.iterator();
        double media = 0;
        int contador = 0;
        while (iterator.hasNext()) {
            media += iterator.next();
            contador ++;
        }
        media = media/contador;
        return media;
    }
}