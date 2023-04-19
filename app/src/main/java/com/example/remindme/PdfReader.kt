package com.example.remindme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor

class PdfReader : AppCompatActivity() {

    lateinit var extractedTV: TextView
    lateinit var extractBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_reader)

        extractedTV = findViewById(R.id.idTVPDF)
        extractBtn = findViewById(R.id.idBtnExtract)

        extractBtn.setOnClickListener {
            Toast.makeText(this, "Clciked",Toast.LENGTH_SHORT).show()
            extractData()
        }
    }

    private fun extractData() {
        try {
            var extractedText = ""


            val pdfReader: PdfReader = PdfReader("res/raw/timetable.pdf")
            Toast.makeText(this, "after",Toast.LENGTH_SHORT).show()

            val n = pdfReader.numberOfPages


            for (i in 0..n) {

                Toast.makeText(this, "Inside for loop",Toast.LENGTH_SHORT).show()
                extractedText =
                    """
                 $extractedText${
                        PdfTextExtractor.getTextFromPage(pdfReader, i+1)
                    }
                  
                    """

                Toast.makeText(this, "Inside for loop 2",Toast.LENGTH_SHORT).show()

            }
            Toast.makeText(this, "After for loop",Toast.LENGTH_SHORT).show()

            extractedTV.text = extractedText
            pdfReader.close()

        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }
}