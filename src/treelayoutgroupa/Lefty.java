package treelayoutgroupa;

import java.util.Arrays;
import java.util.List;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkNode;

public class Lefty extends OurAlgorithms {
    
    /**
     * The class for our Lefty implementation.
     * 
     * @param layoutGraph The root of the graph.
     * @param progressMonitor The monitor to log the progress of the algorithm.
     * @param nodePlacingMonitor The monitor to log the placing of the nodes.
     */
    public Lefty(ElkNode layoutGraph, IElkProgressMonitor progressMonitor, IElkProgressMonitor nodePlacingMonitor) {
        super(layoutGraph, progressMonitor, nodePlacingMonitor);
    }

    /**
     * The actual Lefty implementation.
     * 
     * @param nodes
     * @param padding
     * @param currX
     * @param currY
     * @param nodeNodeSpacing
     * @param nodePlacingMonitor
     */
    public void lefty(List<ElkNode> nodes, ElkPadding padding, double currX, double currY, double nodeNodeSpacing, double maxHeight) {
        nodePlacingMonitor.logGraph(layoutGraph, "Starting Lefty algorithm.");
        
        // Declare and initialize necessary variables for the algorithm.
        
        // Array containing the next possible positions on the x-coordinate.
        int[] nextX = new int[(int) maxHeight];
        // Pointer to the current node. Starting with the root.
        ElkNode currentNode = layoutGraph;
        
        // Let's fill the next_x array with 1s.
        Arrays.fill(nextX, 1);
 
        /*for (ElkNode node : nodes) {
            // Set the node's coordinates
            node.setX(currX);
            node.setY(padding.top);
            
            // Advance the coordinates
            currX += node.getWidth() + nodeNodeSpacing;
            currY = Math.max(currY, padding.top + node.getHeight());
            nodePlacingMonitor.log("currX: " + currX);
            nodePlacingMonitor.logGraph(layoutGraph, node.getIdentifier() + " placed");
        }
        
        if (!nodes.isEmpty()) {
            currX -= nodeNodeSpacing;
        }*/
        
        // TODO: Implement the actual algorithm in here!
    }
}
