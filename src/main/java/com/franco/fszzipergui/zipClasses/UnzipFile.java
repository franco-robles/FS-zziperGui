package com.franco.fszzipergui.zipClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile {

    /**
     * 1.1: recibir el path del archivo a descomprimir
     * 1.2: establecer el path donde se va a descomprimir
     *
     *2.1: establecer la cantidad de bytes que se val a leer( es un byte[])
     *2.2: crear el flujo de entrada de un zip, a partir de un file inut stream
     *2.3: obtener la siguiente entrada del zipInput
     *2.4: un ciclo while que descomprima hasta quedar sin entradas del zipInput
     *
     * 3.1: ciclo while:
     *
     */
    public void unzipAnyFile(String zipName) throws IOException {
        //que se decomprima en la carpeta donde esta el archivo
        File destDir = new File(Paths.get(zipName).getParent().toString());

        byte[] buffer = new byte[1024];
        ZipInputStream zipInpStm =  new ZipInputStream(new FileInputStream(zipName));

        ZipEntry zipEntry = zipInpStm.getNextEntry();

        while(zipEntry != null){
            File fileUnzip = new File(destDir, zipEntry.toString());
            if(zipEntry.isDirectory()){
                if (!fileUnzip.isDirectory() && !fileUnzip.mkdirs()) {
                    throw new IOException("Failed to create directory " + fileUnzip);
                }
            }else{
                // fix for Windows-created archives
                File parent  =  fileUnzip.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }
                // write file content
                FileOutputStream fos = new FileOutputStream(fileUnzip);
                int len;
                while((len=zipInpStm.read(buffer))>0){
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zipInpStm.getNextEntry();
        }

        zipInpStm.closeEntry();
        zipInpStm.close();
    }

}
