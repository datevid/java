package net.datvid.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class FileUtils {

    public static String createFileFromBytes(byte[] bytes, String nombreArchivo, String ruta) throws IOException {
        String separador = obtenerSeparadorRuta();
        String rutaCompleta = ruta + separador + nombreArchivo;

        FileOutputStream fos = new FileOutputStream(rutaCompleta);
        fos.write(bytes);
        fos.close();
        return rutaCompleta;
    }

    private static String obtenerSeparadorRuta() {
        return System.getProperty("file.separator");
    }

    private static String obtenerRutaCompleta(String nombreArchivo, String ruta) {
        String separador = obtenerSeparadorRuta();
        return ruta + separador + nombreArchivo;
    }

    public static void eliminarArchivo(String nombreArchivo, String ruta) throws IOException {
        String rutaCompleta = obtenerRutaCompleta(nombreArchivo, ruta);
        File archivo = new File(rutaCompleta);

        if (archivo.exists()) {
            archivo.delete();
            System.out.println("Archivo eliminado correctamente.");
        } else {
            System.out.println("El archivo no existe en la ruta especificada.");
        }
    }
    public static void createFilesFromByteArrays(List<byte[]> byteArrays, List<String> fileNames, String filePath) throws IOException {
        for (int i = 0; i < byteArrays.size(); i++) {
            byte[] bytes = byteArrays.get(i);
            String fileName = fileNames.get(i);
            createFileFromBytes(bytes, fileName,filePath);
        }
    }

    public static String getCurrentDirectory() {
        File currentDirectory = new File(".");
        String absolutePath = currentDirectory.getAbsolutePath();
        return absolutePath;
    }

    public static void main(String[] args) throws IOException {
        byte[] bytes = { 0x48, 0x65, 0x6C, 0x6C, 0x6F, 0x2C, 0x20, 0x77, 0x6F, 0x72, 0x6C, 0x64 };

        String nombreArchivo = "archivo.txt";
        String ruta = "D:\\directory";

        createFileFromBytes(bytes, nombreArchivo, ruta);
        eliminarArchivo(nombreArchivo, ruta);
    }
}
