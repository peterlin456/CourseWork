package edu.qc.seclass.replace;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Paths;
import java.io.FileWriter;

public class Main
{
  
  private static Charset charset = StandardCharsets.UTF_8;

  public static void main( String[] args ) throws Exception
  {
    
    ArrayList<String> replacelist = new ArrayList<>();
    Collections.addAll(replacelist, args);
    replace(replacelist);
    
  }

  private static void replace( ArrayList<String> args )
  {
	
    ArrayList<String> to = new ArrayList<>();
    ArrayList<String> from = new ArrayList<>();
    ArrayList<String> fileNames = new ArrayList<>();
    boolean fileNamesExist = false;
    boolean backup = false;
    boolean replaceFirst = false;
    boolean replaceLast = false;
    boolean ReplaceAll = false;
    
    //  index before file
    int BeforeFile = args.lastIndexOf("--");

    // check specified file names or "--" 
    if (  args.isEmpty()||BeforeFile == args.size()-1 || BeforeFile < 0 )
    {
      error();
      return;
    }
    
    // check if file exists
    for ( int i = BeforeFile; i < args.size(); i++ )
    {
      if ( args.get(i).equals("--") && i < args.size()-1 )
        fileNamesExist = true;
      else if ( fileNamesExist )
        fileNames.add(args.get(i));
      else{
    	  error();
          return;
      }
    }

  
    // check for OPT
    int opt = 0;
    for ( int i = 0; i < args.size(); i++ )
    {
      if ( args.get(i).equals("-f") )
        replaceFirst = true;
      else if ( args.get(i).equals("-l") )
        replaceLast = true;
      else if ( args.get(i).equals("-i") )
       ReplaceAll = true;
      else if ( args.get(i).equals("-b") )
        backup = true;
      // index after OPT found
      else if ( args.get(i).equals("--") || !args.get(i).equals("-f") || !args.get(i).equals("-l")|| !args.get(i).equals("-i")|| !args.get(i).equals("-b") ){
        opt = i;
        break;
      }
    }

   
    // if opt == -- , then ++. from and to , from is the start
    if ( args.get(opt).equals("--") ) opt++;
    for ( int i = opt, l = 0; i < BeforeFile; i++ )
    {
      if ( l == 0 )
      {
        from.add(args.get(i));
        l++;
      }
      else if ( l == 1 )
      {
        to.add(args.get(i));
        l--;
      }
      else
      {
        break;
      }
    }

   // check from and to
    if ( from.size() == 0 ||from.size()>1|| from.size() != to.size() || to.size() == 0 ||to.size()>1)
    {
      error();
      return;
    }
    

    replace(from, to, fileNames, replaceFirst, replaceLast, ReplaceAll, backup);
  }

  private static void replace( ArrayList<String> from, ArrayList<String> to, ArrayList<String> fileNames, boolean replaceFirst, boolean replaceLast,boolean ReplaceAll, boolean backup )
  {
	  for ( int j = 0; j < from.size(); j++ )
	    {
	      // check from
	      if ( from.get(j).equals("") )
	      {
	        error();
	        return;
	      }
	      // traverse and replace from string to to string
	      for ( int i = 0; i < fileNames.size(); i++ )
	      {
	        String inputFile = getFileContent(fileNames.get(i));
	        String outputFile = "";

        try
        {
          // backup inputFile
          if ( backup )
          {
           Path path = Paths.get(fileNames.get(i) + ".bck");
            if ( Files.notExists(path) )
            {
              File backupFile = new File(fileNames.get(i) + ".bck");
              FileWriter fileWriter = new FileWriter(backupFile, false);
              fileWriter.write(inputFile);
              fileWriter.close();
            } else 
            {
              String paths = path.toString();
              int sposition = paths.lastIndexOf("/") + 1;
              int dPosition = paths.lastIndexOf(".");
              System.err.println("Not performing replace for " + paths.substring(sposition, dPosition) + ": Backup file already exists");
            }
          }
          // replace first and last
          if ( replaceFirst && replaceLast && ReplaceAll )
          {
            String inputFilestring = new StringBuilder(inputFile).reverse().toString();
            String fromstring = new StringBuilder(from.get(j)).reverse().toString();
            String tostrin = new StringBuilder(to.get(j)).reverse().toString();
            outputFile = new StringBuilder(inputFilestring.replaceFirst("(?i)" + fromstring, tostrin)).reverse().toString();
            outputFile = outputFile.replaceFirst("(?i)" + from.get(j), to.get(j));
          }
          // replace first 
          else if ( replaceFirst && ReplaceAll )
          {
            outputFile = inputFile.replaceFirst("(?i)" + from.get(j), to.get(j));
          }
          // replace last 
          else if ( replaceLast && ReplaceAll )
          {
            // reverse the string replace
        	  String inputFilestring = new StringBuilder(inputFile).reverse().toString();
              String fromstring = new StringBuilder(from.get(j)).reverse().toString();
              String tostrin = new StringBuilder(to.get(j)).reverse().toString();
            outputFile = new StringBuilder(inputFilestring.replaceFirst("(?i)" + fromstring, tostrin)).reverse().toString();
          }
          // replace first and last
          else if ( replaceFirst && replaceLast )
          {
            outputFile = inputFile.replaceFirst(from.get(j), to.get(j));
            String inputFilestring = new StringBuilder(outputFile).reverse().toString();
            String fromstring = new StringBuilder(from.get(j)).reverse().toString();
            String tostrin = new StringBuilder(to.get(j)).reverse().toString();
            outputFile = new StringBuilder(inputFilestring.replaceFirst(fromstring, tostrin)).reverse().toString();
          }
          // replace first only
          else if ( replaceFirst )
          {
            outputFile = inputFile.replaceFirst(from.get(j), to.get(j));
          }
          // replace last only
          else if ( replaceLast )
          {
            String inputFilestring = new StringBuilder(inputFile).reverse().toString();
            String fromstring = new StringBuilder(from.get(j)).reverse().toString();
            String tostring = new StringBuilder(to.get(j)).reverse().toString();
            outputFile = new StringBuilder(inputFilestring.replaceFirst(fromstring, tostring)).reverse().toString();
          }
          // replace all
          else if ( ReplaceAll )
          {
            outputFile = inputFile.replaceAll("(?i)" + from.get(j), to.get(j));
          }
          // no opt 
          else
          {
            outputFile = inputFile.replaceAll(from.get(j), to.get(j));
          }

          // outputFile
          FileWriter fw = new FileWriter(fileNames.get(i));
          fw.write(outputFile);
          fw.close();
        } catch (Exception e)
        {
          // check file print an error
        	 File myFile = new File(fileNames.get(i));
             System.err.println("File " + myFile.getName() + " not found");
        }
      }
    }
  }

  private static String getFileContent(String filename) {
    String content = null;
    try {
      content = new String(Files.readAllBytes(Paths.get(filename)), charset);
    } catch (IOException e) {}
    return content;
  }

  private static void error()
  {
    System.err.println("Usage: Replace [-b] [-f] [-l] [-i] <from> <to> -- " + "<filename> [<filename>]*");
  }
}