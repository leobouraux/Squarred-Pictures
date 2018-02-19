package MiniProjets.SquarredPictures.src;

/**
 * @author Nassim Ouzerouhane & Leo bouraux
 */
public final class Color {

    /**
     * Returns red component from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(float, float, float)
     */
    public static float getRed(int rgb) {
        int red= rgb>>16;
        return red / 255.0f ;
    }

    /**
     * Returns green component from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getRed
     * @see #getBlue
     * @see #getRGB(float, float, float)
     */
    public static float getGreen(int rgb) {
        int a= rgb>>8;
        int green= a& 0b11111111;
        return green / 255.0f ;
    }

    /**
     * Returns blue component from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getRed
     * @see #getGreen
     * @see #getRGB(float, float, float)
     */
    public static float getBlue(int rgb) {
        int blue= rgb & 0b11111111;
        return blue / 255.0f;
    }
    
    /**
     * Returns the average of red, green and blue components from given packed color.
     * @param rgb 32-bits RGB color
     * @return a float between 0.0 and 1.0
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     * @see #getRGB(float)
     */
    public static float getGray(int rgb) {
       float gray = (getRed(rgb)+ getGreen(rgb)+ getBlue(rgb))/ 3.0f  ; // average value
        return gray;
    }

    /**
     * Returns packed RGB components from given red, green and blue components.
     * @param red a float between 0.0 and 1.0
     * @param green a float between 0.0 and 1.0
     * @param blue a float between 0.0 and 1.0
     * @return 32-bits RGB color
     * @see #getRed
     * @see #getGreen
     * @see #getBlue
     */
    public static int getRGB(float red, float green, float blue) {
       //Check if between 0 and 1
        if (red < 0) {
            red=0;
        }
        else if (red>1){
            red=1;
        }
        if (green < 0) {
            green=0;
        }
        else if (green>1){
            green=1;
        }
        if (blue < 0) {
            blue=0;
        }
        else if (blue>1){
            blue=1;
        }
        
        int Red= (int) (red*255);
        int Green= (int) (green*255);
        int Blue= (int) (blue* 255);
        int a= Red<<16;
        int b= Green<<8;
        int rgb= a+b+Blue;
        return rgb;
    }
    
    /**
     * Returns packed RGB components from given grayscale value.
     * @param red a float between 0.0 and 1.0
     * @param green a float between 0.0 and 1.0
     * @param blue a float between 0.0 and 1.0
     * @return 32-bits RGB color
     * @see #getGray
     */
    public static int getRGB(float gray){
        
        if (gray < 0) {
            gray=0;
        }
         if (gray>1){
            gray=1;
        }
        int x= (int) (gray * 255);
        int y= x<<8 ;
        int z= x<<16;
        int rgb= x+y+z;
        return rgb; 
        }
    /**
     * Converts packed RGB image to grayscale float image.
     * @param image a HxW int array
     * @return a HxW float array
     * @see #toRGB
     * @see #getGray
     */
    public static float[][] toGray(int[][] image) {
        int length= image.length;
        int height= image[0].length;
        float [][] gray=new float [length][height];
        for (int i=0;i<length;i++){
            for (int j=0;j<height;j++){
                gray[i][j]=getGray(image[i][j]);
            }
                
        }
            
        return gray;
    }

    /**
     * Converts grayscale float image to packed RGB image.
     * @param channels a HxW float array
     * @return a HxW int array
     * @see #toGray
     * @see #getRGB(float)
     */
    public static int[][] toRGB(float[][] gray) {
            int length= gray.length;
            int height= gray[0].length;
            int [][] rgb= new int [length][height];
            for (int i=0;i<length;i++){
                for (int j=0;j<height;j++){
                    rgb[i][j]=getRGB(gray[i][j]);
                     }
           }
                
            return rgb;
        }

}
