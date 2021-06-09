/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author HP
 */
public class Bulambo 
{
    private final String bucky = "stakeholder.ser";
    
    FileWriter fileWriter;
    PrintWriter printWriter;
    
    // classes where i can read my Stakeholder.ser
    FileInputStream fileinputstream ;
    ObjectInputStream objectinputstream;
    
    public void openFile(String MyFileName)
    {
        try
        {
            fileWriter = new FileWriter(new File(MyFileName));
            printWriter = new PrintWriter(fileWriter);
            System.out.println("build "+MyFileName);
            
        }
        catch (IOException ioe)
        {
            System.out.println("**Error"+ioe.getMessage());
            System.exit(1);
        }
    }
    
     // i'm reading supplier objects from stakeholder.ser. and add to ArrayListsuppliers
    private ArrayList<Supplier> suppliersList()
    {
        ArrayList<Supplier> ArrayListsuppliers = new ArrayList<>();
        
        try
        {
            fileinputstream = new FileInputStream(new File(bucky));
            objectinputstream = new ObjectInputStream(fileinputstream);
            // throws an EOFException
            while (true)
            {
            Object object = objectinputstream.readObject();
            if (object instanceof Supplier)
            {
                    ArrayListsuppliers.add((Supplier) object);
            }
            }
            
        } 
        
        catch (EOFException eofe)
        {
        } 
        
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("**Error "+e.getMessage());
        }
        
        finally
        {
        try
        {
            fileinputstream.close();
            objectinputstream.close();
        } 
            catch (IOException ioexception)
            {
             System.out.println("**Error"+ioexception.getMessage());
            }
        }
        
        // i'm checking if ArrayListsuppliers is not empty
        if (!ArrayListsuppliers.isEmpty())
        {
            //i'm sorting ArrayListsuppliers ascending according to the supplier Names
            Collections.sort(ArrayListsuppliers, (Supplier s1, Supplier s2) -> s1.getName().compareTo(s2.getName()));
        }
        
        return ArrayListsuppliers;
    }
    
     
    private void writeSupplierOutFile()
    {
        String first = "======================= SUPPLIERS =========================\n";
        String Second = "%s\t%-20s\t%-10s\t%-10s\n";
        String third = "===========================================================\n";
        
        try
        {
            printWriter.print(first);printWriter.printf(Second, "ID", "Name", "Prod Type","Description");printWriter.print(third);
            for (int value = 0; value < suppliersList().size(); value++)
            {
             printWriter.printf(Second,suppliersList().get(value).getStHolderId(),suppliersList().get(value).getName(),
             suppliersList().get(value).getProductType(),suppliersList().get(value).getProductDescription());
            }
            
        }
        catch (Exception exception)
        {
            System.out.println("**Error "+exception.getMessage());
        }
    }
    
    public void closeFile(String filename)
    {
    try
    {
         fileWriter.close();printWriter.close();
         System.out.println(filename + " has been closed");

     } 
    catch (IOException ki)
        {
           System.out.println("**Error Reading "+ki.getMessage());
        }
    }
    
    // driver code
    public static void main(String[] args)
    {
        Bulambo s = new Bulambo();
        s.openFile("supplierOutFile.txt");
        s.writeSupplierOutFile();
        s.closeFile("supplierOutFile.txt");
    }
}