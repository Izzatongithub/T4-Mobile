package com.example.crudmahasiswa.utils

import android.content.Context
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader

object FileHelper {

    private fun getFileName(studentNim: String) = "note_$studentNim.txt"

    fun saveNote(context: Context, studentNim: String, content: String) {
        context.openFileOutput(getFileName(studentNim), Context.MODE_PRIVATE).use {
            it.write(content.toByteArray())
        }
    }

    fun loadNote(context: Context, studentNim: String): String {
        val sb = StringBuilder()

        try {
            context.openFileInput(getFileName(studentNim)).use { input ->
                BufferedReader(InputStreamReader(input)).use { reader ->
                    reader.forEachLine {
                        sb.append(it).append("\n")
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            return ""
        }

        return sb.toString().trim()
    }

    fun deleteNote(context: Context, studentNim: String): Boolean {
        return context.deleteFile(getFileName(studentNim))
    }

    fun isNoteExists(context: Context, studentNim: String): Boolean {
        return context.getFileStreamPath(getFileName(studentNim)).exists()
    }

    fun getNoteSize(context: Context, studentNim: String): Long {
        val file = context.getFileStreamPath(getFileName(studentNim))
        return if (file.exists()) file.length() else 0
    }
}