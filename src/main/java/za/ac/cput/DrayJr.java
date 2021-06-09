
package za.ac.cput;
/*
 * FullName: MutambaPrinceBulambo
 * StudentN: 220177767
 * Date: 9 June 2021
 * Hour:01:39 PM
 */
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class DrayJr
{
private final String str = "stakeholder.ser";
    
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
            ioe.printStackTrace();
            System.exit(1);
        }
    }
    
    // read customer objects from Stakeholder.ser. and add to  ArrayListcustomers
    private ArrayList<Customer> customersList()
    {
        ArrayList<Customer>  ArrayListcustomers = new ArrayList<>();
        
        try
        {
            fileinputstream = new FileInputStream(new File(str));
            objectinputstream = new ObjectInputStream(fileinputstream);
            
            // throws an EOFException 
            while (true)
            {
                Object object = objectinputstream.readObject();
                if (object  instanceof Customer)
                {
                    ArrayListcustomers.add((Customer) object);
                }
            }
            
        } 
        catch (EOFException eofe)
        {
            
        } 
        catch (IOException | ClassNotFoundException exception)
        {
           exception.printStackTrace();
           System.exit(1);
            
        } 
        finally
        {
            try
            {
                fileinputstream.close();
                objectinputstream.close();
                
            } catch (IOException exception)
            {
                exception.printStackTrace();
            }
        }  
        // iam checking  if ArrayListcustomers is not Empty
        if (!ArrayListcustomers.isEmpty())
        {
            //Iam sorting ArrayListcustomers ascending according to the StHolderId
            Collections.sort(ArrayListcustomers,(Customer customer1, Customer customer2) -> customer1.getStHolderId().compareTo(customer2.getStHolderId()));
        }
        return ArrayListcustomers;
    }
    
    private void writeCustomerOutFile()
    {
        String line1 = "======================= CUSTOMERS =========================\n";
        String space = "%s\t%-10s\t%-10s\t%-10s\t%-10s\n";
        String line2 = "===========================================================\n";
        
        try
        {   
            printWriter.print(line1);
            printWriter.printf(space, "ID", "Name", "Surname", "Date Of Birth", "Age");
            printWriter.print(line2);
            
            for (int i = 0; i < customersList().size(); i++)
            {   
                printWriter.printf(space,customersList().get(i).getStHolderId(), customersList().get(i).getFirstName(),customersList().get(i).getSurName(),
                formatDate(customersList().get(i).getDateOfBirth()),CalculateAge(customersList().get(i).getDateOfBirth())
                );
            }
            printWriter.printf("\nNumber of customers who can rent: %d", canRent());
            printWriter.printf("\nNumber of customers who cannot rent: %d", canNotRent());
        } 
        catch (Exception e)
        {
           e.printStackTrace();
        }
    }
    
    private String formatDate(String formatdate)
    {
        // custom format
        DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("dd MMM yyyy",Locale.ENGLISH);
        LocalDate date = LocalDate.parse(formatdate); 

        return  date.format(datetimeformatter );
    }
    
    private int CalculateAge(String calculateAge)
    {
        LocalDate date= LocalDate.parse(calculateAge); 
        int Year  = date.getYear();
        ZonedDateTime CurrentlyDate = ZonedDateTime.now();
        int currentYear = CurrentlyDate.getYear();
        // Must return customer's Age
        return currentYear - Year;
    }
    
    private int canRent()
    {
        int WhocanRent = 0;
        
        for (int i = 0; i < customersList().size(); i++)
        {
            // i'm checking who can rent
            if (customersList().get(i).getCanRent())
            {
                WhocanRent += 1;
            }
        }
        
        return WhocanRent;
    }
    
    private int canNotRent()
    {
        int WhocanNotRent = 0;
        
        for (int i = 0; i < customersList().size(); i++)
        {
            // i'm checking who cann't rent
            if (!customersList().get(i).getCanRent())
            {
                WhocanNotRent += 1;
            }
        }
        return WhocanNotRent;
    }
    //i'm reading supplier objects from stakeholder.ser. and add to ArrayListsuppliers
   
    public void closeFile(String MyFileName)
    {
        try
        {
            fileWriter.close();
            printWriter.close();
            System.out.println(MyFileName + " has been closed");

        } catch (IOException ioexception)
        {
            System.out.println("**Error Reading My File***");
        }
    }
    
    //My main class or displaying
    public static void main(String[] args)
    {
        DrayJr Iam = new DrayJr();
        
        Iam.openFile("customerOutFile.txt");
        Iam.writeCustomerOutFile();
        Iam.closeFile("customerOutFile.txt");
        
    }
}

