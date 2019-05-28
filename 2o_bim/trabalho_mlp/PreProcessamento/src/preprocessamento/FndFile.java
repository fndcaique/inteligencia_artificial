package preprocessamento;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fnd
 */
public class FndFile {

    private File file;
    private RandomAccessFile raf;

    public FndFile(String path) throws FileNotFoundException {
        file = new File(path);
    }
    
    public FndFile(URI uri) throws FileNotFoundException{
        file = new File(uri);
    }

    public FndFile(File file) {
        this.file = file;
    }
    
    

    /**
     returno um objeto FndFile ou null caso ocorra algum erro
     * @param path
     * @return 
     */
    public static FndFile newFile(String path) {
        try {
            return new FndFile(path);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean exists(){
        return file.exists();
    }
    
    public boolean open(String mode){
        try {
            raf = new RandomAccessFile(file.getAbsolutePath(), mode);
            return true;
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeInt(int v) {
        try {
            raf.writeInt(v);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeDouble(double v) {
        try {
            raf.writeDouble(v);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeString(String s) {
        try {
            raf.writeChars(s);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeByte(byte b) {
        try {
            raf.writeByte(b);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeBytes(byte[] b) {
        try {
            raf.write(b);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeChars(char[] c) {
        try {
            for (char d : c) {
                raf.writeChar(d);
            }
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean writeChar(char c) {
        try {
            raf.writeChar(c);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean writeBoolean(boolean b){
        try {
            raf.writeBoolean(b);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public int readInt() {
        try {
            return raf.readInt();
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return Integer.MIN_VALUE;
        }
    }

    public double readDouble() {
        try {
            return raf.readDouble();
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return Double.MIN_VALUE;
        }
    }

    public String readLine() {
        try {
            return raf.readLine();
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public String readFile() {
        try {
            byte[] all = new byte[(int) raf.length()];
            raf.read(all);
            return new String(all);
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public char readChar() {
        try {
            return raf.readChar();
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return Character.MIN_VALUE;
        }
    }
    
    public byte readByte(){
        try {
            return raf.readByte();
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return Byte.MIN_VALUE;
        }
    }

    public boolean seek(long pos) {
        try {
            raf.seek(pos);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean setLength(long newlen) {
        try {
            raf.setLength(newlen);
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public long getLength() {
        try {
            return raf.length();
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    
    public String getPath(){
        return file.getPath();
    }
    
    public String getAbsolutePath(){
        return file.getAbsolutePath();
    }
    
    public String getCanonicalPath(){
        try {
            return file.getCanonicalPath();
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public String getName(){
        return file.getName();
    }
    
    public boolean delete(){
        return file.delete();
    }
    
    public boolean close(){
        try {
            raf.close();
            return true;
        }
        catch (IOException ex) {
            Logger.getLogger(FndFile.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    
}
