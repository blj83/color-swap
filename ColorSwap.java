/*
 * Name: Benjamin Johnson
 * Due Date: September 12, 2011
 * Class: CSCI1302
 * Professor: Kafka
 * 
 * This program is designed to take five initial arguments from the command line that the
 * user will input. These arguments are to be entered in this order: File(or picture to 
 * manipulate), name of the File after changes, type(either jpg or png), color to change,
 * and color to change to. This program will take the image file from the user and 
 * examine each of the pixels in the image and find the color of the pixel and compare 
 * it to the color entered by the user. If they are equal then the program will change the 
 * color in that specific pixel to the color specified by the user. The program will then
 * take the modified image and go through a process to save the image to a new image file
 * with the name and extension indicated by the user. Through the process of all of this
 * the program will verify the input and check for exceptions that may occur throughout 
 * the process. The end result of the program will be an image file that has the one color
 * changed to another color that was indicated by the user.
 */


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;


public class ColorSwap
{
		/*
		 * The main method of the program is used to check the parameters entered from 
		 * the command line and print error messages if needed.The program chould take
		 * five parameter from the command line and send an error message otherwise. It
		 * will also call the other local methods needed to implement the program. 
		 */
	
        public static void main(String[] args)
        {
                if (args.length != 5)
                {
                        System.out.print("Wrong number of arguments. Enter your arguments in this order.\n" +
                        		"File,desired name of modified file, file type, color to change, color to change to\n");
                        System.exit(0);
                }               

                if (!args[2].equals("jpg") && !args[2].equals("png"))
                {
                        System.out.println("You must enter either jpg or png. Try again.");
                        System.exit(0);
                }
                
                File file = new File(args[0]);
               
                BufferedImage modifiedImage = readImage(file , args[3] , args[4]);
                saveImage(modifiedImage , args[2] , args[1]);
                System.out.println("Done writing file!");
               
        }

        /*
         * The readImage method is a local method that takes three arguments. It will take
         * a File object, a String that represents color to be changed, and another string 
         * that represents another color to be changed. It will then call another local 
         * method to manipulate the color and make it to where it could be used to compare
         * to others. It will use methods from the BufferedImage and ImageIO class to go 
         * through the image pixel by pixel and change the color if needed. It will also 
         * check for IOException and give an appropriate error message if necessary.
         */
        
        private static BufferedImage readImage(File aFile , String color1 , String color2)
        {
                int colorRGB1 = changeColor(color1);
                int colorRGB2 = changeColor(color2);
                
                try
                {
                        BufferedImage bImage = ImageIO.read(aFile);
                        int height = bImage.getHeight();
                        int width = bImage.getWidth();
                        
                        /*
                         * A buffered image of the image file entered by the user will 
                         * enter these loops without any colors or anything else altered
                         * from the original. 
                         */
                        for (int i = 0; i < height ; i++)
                        {
                                for (int j = 0; j < width; j++)
                                {
                                        if ( bImage.getRGB(j , i) == colorRGB1)
                                        {
                                                bImage.setRGB(j , i , colorRGB2);
                                        }
                                }

                        }
                        
                        /*
                         * After the loop all of the pixels of the image will be exaimined 
                         * and changed if the RGB values of the color in the pixel is equal 
                         * to the RGB value of the color indicated by the user. 
                         */
                        
                        return bImage;
                }
                
                catch(IOException e)
                {
                        System.out.println("Please enter a file for the first argument and try again.");                        
                        System.exit(0);
                        return null;

                }
                

        }

        /*
         * The saveImage method will take three different arguments: a bufferedImage object,
         * a string representing the extension, and a string representing the name the user 
         * wants to name the new file. It will pass these arguments to a method of the 
         * ImageIO class called write that will save the new file back to an image file
         * with the extension indicated by the user. It will also check for IllegalArgumentException
         * and IOException and print the appropriate error message if needed.
         */

        private static void saveImage(BufferedImage image, String type, String name)
        {
	        	System.out.println("Writing file...");
        		try
	        	{	        			
        			File newFile = new File(name);	
	        		ImageIO.write(image, type , newFile);
	        	}
	        	catch (IllegalArgumentException e)
	        	{
	        		System.out.println("Usage, Enter in this order: " +
	        			"File,desired name of modified file, file type, color to change, color to change to.\n");
	        		System.exit(0);
	        	}
	        	catch (IOException e)
	        	{
	        		System.out.println("Usage, Enter in this order: " +
							"File,desired name of modified file, file type, color to change, color to change to.\n");
	        		System.exit(0);
	        	}

        }

        /*
         * The changeColor class takes one argument that is a String that represents
         * the color that needs to be changed. It uses methods from the Color, String,
         * and Field class to manipulate the string and convert it to the RGB value
         * of the color indicated. The method will check for NoSuchFieldException,
         * IllegalArgumentException, IllegalAccessException, SecurityException, and
         * ClassNotFoundException and print an appropriate message if needed.
         */
        
        private static int changeColor(String aString)
        {
    			java.awt.Color color; 
    			String begString = aString.substring(0, 6);
    			if (!begString.equalsIgnoreCase("Color."))
    			{
    				System.out.println("Please enter a color in the format \"Color.coloryouwant\"");
    				System.exit(0);
    			}
    			String colorString = aString.substring(6);
        		
    			try
                {
	                  	Field colorField = Class.forName("java.awt.Color").getField(colorString);                     
	                  	color = (java.awt.Color) colorField.get(null);
                     		int RGB = color.getRGB();
                     		return RGB;
                }
                catch (NoSuchFieldException e)
                {
                        System.out.println("Please enter a color in the format \"Color.coloryouwant\"");
                        System.exit(0);
                        return 0;
                }
                catch (IllegalArgumentException e) 
                {
	                System.out.println("Please enter a color in the format \"Color.coloryouwant\"");
	                System.exit(0);
	                return 0;
		} 
                catch (IllegalAccessException e)
                {
	                System.out.println("Please enter a color in the format \"Color.coloryouwant\"");
	                System.exit(0);
	                return 0;
		} 
                catch (SecurityException e) 
                {
                	System.out.println("Please enter a color in the format \"Color.coloryouwant\"");
                	System.exit(0);
                	return 0;
		} 
                catch (ClassNotFoundException e) 
                {
                	System.out.println("Please enter a color in the format \"Color.coloryouwant\"");
                	System.exit(0);
                	return 0;
		}
        	
        }
}

