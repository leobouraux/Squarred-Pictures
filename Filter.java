package MiniProjets.SquarredPictures.src;


/**
 * @author Nassim Ouzerouhane & Leo bouraux
 */
public final class Filter {

    /**
     * Get a pixel without accessing out of bounds
     * @param gray a HxW float array
     * @param row Y coordinate
     * @param col X coordinate
     * @return nearest valid pixel color
     */
    public static float at(float[][] gray, int row, int col) {
        int a= row;
        int b=col;
        if( a < 0){
            a+=1;
            
        }
        if(a>= gray.length){
            a-=1;
        }
        if(b<0){
            b+=1;
            
        }
        if ( b >= gray[0].length){
            b-=1;
        }
        
        return gray[a][b];
    }

    /**
     * Convolve a single-channel image with specified kernel.
     * @param gray a HxW float array
     * @param kernel a MxN float array, with M and N odd
     * @return a HxW float array
     */
    public static float[][] filter(float[][] gray, float[][] kernel) {
    	int constante=kernel.length/2;
    	int a= kernel.length;
    	int b= kernel[0].length; 
    	int row= gray.length;
    	int col= gray[0].length;
    	float c=0;
    	float[][] newArray= new float [row][col];
    	for( int i=0;i<row;i++)  {
    		for(int j=0;j<col;j++){


    			for(int e=0;e<a;e++){
    				for(int r=0;r<b;r++){
    					c+= kernel[e][r]* at(gray,e-constante+i,r-constante+j); 


    				}
    				newArray[i][j]=c; 
    			}

    			c=0;



    		}
    	}
    	return newArray;
    }

    /**
     * Smooth a single-channel image
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] smooth(float[][] gray) {
        int a= gray.length;
        int b= gray[0].length;
        float kernelS [][]= {{0.1f, 0.1f,0.1f},{0.1f, 0.2f,0.1f},{0.1f, 0.1f,0.1f}};
        float smoothed[][]= new float [a][b];
        smoothed= filter(gray,kernelS);
                return smoothed;
    }

    /**
     * Compute horizontal Sobel filter
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] sobelX(float[][] gray) {
        int a= gray.length;
        int b= gray[0].length;
        float kernelSx [][]= {{-1, 0,1},{-2, 0,2},{-1, 0,1}};
        float sobelx[][]= new float [a][b];
        sobelx=filter(gray,kernelSx);
                return sobelx;
    }

    /**
     * Compute vertical Sobel filter
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] sobelY(float[][] gray) {
        int a= gray.length;
        int b= gray[0].length;
        float kernelSy [][]= {{-1, -2,-1},{0, 0,0},{1, 2,1}};
        float sobely[][]= new float [a][b];
        sobely=filter(gray,kernelSy);
                return sobely;
    }

    /**
     * Compute the magnitude of combined Sobel filters
     * @param gray a HxW float array
     * @return a HxW float array
     */
    public static float[][] sobel(float[][] gray) {
        int a= gray.length;
        int b= gray[0].length;
        float[][]sobelx= sobelX(gray);
        float[][]sobely= sobelY(gray);
        float [][] sobel=new float[a][b];
        for (int i=0;i<a;i++){
            for (int j=0;j<b;j++){
                sobel[i][j]= (float) Math.sqrt(sobelx[i][j]*sobelx[i][j] + sobely[i][j]*sobely[i][j]);
            }
        }
                            return sobel;

    }
    

}
