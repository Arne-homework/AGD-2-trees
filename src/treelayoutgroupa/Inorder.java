package treelayoutgroupa;

import java.util.HashMap;
import java.util.List;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkNode;

import treelayoutgroupa.options.TreelayoutgroupaOptions;

public class Inorder {
    
    /**
     * The different states of a node.
     */
    private enum Status{
        FIRST_VISIT,
        LEFT_VISIT,
        RIGHT_VISIT
    }
    
    /**
     * The actual Inorder implementation.
     * Based on Algorithm 2 in [Wetherell, Shannon 1979]
     * 
     * @param layoutGraph The current graph.
     * @param nodes A list of all nodes in the graph.
     * @param root The root node of the given graph.
     * @param nodePlacingMonitor A monitor for placing the nodes.
     * @param nodeNodeSpacing The spacing between nodes.
     */
    public void inorder(ElkNode layoutGraph, List<ElkNode> nodes, ElkNode root, IElkProgressMonitor nodePlacingMonitor) {
        // Retrieving the padding to reduce the number of necessary arguments.
        ElkPadding padding = layoutGraph.getProperty(TreelayoutgroupaOptions.PADDING);
        double nodeNodeSpacing = layoutGraph.getProperty(TreelayoutgroupaOptions.SPACING_NODE_NODE);
        // Also retrieving the max height of the given tree.
        int maxHeight = layoutGraph.getProperty(InternalProperties.MAX_HEIGHT);
        
        // Declare and initialize necessary variables for the algorithm.
        // The next x-coordinate.
        int next_number = 1;
        // The current node, we want to start with the root.
        ElkNode current = root;
        // Map containing the (visited) status of each node.
        HashMap<ElkNode, Status> status = new HashMap<ElkNode, Status>();
        // Setting the status of the root.
        status.put(current, Status.FIRST_VISIT);
      
        // Let's start the actual algorithm.
        while (current != null) {
            // Check, which status the current node is in.
            switch (status.get(current)) {
            case FIRST_VISIT:
                // TODO: Implement me!
                break;
                
            case LEFT_VISIT:
                // TODO: Implement me!
                break;
                
            case RIGHT_VISIT:
                // TODO: Implement me!
                break;
                
                
            default:
                throw new IllegalArgumentException("Unexpected value: " + status.get(current));
            }
        }
    }

}
