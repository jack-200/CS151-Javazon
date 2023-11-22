//package edu.sjsu.cs.cs151javazon;
//
//import java.io.*;
//import java.util.ArrayList;
//
//public class Loader {
//    public <U> ArrayList<Object> deserializeArrList(Object obj, U classManager, String file) {
//        ArrayList<Object> deserializedList;
//        try {
//            FileInputStream fileInputStream = new FileInputStream(file);
//            ObjectInputStream in = new ObjectInputStream(fileInputStream);
//            deserializedList = (ArrayList<Object>) in.readObject();
//
//            classManager.numUsers = deserializedList.size();
//            return deserializedList;
//        } catch (EOFException | ClassNotFoundException e) {
//            return new ArrayList<>();
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
