package treelayoutgroupa;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.swing.text.AsyncBoxView.ChildLocator;

import org.eclipse.elk.core.math.ElkPadding;
import org.eclipse.elk.core.options.CoreOptions;
import org.eclipse.elk.core.util.IElkProgressMonitor;
import org.eclipse.elk.graph.ElkEdge;
import org.eclipse.elk.graph.ElkNode;
import org.eclipse.elk.graph.util.ElkGraphUtil;
import org.eclipse.emf.common.util.EList;

import treelayoutgroupa.options.TreelayoutgroupaOptions;

public class Lefty {
  

    /**
     * The actual Lefty implementation.
     * 
     * @param layoutGraph The current graph.
     * @param nodes A list of all nodes in the graph.
     * @param nodePlacingMonitor A monitor for placing the nodes.
     */
    public void lefty(ElkNode layoutGraph, List<ElkNode> nodes, IElkProgressMonitor nodePlacingMonitor) {
        // Retrieving the padding to reduce the number of necessary arguments.
        ElkPadding padding = layoutGraph.getProperty(TreelayoutgroupaOptions.PADDING);
        // Also retrieving the max height of the given tree.
        int maxHeight = layoutGraph.getProperty(InternalProperties.MAX_HEIGHT);
        
        
        // Declare and initialize necessary variables for the algorithm.
        // Array containing the next possible positions on the x-coordinate.
        int[] nextX = new int[maxHeight+1];
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
                int currentLevel = current.getProperty(InternalProperties.NODE_LEVEL);
                nodePlacingMonitor.log("currX: " + nextX[currentLevel]);
                
                // Set the node on the next possible x position from the array.
                current.setX(nextX[currentLevel]);
                // Use a multiple of the nodes height as y position.
                current.setY(currentLevel * (padding.top + current.getHeight()));
                // Adjust the next possible x position accordingly to the algorithm rules.
                nextX[currentLevel] += current.getWidth() +2;
                
                // Mark the current node as visited.
                status.put(current, 1);
                nodePlacingMonitor.logGraph(layoutGraph, current.getIdentifier() + " placed"); 
                
            } else if (1 <= status.get(current) && status.get(current) <= current.getOutgoingEdges().size()) {
                // Hint: getChildren() is not used in a layered graph.
                // The node has already been visited and there are still children to visit.
                
                status.put(current, status.get(current)+1);
                
                // Check for the next not visited child.
                // There has to be at least one, because #children >= 1.
                EList<ElkEdge> outgoingEdges = current.getOutgoingEdges();
                for (ElkEdge elkEdge : outgoingEdges) {
                    // (current -> child) => The child is the target of the outgoing edge. We only have 2-uniform hypergraph (each edge connects exactly 2 nodes)
                    ElkNode child = ElkGraphUtil.connectableShapeToNode(elkEdge.getTargets().get(0));
                    // TODO: This is different to the algorithm mentioned in [Wetherell, Shannon 1979]! Could an error occur here?
                    if (status.get(child) == 0 || status.get(child) == status.get(current)-1) {
                        // This child has either not been visited or needs to be re-positioned.
                        current = child;
                        break;
                    }
                }
                
            } else {
                // The node has already been visited more often than children it has.
                // current.status > current.#_of_children
                // We backtrack to the parent node.
                // Hopefully there is at most one incomming edge.
                EList<ElkEdge> incommingEdges = current.getIncomingEdges();
                
                // We only have to go on, if there are still children.
                if (!incommingEdges.isEmpty()) {
                    ElkEdge incommingEdge = incommingEdges.get(0);
                    // (parent -> current) => The parent is the source of the incomming edge.
                    if (incommingEdge != null) {
                        current = ElkGraphUtil.connectableShapeToNode(incommingEdge.getSources().get(0));
                    }
                } else {
                    // Assuming the parent of the root is null.
                    current = null;
                }
            }
        }
    }
    
}
