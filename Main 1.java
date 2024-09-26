import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        double totalBubbleSortTime = 0.0;
        double totalBucketSortTime = 0.0;
        int totalWordsExamined = 0;
        String lastWord = null;

        try {
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Cesar\\Downloads\\palabras\\palabras.es"));

            String line;
            int block = 1;
            while ((line = reader.readLine()) != null) {
                List<String> words = new ArrayList<>();
                int count = 0;
                do {
                    words.add(line);
                    lastWord = line;
                    count++;
                    totalWordsExamined++;
                    if (count >= 10000) break;
                } while ((line = reader.readLine()) != null);

                String[] wordsArray1 = words.toArray(new String[0]);
                String[] wordsArray2 = words.toArray(new String[0]);

                long startTime = System.nanoTime();
                bubbleSort(wordsArray1);
                long endTime = System.nanoTime();
                long durationInNano = (endTime - startTime);  // Tiempo de ejecución en nanosegundos
                double durationInSeconds = durationInNano / 1_000_000_000.0;  // Tiempo de ejecución en segundos
                totalBubbleSortTime += durationInSeconds;
                System.out.println("Tiempo de ejecución de Bubble Sort para el bloque " + block + ": " + String.format("%.10f", durationInSeconds) + " segundos");

                startTime = System.nanoTime();
                bucketSort(wordsArray2);
                endTime = System.nanoTime();
                durationInNano = (endTime - startTime);  // Tiempo de ejecución en nanosegundos
                durationInSeconds = durationInNano / 1_000_000_000.0;  // Tiempo de ejecución en segundos
                totalBucketSortTime += durationInSeconds;
                System.out.println("Tiempo de ejecución de Bucket Sort para el bloque " + block + ": " + String.format("%.10f", durationInSeconds) + " segundos");

                block++;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Tiempo total de ejecución de Bubble Sort: " + String.format("%.10f", totalBubbleSortTime) + " segundos");
        System.out.println("Tiempo total de ejecución de Bucket Sort: " + String.format("%.10f", totalBucketSortTime) + " segundos");
        System.out.println("La cantidad de palabras examinadas fue de: " + totalWordsExamined);
        System.out.println("La última palabra del archivo fue: " + lastWord);
    }

    public static void bubbleSort(String[] arr) {
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (arr[j].compareTo(arr[j+1]) > 0) {
                    // Intercambiar arr[j+1] y arr[j]
                    String temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
    }

    public static void bucketSort(String[] arr) {
        int n = arr.length;
        if (n <= 0)
            return;

        List<String>[] bucket = new List[n];

        // Crear cubos vacios
        for (int i = 0; i < n; i++)
            bucket[i] = new LinkedList<>();

        // añadir elementos en los cubos
        for (int i = 0; i < n; i++) {
            String s = arr[i];
            int bucketIndex = s.length() * n / (n + 1); // Distribute elements by length
            bucket[bucketIndex].add(s);
        }

        // Ordenar los elementos de cada cubo
        for (int i = 0; i < n; i++) {
            Collections.sort(bucket[i]);
        }

        int index = 0;
        // mezclar los cubos
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < bucket[i].size(); j++) {
                arr[index++] = bucket[i].get(j);
            }
        }
    }
}