package com.javierproyect.pistio;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.Transliterator;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Header;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;

public class PDF {
    private Font fuenteBold = new Font(Font.FontFamily.COURIER,35,Font.BOLD);
    private Font fuenteNormal = new Font(Font.FontFamily.COURIER,40,Font.NORMAL);
    private Font fuenteItalic = new Font(Font.FontFamily.COURIER,35,Font.ITALIC);
    private Context view;
    private  File PdfFile;


    public PDF(Context view){
        this.view=view;

    }
    public void mPDF(String header, String info, String footer, String Salida ) throws IOException, DocumentException {
        createFile(Salida);
        Document documento = new Document(PageSize.A4);
        PdfWriter.getInstance(documento,new FileOutputStream(PdfFile));
        documento.open();
        documento.add(getHeader("\n\n\n\n\n\n"+header));
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), R.drawable.pdfimage);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        Image imagen = Image.getInstance(stream.toByteArray());
        imagen.scaleToFit(300, 300);
        imagen.setAlignment(Element.ALIGN_CENTER);
        documento.add(imagen);
        documento.add(getInf("\n\n\n\n"+info));
        documento.add(getFooter("\n\n\n\n"+footer));
        documento.add(getFooter("\n\n\n"+Salida));
        documento.close();


    }

    private Paragraph getHeader(String texto){
        Paragraph p = new Paragraph();
        Chunk c= new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.setFont(fuenteNormal);
        c.append(texto);
        p.add(c);
        return p;
    }
    private Paragraph getInf(String texto){
        Paragraph p = new Paragraph();
        Chunk c= new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(texto);
        c.setFont(fuenteBold);
        p.add(c);
        return p;
    }
    private Paragraph getFooter(String texto){
        Paragraph p = new Paragraph();
        Chunk c= new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(texto);
        c.setFont(fuenteItalic);
        p.add(c);
        return p;
    }






    private void createFile(String name){
        File folder= new File(Environment.getExternalStorageDirectory().toString(),"PISTIO");
        if(!folder.exists()){
            folder.mkdir();

        }
        PdfFile=new File(folder,name+".pdf");
        Toast.makeText(view,"Creado",Toast.LENGTH_LONG).show();

    }
}
