package treelayoutgroupa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.emf.common.util.EList;

public class Lefty {
  

    /**
     * The actual Lefty implementation.
     * TODO: Complete comment!
     * 
     * @param nodes
     * @param maxHeight
     */
    public void lefty(ElkNode layoutGraph, List<ElkNode> nodes, double maxHeight, ElkPadding padding, IElkProgressMonitor nodePlacingMonitor) {
        
        // Declare and initialize necessary variables for the algorithm.
        // Array containing the next possible positions on the x-coordinate.
        int[] nextX = new int[(int) maxHeight + 1];
        // The current node, we want to start with the root.
        ElkNode current = nodes.get(0);
        // Map containing the (visited) status of each node.
        HashMap<ElkNode, Integer> status = new HashMap<ElkNode, Integer>();
        
        // Let's fill the next_x array with 1s.
        Arrays.fill(nextX, 1);
        
        // Let's set the status of each node to false.
        for (ElkNode n : nodes) {
            status.put(n, 0);
        }
        
        // The actual implementation based on Algorithm 1 in [Wetherell, Shannon 1979]
        while (current != null) {
            if (status.get(current) == 0) {
                // The current node has not been visited yet.
                nodePlacingMonitor.log("currX: " + nextX[(int) current.getHeight()]);
                
                // Set the node on the next possible x position from the array.
                current.setX(nextX[(int) current.getHeight()]);
                // Use a multiple of the nodes height as y position.
                current.setY(padding.top + current.getHeight());
                // Adjust the next possible x position accordingly to the algorithm rules.
                nextX[(int) current.getHeight()] += current.getWidth() +2;
                
                // Mark the current node as visited.
                status.put(current, 1);
                nodePlacingMonitor.logGraph(layoutGraph, current.getIdentifier() + " placed"); 
                
            } else if (1 <= status.get(current) && status.get(current) <= current.getChildren().size()) {
                // TODO: For some reason there are no children!
                // The node has already been visited and there are still children to visit.
                
                status.put(current, status.get(current)+1);
                
                // Check for the next not visited child.
                // There has to be at least one, because #children >= 1.
                EList<ElkNode> children = current.getChildren();
                for (ElkNode child : children) {
                    if (status.get(child) == status.get(current) -1) {
                        current = child;
                        break;
                    }
                }
                
                
            } else {
                // The node has already been visited more often than children it has.
                // current.status > current.#_of_children
                current = current.getParent();
                // Assuming the parent of the root is null.
            }
        }
    }
    
}
