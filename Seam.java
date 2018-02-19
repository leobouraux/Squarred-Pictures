package MiniProjets.SquarredPictures.src;

/**
 * @author Léopold Bouraux & Nassim Ouzerouhane
 */
import java.util.ArrayList;
public final class Seam {

    /**
     * Compute shortest path between {@code from} and {@code to}
     * @param successors adjacency list for all vertices
     * @param costs weight for all vertices
     * @param from first vertex
     * @param to last vertex
     * @return a sequence of vertices, or {@code null} if no path exists
     */
	public static int[] path(int[][] successors, float[] costs, int from, int to) {
        int row=successors.length;
        
        int[] bestPred= new int[row]; // Best predecessors tab for each vertices
        float[] distance= new float[row]; // distance to vertice
        for(int v=0; v<distance.length;v++){
            distance[v]=Float.POSITIVE_INFINITY;
        }
        distance[from]= costs[from];
        for (int n=0;n< successors[from].length;n++){
            distance[successors[from][n]]= costs[successors[from][n]];
            bestPred[successors[from][n]]=from;
        }
        distance[from]= costs[from];
        for(int i=0;i<row;i++){
            for (int j=0;j<successors[i].length;j++ ){
                if ((distance[successors[i][j]]) >= (distance[i]+ costs[successors[i][j]])){
                    distance[successors[i][j]]= distance[i]+ costs[successors[i][j]];
                    bestPred[successors[i][j]]=i;
                }
            }
        }
    	ArrayList<Integer>chemin= new ArrayList<Integer>();
    	chemin.add(to);
    	while(bestPred[to]!=from){
    	chemin.add(bestPred[to]);
    	to=bestPred[to];
    	}
    	chemin.add(from);
    	
    	int longueur = chemin.size();
    	int [] Final = new int [longueur];
    	for (int i =0; i<longueur ; i++) {
    		Final[i]=chemin.get(longueur-1-i);
    	}
    	return Final;
    }


    /**
     * Find best seam
     * @param energy weight for all pixels
     * @return a sequence of x-coordinates (the y-coordinate is the index)
     */
    public static int[] find(float[][] energy) {
        // TODO find
    	int longueurEnergy = energy.length ;
    	int largeurEnergy = energy[0].length ;
    	
    	int [][] successors = new int [longueurEnergy*largeurEnergy +2][] ;
    	float [] costs = new float [longueurEnergy*largeurEnergy +2] ;
    	
    	// le pixel initial (le premier après les pixels 
    	// formant l'image) est relié à la première ligne
    	successors[longueurEnergy*largeurEnergy]=new int[largeurEnergy];
    	
    	// j'enlève le départ et l'arrivée pour initialiser les coûts
    	costs[longueurEnergy*largeurEnergy-2] = 0 ;
    	
    	// on affecte au pixel initial ses successeurs = les pixels de la première ligne 
    	for (int i=0;i<largeurEnergy;++i){
    		successors[longueurEnergy*largeurEnergy][i]=i;
    	}
    	// le pixel (le second après les pixels formant
    	// l'image) n'a pas de successeur et vaut zéro
    	successors [longueurEnergy*largeurEnergy+1] = new int [] {};
    	costs[longueurEnergy*largeurEnergy-1] = 0 ;
    	
    	
    	for(int lignes=0 ; lignes<longueurEnergy ; lignes++){
    		for(int colonnes=0 ; colonnes<largeurEnergy ; colonnes++){
    			
    			// chaque pixel
    			successors[lignes*largeurEnergy+colonnes]=choix(energy,successors,lignes,colonnes);
    			costs[lignes*largeurEnergy+colonnes]=energy[lignes][colonnes];
    		}
    	}
    	//                                            ↗from                         to↗
    	int []find = path(successors,costs,longueurEnergy*largeurEnergy,longueurEnergy*largeurEnergy+1);
    	int []result = new int [find.length-2];
    	// conversion du tableau successor en dimension image
    	for(int i=1 ; i<find.length-1 ; i++){
    		result[i-1]=find[i]-(largeurEnergy)*(i-1);
    	}
    	return result;
    }
    
    public static int[]choix(float energy [][],int successors[][],int lignes,int colonnes){
    	int longueurEnergy = energy.length ;
    	int largeurEnergy = energy[0].length ;
    	// cas 1 : en bas
	   	if (lignes+1>longueurEnergy-1){
	   		int choix[]={longueurEnergy*largeurEnergy+1};
	   		return choix;
	   	}
	   	 // cas 2 : à gauche
    	if(colonnes-1<0){
	   		int [] choix={(lignes+1)*largeurEnergy+(colonnes),(lignes+1)*largeurEnergy+(colonnes+1)};
	   		return choix;
	   	}
    	// cas 3 : à droite
	   	if(colonnes+1>largeurEnergy-1){
	   		int [] choix ={(lignes+1)*largeurEnergy+(colonnes-1),(lignes+1)*largeurEnergy+(colonnes)};
	   		return choix;
	   	}
	    // cas 4 : cas normal
	   	else {
	   		int []choix = {(lignes+1)*largeurEnergy+(colonnes-1),(lignes+1)*largeurEnergy+(colonnes),(lignes+1)*largeurEnergy+(colonnes+1)};
	   	return choix;
	   	}
    }

    
    	    	
    

    /**
     * Draw a seam on an image
     * @param image original image
     * @param seam a seam on this image
     * @return a new image with the seam in blue
     */
    public static int[][] merge(int[][] image, int[] seam) {
        // Copy image
        int width = image[0].length;
        int height = image.length;
        int[][] copy = new int[height][width];
        for (int row = 0; row < height; ++row)
            for (int col = 0; col < width; ++col)
                copy[row][col] = image[row][col];

        // Paint seam in blue
        for (int row = 0; row < height; ++row)
            copy[row][seam[row]] = 0x0000ff;

        return copy;
    }

    /**
     * Remove specified seam
     * @param image original image
     * @param seam a seam on this image
     * @return the new image (width is decreased by 1)
     */
    public static int[][] shrink(int[][] image, int[] seam) {
        int width = image[0].length;
        int height = image.length;
        int[][] result = new int[height][width - 1];
        for (int row = 0; row < height; ++row) {
            for (int col = 0; col < seam[row]; ++col)
                result[row][col] = image[row][col];
            for (int col = seam[row] + 1; col < width; ++col)
                result[row][col - 1] = image[row][col];
        }
        return result;
    }

}

