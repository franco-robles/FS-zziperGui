package com.franco.fszzipergui.zipClasses;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class zziperFile {


    public void zipFileTxt(String pathFile) throws IOException {
        /**
         * 1.0 leer el nombre del archivo a comprimir
         * 1.1 elejir el  nombre del archivo que va  a tener el archivo comprimido "compressed.zip"
         * 1.2 usar la funcion zipoutputstream(variable que contiene el fileOtPutStream), es esta puedo usar write para guardar la info comprimida
         *
         * 2.0 usar File para abrir el archivo a comprimir
         * 2.1 crear el flujo de entrada del File
         * 2.2 usar zipEntry
         * 2.3 con el nombre del File to zip
         *
         * 3.1 crear un array de bytes para decir cauntos bytes quiero leer y escribir como maximo
         * 3.2 y con uun while voy a escribir mientras tenga datos en el array
         * 3.3
         *
         *
         */
        File archivo = new File(Paths.get(pathFile).getParent()+"/compressed.zip");
        if (archivo.exists()) {
            addFileToZip(pathFile);
        }else {
            String nameFile = pathFile;
            FileOutputStream FOStream = new FileOutputStream(Paths.get(pathFile).getParent() + "/compressed.zip");
            ZipOutputStream zipOut = new ZipOutputStream(FOStream);

            File fileToZip = new File(nameFile);
            FileInputStream fileInput = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry); //empieza escribiendo una nueva entada en el archivo zip y posicionando el stream en el comienzo de la entrada de datos

            byte[] bytes = new byte[1024];// 1kb
            int length;
            while ((length = fileInput.read(bytes)) > 0) {
                zipOut.write(bytes, 0, length);
            }

            zipOut.close();
            fileInput.close();
            FOStream.close();
        }
    }
    public void zipMultipleFiles(String ...files) throws IOException {
             FileOutputStream FOStream = new FileOutputStream(Paths.get(files[0]).getParent() + "/compressed.zip");
                ZipOutputStream zipOut = new ZipOutputStream(FOStream);

                for (String pathFile: files) {
                    File fileToZip = new File(pathFile);
                    FileInputStream fileInput = new FileInputStream(fileToZip);
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry); //empieza escribiendo una nueva entada en el archivo zip y posicionando el stream en el comienzo de la entrada de datos

                    byte[] bytes = new byte[1024];// 1kb
                    int length;
                    while ((length = fileInput.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                    fileInput.close();

                }
                zipOut.close();
                FOStream.close();
    }

    public void zipFolder(String sourceFile) throws IOException {
        FileOutputStream fos = new FileOutputStream("dirCompressed.zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        File fileToZip = new File(sourceFile);
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }

    private void zipFile(File fileToZip, String name, ZipOutputStream zipOut) {
        if (fileToZip.isHidden()) {
            return;
        }
    }

    private void addFileToZip(String file3){

        Map<String, String> env = new HashMap<>();
        env.put("create", "true");

        Path path = Paths.get(Paths.get(file3).getParent() + "/compressed.zip");
        URI uri = URI.create("jar:" + path.toUri());

        try (FileSystem fs = FileSystems.newFileSystem(uri, env)) {
            Path nf = fs.getPath(file3).getFileName();
            Files.write(nf, Files.readAllBytes(Paths.get(file3)), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
