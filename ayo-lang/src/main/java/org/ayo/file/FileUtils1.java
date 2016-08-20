package org.ayo.file;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;

/**
 * Provides operations with files
 * 
 */
public final class FileUtils1 {

    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 KB
    public static final int CONTINUE_LOADING_PERCENTAGE = 75;
    private final static String FILE_EXTENSION_SEPARATOR = ".";

    private FileUtils1() {
    }

    public static void copyStream(InputStream is, OutputStream os) throws IOException {
        copyStream(is, os, null);
    }

    public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener) throws IOException {
        return copyStream(is, os, listener, DEFAULT_BUFFER_SIZE);
    }

    /**
     * Copies stream, fires progress events by listener, can be interrupted by listener.
     *
     * @param is         Input stream
     * @param os         Output stream
     * @param listener   Listener of copying progress and controller of copying interrupting
     * @param bufferSize Buffer size for copying, also represents a step for firing progress listener callback, i.e.
     *                   progress event will be fired after every copied <b>bufferSize</b> bytes
     * @return <b>true</b> - if stream copied successfully; <b>false</b> - if copying was interrupted by listener
     * @throws IOException
     */
    public static boolean copyStream(InputStream is, OutputStream os, CopyListener listener, int bufferSize) throws IOException {
        int current = 0;
        final int total = is.available();
        final byte[] bytes = new byte[bufferSize];
        int count;
        if (shouldStopLoading(listener, current, total)) return false;
        while ((count = is.read(bytes, 0, bufferSize)) != -1) {
            os.write(bytes, 0, count);
            current += count;
            if (shouldStopLoading(listener, current, total)) return false;
        }
        return true;
    }

    private static boolean shouldStopLoading(CopyListener listener, int current, int total) {
        if (listener != null) {
            boolean shouldContinue = listener.onBytesCopied(current, total);
            if (!shouldContinue) {
                if (100 * current / total < CONTINUE_LOADING_PERCENTAGE) {
                    return true; // if loaded more than 75% then continue loading anyway
                }
            }
        }
        return false;
    }


    /**
     * Reads all data from stream and close it silently
     *
     * @param is Input stream
     */
    public static void readAndCloseStream(InputStream is) {
        final byte[] bytes = new byte[DEFAULT_BUFFER_SIZE];
        try {
            while (is.read(bytes, 0, DEFAULT_BUFFER_SIZE) != -1) {
            }
        } catch (IOException e) {
            // Do nothing
        } finally {
            closeSilently(is);
        }
    }

    public static void closeSilently(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            // Do nothing
        }
    }

    /** Listener and controller for copy process */
    public static interface CopyListener {
        /**
         * @param current Loaded bytes
         * @param total   Total bytes for loading
         *
         * @return <b>true</b> - if copying should be continued; <b>false</b> - if loading should be interrupted
         */
        boolean onBytesCopied(int current, int total);
    }

    public static boolean copyFile(File source, File target) {
        FileInputStream fin = null;
        FileOutputStream fout = null;
        try {
            if (!target.exists()) {
                target.createNewFile();
            }
            fin = new FileInputStream(source);
            fout = new FileOutputStream(target);
            copyStream(fin, fout);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fin != null) {
                    fin.close();
                }
                if (fout != null) {
                    fout.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Delete the earliest file in the specified directory
     * 
     * @param dir
     *            The specified directory
     * @param exceptFile
     *            Exclude the file name
     */
    public static final void deleteEarliestFile(File dir, String exceptFile) {
        if (dir != null && dir.isDirectory()) {
            File earlyFile = null;
            File[] files = dir.listFiles();
            if (files.length == 0)
                return;
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                if (f.getName().equals(exceptFile))
                    continue;
                if (earlyFile == null) {
                    earlyFile = files[i];
                    continue;
                }
                if (earlyFile.lastModified() > f.lastModified()) {
                    earlyFile = f;
                }
            }
            if (earlyFile != null)
                earlyFile.delete();
        }
    }

    /**
     * Read file
     * 
     * @param filePath
     *            The path of the file
     * @return StringBuilder Return file content as StringBuffer, if the file
     *         doesn't exist return null
     */
    public static StringBuilder readFile(String filePath) {
        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file != null && file.isFile()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (!fileContent.toString().equals("")) {
                        fileContent.append("\r\n");
                    }
                    fileContent.append(line);
                }
                reader.close();
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException("IOException occurred. ", e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Write file
     * 
     * @param filePath
     *            The path of the file
     * @param content
     *            The content of the file
     * @param append
     *            If append to the end of the file, true : append��false :
     *            overwrite
     * @return
     */
    public static boolean writeFile(String filePath, String content, boolean append) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * Write file
     * 
     * @param filePath
     *            The path of the file
     * @param stream
     *            Input content stream
     * @return
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        OutputStream o = null;
        try {
            o = new FileOutputStream(filePath);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            if (o != null) {
                try {
                    o.close();
                    stream.close();
                } catch (IOException e) {
                    throw new RuntimeException("IOException occurred. ", e);
                }
            }
        }
    }

    /**
     * Read file, one line as a element of the String List
     * 
     * @param filePath
     *            The path of the file
     * @return List<String> Return file content as a String List, if the file
     *         doesn't exist return null
     */
    public static List<String> readFileToList(String filePath) {
        File file = new File(filePath);
        List<String> fileContent = new ArrayList<String>();
        if (file != null && file.isFile()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line);
                }
                reader.close();
                return fileContent;
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        throw new RuntimeException("IOException occurred. ", e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get file name from the path (without extension)
     * 
     * @param filePath
     *            The path of the file
     * @return String File name without extension
     * @see <pre>
     *      getFileNameWithoutExtension(null)               =   null
     *      getFileNameWithoutExtension("")                 =   ""
     *      getFileNameWithoutExtension("   ")              =   "   "
     *      getFileNameWithoutExtension("abc")              =   "abc"
     *      getFileNameWithoutExtension("a.mp3")            =   "a"
     *      getFileNameWithoutExtension("a.b.rmvb")         =   "a.b"
     *      getFileNameWithoutExtension("/home/admin")      =   "admin"
     *      getFileNameWithoutExtension("/home/admin/a.txt/b.mp3")  =   "b"
     * </pre>
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0, extenPosi));
        } else {
            if (extenPosi == -1) {
                return filePath.substring(filePosi + 1);
            } else {
                return (filePosi < extenPosi ? filePath.substring(filePosi + 1, extenPosi) : filePath.substring(filePosi + 1));
            }
        }
    }

    /**
     * Get file name from the path (with extension)
     * 
     * @param filePath
     *            The path of the file
     * @return String File name with extension
     * @see <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return filePath;
        }
        return filePath.substring(filePosi + 1);
    }

    /**
     * Get folder name from the path
     * 
     * @param filePath
     *            The path of the file
     * @return String The folder path
     * @see <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     */
    public static String getFolderName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return "";
        }
        return filePath.substring(0, filePosi);
    }

    /**
     * Get the extension name from the path
     * 
     * @param filePath
     *            The path of the file
     * @return String The extension name of the file
     * @see <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   ""
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     */
    public static String getFileExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        } else {
            if (filePosi >= extenPosi) {
                return "";
            }
            return filePath.substring(extenPosi + 1);
        }
    }

    public static boolean makeFolder(String filePath) {
        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    public static boolean isFolderExist(String directoryPath) {
        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }

    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (file.exists()) {
            if (file.isFile()) {
                return file.delete();
            } else if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    if (f.isFile()) {
                        f.delete();
                    } else if (f.isDirectory()) {
                        deleteFile(f.getAbsolutePath());
                    }
                }
                return file.delete();
            }
            return false;
        }
        return true;
    }

    public static long getFileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return -1;
        }
        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }

    public static byte[] inputStreamToByte(InputStream is) {
        try {
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = is.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            //WLog.i(FileUtils.class, "readFromFile: file not found");
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        if (offset < 0) {
            //WLog.e(FileUtils.class, "readFromFile invalid offset:" + offset);
            return null;
        }
        if (len <= 0) {
            //WLog.e(FileUtils.class, "readFromFile invalid len:" + len);
            return null;
        }
        if (offset + len > (int) file.length()) {
            //WLog.e(FileUtils.class, "readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len];
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
           // WLog.e(FileUtils.class, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }

    public static void saveSerializableObjectToFile(Object object, FileOutputStream fileOut) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Object readSerializableObjectFromFile(FileInputStream fileIn) {
        Object b = null;
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(fileIn);
            b = in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return b;
    }

}
